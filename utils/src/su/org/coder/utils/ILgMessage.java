package su.org.coder.utils;

import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.EOFException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 03.03.2005
 * Time: 16:04:09
 * To change this template use File | Settings | File Templates.
 */
public interface ILgMessage
{
	static final String ERR_HEAD = "Wrong message format unknown header";
	static final String ERR_BODY = "Wrong message format byte array not enough";
	static final String ERR_NOTSUPPORT = "Operation is not supported";

	void sendToReceiver(OutputStream stream) throws IOException;

	byte[] toByteArray();

	public int setByByteArry (byte[] tmp,int offset) throws IOException;


	int setBySender(InputStream stream) throws IOException, EOFException;

	String getAsString();

	void pPrint();

	boolean isFinalMessage();
}
