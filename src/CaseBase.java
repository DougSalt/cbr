/**
 * CaseBase.java, uk.ac.hutton.caber
 *
 * Copyright (C) The James Hutton Institute 2017
 *
 * This file is part of caber
 *
 * caber is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * caber is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * caber. If not, see <http://www.gnu.org/licenses/>.
 */


import org.nlogo.api.*;
import org.nlogo.agent.*;

import java.io.IOException;
import java.time.*;

import org.nlogo.core.*;
import org.nlogo.api.Reporter;
import java.util.*;
import java.util.stream.Collectors;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * <!-- CaseBase -->
 *
 * A collection of cases.
 *
 * @author Gary Polhill
 */
public class CaseBase implements Collection<Case> {
	private ArrayList<Case> base;

	private int maxSize = Integer.MAX_VALUE;

	private Double tickInfimum = -1.0;

	private AnonymousReporter caseLambda = null;

    public static final String LESS_THAN = "less than";
    public static final String GREATER_THAN = "greater than";
    public static final String INCOMPARABLE = "incomparable";
    public static final String EQUAL = "equal";
	/*
	 * Have a think about tick list and whether that could be maintained.
	 * At each element in the tick list we'd record the last + 1 element in the
	 * base that is associated with that tick. Maybe it would need to be a map.Finished Ki
	 * The maintenance around removing things would be the issue -- especially
	 * removing a specific case (which could be disabled).
	 */

	/**
	 * <!-- CaseBase constructor -->
	 *
	 */
	public CaseBase() {
		base = new ArrayList<Case>();
	}

    public void addCase(Case c) {
		base.ensureCapacity(base.size() + 1);
        base.add(c);
    }

	public void addCases(Collection<? extends Case> cc) {
		base.ensureCapacity(base.size() + cc.size());
		for(Case c : cc) {
			addCase(c);
		}
	}

	public void addCases(Case[] cc) {
		base.ensureCapacity(base.size() + cc.length);
		for(Case c : cc) {
			addCase(c.clone());
		}
	}

	public Case getCase(int i) {
		return base.get(i);
	}

	public Collection<Case> forActivity(Object activity) {
		LinkedList<Case> list = new LinkedList<Case>();

		for(Case c : base) {
			if(c.getActivity().equals(activity)) {
				list.add(c);
			}
		}

		return list;
	}

	public void imposeSizeLimit(int size) {
		while(base.size() > size) {
			base.remove(0);
		}
	}

	@Override
	public int size() {
		return base.size();
	}

	@Override
	public boolean isEmpty() {
		return base.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return base.contains(o);
	}

	public boolean containsCase(Case c) {
		// Nothing for it but to iterate and check equality
		for(Case myc : this) {
			if(myc.equals(c)) return true;
		}
		return false;
	}

	@Override
	public Iterator<Case> iterator() {
		return base.iterator();
	}

	@Override
	public Object[] toArray() {
		return base.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return base.toArray(a);
	}

	@Override
	public boolean add(Case e) {
		addCase(e);
		return true;
	}

	@Override
	public boolean remove(Object o) {
		return base.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return base.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Case> c) {
		addCases(c);
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return base.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return base.retainAll(c);
	}

	@Override
	public void clear() {
		base.clear();
	}

