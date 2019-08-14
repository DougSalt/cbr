/**
 * 
 */
import java.io.IOException;
import java.util.*;

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
