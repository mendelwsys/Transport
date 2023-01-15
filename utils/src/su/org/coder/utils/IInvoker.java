package su.org.coder.utils;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 01.02.2005
 * Time: 11:37:07
 * To change this template use File | Settings | File Templates.
 */
public interface IInvoker extends IMetaInfo
{
	/**
	 * Основная функция обменом сообщений
	 * @param msg - сообщение, которое переслается для обработки имплементации объекта
	 * @param attr - дополнительные атрибуты, например контекст безопасности
	 * @return - сообщение которым имплементация реагирует на переданное сообщение
	 * @throws IOException
	 * @throws SysCoderEx
	 */
	public CallMessageImpl invoke(CallMessageImpl msg,Vector attr) throws IOException, SysCoderEx;

   /**
	* Показывает  находится ли имплементация в рабочем состоянии, используется для
	* освобождения ресурсов
	* @return состояние иплементации
	* @throws IOException
	* @throws SysCoderEx
	*/
	boolean isOnService() throws IOException, SysCoderEx;	
}
