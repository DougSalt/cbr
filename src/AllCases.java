import java.util.LinkedList;
import java.util.List;

import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;

public class AllCases implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase) {
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();

			List<NetLogoCase> caseList = new LinkedList<NetLogoCase>();

			for(Case c: caseBase) {
				if(c instanceof NetLogoCase) {
					caseList.add((NetLogoCase)c);
				}
				else {
					throw new ExtensionException("BUG! Non-NetLogoCase member of NetLogoCaseBase");
				}
            }
		    return LogoList.fromJava(caseList);
        }
		else {
			throw new ExtensionException("Argument to cbr:all must be a case base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType() }, Syntax.ListType());
	}
}
