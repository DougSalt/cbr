import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;

import java.util.*;

public class Closer implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase)
		{
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();
            if (caseBase.getCaseLambda() == null)
                throw new ExtensionException("No comparator set");
			for (int i=1; i < 4; i++)
				if (!(args[i].get() instanceof NetLogoCase))
					throw new ExtensionException("Invalid case");
            // Note we include the casebase identifier as well
			return caseBase.isCloser(Arrays.copyOfRange(args, 0, 4), context);
		}
		else
		{
			throw new ExtensionException("Invalid case-base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType(), Syntax.WildcardType(), Syntax.WildcardType(), Syntax.WildcardType() }, Syntax.BooleanType());
	}
}
