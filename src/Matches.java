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

            //if (caseBase.getMaxSize() < Integer.MAX_VALUE) {
            //    caseBase.imposeSizeLimit(caseBase.getMaxSize());
            //}
            //caseBase.forgetCasesOlderThanTickInfimum();

            Case[] cases = caseBase.toArray(new Case[caseBase.size()]);
            if (cases.length > 1) {
                Case obj  = cases[0];
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
                    } else if (answer instanceof Incomparable || answer == CaseBase.INCOMPARABLE) {
                        continue;
                    } else if (answer instanceof LessThan || answer == CaseBase.LESS_THAN) {
                            obj = src;
                            result = src;
                    } else if (answer instanceof GreaterThan || answer == CaseBase.GREATER_THAN) {
                            result = obj; 
                    } else if (answer instanceof Equal || answer == CaseBase.EQUAL) {
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

            // So now we have the least one, so let us find the rest.

            LogoList resultList = LogoList.Empty();

            System.err.println("\n\n\nNEW RUN\n=======\n\n");
            for (int i = 0; i < cases.length - 1; i++) {
                Object answer = null;
                Case obj  = cases[i];
                if ( caseBase.getCaseLambda() == null ) {
                    answer = caseBase.defaultLambda(result, obj, ref);
                } else {
                    Object[] lambdaArgs = new Object[] { caseBase, result, obj, ref };
                    answer = caseBase.getCaseLambda().report(context, lambdaArgs);
                }
                System.err.println("Res = " + result + "\n");
                System.err.println("Obj = " + obj + "\n");
                System.err.println("Ref = " + ref + "\n");
                System.err.println("Ans = " + answer + "\n");
                if (answer instanceof Equal || answer == CaseBase.EQUAL) {
                    System.err.println("Match  " + result + " " + obj + "\n");
                    //  And this is why I fscking detest java
                    if (Double.compare(((Case)result).getRank(), obj.getRank()) == 0) {
                        System.err.println("Added \n\n\n");
                        resultList = resultList.lput((Object)obj);
                    }
                }
            }

            caseBase.remove(ref);

            return resultList;

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
