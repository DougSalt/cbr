import org.nlogo.api.Reporter;
import org.nlogo.api.*;
import org.nlogo.core.*;

public class Invalid implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		return "invalid";
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(Syntax.StringType());
	}
}
