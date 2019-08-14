/**
 * 
 */
import java.io.IOException;

import org.nlogo.api.ExtensionException;
import org.nlogo.core.ExtensionObject;
import org.nlogo.core.LogoList;

/**
 * @author GP40285
 *
 */
public class NetLogoCaseBase extends CaseBase implements ExtensionObject {

	private final boolean fixedStateFeatures;
	private final boolean fixedOutcomeFeatures;
	
	private final long id;
	private static long next = 0;

	private static final int CASE_LIST_SIZE = 3;
	private static final int CASE_LIST_STATE_IX = 0;
	private static final int CASE_LIST_ACTIVITY_IX = 1;
	private static final int CASE_LIST_OUTCOME_IX = 2;
	private static final int CASE_LIST_TIME_IX = 3;
	
	public static final String typeName = "base";
	
	public NetLogoCaseBase() {
		fixedStateFeatures = false;
		fixedOutcomeFeatures = false;
		id = next();
	}
	
	public NetLogoCaseBase(boolean fixedStateFeatures, boolean fixedOutcomeFeatures) {
		this.fixedStateFeatures = fixedStateFeatures;
		this.fixedOutcomeFeatures = fixedOutcomeFeatures;
		id = next();
	}
	
//	public NetLogoCaseBase(NetLogoFeatureSet stateFeatures, NetLogoFeatureSet outcomeFeatures) {
//		id = next();
//		if(stateFeatures != null) {
//			this.stateFeatures = stateFeatures;
//			fixedStateFeatures = true;
//		}
//		else {
//			fixedStateFeatures = false;
//		}
//		if(outcomeFeatures != null) {
//			this.outcomeFeatures = outcomeFeatures;
//			fixedOutcomeFeatures = true;
//		}
//		else {
//			fixedOutcomeFeatures = false;
//		}
//	}
	
	public NetLogoCaseBase(CaseBase value) {
		this();
		for(Case aCase: value) {
			this.add(new NetLogoCase(aCase));
		}
	}
	
//	public NetLogoCaseBase(String value) throws ClassNotFoundException, IOException, CaseBaseException {
//		this(CaseBase.parseString(value));
//	}

//	public void addCase(LogoList case_list) throws ExtensionException {
//		if(case_list.length() != CASE_LIST_SIZE) {
//			throw new ExtensionException("A case should be initialized from a list of length " + CASE_LIST_SIZE + ": " + case_list);
//		}
//
//		Object state = case_list.get(CASE_LIST_STATE_IX);
//		if(!(state instanceof LogoList)) {
//			throw new ExtensionException("A state in a case should be initialized from a list: " + state);
//		}
//
//		Object activity = case_list.get(CASE_LIST_ACTIVITY_IX);
//
//		Object outcome = case_list.get(CASE_LIST_OUTCOME_IX);
//		if(!(outcome instanceof LogoList)) {
//			throw new ExtensionException("An outcome in a case should be initialized from a list: " + outcome);
//		}
//
//        Object ticks = case_list.get(CASE_LIST_TIME_IX);
//        if (!(ticks instanceof Number)) {
//			throw new ExtensionException("A time in a case should be a number: " + ticks);
//        }
//
//
//		NetLogoFeatureValueSet state_fsv = (stateFeatures == null) ? NetLogoFeatureValueSet.manifest((LogoList)state)
//				: NetLogoFeatureValueSet.manifest((LogoList)state, stateFeatures);
//		
//		if(stateFeatures == null && fixedStateFeatures) {
//			stateFeatures = state_fsv.getFeatureSet();
//		}
//		
//		NetLogoFeatureValueSet outcome_fsv = (outcomeFeatures == null) ? NetLogoFeatureValueSet.manifest((LogoList)outcome)
//				: NetLogoFeatureValueSet.manifest((LogoList)outcome, outcomeFeatures);
//		
//		if(outcomeFeatures == null && fixedOutcomeFeatures) {
//			outcomeFeatures = outcome_fsv.getFeatureSet();
//		}
//		
//		super.add(new NetLogoCase(state_fsv, activity, outcome_fsv, ((Number)ticks).doubleValue()));
//	}
	
	private static final long next() {
		return ++next;
	}

//	public void setStateFeatures(NetLogoFeatureSet state_features) throws ExtensionException {
//		if(stateFeatures != null && !stateFeatures.equals(state_features)) {
//			throw new ExtensionException("You can only set the state-features of a case base once (current setting " + stateFeatures
//					+ " -- attempted setting " + state_features + ")");
//		}
//		if(stateFeatures == null) stateFeatures = state_features;
//	}

//	public void setOutcomeFeatures(NetLogoFeatureSet outcome_features) throws ExtensionException {
//		if(outcomeFeatures != null && !outcomeFeatures.equals(outcome_features)) {
//			throw new ExtensionException("You can only set the outcome-features of a case base once (current setting " + outcomeFeatures
//					+ " -- attempted setting " + outcome_features + ")");
//		}
//		if(outcomeFeatures == null) stateFeatures = outcome_features;
//	}

	public LogoList asLogoList() {
		LogoList list = LogoList.Empty();

		for(Case c : this) {
			NetLogoCase nc = (NetLogoCase)c;

			list = list.lput(nc.asLogoList());
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.nlogo.core.ExtensionObject#dump(boolean, boolean, boolean)
	 */
	@Override
	public String dump(boolean readable, boolean exporting, boolean reference) {
		StringBuilder buff = new StringBuilder();
		if(exporting) {
			buff.append(id);
			if(!reference) {
				buff.append(": ");
			}
		}
		if(!(reference && exporting)) {
			buff.append(this.toString());
		}

		return buff.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.nlogo.core.ExtensionObject#getExtensionName()
	 */
	@Override
	public String getExtensionName() {
		return CBRExtension.EXTENSION_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.nlogo.core.ExtensionObject#getNLTypeName()
	 */
	@Override
	public String getNLTypeName() {
		return typeName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.nlogo.core.ExtensionObject#recursivelyEqual(java.lang.Object)
	 */
	@Override
	public boolean recursivelyEqual(Object obj) {
		// N.B. Expensive computationally especially for large case bases -- O(N^2)
		return super.equals(obj);
	}
	
}
