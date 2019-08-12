import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;

public class New implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		NetLogoCaseBase caseBase = new NetLogoCaseBase();

		return caseBase;
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(Syntax.WildcardType());
	}
}
