import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;
import org.nlogo.core.Nobody;

import java.time.*;

public class Match implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase) {
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();

			LogoList state = args[2].getList();
			Object activity = args[3].get();
			LogoList outcome = args[4].getList();
			LocalDateTime time = DateUtils.currentTime();

            // Create a temporary case to allow the use of the comparator.

			NetLogoFeatureValueSet stateValueSet = NetLogoFeatureValueSet.manifest(state);
			NetLogoFeatureValueSet outcomeValueSet = NetLogoFeatureValueSet.manifest(outcome);
			NetLogoCase refCase = new NetLogoCase(stateValueSet, activity, outcomeValueSet, time);
            // Er-u-unno - go figure
            Object result = Nobody$.MODULE$;
			caseBase.addCase(refCase);

            if (caseBase.getMaxSize() < Integer.MAX_VALUE) {
                caseBase.imposeSizeLimit(caseBase.getMaxSize());
            }

            if (caseBase.isForgettable()) {

            }


            caseBase.remove(refCase);
            return result;

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
