/**
 *
 */

import org.nlogo.api.DefaultClassManager;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.ExtensionManager;
import org.nlogo.api.PrimitiveManager;
import org.nlogo.api.ImportErrorHandler;
import org.nlogo.core.ExtensionObject;
import java.util.List;

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
		primManager.addPrimitive("combine", new CombineCaseBases());
		primManager.addPrimitive("all", new AllCases());
		primManager.addPrimitive("remove", new Remove());
		primManager.addPrimitive("state", new State());
		primManager.addPrimitive("decision", new Decision());
		primManager.addPrimitive("outcome", new Outcome());
		primManager.addPrimitive("forget", new Forget());
		primManager.addPrimitive("get-earliest", new GetEarliest());
		primManager.addPrimitive("set-earliest", new SetEarliest());
		primManager.addPrimitive("get-max-size", new GetMaxSize());
		primManager.addPrimitive("set-max-size", new SetMaxSize());
		primManager.addPrimitive("get-rank", new GetRank());
		primManager.addPrimitive("set-rank", new SetRank());
		primManager.addPrimitive("get-time", new GetTime());
		primManager.addPrimitive("set-time", new SetTime());
		primManager.addPrimitive("resize", new Resize());
		primManager.addPrimitive("lambda", new CaseLambda());
		primManager.addPrimitive("match", new Match());
		primManager.addPrimitive("matches", new Matches());
		primManager.addPrimitive("eq", new Equal());
		primManager.addPrimitive("eq?", new EqualBoolean());
		primManager.addPrimitive("lt",  new LessThan());
		primManager.addPrimitive("lt?",  new LessThanBoolean());
		primManager.addPrimitive("gt", new GreaterThan());
		primManager.addPrimitive("gt?", new GreaterThanBoolean());
		primManager.addPrimitive("incmp", new Incomparable());
		primManager.addPrimitive("incmp?", new IncomparableBoolean());
	}

	@Override
	public ExtensionObject readExtensionObject(ExtensionManager reader, String typeName, String value) throws ExtensionException {
		if(typeName == null) {
			throw new IllegalArgumentException("typeName is null");
		}
		if(value == null) {
			throw new IllegalArgumentException("value is null");
		}
//		try {
			//if(typeName.equals(NetLogoCaseBase.typeName)) {
			//	return new NetLogoCaseBase(value);
			//}
			//else if(typeName.equals(NetLogoCase.typeName)) {
		    //		return new NetLogoCase(value);
			//}
			//else
//            if(typeName.equals(NetLogoFeature.typeName)) {
//				return new NetLogoFeature(value);
//			}
//			else if(typeName.equals(NetLogoFeatureValue.typeName)) {
//				return new NetLogoFeatureValue(value);
//			}
//			else if(typeName.equals(NetLogoFeatureSet.typeName)) {
//				return new NetLogoFeatureSet(value);
//			}
			// TODO: Think about how to make this work
//			else if(typeName.equals(NetLogoFeatureValueSet.typeName)) {
//				return new NetLogoFeatureValueSet(value);
//			}
//			else {
				return super.readExtensionObject(reader, typeName, value);
//			}
		}
//		catch(CaseBaseException | ClassNotFoundException | IOException e) {
//
//		}

//		throw new ExtensionException("Unknown extension type");
//	}

		@Override
		public void clearAll() {
			// TODO
		}

		@Override
		public StringBuilder exportWorld() {
			//TODO
			return new StringBuilder();
		}

		@Override
		public void importWorld(List<String[]> lines, ExtensionManager reader, ImportErrorHandler handler) {
			//TODO
		}
}
