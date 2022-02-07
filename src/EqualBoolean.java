import org.nlogo.api.*; 
import org.nlogo.api.Reporter;
import org.nlogo.core.*;
import org.nlogo.core.Nobody;

import java.time.*;

public class EqualBoolean implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase) {
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();
			NetLogoCase src = (NetLogoCase)args[1].get();
			NetLogoCase obj = (NetLogoCase)args[2].get();
			NetLogoCase ref = (NetLogoCase)args[3].get();

            Object[] lambdaArgs = new Object[] { caseBase, src, obj, ref };
            Object answer = null;
            if (caseBase.getCaseLambda() == null) {
                answer = caseBase.defaultLambda(src, obj, ref);
            } else {
                answer = caseBase.getCaseLambda().report(context, lambdaArgs);
            }
            if (answer instanceof Equal) {
                return true;
            }
            return false;

		} else {
			throw new ExtensionException("Invalid case-base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType(), Syntax.WildcardType(), Syntax.WildcardType(), Syntax.WildcardType()}, Syntax.WildcardType());
	}
}
