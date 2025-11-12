import org.nlogo.api.*;
import org.nlogo.api.Command;
import org.nlogo.core.*;

public class Resize implements Command
{
	@Override
	public void perform(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase)
		{
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();
            caseBase.imposeSizeLimit(caseBase.getMaxSize());
		}
		else
		{
			throw new ExtensionException("Argument to cbr:resize must be a case-base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.commandSyntax(new int[] { Syntax.WildcardType() });
	}
}
