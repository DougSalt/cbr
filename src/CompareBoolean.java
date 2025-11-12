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
	
	
	public CompareBoolean(PartiallyOrderedComparison command) {
		this.command = command;
	}

	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args.length == 4
				&& args[0].get() instanceof NetLogoCaseBase
				&& args[1].get() instanceof NetLogoCase
				&& args[2].get() instanceof NetLogoCase
				&& args[3].get() instanceof NetLogoCase) {
			
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();
			NetLogoCase src = (NetLogoCase)args[1].get();
			NetLogoCase obj = (NetLogoCase)args[2].get();
			NetLogoCase ref = (NetLogoCase)args[3].get();
			
			caseBase.setContext(context);
			return caseBase.cmp(src, obj, ref) == command;
		} else if(args.length != 4) {
			throw new ExtensionException("Command takes four arguments, a case base and three cases");
		} else if(!(args[0].get() instanceof NetLogoCaseBase)) {
			throw new ExtensionException("First argument is not a case base");
		} else if(!(args[1].get() instanceof NetLogoCase)) {
			throw new ExtensionException("Second argument is not a case");
		} else if(!(args[2].get() instanceof NetLogoCase)) {
			throw new ExtensionException("Third argument is not a case");
		} else if(!(args[3].get() instanceof NetLogoCase)) {
			throw new ExtensionException("Fourth argument is not a case");
		} else {
			throw new ExtensionException("PANIC!");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType(), Syntax.WildcardType(), Syntax.WildcardType(), Syntax.WildcardType()}, Syntax.WildcardType());
	}
}
