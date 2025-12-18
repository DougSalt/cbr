import org.nlogo.api.*; 
import org.nlogo.api.Reporter;
import org.nlogo.core.*;

/**
 * <!-- CompareBoolean -->
 * 
 * Provides an implementation of four Boolean comparison commands that simultaneously
 * perform a comparison and confirm that the result of the comparison is that expected
 * by the command-name.
 * 
 *   + `eq? cb src obj ref` -- return `true` iff src and obj are equally similar to ref using cb's lambda
 *   + `lt? cb src obj ref` -- return `true` iff src is less similar than obj to ref using cb's lambda
 *   + `gt? cb src obj ref` -- return `true` iff src is more similar than obj to ref using cb's lambda
 *   + `incmp? cb src obj ref` -- return `true` iff src and obj are incomparably similar to ref using cb's lambda
 */
public class CompareBoolean implements Reporter
{
	PartiallyOrderedComparison command;
	boolean modifier;
	
	
	public CompareBoolean(PartiallyOrderedComparison command) {
		this.command = command;
		this.modifier = false;
	}
	
	public CompareBoolean(PartiallyOrderedComparison command, boolean modifier) {
		this.command = command;
		this.modifier = modifier;
	}

	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args.length == 1) {
			if(args[0].get() instanceof NetLogoPartialComparison) {
				NetLogoPartialComparison result = (NetLogoPartialComparison)args[0].get();
				return isCommand(result);
			}
			else {
				throw new ExtensionException("Single argument form of " + this.toString()
						+ " expects a partial comparison result (cbr:eq, cbr:lt, "
						+ "cbr:gt or cbr:incmp) as its argument (\"" + args[0].get().toString()
						+ "\")");
				
			}
		}
		else if (args.length == 4) {
			if(args[0].get() instanceof NetLogoCaseBase) {
				NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();
				caseBase.setContext(context);
				
				if(args[1].get() instanceof NetLogoCase
						&& args[2].get() instanceof NetLogoCase
						&& args[3].get() instanceof NetLogoCase) {
					NetLogoCase src = (NetLogoCase)args[1].get();
					NetLogoCase obj = (NetLogoCase)args[2].get();
					NetLogoCase ref = (NetLogoCase)args[3].get();
					
					return isCommand(caseBase.cmp(src, obj, ref));

				}
				else {
					return isCommand(caseBase.n_cmp(args[1].get(), args[2].get(), args[3].get()));
				}
			}
			else if(args[0].get() instanceof AnonymousReporter) {
				AnonymousReporter r = (AnonymousReporter)args[0].get();
				Object result = r.report(context,
					new Object[] {
						args[1].get(), args[2].get(), args[3].get()
					}
				);
				if(result instanceof NetLogoPartialComparison) {
					return isCommand((NetLogoPartialComparison)result);
				}
				else {
					throw new ExtensionException("Anonymous reporter first argument to "
						+ this.toString() + "must return a partial order "
						+ "comparison cbr:eq, cbr:lt, cbr:gt or cbr:incmp (\"" 
						+ result.toString() + "\"");
				}
			}
			else {
				throw new ExtensionException("First argument to " + this.toString()
						+ " must be a case base or a three-argument anonymous reporter");
			}
		}
		else {
			throw new ExtensionException(this.toString()
					+ " takes four arguments, a case base and three cases");
		}
	}
	
	@Override
	public String toString() {
		try {
			return CBRExtension.EXTENSION_NAME + ":" + command(command, modifier);
		}
		catch(ExtensionException e) {
			return "PANIC!";
		}
	}
	
	public static String command(PartiallyOrderedComparison command, boolean modifier)
			throws ExtensionException
	{
		if(modifier) {
			switch(command) {
			case EQUAL:
				return "ne?";
			case LESS_THAN:
				return "le?";
			case GREATER_THAN:
				return "ge?";
			case INCOMPARABLE:
				return "cmp?";
			default:
				throw new ExtensionException("PANIC!");
			}
		}
		else {
			switch(command) {
			case EQUAL:
				return "eq?";
			case LESS_THAN:
				return "lt?";
			case GREATER_THAN:
				return "gt?";
			case INCOMPARABLE:
				return "incmp?";
			default:
				throw new ExtensionException("PANIC!");
			}			
		}
	}
	
	public Boolean isCommand(PartiallyOrderedComparison result) {
		if(modifier) { 
			if(result == PartiallyOrderedComparison.INCOMPARABLE) {
				// All of the modified versions are true iff result is comparable
				return false;
			}
			else if(command == PartiallyOrderedComparison.INCOMPARABLE) {
				// All of the modified versions of the commands are true
				// as long as the result isn't incomparable
				return true;
			}
			else if(command == PartiallyOrderedComparison.EQUAL) {
				// Here, result is EQUAL, LESS_THAN or GREATER_THAN
				// and command is "ne?", so we're true as long as result
				// isn't EQUAL.
				return result != PartiallyOrderedComparison.EQUAL;
			}
			else if(result == PartiallyOrderedComparison.EQUAL){
				// command must be "le?" or "ge?"
				return true;
			}
			else {
				return result == command;
			}
		}
		else {
			return result == command;
		}
	}
	
	public Boolean isCommand(NetLogoPartialComparison result) throws ExtensionException {
		if(modifier) {
			switch(command) {
			case EQUAL:
				return result.isLessOrMore();
			case LESS_THAN:
				return result.isLessOrEqual();
			case GREATER_THAN:
				return result.isMoreOrEqual();
			case INCOMPARABLE:
				return result.isComparable();
			default:
				throw new ExtensionException("PANIC!");
			}
		}
		else {
			switch(command) {
			case EQUAL:
				return result.isEqual();
			case LESS_THAN:
				return result.isLess();
			case GREATER_THAN:
				return result.isMore();
			case INCOMPARABLE:
				return result.isIncomparable();
			default:
				throw new ExtensionException("PANIC!");
			}			
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType() | Syntax.RepeatableType()}, Syntax.BooleanType(), 1);
	}
}
