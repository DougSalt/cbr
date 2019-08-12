import org.nlogo.api.*;
import org.nlogo.api.Command;
import org.nlogo.core.*;

public class Forgettable implements Command
{
	@Override
	public void perform(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoCaseBase)
		{
			NetLogoCaseBase caseBase = (NetLogoCaseBase) args[0].get();

			if (args[1].get() instanceof Boolean)
			{
				boolean forgettable = args[1].getBoolean();
				caseBase.setForgettable(forgettable);
			}
			else
			{
				throw new ExtensionException("Expected second argument to be a boolean");
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
		return SyntaxJ.commandSyntax(new int[] { Syntax.WildcardType(), Syntax.BooleanType() });
	}
}
