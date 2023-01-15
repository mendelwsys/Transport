package su.org.coder.multiplexer.protocols;

import su.org.coder.multiplexer.TMultiplexerEx;
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.06.2004
 * Time: 12:28:29
 * To change this template use File | Settings | File Templates.
 */
public interface ILgChannel
{

	public void put(Object l_message) throws TMultiplexerEx;

	public Object get(int timeOut) throws TMultiplexerEx;

	/**
	 * возвращает размер очереди сообщений в канале
	 *
	 * @return размер очереди
	 * @throws TMultiplexerEx
	 */
	public int getListSize() throws TMultiplexerEx;

	/**
	 * Возвращает идентификационный номер канала
	 * @return идентификационный номер канала
	 */
	public int getRegId();

	/**
	 * Возвращает работает ли вход канала или нет (можно через него чего нибудь передать или нет)
	 * @return true если через канал возможно передача
	 */
	public boolean isActualIn();

    /**
	 * Возвращает работает ли вход канала или нет (можно ли чего нибудь из него получить)
	 * @return true если из канала возможно что нибудь получить
	 */
	public boolean isActualOut();

	public void close() throws TMultiplexerEx;
}