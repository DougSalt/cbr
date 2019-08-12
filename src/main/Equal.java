import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;

public class Equal implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		return "equal";
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(Syntax.StringType());
	}
}
