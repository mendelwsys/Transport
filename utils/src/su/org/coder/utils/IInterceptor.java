package su.org.coder.utils;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 21.03.2005
 * Time: 19:14:48
 * To change this template use File | Settings | File Templates.
 */
public interface IInterceptor extends IInvoker
{

	/**
	 * Зарегистрировать следующий интерсептор в цепочке обслуживания
	 *
	 * @param next Укзатель на следующий интерсептор
	 */
	public void registerInterseptor(IInterceptor next);

	/**
	 * Функции исполнения работы интерсептора, сделано для того что бы выделить код
	 * который придется писать ручками, от кода который генерируется автоматом.
	 *
	 * @param msg
	 * @param attr
	 * @return
	 * @throws IOException
	 * @throws SysCoderEx
	 */
	public CallMessageImpl preInvoke(CallMessageImpl msg, Vector attr,SysCoderEx ex) throws IOException, SysCoderEx;

	/**
	 * @param msg
	 * @param attr
	 * @param ex
	 * @return
	 * @throws IOException
	 * @throws SysCoderEx
	 */
	public CallMessageImpl postInvoke(CallMessageImpl msg, Vector attr, SysCoderEx ex) throws IOException, SysCoderEx;

}
