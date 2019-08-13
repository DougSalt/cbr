import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;

public class GetTime implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase)
		{
			if (args[1].get() instanceof NetLogoCase)
			{
				NetLogoCaseBase caseBase = (NetLogoCaseBase)args[0].get();
				NetLogoCase logoCase = (NetLogoCase)args[1].get();

				if (caseBase.contains(logoCase))
				{
					return String.valueOf(logoCase.getTime());
				}
				else
				{
					throw new ExtensionException("Case not found in CaseBase");
				}
			}
			else
			{
				throw new ExtensionException("Invalid Case");
			}
		}
		else
		{
			throw new ExtensionException("Invalid CaseBase");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType(), Syntax.WildcardType()}, Syntax.NumberType());
	}
}
