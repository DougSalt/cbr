import org.nlogo.api.*;
import org.nlogo.api.Command;
import org.nlogo.core.*;

public class SetMaxSize implements Command
{
	@Override
	public void perform(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase)
		{
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();

			if (args[1].get() instanceof Double)
			{
				int maxSize = args[1].getIntValue();
				caseBase.setMaxSize(maxSize);
			}
			else
			{
				throw new ExtensionException("Invalid size");
			}
		}
		else
		{
			throw new ExtensionException("Invalid case-base");
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.commandSyntax(new int[] { Syntax.WildcardType(), Syntax.NumberType() });
	}
}
