import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;

import java.util.LinkedList;
import java.util.List;

public class Match implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{

		List<NetLogoCase> result = new LinkedList<NetLogoCase>();
		
		if (args[0].get() instanceof NetLogoCaseBase) {
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();
			
			Object activity = args[2].get();
			
			caseBase.setContext(context);	
			
			for(Case c: caseBase.getStateMatches(args[1].get())) {
				if(c instanceof NetLogoCase) {
					if(c.getActivity().equals(activity)) {
						result.add((NetLogoCase)c);
					}
				}
				else {
					throw new ExtensionException("BUG! Non-NetLogoCase member of NetLogoCaseBase");
				}
			}
			
		} else {
			throw new ExtensionException("First argument to match must be a case base");
		}
        return LogoList.fromJava(result);
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType(), Syntax.WildcardType(), Syntax.WildcardType() }, Syntax.ListType());
	}
}
