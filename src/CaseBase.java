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

import java.io.IOException;
import java.time.*;
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

    public static final String YES = "yes";
    public static final String NO = "no";
    public static final String INVALID = "invalid";
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

//	public int addCase(Case c) {
//		if (!((base.size() + 1) == maxSize) && !base.add(c))
//		{
//			throw new RuntimeException("Failed to add case " + c + " to case base");
//		}
//		return base.size() - 1;
//	}

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

//	public Collection<Case> matches(Feature<?>.Value... values) {
//		LinkedList<Case> list = new LinkedList<Case>();
//
//		for(Case c : base) {
//			if(c.matches(values)) {
//				list.add(c);
//			}
//		}
//
//		return list;
//	}

	public Collection<Case> forActivity(Object activity) {
		LinkedList<Case> list = new LinkedList<Case>();

		for(Case c : base) {
			if(c.getActivity().equals(activity)) {
				list.add(c);
			}
		}

		return list;
	}

//	public Collection<Case> outcomeMatches(Feature<?>.Value... values) {
//		LinkedList<Case> list = new LinkedList<Case>();
//
//		for(Case c : base) {
//			if(c.outcomeMatches(values)) {
//				list.add(c);
//			}
//		}
//
//		return list;
//	}

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

//	public static CaseBase parseString(String cbstr) throws ClassNotFoundException, IOException, CaseBaseException {
//		if(cbstr.startsWith("[") && cbstr.endsWith("]")) {
//			CaseBase cb = new CaseBase();
//			cbstr = cbstr.substring(1, cbstr.length() - 1);
//			String[] cases = cbstr.split("; ");
//			for(String case_str: cases) {
//				cb.add(Case.parseString(case_str));
//			}
//			return cb;
//		}
//		else {
//			throw new CaseBaseException("Invalid case base string: \"" + cbstr + "\" -- must start with \"[\" and end with \"]\"");
//		}
//	}

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
		Object result = caseLambda.report(context,convertedArgs);
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
		return YES.equalsIgnoreCase(result.toString());
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
		return !NO.equalsIgnoreCase(result.toString());
	}
}
