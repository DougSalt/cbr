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

			if (args[1].get() instanceof String)
			{
				LocalDateTime dateTime = DateUtils.parseDateTime(args[1].getString().trim());
				caseBase.forgetOlderThan(dateTime);
			}
			else
			{
				throw new ExtensionException("Invalid time");
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
		return SyntaxJ.commandSyntax(new int[] { Syntax.WildcardType(), Syntax.StringType() });
	}
}
