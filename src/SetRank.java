import org.nlogo.api.*;
import org.nlogo.api.Command;
import org.nlogo.core.*;

public class SetRank implements Command
{
	@Override
	public void perform(Argument[] args, Context context)
		throws ExtensionException
	{

		if (args[0].get() instanceof NetLogoCase) {
			NetLogoCase logoCase = (NetLogoCase) args[0].get();
		    Double rank = (Double)args[2].get();
            logoCase.setRank(rank);
		}
		else {
			throw new ExtensionException("First argument to cbr:set-rank must be a case");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.commandSyntax(new int[] { Syntax.WildcardType(), Syntax.NumberType()});
	}
}
