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


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

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

	protected PartiallyComparableLambda<Case> caseLambda = null;

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
		caseLambda = new DefaultLambda();
	}
	
	public CaseBase(PartiallyComparableLambda<Case> caseLambda) {
		base = new ArrayList<Case>();
		this.caseLambda = caseLambda;
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

	public PartiallyComparableLambda<Case> getCaseLambda()
	{
		return caseLambda;
	}

	public void setCaseLambda(PartiallyComparableLambda<Case> caseLambda)
	{
		this.caseLambda = caseLambda;
	}

	public boolean isSame(Case src, Case obj, Case ref)
	{
        return caseLambda.cmp(src, obj, ref) == PartiallyOrderedComparison.EQUAL;
	}

	public boolean isCloser(Case src, Case obj, Case ref)
	{
		return caseLambda.cmp(src, obj, ref) == PartiallyOrderedComparison.LESS_THAN;
	}

	public boolean isComparable(Case src, Case obj, Case ref)
	{
		return caseLambda.cmp(src, obj, ref) != PartiallyOrderedComparison.INCOMPARABLE;
	}
	
	public Collection<Case> getStateMatches(Object state) {
		return getStateMatches(state, caseLambda);
	}
	
	public Collection<Case> getStateMatches(Case ref) {
		return getStateMatches(ref, caseLambda);
	}
	
	public Collection<Case> getStateMatches(Object state, PartiallyComparableLambda<Case> lambda) {
		return getStateMatches(new Case(state, null, null, 0.0), lambda);
	}
	
	public Collection<Case> getStateMatches(Case ref, PartiallyComparableLambda<Case> lambda) {
		Set<Case> matches = new HashSet<Case>();
		for(Case c: base) {
			if(matches.isEmpty()) {
				matches.add(c);
			}
			else {
				Set<Case> rm = new HashSet<Case>();
				for(Case d: matches) {
					PartiallyOrderedComparison pcmp = lambda.cmp(c, d, ref);
					if(pcmp == PartiallyOrderedComparison.LESS_THAN) {
						// c is closer to ref than d: remove d
						rm.add(d);
					}
					if(pcmp != PartiallyOrderedComparison.GREATER_THAN) {
						// c is as close to ref as d, nearer to ref than d, or incomparable
						matches.add(c);
					}
				}
				matches.removeAll(rm);
			}
		}
		return matches;
	}
 }
