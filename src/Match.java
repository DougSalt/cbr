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
        Object result = Nobody$.MODULE$;
		if (args[0].get() instanceof NetLogoCaseBase) {
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();

			Object state = args[1].get();
			Object activity = args[2].get();
			Double time = context.getAgent().world().ticks();
            Object outcome = Nobody$.MODULE$;

            // Create a temporary case to allow the use of the comparator.
			NetLogoCase ref = new NetLogoCase(state, activity, outcome, time);
			caseBase.addCase(ref);

            Case[] cases = caseBase.toArray(new Case[caseBase.size()]);
            if (cases.length > 1) {
                // This get the "obj" for comparison with "ref"
                Case obj  = cases[0];
                // One less because the last case is the reference case
                for (int i = 0; i < cases.length - 1; i++) {
                    // This get the "src" for comparison with "ref"
                    Case src = (Case)cases[i];
                    Object answer = null;
                    if ( caseBase.getCaseLambda() == null ) {
                        answer = caseBase.defaultLambda(src, obj, ref);
                    } else {
                        Object[] lambdaArgs = new Object[] { caseBase, src, obj, ref };
                        answer = caseBase.getCaseLambda().report(context, lambdaArgs);
                        //System.err.println("The object type of the answer is " + answer.getClass());
                        
                    }
                    if (src.getOutcome() == Nobody$.MODULE$) {
                        continue;
                    } else if (answer instanceof Incomparable || answer == CaseBase.INCOMPARABLE) {
                        continue;
                    } else if (answer instanceof LessThan || answer == CaseBase.LESS_THAN ) {
                        // The "obj" is replaced with "src"
                        obj = src;
                        result = src;
                    } else if (answer instanceof GreaterThan || answer == CaseBase.GREATER_THAN ) {
                        // This original "obj" answer should persist to the next round
                        result = obj;
                    } else if (answer instanceof Equal || answer == CaseBase.EQUAL ) {
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
		} else {
			throw new ExtensionException("Invalid case-base");
		}
        return result;
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType(), Syntax.WildcardType(), Syntax.WildcardType() }, Syntax.WildcardType());
	}
}
