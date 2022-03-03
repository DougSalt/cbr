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
            Object result = Nobody$.MODULE$;
			caseBase.addCase(ref);

            Case[] cases = caseBase.toArray(new Case[caseBase.size()]);
            if (cases.length > 1) {
                Case obj  = cases[0];
                // One less because the last case is the reference case
                for (int i = 0; i < cases.length - 1; i++) {
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
                    } else if (answer instanceof Incomparable) {
                        continue;
                    } else if (answer instanceof LessThan) {
                        result = obj;
                    } else if (answer instanceof GreaterThan) {
                        obj = src;
                        result = src;
                    } else if (answer instanceof Equal) {
                        if (src.getRank() > obj.getRank()) {
                            result = src;
                            obj = src;
                        } else { 
                            if (src.getTime() < obj.getTime()) {
                                result = src;
                                obj = src;
                            } else {
                                result = obj;
                            }
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
