import org.nlogo.api.*;
import org.nlogo.api.Command;
import org.nlogo.core.*;

import java.time.*;

public class SetTime implements Command
{
	@Override
	public void perform(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase) {
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();

			if (args[1].get() instanceof NetLogoCase) {
				NetLogoCase logoCase = (NetLogoCase) args[1].get();
    		    LocalDateTime time = DateUtils.parseDateTime(args[2].getString().trim());
                logoCase.setTime(time);
			}
			else {
				throw new ExtensionException("Invalid case");
			}
		} else {
			throw new ExtensionException("Invalid case-base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.commandSyntax(new int[] { Syntax.WildcardType(), Syntax.WildcardType(),Syntax.WildcardType()});
	}
}
