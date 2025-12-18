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
				AnonymousReporter reporter = args[1].getReporter();
				
				//TODO: We also need to do some validation of the Reporter
      	        // My feeling is we could do this with some kind of activation of
                // the code with say 3 string cases.
				
				Syntax syn = reporter.syntax();
				if(syn.allArgs().length() != 3) {
					throw new ExtensionException("Anonymous reporter to cbr:lambda must take three arguments");
				}
				if(syn.ret() != Syntax.WildcardType()) {
					throw new ExtensionException("Anonymous reporter to cbr:lambda returns the wrong thing");
				}
				
				caseBase.setCaseLambda(reporter);
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
