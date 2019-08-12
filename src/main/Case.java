/**
 * Case.java, uk.ac.hutton.caber
 *
 * Copyright (C) The James Hutton Institute 2016
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


import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <!-- Case -->
 * 
 * A case is a single experience, comprising a {@link State} of the world, an
 * action taken, and an outcome
 * 
 * @author Gary Polhill
 */
public class Case {

	private final FeatureSet.Value state;

	private final Object activity;

	private final FeatureSet.Value outcome;

	private LocalDateTime time;

	public Case(FeatureSet.Value state, Object activity, FeatureSet.Value outcome, LocalDateTime time) {
		this.state = state;
		this.activity = activity;
		this.outcome = outcome;
		this.time = time;
	}

	public FeatureSet.Value getState() {
		return state;
	}

	public Object getActivity() {
		return activity;
	}

	public FeatureSet.Value getOutcome() {
		return outcome;
	}

	public LocalDateTime getTime()
	{
		return time;
	}

	public void setTime(LocalDateTime time)
	{
		this.time = time;
	}

	public boolean matches(Feature<?>.Value... queries) {
		for(Feature<?>.Value query : queries) {
			if(!state.matches(query)) {
				return false;
			}
		}
		return true;
	}

	public boolean outcomeMatches(Feature<?>.Value... queries) {
		for(Feature<?>.Value query : queries) {
			if(!outcome.matches(query)) {
				return false;
			}
		}
		return true;
	}

	public POCmp cmp(FeatureSet.Value other) {
		return POCmp.NO;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("<");

		buf.append(state.toString());
		buf.append(" | ");
		buf.append(activity.getClass().getName());
		buf.append("(");
		buf.append(activity.toString());
		buf.append(")");
		buf.append(" | ");
		buf.append(outcome.toString());
		buf.append(" | ");
		buf.append(time.toString());

		buf.append(">");

		return buf.toString();
	}
	
	public String dump() throws IOException {
		StringBuffer buf = new StringBuffer("<");
		buf.append(state.toString());
		buf.append(" | ");
		buf.append(StringInitializer.toInitializableString(activity));
		buf.append(" | ");
		buf.append(outcome.toString());
		buf.append(" | ");
		buf.append(time.toString());
		buf.append(">");
		return buf.toString();
	}

	public static Case parseString(String cstr) throws ClassNotFoundException, IOException, CaseBaseException {
		if(cstr.startsWith("<") && cstr.endsWith(">")) {
			cstr = cstr.substring(1, cstr.length() - 1);
			String[] parts = cstr.split(" \\| ");
			FeatureSet.Value state = FeatureSet.parseValueString(parts[0]);
			Object activity = StringInitializer.parseNewInstance(parts[1]);
			FeatureSet.Value outcome = FeatureSet.parseValueString(parts[2]);
			LocalDateTime time = LocalDateTime.parse(parts[3].trim());
			return new Case(state, activity, outcome, time);
		}
		else {
			throw new CaseBaseException("Invalid case string: \"" + cstr + "\" -- must start with \"<\" and end with \">\"");
		}
	}

	@Override
	public boolean equals(Object other) {
		if(other == null) {
			return false;
		}
		if(other instanceof Case) {
			Case other_case = (Case)other;

			if(!state.equals(other_case.state)) return false;
			if(!activity.equals(other_case.activity)) return false;
			if(!outcome.equals(other_case.outcome)) return false;

			return true;
		}
		else {
			return false;
		}
	}
}
