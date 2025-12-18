import org.nlogo.api.*;
import org.nlogo.api.Command;
import org.nlogo.core.*;

public class AddCase implements Command
{
	@Override
	public void perform(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase) {
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();
            Double ticks = context.getAgent().world().ticks();

            if(args.length == 2) {
				if(args[1].get() instanceof NetLogoCase) {
					NetLogoCase logoCase = new NetLogoCase((NetLogoCase)args[1].get());
					logoCase.setTime(ticks);
					caseBase.addCase(logoCase);
				}
				else if(args[1].get() instanceof LogoList) {
					LogoList list = (LogoList)args[1].get();
					NetLogoCase logoCase = NetLogoCase.fromLogoList(list, context);
					caseBase.addCase(logoCase);
				}
				else {
					throw new ExtensionException("Second argument to two-argument form of cbr:add"
							+ " must be a case or a list");
				}
			}
			else if(args.length == 4) {

				Object state = args[1].get();
				Object activity = args[2].get();
				Object outcome = args[3].get();

				NetLogoCase logoCase = new NetLogoCase(state, activity, outcome, ticks);
				caseBase.addCase(logoCase);

			}
			else {
				throw new ExtensionException("cbr:add has a two-argument form accepting a case"
						+ " base and a case, or a four-argument form in parentheses accepting "
						+ " a case base and objects to use as state, decision and outcome");
			}
		} else {
			throw new ExtensionException("First argument to cbr:add must be a case-base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.commandSyntax(new int[] { Syntax.WildcardType(), Syntax.WildcardType() | Syntax.RepeatableType() }, 2);
	}
}
