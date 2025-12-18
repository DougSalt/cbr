/**
 * 
 */

import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;

/**
 * 
 */
public class NewCase implements Reporter {
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		return new NetLogoCase(args[0].get(), args[1].get(), args[2].get(), context.getAgent().world().ticks());
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] {Syntax.WildcardType(), Syntax.WildcardType(), Syntax.WildcardType()}, Syntax.WildcardType());
	}

}
