import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;

import java.time.*;

public class AddCase implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase) {
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();

			Object state = args[1].get();
			Object activity = args[2].get();
			Object outcome = args[3].get();
            Double ticks = context.getAgent().world().ticks();

			NetLogoCase logoCase = new NetLogoCase(state, activity, outcome, ticks);
			caseBase.addCase(logoCase);

			return logoCase;
		} else {
			throw new ExtensionException("Invalid case-base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType(), Syntax.WildcardType(), Syntax.WildcardType(), Syntax.WildcardType() }, Syntax.WildcardType());
	}
}
