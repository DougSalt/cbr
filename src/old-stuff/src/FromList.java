/**
 * 
 */

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.Reporter;
import org.nlogo.core.LogoList;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

/**
 * <!-- FromList -->
 *
 * 
 *
 * @author Gary Polhill
 */
public class FromList implements Reporter {

	@Override
	public Syntax getSyntax() {
		return SyntaxJ.reporterSyntax(new int[] { Syntax.ListType() }, Syntax.WildcardType(), "OT");
	}

	/**
	 * <!-- override report -->
	 *
	 * Argument is expected to be a list of cases. Each case is a three-element
	 * list containing a feature-value-set, an activity and another feature-
	 * value-set. Both feature-value-sets are lists. Each element of the each list
	 * is a feature-value pair (as another list)
	 *
	 * @see org.nlogo.api.Reporter#report(org.nlogo.api.Argument[],
	 *      org.nlogo.api.Context)
	 */
	@Override
	public Object report(Argument[] args, Context context) throws ExtensionException {
		NetLogoCaseBase cb = new NetLogoCaseBase();
		LogoList list = args[0].getList();
		
		while(list.length() > 0) {
			Object first = list.first();
			list = list.butFirst();

			if(first instanceof LogoList) {
				cb.addCase((LogoList)first);
			}
			else if(first instanceof String) {
				String token = (String)first;

				if(token.equalsIgnoreCase("state-features")) {
					first = list.first();
					list = list.butFirst();

					if(first instanceof LogoList) {
						LogoList fvs = (LogoList)first;

						cb.setStateFeatures(NetLogoFeatureSet.manifest(fvs));
					}
					else {
						throw new ExtensionException("Error initializing state-features: expecting a list, got " + first);
					}
				}
				else if(token.equalsIgnoreCase("outcome-features")) {
					first = list.first();
					list = list.butFirst();

					if(first instanceof LogoList) {
						LogoList fvs = (LogoList)first;

						cb.setOutcomeFeatures(NetLogoFeatureSet.manifest(fvs));
					}
					else {
						throw new ExtensionException("Error initializing outcome-features: expecting a list, got " + first);
					}
				}
				else {
					throw new ExtensionException("Error initializing case base from a list: unexpected string " + token
							+ " -- expecting \"state-features\" or \"outcome-features\"");
				}
			}
		}
		return cb;
	}

}
