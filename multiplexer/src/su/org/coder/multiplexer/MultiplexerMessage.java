package su.org.coder.multiplexer;

import su.org.coder.utils.CallMessageImpl;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.06.2004
 * Time: 12:42:23
 * Сообщение которое передается между связанными объектами
 */

//Делаем сообщение мультиплексируемым
public class MultiplexerMessage
		extends CallMessageImpl
		implements IMultiplexerable
{
    public MultiplexerMessage()
	{

	}
	public MultiplexerMessage(CallMessageImpl msg)
	{
		super(msg);
	}

	public MultiplexerMessage(byte command, short TypeID, short ObjID, byte methodCode, byte[] bmessage)
	{
		super(command,TypeID, ObjID,methodCode,bmessage);
	}

	public IMultiplexerable createEmpty()
	{
		return new MultiplexerMessage();
	}
}