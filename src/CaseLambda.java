import org.nlogo.api.*;
import org.nlogo.api.Command;
import org.nlogo.api.AnonymousReporter;
import org.nlogo.core.*;

public class CaseLambda implements Command
{
	@Override
	public void perform(Argument[] args, Context context)
		throws ExtensionException
	{

		if (args[0].get() instanceof NetLogoCaseBase)
		{
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();

			if (args[1].get() instanceof AnonymousReporter)
			{
				//Reporter reporter = (Reporter) args[1].getReporter();
				AnonymousReporter reporter = args[1].getReporter();
				caseBase.setCaseLambda(reporter);

				//TODO: We also need to do some validation of the Reporter
                // My feeliing is we could do this with some dummy values
                // or some kind of scan of the code.
			}
			else
			{
				throw new ExtensionException("Invalid reporter" + args[1].getClass());
			}
		}
		else
		{
			throw new ExtensionException("Invalid case-base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.commandSyntax(new int[] { Syntax.WildcardType(), Syntax.ReporterType() });
	}
}
