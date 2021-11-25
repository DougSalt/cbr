import org.nlogo.api.*;
import org.nlogo.core.*;
import org.nlogo.core.Reporter;
import java.util.*;

import java.time.*;

public class DefaultComparator implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase) {
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();

            Object result = Nobody$.MODULE$;
    
            Object a;
            if (args[1].get() instanceof Case) {
                a = ((Case) args[1].get()).getState();
            } else {
                a = args[1].get();
            }

            Object b;
            if (args[2].get() instanceof Case) {
                b = ((Case) args[2].get()).getState();
            } else {
                b = args[2].get();
            }
            
            if (a.getClass() !=  b.getClass()) 
                return new Incomparable();

            Object r;
            if (args[3].get() instanceof Case) {
                r = ((Case) args[3].get()).getState();
            } else {
                r = args[3].get();
            }
             
            if (a.getClass() !=  r.getClass()) 
                return new Incomparable();
            if (a instanceof Double) {
                Double rMinusA = (Double) r - (Double) a;
                Double rMinusB = (Double) r - (Double) b;
                if (Math.abs((double)rMinusA) == Math.abs((double)rMinusB) ) {
                    return new Equal();
                } else if (Math.abs(rMinusA) < Math.abs(rMinusB)) {
                    return new LessThan();
                } else {
                    return new GreaterThan();
                }
            } else if (a instanceof LogoList) {

                LogoList aList = (LogoList) a;
                LogoList bList = (LogoList) b;
                LogoList rList = (LogoList) r;

                LogoList anrList = (LogoList) aList.intersect(rList);
                if (anrList.isEmpty())
                    return new Incomparable();

                LogoList bnrList = (LogoList) bList.intersect(rList);
                if (bnrList.isEmpty())
                    return new Incomparable();

                LogoList anbnrList = (LogoList) anrList.intersect(bnrList);
                if (anbnrList.isEmpty())
                    return new Incomparable();

                if ( anrList.length() > bnrList.length() )
                    return new GreaterThan();

                if ( anrList.length() < bnrList.length() )
                    return new LessThan();

                if ( anrList != bnrList )
                    return new LessThan();

                return new Equal();

             } else if (a instanceof AgentSet) {

                 AgentSet aAgentSet = (AgentSet) a;
                 LogoList aList = aAgentSet.toLogoList();

/*
                LogoList aList = ((AgentSet) a).toLogoList();
                LogoList bList = ((AgentSet) b).toLogoList();
                LogoList rList = ((AgentSet) r).toLogoList();

                LogoList anrList = (LogoList) aList.intersect(rList);
                if (anrList.isEmpty())
                    return new Incomparable();

                LogoList bnrList = (LogoList) bList.intersect(rList);
                if (bnrList.isEmpty())
                    return new Incomparable();

                LogoList anbnrList = (LogoList) anrList.intersect(bnrList);
                if (anbnrList.isEmpty())
                    return new Incomparable();

                if ( anrList.length() > bnrList.length() )
                    return new GreaterThan();

                if ( anrList.length() < bnrList.length() )
                    return new LessThan();

                if ( anrList != bnrList )
                    return new LessThan();

                return new Equal();
*/
            } else if (a instanceof String) {

                Set aSet = convertToSet((String) a);
                Set bSet = convertToSet((String) b);
                Set rSet = convertToSet((String) r);

                Set anrSet = new HashSet(aSet);
                anrSet.retainAll(rSet);
                if (anrSet.isEmpty())
                    return new Incomparable();

                Set bnrSet = new HashSet(bSet);
                bnrSet.retainAll(rSet);
                if (bnrSet.isEmpty())
                    return new Incomparable();

                Set anbnrSet = new HashSet(anrSet);
                anbnrSet.retainAll(bnrSet);
                if (anbnrSet.isEmpty())
                    return new Incomparable();


                if ( anrSet.size() > bnrSet.size() )
                    return new GreaterThan();

                if ( anrSet.size() < bnrSet.size() )
                    return new LessThan();

                if ( anrSet != bnrSet)
                    return new LessThan();

                return new Equal();
            }
                
            return result;

		} else {
			throw new ExtensionException("Invalid case-base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType(), Syntax.ListType(), Syntax.ListType(), Syntax.ListType() }, Syntax.WildcardType());
	}
    public static Set convertToSet(String string) {

      // Result hashset
      Set resultSet = new HashSet();

      for (int i = 0; i < string.length(); i++) {
          resultSet.add(new Character(string.charAt(i)));
      }

      // Return result
      return resultSet;
  }
}
