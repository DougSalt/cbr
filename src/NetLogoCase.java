/**
 * 
 */
import java.util.*;

import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.core.ExtensionObject;
import org.nlogo.core.LogoList;

/**
 * @author GP40285
 *
 */
public class NetLogoCase extends Case implements ExtensionObject {
	
	private final long id;
	private static long next = 0;

	public static final String typeName = "case";
	
	public NetLogoCase(Object state, Object activity, Object outcome, Double ticks) {
		super(state, activity, outcome, ticks);
		this.id = next();
	}
	
	public NetLogoCase(Case value) {
		super(value.getState(), value.getActivity(), value.getOutcome(), value.getTime());
		this.id = next();
	}
	
//	public NetLogoCase(String value) throws ClassNotFoundException, IOException, CaseBaseException {
//		this(Case.parseString(value));
//	}
		
	public LogoList asLogoList() {
		LogoList list = LogoList.Empty();
		
		list = list.lput(getState());
		list = list.lput(getActivity());
		list = list.lput(getOutcome());
		list = list.lput(getTime());
		
		return list;
	}
	
	public static NetLogoCase fromLogoList(LogoList list, Context context) throws ExtensionException {
		if(list.length() < 3) {
			throw new ExtensionException("Constructing a case from a list requires a list of length 4. List "
				+ list.toString() + " has length " + list.length());
		}
		Object state = list.get(0);
		Object activity = list.get(1);
		Object outcome = list.get(2);
		Object time = (list.length() == 4) ? list.get(3) : context.world().ticks();
		if(time instanceof Double) {
			return new NetLogoCase(state, activity, outcome, (Double)time);
		}
		else {
			throw new ExtensionException("Constructing a case from a list requires the 4th item to be a number ("
				+ time.toString() + ")");
		}
	}
	
	
	private static final long next() {
		return ++next;
	}

	/** 
	 * <!-- override getState -->
	 *
	 * @see uk.ac.hutton.caber.Case#getState()
	 */
	@Override
	public Object getState() {
		return (Object)super.getState();
	}

	/** 
	 * <!-- override getOutcome -->
	 *
	 * @see uk.ac.hutton.caber.Case#getOutcome()
	 */
	@Override
	public Object getOutcome() {
		return (Object)super.getOutcome();
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
	 * @see org.nlogo.core.ExtensionObject#getExtensionName()
	 */
	@Override
	public String getExtensionName() {
		return CBRExtension.EXTENSION_NAME;
	}

	/* (non-Javadoc)
	 * @see org.nlogo.core.ExtensionObject#getNLTypeName()
	 */
	@Override
	public String getNLTypeName() {
		return typeName;
	}

	/* (non-Javadoc)
	 * @see org.nlogo.core.ExtensionObject#recursivelyEqual(java.lang.Object)
	 */
	@Override
	public boolean recursivelyEqual(Object obj) {
		return super.equals(obj);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof NetLogoCase))
			return false;
		if (!super.equals(o))
			return false;
		NetLogoCase logoCase = (NetLogoCase) o;
		return id == logoCase.id;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id);
	}

}
