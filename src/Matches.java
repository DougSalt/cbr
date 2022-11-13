import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;
import org.nlogo.core.Nobody;

import java.time.*;

public class Matches implements Reporter
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
            //Object result = Nobody$.MODULE$;
            LogoList result = LogoList.Empty();
			caseBase.addCase(ref);

            //if (caseBase.getMaxSize() < Integer.MAX_VALUE) {
            //    caseBase.imposeSizeLimit(caseBase.getMaxSize());
            //}

            //caseBase.forgetCasesOlderThanTickInfimum();

            Case[] cases = caseBase.toArray(new Case[caseBase.size()]);
            if (cases.length > 1) {
                Case obj  = cases[0];
                result = result.lput(obj);
                for (int i = 1; i < cases.length; i++) {
                    Case src = (Case)cases[i];
                    Object answer = null;
                    if ( caseBase.getCaseLambda() == null ) {
                        answer = caseBase.defaultLambda(src, obj, ref);
                    } else {
                        Object[] lambdaArgs = new Object[] { caseBase, src, obj, ref };
                        answer = caseBase.getCaseLambda().report(context, lambdaArgs);
                    }
                    if (src.getOutcome() == Nobody$.MODULE$) {
                        continue;
                    }
                    else if (answer instanceof Incomparable || answer == CaseBase.INCOMPARABLE) {
                        continue;
                    }
                    else if (answer instanceof GreaterThan || answer == CaseBase.GREATER_THAN) {
                        result = LogoList.Empty().lput(obj);
                    }
                    else if (answer instanceof LessThan || answer == CaseBase.LESS_THAN) {
                        obj = src;
                        result = LogoList.Empty().lput(src);
                    }
                    else if (answer instanceof Equal || answer == CaseBase.EQUAL) {
                        if (src.getRank() > obj.getRank()) {
                            result = LogoList.Empty().lput(src);
                            obj = src;
                        }
                        else if (src.getRank().equals(obj.getRank())) {
                            result = result.lput((Object)src);
                        }
                        else {
                            result = LogoList.Empty().lput(src);
                            obj = src;
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
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType(), Syntax.WildcardType(), Syntax.WildcardType() }, Syntax.ListType());
	}
}
