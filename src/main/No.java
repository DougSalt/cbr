import org.nlogo.api.Reporter;
import org.nlogo.api.*;
import org.nlogo.core.*;

public class No implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		return "no";
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(Syntax.StringType());
	}
}