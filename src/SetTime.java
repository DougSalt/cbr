import org.nlogo.api.*;
import org.nlogo.api.Command;
import org.nlogo.core.*;

public class SetTime implements Command
{
	@Override
	public void perform(Argument[] args, Context context)
		throws ExtensionException
	{

		if (args[0].get() instanceof NetLogoCase) {
			NetLogoCase logoCase = (NetLogoCase) args[0].get();
    		Double ticks = (Double)args[1].get();

    		logoCase.setTime(ticks);
		}
		else {
			throw new ExtensionException("First argument to cbr:set-time must be a case");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.commandSyntax(new int[] { Syntax.WildcardType(), Syntax.NumberType()});
	}
}
