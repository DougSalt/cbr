import org.nlogo.api.*;
import org.nlogo.api.Command;
import org.nlogo.core.*;

import java.time.*;
import scala.collection.*;

public class AddCaseBase implements Command
{
	@Override
	public void perform(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase ||
                args[1].get() instanceof NetLogoCaseBase) {
			NetLogoCaseBase toCaseBase = (NetLogoCaseBase) args[0].get();
            NetLogoCaseBase fromCaseBase = (NetLogoCaseBase) args[1].get();

            for (Case c : fromCaseBase) {
                NetLogoCase fromCase = (NetLogoCase)c;
                toCaseBase.addCase(fromCase);
            }

		} else {
			throw new ExtensionException("Invalid case-base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.commandSyntax(new int[] { Syntax.WildcardType(), Syntax.WildcardType() });
	}
}
