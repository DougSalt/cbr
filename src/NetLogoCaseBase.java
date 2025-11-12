/**
 * 
 */
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.nlogo.api.Agent;
import org.nlogo.api.AgentSet;
import org.nlogo.api.AnonymousReporter;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.core.ExtensionObject;
import org.nlogo.core.LogoList;

/**
 * <!-- NetLogoCaseBase -->
 * 
 * Provide a NetLogo `ExtensionObject` wrapper around the internal `CaseBase` class.
 *
 */
public class NetLogoCaseBase extends CaseBase implements ExtensionObject, PartiallyOrderedComparison.Hook<Case> {

	
	private final long id;
	private static long next = 0;

	private AnonymousReporter netLogoCaseLambda = null;
	private Context context;
	
	public static final String typeName = "base";
	
	public NetLogoCaseBase() {
		super.caseLambda = new DefaultLambda(this);
		id = next();
	}
	
	public NetLogoCaseBase(CaseBase value) {
		this();
		for(Case aCase: value) {
			this.add(new NetLogoCase(aCase));
		}
	}
	
	
	private static final long next() {
		return ++next;
	}

	public LogoList asLogoList() {
		LogoList list = LogoList.Empty();

		for(Case c : this) {
			NetLogoCase nc = (NetLogoCase)c;

			list = list.lput(nc.asLogoList());
		}

		return list;
	}
	
	public static NetLogoCaseBase fromLogoList(LogoList list) throws ExtensionException {
		NetLogoCaseBase cb = new NetLogoCaseBase();
		
		int ix = 0;
		for(Object o : list.javaIterable()) {
			if(o instanceof LogoList) {
				cb.addCase(NetLogoCase.fromLogoList((LogoList)o));
			}
			else {
				throw new ExtensionException("A case base must be initialized from a list of lists. Item " +
					ix + " of the list from which to build a case base is not a list");
			}
			ix++;
		}
		
		return cb;
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


	public void setContext(Context context) {
		this.context = context;
	}
	
	@Override
	public PartiallyOrderedComparison cmp(Case src, Case obj, Case ref) {
		if(src instanceof NetLogoCase && obj instanceof NetLogoCase && ref instanceof NetLogoCase) {
			return n_cmp(src.getState(), obj.getState(), ref.getState());
		}
		else {
			return src.relativeCompareTo(obj, ref);
		}
	}
	
	public PartiallyOrderedComparison n_cmp(Object src, Object obj, Object ref) {
		
		if(netLogoCaseLambda != null) {
			Object result = netLogoCaseLambda.report(context, new Object[] {src, obj, ref });
			return PartiallyOrderedComparison.fromString(result.toString());
		}
		else if(obj.getClass().isInstance(ref) && src.getClass().isInstance(ref)) {
			if(ref instanceof LogoList) {
				return PartiallyOrderedComparison.cmp(((LogoList)src).toJava(), ((LogoList)obj).toJava(),((LogoList)ref).toJava());
			}
			else if(ref instanceof AgentSet) {
				Set<Agent> s = new HashSet<Agent>();
				for(Agent a: ((AgentSet)src).agents()) {
					s.add(a);
				}
				Set<Agent> o = new HashSet<Agent>();
				for(Agent a: ((AgentSet)obj).agents()) {
					o.add(a);
				}
				Set<Agent> r = new HashSet<Agent>();
				for(Agent a: ((AgentSet)ref).agents()) {
					r.add(a);
				}
				return PartiallyOrderedComparison.cmp(s, o, r);
			}
			else if(ref instanceof Agent) {
				if(((Agent)src).kind().equals(((Agent)ref).kind()) 
					&& ((Agent)obj).kind().equals(((Agent)ref).kind())) {
				
					List<Object> s = Arrays.asList(((Agent)src).variables());
					List<Object> o = Arrays.asList(((Agent)obj).variables());
					List<Object> r = Arrays.asList(((Agent)ref).variables());
					
					return PartiallyOrderedComparison.cmp(s, o, r);
				}
				else if(((Agent)src).kind().equals(((Agent)ref).kind())) {
					return PartiallyOrderedComparison.LESS_THAN;
				}
				else if(((Agent)obj).kind().equals(((Agent)ref).kind())) {
					return PartiallyOrderedComparison.GREATER_THAN;
				}
				else {
					return PartiallyOrderedComparison.INCOMPARABLE;
				}
			}
			else {
				return PartiallyOrderedComparison.cmp(src, obj, ref);
			}
		}
		else {
			return PartiallyOrderedComparison.INCOMPARABLE;
		}
		
		
	}
	
	
	public void setCaseLambda(AnonymousReporter lambda) {
		netLogoCaseLambda = lambda;
	}
}
