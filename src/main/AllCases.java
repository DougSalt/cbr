import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;
import java.util.*;
import scala.collection.JavaConverters;

public class AllCases implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase) {
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();

            //LogoList caseBaseList = caseBase.asLogoList();
            //LogoList caseBaseList = LogoList.fromJava(Collections.emptyList());
            LogoList caseBaseList = LogoList.Empty();
            for (Case c : caseBase) {
                NetLogoCase nc = (NetLogoCase)c;
                caseBaseList = caseBaseList.lput(nc);
            }
		    return caseBaseList;
        }
		else {
			throw new ExtensionException("Invalid case-base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType()},Syntax.ListType());
	}
}
