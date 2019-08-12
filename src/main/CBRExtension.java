/**
 * 
 */

import org.nlogo.api.DefaultClassManager;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.ExtensionManager;
import org.nlogo.api.PrimitiveManager;
import org.nlogo.core.ExtensionObject;

import java.io.*;

/**
 * @author GP40285
 *
 */
public class CBRExtension extends DefaultClassManager {
	static final String EXTENSION_NAME = "cbr";

	@Override
	public void load(PrimitiveManager primManager) throws ExtensionException {
		//primManager.addPrimitive("from-list", new FromList());
		primManager.addPrimitive("new", new New());
		primManager.addPrimitive("add", new AddCase());
		primManager.addPrimitive("add-case-base", new AddCaseBase());
		primManager.addPrimitive("all", new AllCases());
		primManager.addPrimitive("remove", new Remove());
		primManager.addPrimitive("time", new Time());
		primManager.addPrimitive("state", new State());
		primManager.addPrimitive("decision", new Decision());
		primManager.addPrimitive("outcome", new Outcome());
		primManager.addPrimitive("forget", new Forget());
		primManager.addPrimitive("forgettable", new Forgettable());
		primManager.addPrimitive("max-size", new MaxSize());
		primManager.addPrimitive("set-time", new SetTime());
		primManager.addPrimitive("resize", new Resize());
		primManager.addPrimitive("lambda", new CaseLambda());
		primManager.addPrimitive("same", new Same());
		primManager.addPrimitive("closer", new Closer());
		primManager.addPrimitive("comparable", new Comparable());
		primManager.addPrimitive("yes",  new Yes());
		primManager.addPrimitive("no", new No());
		primManager.addPrimitive("equal", new Equal());
		primManager.addPrimitive("invalid", new Invalid());
	}

	@Override
	public ExtensionObject readExtensionObject(ExtensionManager reader, String typeName, String value) throws ExtensionException {
		if(typeName == null) {
			throw new IllegalArgumentException("typeName is null");
		}
		if(value == null) {
			throw new IllegalArgumentException("value is null");
		}
		try {
			if(typeName.equals(NetLogoCaseBase.typeName)) {
				return new NetLogoCaseBase(value);
			}
			else if(typeName.equals(NetLogoCase.typeName)) {
				return new NetLogoCase(value);
			}
			else if(typeName.equals(NetLogoFeature.typeName)) {
				return new NetLogoFeature(value);
			}
			else if(typeName.equals(NetLogoFeatureValue.typeName)) {
				return new NetLogoFeatureValue(value);			
			}
			else if(typeName.equals(NetLogoFeatureSet.typeName)) {
				return new NetLogoFeatureSet(value);
			}
			// TODO: Think about how to make this work
//			else if(typeName.equals(NetLogoFeatureValueSet.typeName)) {
//				return new NetLogoFeatureValueSet(value);
//			}
			else {
				return super.readExtensionObject(reader, typeName, value);
			}
		}
		catch(CaseBaseException | ClassNotFoundException | IOException e) {

		}

		throw new ExtensionException("Unknown extension type");
	}
}
