import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;

public class GetRank implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCase) {
			NetLogoCase logoCase = (NetLogoCase)args[0].get();

			return logoCase.getRank();
		}
		else {
			throw new ExtensionException("Argument to cbr:get-rank must be a case");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType() }, Syntax.NumberType());
	}
}