	@Override
	public boolean equals(Object other) {
		if(other instanceof CaseBase) {
			// Two case bases are equal if they both contain equivalent cases
			// This is an expensive algorithm...

			CaseBase other_cb = (CaseBase)other;

			for(Case c : this) {
				if(!other_cb.containsCase(c)) {
					return false;
				}
			}

			for(Case c : other_cb) {
				if(!containsCase(c)) {
					return false;
				}
			}

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("[");

		for(Case c : this) {
			if(buf.length() > 1) {
				buf.append("; ");
			}
			buf.append(c.toString());
		}

		buf.append("]");
		return buf.toString();
	}

	public int getMaxSize()
	{
		return maxSize;
	}

	public void setMaxSize(int maxSize)
	{
		this.maxSize = maxSize;
	}

    public Double getTickInfimum() {
        return tickInfimum;
    }

    public void setTickInfimum(Double tickInfimum) {
        this.tickInfimum = tickInfimum;
    }

    public void forgetCasesOlderThanTickInfimum() {
        if (tickInfimum >= 0)
            base.removeIf(c -> c.getTime() < tickInfimum);
    }

	public AnonymousReporter getCaseLambda()
	{
		return caseLambda;
	}

	public void setCaseLambda(AnonymousReporter caseLambda)
	{
		this.caseLambda = caseLambda;
	}

	public boolean isSame(Argument[] args, Context context)
		throws ExtensionException
	{
	    CaseBase cbr = (CaseBase) args[0].get();
        Case src = (Case) args[1].get();
        Case obj = (Case) args[2].get();
        Case ref = (Case) args[3].get();
        Object[] convertedArgs = new Object[]{ cbr, src, obj, ref};
		Object result = caseLambda.report(context, convertedArgs);
		return EQUAL.equalsIgnoreCase(result.toString());
	}

	public boolean isCloser(Argument[] args, Context context)
		throws ExtensionException
	{
        CaseBase cbr = (CaseBase) args[0].get();
        Case src = (Case) args[1].get();
        Case obj = (Case) args[2].get();
        Case ref = (Case) args[3].get();
        Object[] convertedArgs = new Object[]{ cbr, src, obj, ref};
		Object result = caseLambda.report(context,convertedArgs);
		return LESS_THAN.equalsIgnoreCase(result.toString());
	}

	public boolean isComparable(Argument[] args, Context context)
		throws ExtensionException
	{
	    CaseBase cbr = (CaseBase) args[0].get();
        Case src = (Case) args[1].get();
        Case obj = (Case) args[2].get();
        Case ref = (Case) args[3].get();
        Object[] convertedArgs = new Object[]{ cbr, src, obj, ref};
		Object result = caseLambda.report(context,convertedArgs);
		return !GREATER_THAN.equalsIgnoreCase(result.toString());
	}
    public static Set convertToSet(String string) {

      // Result hashset
      Set resultSet = new HashSet();

      for (int i = 0; i < string.length(); i++) {
          resultSet.add(new Character(string.charAt(i)));
      }

      // Return result
      return resultSet;
    }
	public Object defaultLambda(Object src, Object obj, Object ref)
		throws ExtensionException
	{
        Object result = Nobody$.MODULE$;

        Object a;
        if (src instanceof Case) {
            a = ((Case) src).getState();
        } else {
            a = src;
        }

        Object b;
        if (obj instanceof Case) {
            b = ((Case) obj).getState();
        } else {
            b = obj;
        }

        if (a.getClass() !=  b.getClass())
            if (! (a instanceof ArrayAgentSet || a instanceof TreeAgentSet) &&
                ! (b instanceof ArrayAgentSet || b  instanceof TreeAgentSet) ) { 
                return new Incomparable();
            }


        Object r;
        if (ref instanceof Case) {
            r = ((Case) ref).getState();
        } else {
            r = ref;
        }

        if (a.getClass() !=  r.getClass())
            if (! (r instanceof ArrayAgentSet || r instanceof TreeAgentSet) ) {
                return new Incomparable();
            }

        if (a instanceof Double) {
            Double rMinusA = (Double) r - (Double) a;
            Double rMinusB = (Double) r - (Double) b;
           if (Math.abs((double)rMinusA) == Math.abs((double)rMinusB) ) {
                return new Equal();
            } else if (Math.abs(rMinusA) < Math.abs(rMinusB)) {
                return new LessThan();
            } else {
                return new GreaterThan();
            }
        } else if (a instanceof LogoList) {

            List aList = ((LogoList) a).toJava();
            List bList = ((LogoList) b).toJava();
            List rList = ((LogoList) r).toJava();

            Set anrList = (Set) aList.stream().
                distinct().
                filter(rList::contains).
                collect(Collectors.toSet());
            Set bnrList = (Set) bList.stream().
                distinct().
                filter(rList::contains).
                collect(Collectors.toSet());

            if (anrList.isEmpty() && bnrList.isEmpty())
                return new Incomparable();

            if (anrList.size() > bnrList.size())
                return new LessThan();

            if (anrList.size() < bnrList.size())
                return new GreaterThan();

            if (! anrList.equals(bnrList))
                return new LessThan();

            return new Equal();

         } else if (a instanceof TreeAgentSet || a instanceof ArrayAgentSet) {

            List aList;
            if (a instanceof TreeAgentSet) {
                aList = ((TreeAgentSet) a).toLogoList().toJava();
            } else {
                aList = ((ArrayAgentSet) a).toLogoList().toJava();
            }

            List bList;
            if (b instanceof TreeAgentSet) {
                bList = ((TreeAgentSet) b).toLogoList().toJava();
            } else {
                bList = ((ArrayAgentSet) b).toLogoList().toJava();
            }

            List rList;
            if (r instanceof TreeAgentSet) {
                rList = ((TreeAgentSet) r).toLogoList().toJava();
            } else {
                rList = ((ArrayAgentSet) r).toLogoList().toJava();
            }

            Set anrList = (Set) aList.stream().
                distinct().
                filter(rList::contains).
                collect(Collectors.toSet());
            Set bnrList = (Set) bList.stream().
                distinct().
                filter(rList::contains).
                collect(Collectors.toSet());

            if (anrList.isEmpty() && bnrList.isEmpty())
                return new Incomparable();

            if (anrList.size() > bnrList.size())
                return new LessThan();

            if (anrList.size() < bnrList.size())
                return new GreaterThan();

            if (! anrList.equals(bnrList))
                return new LessThan();

            return new Equal();

         } else if (a instanceof ArrayAgentSet) {

            List aList = ((ArrayAgentSet) a).toLogoList().toJava();
            List bList = ((ArrayAgentSet) b).toLogoList().toJava();
            List rList = ((ArrayAgentSet) r).toLogoList().toJava();

            Set anrList = (Set) aList.stream().
                distinct().
                filter(rList::contains).
                collect(Collectors.toSet());
            Set bnrList = (Set) bList.stream().
                distinct().
                filter(rList::contains).
                collect(Collectors.toSet());

            if (anrList.isEmpty() && bnrList.isEmpty())
                return new Incomparable();

            if (anrList.size() > bnrList.size())
                return new LessThan();

            if (anrList.size() < bnrList.size())
                return new GreaterThan();

            if (! anrList.equals(bnrList))
                return new LessThan();

            return new Equal();

        } else if (a instanceof String) {

            Set aSet = convertToSet((String) a);
            Set bSet = convertToSet((String) b);
            Set rSet = convertToSet((String) r);

            Set anrSet = new HashSet(aSet);
            anrSet.retainAll(rSet);

            Set bnrSet = new HashSet(bSet);
            bnrSet.retainAll(rSet);
            if (anrSet.isEmpty() && bnrSet.isEmpty())
                return new Incomparable();

            if ( anrSet.size() > bnrSet.size() )
                return new LessThan();

            if ( anrSet.size() < bnrSet.size() )
                return new GreaterThan();

            if (! anrSet.equals(bnrSet))
                return new LessThan();

            return new Equal();
        }

        return result;

	}
}
