import org.nlogo.api.*;
import org.nlogo.api.Command;
import org.nlogo.core.*;

import java.time.*;

public class Forget implements Command
{
	@Override
	public void perform(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase)
		{
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();
			Double ticks = (Double)args[1].get();
			caseBase.forgetOlderThan(ticks);
		}
		else
		{
			throw new ExtensionException("Invalid case-base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.commandSyntax(new int[] { Syntax.WildcardType(), Syntax.NumberType() });
	}
}
