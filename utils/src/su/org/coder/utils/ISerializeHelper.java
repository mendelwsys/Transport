package su.org.coder.utils;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 21.01.2005
 * Time: 15:19:15
 * To change this template use File | Settings | File Templates.
 */
public interface ISerializeHelper
{
	/**
	 * Восстановление объекта из массива байтов
	 *
	 * @param fromArray массив из которого восстанавливается объект
	 * @return Восстановленный объект
	 * @throws IOException
	 */
	Object createChannelObj(byte[] fromArray) throws IOException;

	/**
	 * Используется когда из входного потока необходимо
	 * считать несколько объектов (например при восстановлении массива)
	 *
	 * @param dis Входной поток из которого необходимо создать обхект
	 * @return Восстановленный объект
	 * @throws IOException
	 */
	Object createChannelObj(DataInputStream dis) throws IOException;

	/**
	 * Перевод объекта в байтовый массив
	 *
	 * @param toArray Объект который переводится в байтовый массив
	 * @return байтовый массив соотвествующий объекту
	 * @throws IOException
	 */

	byte[] serialChannelObj(Object toArray) throws IOException;

	/**
	 * Функция для сериализации объектов в
	 * предоставленый поток, используется если объект необходимо
	 * сериализовать вместе с остальными объектами напрмер при сериализации
	 * массивов
	 *
	 * @param toArray Объект который переводится в байтовый массив
	 * @param dos     выходной поток в который необходимо записать объект
	 * @throws IOException
	 */

	void serialChannelObj(Object toArray, DataOutputStream dos) throws IOException;

	/**
	 * Перевести в печатаемую строку
	 *
	 * @param toPrintable
	 * @return
	 */
	public String toPrintableString(Object toPrintable);
}
