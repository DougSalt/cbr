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

			Object state = args[1].get();
			Object activity = args[2].get();
			Double time = context.getAgent().world().ticks();
            Object outcome = Nobody$.MODULE$;

            // Create a temporary case to allow the use of the comparator.
			NetLogoCase ref = new NetLogoCase(state, activity, outcome, time);
            // Er I, dunno - go figure
            Object result = Nobody$.MODULE$;
			caseBase.addCase(ref);

            if (caseBase.getMaxSize() < Integer.MAX_VALUE) {
                caseBase.imposeSizeLimit(caseBase.getMaxSize());
            }

            caseBase.forgetCasesOlderThanTickInfimum();

            Case[] cases = caseBase.toArray(new Case[caseBase.size()]);
            if (cases.length > 1) {
                Case obj  = cases[0];
                for (int i = 1; i < cases.length; i++) {
                    Case src = (Case)cases[i];
                    Object[] lambdaArgs = new Object[] { caseBase, src, obj, ref };
                    Object answer = caseBase.getCaseLambda().report(context, lambdaArgs);
                    if (CaseBase.INVALID.equalsIgnoreCase(answer.toString())) {
                        continue;
                    }
                    else if (CaseBase.YES.equalsIgnoreCase(answer.toString())) {
                        obj = src;
                        result = src;
                    }
                    else if (CaseBase.NO.equalsIgnoreCase(answer.toString())) {
                        result = obj;
                    }
                    else if (CaseBase.EQUAL.equalsIgnoreCase(answer.toString())) {
                        if (src.getRank() > obj.getRank()) {
                            result = src;
                            obj = src;
                        }
                        else {
                            result = obj;
                        }
                    }

                }
            }

            caseBase.remove(ref);
            return result;

		} else {
			throw new ExtensionException("Invalid case-base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType(), Syntax.WildcardType(), Syntax.WildcardType() }, Syntax.WildcardType());
	}
}
