package su.org.coder.multiplexer;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12.02.2005
 * Time: 0:34:50
 * To change this template use File | Settings | File Templates.
 */
public class TMultiplexerEx
		extends Exception
{
	public TMultiplexerEx()
	{
	}

	public TMultiplexerEx(String str)
	{
		super(str);
	}

	public TMultiplexerEx(Throwable e)
	{
		super(e.getMessage());
	}
}