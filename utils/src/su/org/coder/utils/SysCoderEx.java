package su.org.coder.utils;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 01.02.2005
 * Time: 11:43:20
 * To change this template use File | Settings | File Templates.
 */
public class SysCoderEx extends Exception
{
	public int status;

	public SysCoderEx(int status,CallMessageImpl msg) throws IOException
	{
		super((String) new String0AHelper().createChannelObj(msg.bmessage));
//TODO Уяснить семантику остальных полей при выдаче
		this.status = status;
	}

	public String getMessage()
	{
		return "Status : "+status+" Message : "+super.getMessage();
	}

	public SysCoderEx(int status,String str)
	{
		super(str);
		this.status = status;
	}
}
