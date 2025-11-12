import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;

public class GetMaxSize implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase)
		{
            NetLogoCaseBase caseBase = (NetLogoCaseBase)args[0].get();
		    return caseBase.getMaxSize();
		}
		else
		{
			throw new ExtensionException("Argument to cbr:get-max-size must be a case base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType() }, Syntax.NumberType());
	}
}
