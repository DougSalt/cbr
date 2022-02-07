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

			LogoList state = args[1].getList();
			Object activity = args[2].get();
			LogoList outcome = args[3].getList();
            Double ticks = context.getAgent().world().ticks();

			NetLogoFeatureValueSet stateValueSet = NetLogoFeatureValueSet.manifest(state);
			NetLogoFeatureValueSet outcomeValueSet = NetLogoFeatureValueSet.manifest(outcome);

			NetLogoCase logoCase = new NetLogoCase(stateValueSet, activity, outcomeValueSet, ticks);
			caseBase.addCase(logoCase);

			return logoCase;
		} else {
			throw new ExtensionException("Invalid case-base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType(), Syntax.ListType(), Syntax.WildcardType(), Syntax.ListType() }, Syntax.WildcardType());
	}
}
