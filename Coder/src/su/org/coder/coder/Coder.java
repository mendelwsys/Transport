package su.org.coder.coder;

import su.org.coder.structs.Decriptors;
import su.org.coder.translator.DescriptorGenerator;

import java.io.*;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * User: Administrator
 * Date: 21.01.2005
 * Time: 0:33:41
 *
 */
public class Coder
{
	public static String getcodercharset()
	{
		return "UTF-8";
	}

	private static final String CLASS_HEADER = (
			"import su.org.coder.utils.*;\n" +
			"\n" +
			"import java.io.*;\n" +
			"\n" +
			"public class ");
	private static final String CLASS_CONTINUE = (" implements ISerializeHelper\n" +
			"{\n" +
			"\n" +
			"\tpublic byte[]  serialChannelObj(Object toArray) throws IOException\n" +
			"\t{\n" +
			"\t\tByteArrayOutputStream bos = new ByteArrayOutputStream();\n" +
			"\t\tDataOutputStream dos=new DataOutputStream(bos);\n" +
			"\t    serialChannelObj(toArray,dos);\n" +
			"\t\treturn bos.toByteArray();\n" +
			"\t}\n" +
			"\n" +
			"\tpublic Object createChannelObj(byte[] fromArray) throws IOException\n" +
			"\t{\n" +
			"\t\tif (fromArray==null)\n" +
			"\t\t\treturn null;\n" +
			"\t\tDataInputStream dis=new DataInputStream(new ByteArrayInputStream(fromArray, 0, fromArray.length));\n" +
			"\t\treturn createChannelObj(dis);\n" +
			"\t}\n" +
			"\n"
			);
	private static final String CREATECHANNEL_BEGIN = ("\n\tpublic Object createChannelObj(DataInputStream dis) throws IOException\n" +
			"\t{\n" +
			"\t\tif (dis.readByte()==1)\n" +
			"\t\t\treturn null;\n");
	private static final String SERIALCHANNEL_BEGIN = ("\n\tpublic void serialChannelObj(Object toArray, DataOutputStream dos) throws IOException\n" +
			"\t{\n" +
			"\t\tif (toArray==null)\n" +
			"\t\t{\n" +
			"\t\t\tdos.writeByte(1);\n" +
			"\t\t\treturn;\n" +
			"\t\t}\n" +
			"\t\tdos.writeByte(0);\n");
	private static final String TOPRINTABLE_BEGIN = "\n\tpublic String toPrintableString(Object toPrintable)\n" +
			"\t{\n" +
			"\t\tif (toPrintable instanceof ";
	private static final String TOPRINTABLE_END =
			"\t\telse if (toPrintable==null)\n" +
			"\t\t\treturn \"toPrintable is null\\n\";\n" +
			"\t\treturn \"toPrintable has wrong type\\n\";\n" +
			"\t}\n";
	private static final String GETTYPENAME =
			"\n\tpublic String getTypeName()\n" +
			"\t{\n" +
			"\t\treturn typeName;\n" +
			"\t}\n\n" +
			"\tpublic short getTypeID()\n" +
			"\t{\n" +
			"\t\treturn typeID;\n" +
			"\t}\n\n";
	private static final String PROXYPARENTS =
			"\tpublic boolean isOnService()\n" +
			"\t\t\tthrows SysCoderEx, IOException\n" +
			"\t{\n" +
			"\t\treturn invoker.isOnService();\n" +
			"\t}\n\n";
	private static final String ISONSERVICE =
			"\tpublic boolean isOnService() throws SysCoderEx\n" +
			"\t{\n" +
			"\t\treturn onService;\n" +
			"\t}\n" +
			"\tprotected void setOffLine()\n" +
			"\t{\n" +
			"\t\tonService=false;\n" +
			"\t}\n" +
			"\tprotected void entranceNotify () throws SysCoderEx\n" +
			"\t{\n" +
			"\t\tif (!onService)\n" +
			"\t\t\tthrow new SysCoderEx(Constants.ERR_NO_SERVICE, Constants.ERR_NO_SERVICE_MSG);//Объект отключился\n" +
			"//Установка последнего времени обращения\n" +
			"\t\tlastApply=System.currentTimeMillis();\n" +
			"\t}\n" +
			"\tprotected void leaveNotify ()\n" +
			"\t{\n" +
			"\t\t\n" +
			"\t}\n";
//			"\tpublic boolean isOnService() throws IOException, SysCoderEx\n" +
//			"\t{\n" +
//			"\t\treturn true;\n"+
//			"\t}\n\n"+
//			"\tpublic void entranceNotify ()\n" +
//			"\t{\n" +
//			"//Установка последнего времени обращения\n" +
//			"\t\tlastApply=System.currentTimeMillis();\n" +
//			"\t}\n" +
//			"\n" +
//			"\tpublic void leaveNotify ()\n" +
//			"\t{\n" +
//			"\t\t\n" +
//			"\t}\n";

	private static final String SKEL_INVOKER_BEGIN = "\tpublic CallMessageImpl invoke(CallMessageImpl in, Vector attr) throws IOException, SysCoderEx\n" +
			"\t{\n" +
			"\t\tentranceNotify();\n" +
			"\t\ttry\n" +
			"\t\t{\n" +
			"//Идентификатор typeID уникален для каждого экземпляра класса в пределах процесса\n" +
			"//поэтому невозможно что бы сообщение предназначалось вышестоящим объектам,\n" +
			"//а было обработано по ошибке нижестоящим\n" +
			"\t\tif (in.typeID != typeID)\n" +
			"\t\t\tthrow new SysCoderEx(Constants.ERR_TYPEID, Constants.ERR_TYPEID_MSG); //Здесь нормальная реакция интерсептора\n" +
			"\t\tsetCallCtx(attr);\n" +
			"\t\tDataInputStream dis =(in.bmessage!=null)?\n" +
			"\t\t\t\tnew DataInputStream(new ByteArrayInputStream(in.bmessage, 0, in.bmessage.length)):null;\n" +
			"\t\tByteArrayOutputStream bos = new ByteArrayOutputStream();\n" +
			"\t\tDataOutputStream dos = new DataOutputStream(bos);\n" +
			"\t\tCallMessageImpl retMessage = new CallMessageImpl(in.command==Constants.INVOKE_VAL?Constants.RET_VAL:Constants.RET_VAL_NORET, in.typeID, in.objID, in.methodCode, null);\n" +
			"\t\tswitch (in.methodCode)\n" +
			"\t\t{\n";

/*
	public static class MethodDesc
	{
		public String methodName; //Имя метода
		public List exceptionNames;//Имя исключения которое выьрасывает метод
		public String retType; //Тип возвращаемого знаяения в виде БАЗОВЫЙ_ТИП#РАЗМЕРНОСТЬ
		public List args; //Список аргументов в виде списка структур ArgDesc
	}
*/
/*
 1. Перед тем как вызывать генерацию скелетона, должны быть определены все базовые типы
 учавствующие в вызовах функций, допускаеся лишь генерация хелперов интерфейсов вызова

*/

	private String packagename = "";
	private String pathname = "";

	private String getPackageString()
	{
		return packagename.length() > 0 ? "package " + packagename : "";
	}

	/**
	 * Генератор прокси и склетонов для удаленного объекта
	 *
	 * @param typeName        имя типа для которого генерируется скелетон и прокси
	 * @param structDescTable Описание методов удаленного объекта
	 * @param typedescriptors Извесные имена типов - структура описания типов(пока не ясно какая)
	 * @throws IOException
	 */
	void inSkelProxyCode(String typeName, List structDescTable, Hashtable typedescriptors) throws IOException
	{
		Hashtable regskelhelpers = new Hashtable();
		Hashtable regproxyhelpers = new Hashtable();

		Iterator iter = structDescTable.iterator();

		String scelclassName = "Skel" + typeName;
		String inerfaceName = "I" + typeName;
		String proxyclassName = "Proxy" + typeName;

		if (typedescriptors.get(inerfaceName) == null)
			inICallIntarfaceCode(typeName, structDescTable, typedescriptors);

		String proxyClassCode = getPackageString() + ";\n" +
				"\n" +
				"import su.org.coder.utils.*;\n" +
				"\n" +
				"import java.io.*;\n" +
				"\n" +
				"public class " + proxyclassName + "\n" +
				"\t\timplements I" + typeName + "Proxy ,IMetaInfo\n" +
				"{\n" +
				"\tprivate IInvoker invoker;\n" +
				"\tprivate static String typeName=\"" + typeName + "\";\n" +
				"\tprivate short typeID;\n" +
				"\tprivate short objID;" +
				"\n" +
				"\n" +
				"\t" + proxyclassName + "(IInvoker invoker,short typeID,short objID)\n" +
				"\t{\n" +
				"\t\tthis.invoker = invoker;\n" +
				"\t\tthis.typeID = typeID;\n" +
				"\t\tthis.objID=objID;\n" +
				"\t}\n\n";

		String skelClassCode = "package " + packagename + ";\n" +
				"\n" +
				"import su.org.coder.utils.*;\n" +
				"\n" +
				"import java.io.*;\n" +
				"\n" +
				"import java.util.Vector;\n" +
				"\n" +
				//"import java.util.Enumeration;\n"+
				"\n";
		if (DescriptorGenerator.isTIE())
			skelClassCode += "public class " + scelclassName + "\n" +
					"\t\timplements IInvoker\n";
		else
			skelClassCode += "public abstract class " + scelclassName + "\n" +
					"\t\timplements I" + typeName + " ,IInvoker\n";

		skelClassCode += "{\n" +
				"\tprivate static String typeName=\"" + typeName + "\";\n" +
				"\tprivate short typeID = TypeId.getNextId();\n" +
				"\tprotected long lastApply=System.currentTimeMillis();\n" +
				"\tprotected boolean onService=true;\n" +
				"\tprivate I" + typeName + " impl" +
				(DescriptorGenerator.isTIE() ?
				";\n\n\tpublic " + scelclassName + "( I" + typeName + " impl)\n" +
				"\t{\n" +
				"\t\tthis.impl=impl;\n" +
				"\t}\n"
				: "=this;\n") +
				(DescriptorGenerator.isMob() ? "// TODO" : "") + "\tprivate static ThreadLocal callCtx = new ThreadLocal();\n\n" +
				"\tprotected Object getCallCtx()\n" +
				"\t{\n" +
				(DescriptorGenerator.isMob() ? "\t\treturn null;// TODO" : "") + "\t\treturn callCtx.get();\n" +
				"\t}\n" +
				"\n" +
				"\tprotected void setCallCtx(Object attr)\n" +
				"\t{\n" +
				(DescriptorGenerator.isMob() ? "// TODO" : "") + "\t\tcallCtx.set(attr);//Установить контекст вызова\n" +
				"\t}\n\n";

		String invokerMethodCode = SKEL_INVOKER_BEGIN;


		String proxyMethodCode = "\n";
		int MethodCounter = 0;
		String exAddInfoMemberHelperName = "_exceptionAddInfo0Ah";

		while (iter.hasNext())
		{
			Decriptors.MethodDesc desc = (Decriptors.MethodDesc) iter.next();

			Iterator itarg = desc.args.iterator();

			String[] ret$szTypeNames = desc.retType.split("[#]");
			String retTypeName = ret$szTypeNames[0];
			int retsize = Integer.parseInt(ret$szTypeNames[1]);
			String retarrType = "";
			for (int i = 0; i < retsize; i++)
				retarrType += "[]";

			boolean isExGenerate = desc.exceptionNames != null && !desc.exceptionNames.isEmpty();
			int methodExCounter = 0;
			if (isExGenerate)
			{
				String exMemberHelperName = null;
				String exMemberHelperTypeName = null;
				Iterator exiter = desc.exceptionNames.iterator();
				while (exiter.hasNext())
				{
					String exTypeName = (String) exiter.next();
					exMemberHelperName = "_" + exTypeName.substring(0, 1).toLowerCase() +
							exTypeName.substring(1) + 0 + "Ah";
					exMemberHelperTypeName = exTypeName + 0 + "AHelper";

					if (regskelhelpers.get(exMemberHelperName) == null)
					{
						skelClassCode += "\tprotected ISerializeHelper " + exMemberHelperName + " = new " + exMemberHelperTypeName + "();\n";
						regskelhelpers.put(exMemberHelperName, new Integer(0));
					}

					if (regproxyhelpers.get(exMemberHelperName) == null)
					{
						proxyClassCode += "\tprotected ISerializeHelper " + exMemberHelperName + " = new " + exMemberHelperTypeName + "();\n";
						regproxyhelpers.put(exMemberHelperName, new Integer(0));
					}
					methodExCounter++;
				}
				if (methodExCounter > 1)
				{
					if (regskelhelpers.get(exAddInfoMemberHelperName) == null)
					{
						skelClassCode += "\tprotected ISerializeHelper " + exAddInfoMemberHelperName + " = new ExceptionAddInfo0AHelper ();\n";
						regskelhelpers.put(exAddInfoMemberHelperName, new Integer(0));
					}

					if (regproxyhelpers.get(exAddInfoMemberHelperName) == null)
					{
						proxyClassCode += "\tprotected ISerializeHelper " + exAddInfoMemberHelperName + " = new ExceptionAddInfo0AHelper ();\n";
						regproxyhelpers.put(exAddInfoMemberHelperName, new Integer(0));
					}
				}
			}
			boolean iswrap =
					(DescriptorGenerator.options.get(DescriptorGenerator.optarr[DescriptorGenerator.O_wrap]) != null);

			String retMemberHelperName = "";
			boolean oneway = true;//Флаг того что можно не возвращать сообщения вызывающей стороне
			String retCreateBaseCode = null;
			String retserialBaseCode = null;

			if (!retTypeName.equals("void"))
			{
				oneway = false;
				retMemberHelperName = "_" + retTypeName.substring(0, 1).toLowerCase() +
						retTypeName.substring(1) + retsize + "Ah";
				String retMemeberHelperTypeName = retTypeName + retsize + "AHelper";

				if (typedescriptors.get(retMemeberHelperTypeName) == null)
					inAHelperCode(retTypeName, retsize, typedescriptors);

				if (!iswrap && retsize == 0)
					retCreateBaseCode = (String) Decriptors.createBaseCodes.get(retTypeName);

				if (!iswrap && retsize == 0)
					retserialBaseCode = (String) Decriptors.serialBaseCodes.get(retTypeName);

				if (regskelhelpers.get(retMemberHelperName) == null)
				{
					if (iswrap || retserialBaseCode == null || retCreateBaseCode == null)
						skelClassCode += "\tprotected ISerializeHelper " + retMemberHelperName + " = new " + retMemeberHelperTypeName + "();\n"; //TODO
					regskelhelpers.put(retMemberHelperName, new Integer(0));
				}

				if (regproxyhelpers.get(retMemberHelperName) == null)
				{
					if (iswrap || retserialBaseCode == null || retCreateBaseCode == null)
						proxyClassCode += "\tprotected ISerializeHelper " + retMemberHelperName + " = new " + retMemeberHelperTypeName + "();\n"; //TODO
					regproxyhelpers.put(retMemberHelperName, new Integer(0));
				}
			}

			String skelCallCode = "";
			String proxyCallCode = "";
			String proxyRetCode = "";
			String skelSuffixCallCode = "";

			MethodCounter++;
			invokerMethodCode += "\t\t\tcase " + MethodCounter + " :\n" + "\t\t\t{\n";

			proxyMethodCode += "\tpublic " + retTypeName + retarrType + " " + desc.methodName + "(";
			String toBytesProxyCode = "";

			while (itarg.hasNext())
			{
				Decriptors.ArgDesc argDesc = (Decriptors.ArgDesc) itarg.next();

				oneway &= argDesc.argMod.equals("in"); //Если все аргументы in и метод ничего не возвращает

				if (skelCallCode.length() > 0)
					skelCallCode += ",";


				String[] member$szTypeNames = argDesc.argType.split("[#]");
				String memberTypeName = member$szTypeNames[0];
				int size = Integer.parseInt(member$szTypeNames[1]);
				String arrType = "";
				for (int i = 0; i < size; i++)
					arrType += "[]";

				if (proxyCallCode.length() > 0)
					proxyCallCode += ",";

				String memberHelperName = "_" + memberTypeName.substring(0, 1).toLowerCase() +
						memberTypeName.substring(1) + size + "Ah";
				String memeberHelperTypeName = memberTypeName + size + "AHelper";

				if (typedescriptors.get(memeberHelperTypeName) == null)
					inAHelperCode(memberTypeName, size, typedescriptors);

				String createBaseCode = null;
				if (!iswrap && size == 0)
					createBaseCode = (String) Decriptors.createBaseCodes.get(memberTypeName);

				String serialBaseCode = null;
				if (!iswrap && size == 0)
					serialBaseCode = (String) Decriptors.serialBaseCodes.get(memberTypeName);

				if (regskelhelpers.get(memberHelperName) == null)
				{
					if (iswrap || serialBaseCode == null || createBaseCode == null)  //TODO
						skelClassCode += "\tprotected ISerializeHelper " + memberHelperName + " = new " + memeberHelperTypeName + "();\n";
					regskelhelpers.put(memberHelperName, new Integer(0));
				}

				if (regproxyhelpers.get(memberHelperName) == null)
				{
					if (iswrap || serialBaseCode == null || createBaseCode == null) //TODO
						proxyClassCode += "\tprotected ISerializeHelper " + memberHelperName + " = new " + memeberHelperTypeName + "();\n";
					regproxyhelpers.put(memberHelperName, new Integer(0));
				}

				if (argDesc.argMod.equals("in") || argDesc.argMod.equals("inout"))
				{

					invokerMethodCode += "\t\t\t\t" + memberTypeName + arrType + " "
							+ argDesc.argName + " = " +
							((iswrap || createBaseCode == null) ?
							"(" + memberTypeName + arrType + ")"
							+ memberHelperName + ".createChannelObj(dis);\n" :
							createBaseCode + ";\n"); //TODO
				}

				if (argDesc.argMod.equals("out") || argDesc.argMod.equals("inout"))
				{
					String holderTypeName = inHolderCode(argDesc, typedescriptors);
					proxyCallCode += holderTypeName + " " + argDesc.argName;

					if (argDesc.argMod.equals("inout"))
					{
						if (iswrap || serialBaseCode == null)
							toBytesProxyCode += "\t\t" + memberHelperName +
									".serialChannelObj(" + argDesc.argName + ".value,dos);\n";
						else
							toBytesProxyCode += "\t\t" + serialBaseCode.replaceAll("[#]{1}A",argDesc.argName + ".value")+";\n";

					}

					proxyRetCode += "\t\t\t\t" + argDesc.argName + ".value=" + //TODO
							((iswrap || createBaseCode == null) ? "(" + memberTypeName + arrType + ")" +
							memberHelperName + ".createChannelObj(dis);\n" :
							createBaseCode + ";\n");

					if (argDesc.argMod.equals("inout"))
						invokerMethodCode += "\t\t\t\t\t" + holderTypeName +
								" " + argDesc.argName + "Holder=new " +
								holderTypeName + "(" + argDesc.argName + ");\n";
					else
						invokerMethodCode += "\t\t\t\t\t" + holderTypeName +
								" " + argDesc.argName + "Holder=new " +
								holderTypeName + "();\n";


					skelCallCode += argDesc.argName + "Holder";
					if (iswrap || serialBaseCode == null)
						skelSuffixCallCode += "\t\t\t\t\t" + memberHelperName + ".serialChannelObj("
								+ argDesc.argName + "Holder.value,dos);\n";
					else
						skelSuffixCallCode += "\t\t\t\t\t" + serialBaseCode.replaceAll("[#]{1}A",argDesc.argName + "Holder.value")+";\n";
				}
				else
				{
					proxyCallCode += memberTypeName + arrType + " " + argDesc.argName;
					if (iswrap || serialBaseCode == null)
						toBytesProxyCode += "\t\t" + memberHelperName + ".serialChannelObj(" + argDesc.argName + ",dos);\n";//TODO
					else
						toBytesProxyCode += "\t\t" + serialBaseCode.replaceAll("[#]{1}A",argDesc.argName )+";\n"; //TODO
					skelCallCode += argDesc.argName;

				}
			}

			if (isExGenerate)
			{
				invokerMethodCode += "\t\t\t\ttry\n" +
						"\t\t\t\t{\n";
				invokerMethodCode += "\t";
			}


			if (retTypeName.equals("void"))
			{
				invokerMethodCode += "\t\t\t\timpl." + desc.methodName + "(" + skelCallCode + ");\n";
				invokerMethodCode += skelSuffixCallCode;
			}
			else
			{
				invokerMethodCode += "\t\t\t\t" + retTypeName + retarrType + " " + desc.methodName + retsize + "AretVal" + " = impl." + desc.methodName + "(" + skelCallCode + ");\n";
				if (isExGenerate)
					invokerMethodCode += "\t";
				invokerMethodCode += skelSuffixCallCode;

				if (iswrap || retserialBaseCode == null)
					invokerMethodCode += "\t\t\t\t" + retMemberHelperName + ".serialChannelObj(" + desc.methodName + retsize + "AretVal" + ",dos);\n"; //TODO
				else
					invokerMethodCode += "\t\t\t\t" + retserialBaseCode.replaceAll("[#]{1}A",desc.methodName + retsize + "AretVal")+";\n"; //TODO
			} //потом сериализуем возвращаемое значение

			if (isExGenerate)
			{
				invokerMethodCode +=
						"\t\t\t\t}\n";

				if (methodExCounter == 1)
				{
					String exTypeName = (String) desc.exceptionNames.iterator().next();
					String exName = "_" + exTypeName.substring(0, 1).toLowerCase() +
							exTypeName.substring(1);
					String exMemberHelperName = "_" + exTypeName.substring(0, 1).toLowerCase() +
							exTypeName.substring(1) + 0 + "Ah";
					invokerMethodCode +=
							"\t\t\t\tcatch ( " + exTypeName + " " + exName + " )\n" +
							"\t\t\t\t{\n" +
							"\t\t\t\t\tretMessage.command = Constants.APPEX_VAL;\n" +
							"\t\t\t\t\t" + exMemberHelperName + ".serialChannelObj(" + exName + " ,dos);\n" +
							"\t\t\t\t}\n";
				}
				else
				{
					Iterator exiter = desc.exceptionNames.iterator();
					int i = 0;
					while (exiter.hasNext())
					{
						String exTypeName = (String) exiter.next();
						String exName = "_" + exTypeName.substring(0, 1).toLowerCase() +
								exTypeName.substring(1);
						String exMemberHelperName = "_" + exTypeName.substring(0, 1).toLowerCase() +
								exTypeName.substring(1) + 0 + "Ah";
						invokerMethodCode +=
								"\t\t\t\tcatch ( " + exTypeName + " " + exName + " )\n" +
								"\t\t\t\t{\n" +
								"\t\t\t\t\tretMessage.command = Constants.APPEX_VAL;\n" +
								"\t\t\t\t\tExceptionAddInfo exAddInfo = new ExceptionAddInfo();\n" +
								"\t\t\t\t\texAddInfo.exIdentifier = new Byte((byte)" + i + ");\n" +
								"\t\t\t\t\t" + exAddInfoMemberHelperName + ".serialChannelObj(exAddInfo ,dos);\n" +
								"\t\t\t\t\t" + exMemberHelperName + ".serialChannelObj(" + exName + " ,dos);\n" +
								"\t\t\t\t}\n";
						i++;
					}
				}
			}

			invokerMethodCode += "\t\t\t\tretMessage.bmessage = bos.toByteArray();\n";
			invokerMethodCode += "\t\t\t}\n\t\t\tbreak;\n";

			proxyMethodCode += proxyCallCode + ") throws IOException, SysCoderEx ";
			if (isExGenerate)
			{
				Iterator exiter = desc.exceptionNames.iterator();
				while (exiter.hasNext())
					proxyMethodCode += ", " + (String) exiter.next();
			}
			proxyMethodCode += "\n\t{\n";
			proxyMethodCode += "\t\tByteArrayOutputStream bos = new ByteArrayOutputStream();\n" +
					"\t\tDataOutputStream dos=new DataOutputStream(bos);\n";

			proxyMethodCode += toBytesProxyCode;
			proxyMethodCode += "\t\tCallMessageImpl msg = new CallMessageImpl(Constants." + (oneway ? "INVOKE_VAL_NORET" : "INVOKE_VAL") + ", typeID, objID, (byte)" + MethodCounter + ",\n" +
					"\t\t\t\tbos.toByteArray());\n" +
					"\t\tmsg = invoker.invoke(msg,null);\n" +
					"\n" +
					"\t\tswitch (msg.command)\n" +
					"\t\t{\n" +
					"\t\t\tcase Constants.SYSEX_VAL:\n" +
					"\t\t\t\tthrow new SysCoderEx(Constants.SYSEX_VAL,msg);\n" +
					(oneway ? ("\t\t\tcase Constants.RET_VAL:\n" +
					"\t\t\tcase Constants.RET_VAL_NORET:\n") : "\t\t\tcase Constants.RET_VAL:\n");
			if (!retTypeName.equals("void") || proxyRetCode.length() > 0)
			{
				proxyMethodCode += "\t\t\t\tDataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));\n" +
						proxyRetCode;
			}

			if (retTypeName.equals("void"))
				proxyMethodCode += "\t\t\t\treturn;\n";
			else
			{
				if (iswrap || retCreateBaseCode == null)
					proxyMethodCode += "\t\t\t\treturn (" + retTypeName + retarrType + ")" + retMemberHelperName + ".createChannelObj(dis);\n";
				else
					proxyMethodCode += "\t\t\t\treturn " + retCreateBaseCode + ";\n";
			}

			if (isExGenerate)
			{
				proxyMethodCode += "\t\t\tcase Constants.APPEX_VAL:\n";
				if (methodExCounter == 1)
				{
					String exTypeName = (String) desc.exceptionNames.iterator().next();
					String exMemberHelperName = "_" + exTypeName.substring(0, 1).toLowerCase() +
							exTypeName.substring(1) + 0 + "Ah";
					proxyMethodCode += "\t\t\t\tthrow (" + exTypeName + ")" + exMemberHelperName + ".createChannelObj(msg.bmessage);\n";
				}
				else
				{
					Iterator exiter = desc.exceptionNames.iterator();
					int i = 0;
					proxyMethodCode += "\t\t\t\t{\n" +
							"\t\t\t\t\tDataInputStream dis = new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));\n" +
							"\t\t\t\t\tExceptionAddInfo addInfo = (ExceptionAddInfo) " + exAddInfoMemberHelperName + ".createChannelObj(dis);\n" +
							"\t\t\t\t\tswitch (addInfo.exIdentifier.byteValue())\n" +
							"\t\t\t\t\t{\n";
					while (exiter.hasNext())
					{
						String exTypeName = (String) exiter.next();
						String exMemberHelperName = "_" + exTypeName.substring(0, 1).toLowerCase() +
								exTypeName.substring(1) + 0 + "Ah";
						proxyMethodCode += "\t\t\t\t\t\tcase " + i + ":\n";
						proxyMethodCode += "\t\t\t\t\t\t\tthrow (" + exTypeName + ")" + exMemberHelperName + ".createChannelObj(dis);\n";
						i++;
					}
					proxyMethodCode += "\t\t\t\t\t}\n";
					proxyMethodCode += "\t\t\t\t}\n";
				}
			}
			proxyMethodCode +=
					"\t\t\tdefault:\n" +
					"\t\t\t\tthrow new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);\n" +
					"\t\t}\n\t}\n\n";

		}
		invokerMethodCode += "\t\t\tdefault:\n" +
				"\t\t\t\tthrow new SysCoderEx(Constants.ERR_OPER, Constants.ERR_OPER_MSG);\n";
		invokerMethodCode += "\t\t}\n";
		invokerMethodCode += "\t\treturn retMessage;\n" +
				"\t\t}\n" +
				"\t\tcatch (IOException e)\n" +
				"\t\t{\n" +
				"\t\t\tsetOffLine();\n" +
				"\t\t\tthrow e;\n" +
				"\t\t}\n" +
				"\t\tfinally\n" +
				"\t\t{\n" +
				"    \t\tleaveNotify ();\n" +
				"\t\t}\n" +
				"\t}\n";
		
//Запись склетона
		ByteArrayOutputStream bufos = new ByteArrayOutputStream(1000);
		bufos.write(skelClassCode.getBytes(getcodercharset()));
		bufos.write(invokerMethodCode.getBytes(getcodercharset()));
		bufos.write(GETTYPENAME.getBytes(getcodercharset()));
		bufos.write(ISONSERVICE.getBytes(getcodercharset()));
		bufos.write("}\n".getBytes(getcodercharset()));
		typedescriptors.put(scelclassName, new Integer(0));
		WriteToFile(bufos, pathname + "/" + scelclassName + ".java");
//Запись интерфейса прокси
		bufos = new ByteArrayOutputStream(1000);

		bufos.write((getPackageString() + ";\n" +
				"\n"+
				"import su.org.coder.utils.IProxyParent;\n" +
				"\n" +
				"public interface I"+typeName+"Proxy\n" +
				"\t\textends IProxyParent , I"+typeName+"\n" +
				"{\n" +
				"}\n").getBytes(getcodercharset()));

		WriteToFile(bufos, pathname + "/I" + typeName + "Proxy.java");

//Запись прокси имплементации
		bufos = new ByteArrayOutputStream(1000);
		bufos.write(proxyClassCode.getBytes(getcodercharset()));
		bufos.write(("\n\tpublic static I" + typeName + "Proxy bind(IInvoker invoker,String regName) throws IOException, SysCoderEx\n" +
				"\t{\n" +
				"\n" +
				"    \tString0AHelper s_string0Ah=new String0AHelper();\n" +
				"\n" +
				"    \tCallMessageImpl inimsg=new CallMessageImpl(Constants.INIT_VAL,(short) 0,(short) 0,(byte) 0,\n" +
				"\t\t\t\ts_string0Ah.serialChannelObj(regName+\"#\"+typeName));\n" +
				"\t\tCallMessageImpl retmsg=invoker.invoke(inimsg,null);\n" +
				"\t\tswitch (retmsg.command)\n" +
				"\t\t{\n" +
				"\t\t\tcase Constants.SYSEX_VAL:\n" +
				"\t\t\t\tthrow new SysCoderEx(Constants.SYSEX_VAL,retmsg);\n" +
				"\t\t\tcase Constants.INIT_VAL:\n" +
				"\t\t\t\treturn new " + proxyclassName + "(invoker,retmsg.typeID,retmsg.objID);\n" +
				"\t\t\tdefault:\n" +
				"\t\t\t\tthrow new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);\n" +
				"\t\t}\n" +
				"\t}\n").getBytes(getcodercharset()));
		bufos.write(proxyMethodCode.getBytes(getcodercharset()));
		bufos.write(GETTYPENAME.getBytes(getcodercharset()));
		bufos.write(PROXYPARENTS.getBytes(getcodercharset()));
		bufos.write("}\n".getBytes(getcodercharset()));
		typedescriptors.put(proxyclassName, new Integer(0));
		WriteToFile(bufos, pathname + "/" + proxyclassName + ".java");
	}

	/**
	 * Генератор интерфейса удаленного объекта
	 *
	 * @param typeName        имя типа для которого надо сгенерировать удаленный объект
	 * @param structDescTable Описание методов удаленного объекта
	 * @param typedescriptors Извесные имена типов - структура описания типов(пока не ясно какая)
	 * @throws IOException
	 */
	void inICallIntarfaceCode(String typeName, List structDescTable, Hashtable typedescriptors) throws IOException
	{
		Iterator iter = structDescTable.iterator();
		String inerfaceName = "I" + typeName;
		String ICode = getPackageString() + ";\n" +
				"\n" +
				"import su.org.coder.utils.SysCoderEx;\n" +
				"\n" +
				"import java.io.IOException;\n" +
				"\n" +
				"public interface " + inerfaceName + "\n" +
				"{ \n\n";
		while (iter.hasNext())
		{
			Decriptors.MethodDesc desc = (Decriptors.MethodDesc) iter.next();

			Iterator itarg = desc.args.iterator();

			String[] ret$szTypeNames = desc.retType.split("[#]");
			String rettName = ret$szTypeNames[0];
			int retsize = Integer.parseInt(ret$szTypeNames[1]);
			String retarrType = "";
			for (int i = 0; i < retsize; i++)
				retarrType += "[]";

			ICode += "\t" + rettName + retarrType + " " + desc.methodName + "(";

			String argsCallCode = "";
			while (itarg.hasNext())
			{
				Decriptors.ArgDesc argDesc = (Decriptors.ArgDesc) itarg.next();

				String[] member$szTypeNames = argDesc.argType.split("[#]");
				String memeberTypeName = member$szTypeNames[0];
				int size = Integer.parseInt(member$szTypeNames[1]);
				String arrType = "";

				for (int i = 0; i < size; i++)
					arrType += "[]";


				if (argsCallCode.length() > 0)
					argsCallCode += ",";

				if (argDesc.argMod.equals("out") || argDesc.argMod.equals("inout"))
					argsCallCode += inHolderCode(argDesc, typedescriptors) + " " + argDesc.argName;
				else
					argsCallCode += memeberTypeName + arrType + " " + argDesc.argName;
			}
			ICode += argsCallCode + ") throws IOException, SysCoderEx";
			if (desc.exceptionNames != null && !desc.exceptionNames.isEmpty())
			{
				Iterator exiter = desc.exceptionNames.iterator();
				while (exiter.hasNext())
					ICode += ", " + (String) exiter.next();
			}
			ICode += "; \n\n";
		}

		ByteArrayOutputStream bufos = new ByteArrayOutputStream(1000);
		bufos.write(ICode.getBytes(getcodercharset()));
		bufos.write("}\n".getBytes(getcodercharset()));
		typedescriptors.put(inerfaceName, new Integer(0));
		WriteToFile(bufos, pathname + "/" + inerfaceName + ".java");
	}

	/**
	 * @param argDesc         - описател аргумента
	 * @param typedescriptors - известные типы данных
	 * @return имя холдера
	 * @throws IOException
	 */
	public String inHolderCode(Decriptors.ArgDesc argDesc, Hashtable typedescriptors) throws IOException
	{
		String[] member$szTypeNames = argDesc.argType.split("[#]");
		String memeberTypeName = member$szTypeNames[0];
		int size = Integer.parseInt(member$szTypeNames[1]);
		String className = memeberTypeName + String.valueOf(size) + "AHolder";
		if (typedescriptors.get(className) != null)
			return className;

		String arrType = "";
		for (int i = 0; i < size; i++)
			arrType += "[]";

		String holderCode = getPackageString() + ";\n" +
				"\n" +
				"public class " + className + "\n" +
				"{\n" +
				"\tpublic " + memeberTypeName + arrType + " value;\n" +
				"\tpublic " + className + " ()\n" +
				"\t{\n" +
				"\t}\n" +
				"\n" +
				"\tpublic " + className + " (" + memeberTypeName + arrType + " initialValue)\n" +
				"\t{\n" +
				"\t\tvalue = initialValue;\n" +
				"\t}\n" +
				"}";

		typedescriptors.put(className, new Integer(0));
		WriteToFile(holderCode, pathname + "/" + className + ".java");
		return className;
	}

	/**
	 * Сгенерировать Helper класс по описанию типа
	 *
	 * @param typeName        Имя типа класса для которого генерируется хелпер
	 * @param ParentName      Имя класса от которого может наследоваться данный класс
	 * @param structrel       Соотношение имя парметра -тип  (Имя типа имеет формат БАЗОВЫЙ_ТИП#РАЗМЕРНОСТЬ)
	 * @param typedescriptors Извесные имена типов - структура описания типов(пока не ясно какая)
	 * @throws IOException
	 */
	public void inHelperCode(String typeName, String ParentName, Hashtable structrel, Hashtable typedescriptors) throws IOException
	{
/*
			1. Генерировать класс с именем <typeName> содержащий все поля структуры
			2. Генерировать заголовок класса с именем <typeName><N>AHelper
			3.Для каждого имени параметра генерировать
			 3.1. Member c типом ISerializeHelper и именем
			 <имя_парметра><N>Ah, где N размерность параметра и инициализировать
			 объектом типа с именем <БАЗОВЫЙ_ТИП><N>AHelper
			Проверить существует тип в typedescriptors, если нет то для
			массивов вызвать функцию генерации хелперов для массивов,
			для скалярного типа отсутсвие базового типа означает ошибку
			 3.2 Генерировать код для метода public Object createChannelObj(DataInputStream dis) throws IOException
			 3.3 Генерировать код для метода public void serialChannelObj(Object toArray, DataOutputStream dos) throws IOException
			4. Склеить все куски кода в единый класс (возвращаемое значение)
			5. Занести typedescriptors имя сгенерированного типа
*/
		inStructCode(typeName, ParentName, structrel, typedescriptors);
		ByteArrayOutputStream bufos = new ByteArrayOutputStream(1000);
		bufos.write((getPackageString() + ";\n").getBytes(getcodercharset()));
		bufos.write(CLASS_HEADER.getBytes(getcodercharset()));
		String className = typeName + "0AHelper";
		bufos.write(className.getBytes(getcodercharset()));
		bufos.write(CLASS_CONTINUE.getBytes(getcodercharset()));
		Iterator iter = structrel.keySet().iterator();

		String createChannelObjCode = "\t\t" + typeName + " st = new " + typeName + "();\n";
		String serialChannelObjCode = "\t\t" + typeName + " st = (" + typeName + ") toArray;\n";
		String toPrintableString = TOPRINTABLE_BEGIN
				+ typeName + ")\n" +
				"\t\t{\n" +
				"\t\t\t" + typeName + " st = (" + typeName + ") toPrintable;\n" +
				"\t\t\treturn\n";

		Hashtable helperMem = new Hashtable();
		while (iter.hasNext())
		{
			String identifier = (String) iter.next();
			String[] memeber$szTypeNames = ((Decriptors.StructMemDesc) structrel.get(identifier)).typedesc.split("[#]");
			String memberTypeName = memeber$szTypeNames[0];
			int size = Integer.parseInt(memeber$szTypeNames[1]);
			String arrType = "";
			for (int i = 0; i < size; i++)
				arrType += "[]";

			boolean iswrap =
					(DescriptorGenerator.options.get(DescriptorGenerator.optarr[DescriptorGenerator.O_wrap]) != null)
					|| size > 0;


			String memberHelperTypeName = memberTypeName + size + "AHelper";
			if (typedescriptors.get(memberHelperTypeName) == null)
				inAHelperCode(memberTypeName, size, typedescriptors);

			String memberHelperName = (String) helperMem.get(memberHelperTypeName);

			String createBaseCode = null;
			if (!iswrap)
				createBaseCode = (String) Decriptors.createBaseCodes.get(memberTypeName);

			String serialBaseCode = null;
			if (!iswrap)
				serialBaseCode = (String) Decriptors.serialBaseCodes.get(memberTypeName);

			if (memberHelperName == null)
			{
				memberHelperName = "_" + memberTypeName.substring(0, 1).toLowerCase() +
						memberTypeName.substring(1) + size + "Ah";
				if (iswrap || serialBaseCode == null || createBaseCode == null)
					bufos.write(("\tISerializeHelper " + memberHelperName + " = new " + memberHelperTypeName + "();\n").getBytes(getcodercharset()));
				helperMem.put(memberHelperTypeName, memberHelperName);
			}

			createChannelObjCode +=
					(iswrap || (createBaseCode == null) ?
					"\t\tst." + identifier + "=(" + memberTypeName + arrType + ")" + memberHelperName + ".createChannelObj(dis);\n" :
					"\t\tst." + identifier + " = " + createBaseCode + ";\n");

			serialChannelObjCode += (iswrap || (serialBaseCode == null) ?
					"\t\t" + memberHelperName + ".serialChannelObj( st." + identifier + ",dos);\n" :
					"\t\t" + serialBaseCode.replaceAll("[#]{1}A","st." + identifier)+";\n");
			if (iswrap)
				toPrintableString += "\t\t\t\t" + memberHelperName + ".toPrintableString( st." + identifier + ")+\n";
			else
				toPrintableString += "\t\t\t\t" + "\"" + identifier + ":\"+" + "st." + identifier + "+\" \"+\n";
		}

		createChannelObjCode += "\t\treturn st;\n\t}\n";
		serialChannelObjCode += "\t}\n";
		toPrintableString += "\t\t\t\t\"\\n\";\n" +
				"\t\t}\n" +
				TOPRINTABLE_END;

		bufos.write(CREATECHANNEL_BEGIN.getBytes(getcodercharset()));
		bufos.write(createChannelObjCode.getBytes(getcodercharset()));

		bufos.write(SERIALCHANNEL_BEGIN.getBytes(getcodercharset()));
		bufos.write(serialChannelObjCode.getBytes(getcodercharset()));
		bufos.write(toPrintableString.getBytes(getcodercharset()));
		bufos.write("}\n".getBytes(getcodercharset()));

		typedescriptors.put(className, new Integer(0));
		WriteToFile(bufos, pathname + "/" + className + ".java");
	}

	/**
	 * Сгенерировать Helper класс для массива типов
	 *
	 * @param typeName        Имя типа класса для которого генерируется хелпер
	 * @param size            размерность массива
	 * @param typedescriptors см.inHelperCode
	 * @throws IOException
	 */
	public void inAHelperCode(String typeName, int size, Hashtable typedescriptors) throws IOException
	{
/*
		1. Генерировать заголовок класса с именем <typeName>Helper
		2. Генерировать Member c типом ISerializeHelper и именем
		 <имя_парметра><N>Ah, где N размерность size-1
	    объектом типа с именем <БАЗОВЫЙ_ТИП><N>AHelper
		 2.1 При size==0 проверить наличие типа в typedescriptors если там нет выдать ошибку
		Если size>0 имени в typedescriptors нет вызвать рекурсивно эту функцию для size=size-1
    	3 Генерировать код для метода public Object createChannelObj(DataInputStream dis) throws IOException
		4 Генерировать код для метода public void serialChannelObj(Object toArray, DataOutputStream dos) throws IOException
		5. Склеить все куски кода в единый класс (возвращаемое значение)
		6. Занести typedescriptors имя сгенерированного типа
*/

		if (size == 0) return;

		if (typedescriptors.get(typeName + (size - 1) + "AHelper") == null)
			inAHelperCode(typeName, size - 1, typedescriptors);

		ByteArrayOutputStream bufos = new ByteArrayOutputStream(1000);
		bufos.write((getPackageString() + ";\n").getBytes(getcodercharset()));
		bufos.write(CLASS_HEADER.getBytes(getcodercharset()));
		String className = typeName + size + "AHelper";
		bufos.write(className.getBytes(getcodercharset()));
		bufos.write(CLASS_CONTINUE.getBytes(getcodercharset()));


		String memberHelperName = "_" + typeName.substring(0, 1).toLowerCase() +
				typeName.substring(1) + (size - 1) + "Ah";

		boolean iswrap =
				(DescriptorGenerator.options.get(DescriptorGenerator.optarr[DescriptorGenerator.O_wrap]) != null) || (size - 1) > 0;

		String createBaseCode = null;
		if (!iswrap)
			createBaseCode = (String) Decriptors.createBaseCodes.get(typeName);

		String serialBaseCode = null;
		if (!iswrap)
			serialBaseCode = (String) Decriptors.serialBaseCodes.get(typeName);

		if (iswrap || createBaseCode == null || serialBaseCode == null)
			bufos.write(
					("\tISerializeHelper " + memberHelperName + " = new " + typeName + (size - 1) + "AHelper ();\n\n").getBytes(getcodercharset()));

		String loctype1 = "";
		for (int i = 0; i < size - 1; i++)
			loctype1 += "[]";

		bufos.write(CREATECHANNEL_BEGIN.getBytes(getcodercharset()));


		String createChannelObjCode =
						"\t\tbyte[] buff=new byte[4];\n" +
						"\t\tdis.readFully(buff);\n" +
						"\t\tint length=SerialUtils.unserialint32(buff);\n"+
				"\t\t" + typeName + "[]" + loctype1 + " retArray = new " + typeName + "[length]" + loctype1 + ";\n" +
				 ((iswrap || (createBaseCode == null) || !typeName.equals("byte") )?
				"\t\tfor (int i = 0; i < length; i++)\n" +
				(iswrap || (createBaseCode == null) ?
				"\t\t\tretArray[i]=" + "(" + typeName + loctype1 + ")" + memberHelperName + ".createChannelObj(dis);\n" :
				"\t\t\tretArray[i]=" + createBaseCode + ";\n"
				)
				:
				("\t\tdis.readFully(retArray);\n")
				) 
				+
				"\t\treturn retArray; \n\t}\n";
		bufos.write(createChannelObjCode.getBytes(getcodercharset()));

		bufos.write(SERIALCHANNEL_BEGIN.getBytes(getcodercharset()));
		String serialChannelObjCode = "\t\t" + typeName + "[]" + loctype1 + " st=(" + typeName + "[]" + loctype1 + ") toArray;\n" +
				"\t\tdos.write(SerialUtils.serialint32(st.length));\n" +
				((iswrap || (serialBaseCode == null) || !typeName.equals("byte") )?
				("\t\tfor (int i=0;i<st.length;i++)\n" +
				(iswrap || (serialBaseCode == null) ?
				"\t\t\t" + memberHelperName + ".serialChannelObj(st[i], dos);\n\t}\n" :
				"\t\t\t" + serialBaseCode.replaceAll("[#]{1}A","st[i]")+";\n\t}\n"
				))
				:
				("\t\tdos.write(st);\n\t}\n")
				);
		bufos.write(serialChannelObjCode.getBytes(getcodercharset()));

		bufos.write((TOPRINTABLE_BEGIN
				+ typeName + "[]" + loctype1 + ")\n" +
				"\t\t{\n" +
				"\t\t\tString retVal = \"\";\n" +
				"\t\t\t" + typeName + "[]" + loctype1 + " st=(" + typeName + "[]" + loctype1 + ") toPrintable;\n" +
				"\t\t\tfor (int i=0;i<st.length;i++)\n" +
				(iswrap ?
				"\t\t\t\tretVal+=" + memberHelperName + ".toPrintableString(st[i]);\n" :
				"\t\t\t\tretVal+=st[i];\n") +
				"\t\t\treturn retVal;\n" +
				"\t\t}\n" +
				TOPRINTABLE_END).getBytes(getcodercharset()));
		bufos.write("}\n".getBytes(getcodercharset()));
		typedescriptors.put(className, new Integer(0));
		WriteToFile(bufos, pathname + "/" + className + ".java");
	}

	/**
	 * Генератор структуры сложного типа
	 *
	 * @param typeName        Имя типа класса для которого генерируется структура
	 * @param ParentName      Имя класса от которого может наследоваться данный класс
	 * @param structrel       см.inHelperCode
	 * @param typedescriptors см.inHelperCode
	 * @throws IOException
	 */
	public void inStructCode(String typeName, String ParentName, Hashtable structrel, Hashtable typedescriptors) throws IOException
	{
		ByteArrayOutputStream bufos = new ByteArrayOutputStream(1000);
		String constructor1 = "\tpublic " + typeName + " ()\n\t{\n\t}\n";
		String constructor2_decl = "";
		String constructor2_body = "";

		String structCode = getPackageString() + ";\n" +
				"public class " + typeName + " " + ParentName + "\n" +
				"{\n";
		Iterator iter = structrel.keySet().iterator();

		String[] constructor2_declarr = new String[structrel.keySet().size()];
		String[] constructor2_bodyarr = new String[structrel.keySet().size()];
		String[] classmemyarr = new String[structrel.keySet().size()];

		while (iter.hasNext())
		{

			String identifier = (String) iter.next();

			Decriptors.StructMemDesc structMemDesc = ((Decriptors.StructMemDesc) structrel.get(identifier));
			String[] m$szTypeNames = structMemDesc.typedesc.split("[#]");
			String mTypeName = m$szTypeNames[0];
			int size = Integer.parseInt(m$szTypeNames[1]);
			String arrType = "";
			for (int i = 0; i < size; i++)
				arrType += "[]";
			classmemyarr[structMemDesc.orderNum] = "\tpublic " + mTypeName + arrType + " " + identifier + ";\n";
			constructor2_declarr[structMemDesc.orderNum] = "\t\t\t" + mTypeName + arrType + " " + identifier;
			constructor2_bodyarr[structMemDesc.orderNum] = "\t\t\tthis." + identifier + "=" + identifier + ";\n";
		}

		for (int i = 0; i < classmemyarr.length; i++)
		{
			structCode += classmemyarr[i];
			if (i == classmemyarr.length - 1)
				constructor2_decl += constructor2_declarr[i] + " )\n";
			else
				constructor2_decl += constructor2_declarr[i] + ",\n";
			constructor2_body += constructor2_bodyarr[i];
		}

		structCode += "\n" + constructor1 + "\n";

		if (classmemyarr.length > 0)
		{
			structCode += "\tpublic " + typeName + " (\n";
			structCode += constructor2_decl;
			structCode += "\t{\n";
			structCode += constructor2_body;
			structCode += "\t}\n\n";
		}
		structCode += "}\n";

		typedescriptors.put(typeName, new Integer(0));
		bufos.write(structCode.getBytes(getcodercharset()));
		WriteToFile(bufos, pathname + "/" + typeName + ".java");
	}


	/**
	 * Запись в файл сгенерированного кода
	 *
	 * @param bufos что записывать
	 * @param url   куда записывать
	 * @throws IOException
	 */
	private void WriteToFile(ByteArrayOutputStream bufos, String url) throws IOException
	{
		File f = new File(url);
		FileOutputStream fo = new FileOutputStream(f);
		fo.write(bufos.toByteArray());
		fo.close();
	}


	private void ReadFromFile(ByteArrayOutputStream bufos, String url) throws IOException
	{
		File f = new File(url);
		FileInputStream fi = new FileInputStream(f);
		byte[] buff = new byte[100];
		int len = 0;
		while ((len = fi.read(buff)) > 0)
			bufos.write(buff, 0, len);
	}

	private void WriteToFile(String str, String url) throws IOException
	{
		File f = new File(url);
		FileOutputStream fo = new FileOutputStream(f);
		fo.write(str.getBytes(getcodercharset()));
		fo.close();
	}

	public void GeneratebyModuleDesc(Decriptors.ModuleDesc moddsc) throws IOException
	{
		packagename = moddsc.packagename;

		String pathprefix = (String) DescriptorGenerator.options.get(DescriptorGenerator.optarr[DescriptorGenerator.O_td]);
		if (pathprefix == null)
			pathprefix = ".";

		pathname = pathprefix + "/" + packagename.replace('.', '/');
		new File(pathname).mkdirs();


		Hashtable typedescriptors = new Hashtable();

		Iterator itr = moddsc.structs.iterator();
		while (itr.hasNext())
		{
			Decriptors.StructDesc dsc = ((Decriptors.StructDesc) itr.next());
			inHelperCode(dsc.structName, "", dsc.structrel, typedescriptors);
		}
		itr = moddsc.exceptions.iterator();
		while (itr.hasNext())
		{
			Decriptors.StructDesc dsc = ((Decriptors.StructDesc) itr.next());
			inHelperCode(dsc.structName, "extends Throwable", dsc.structrel, typedescriptors);
		}
		itr = moddsc.remoteTypeList.iterator();
		while (itr.hasNext())
		{
			Decriptors.RemoteType idsc = ((Decriptors.RemoteType) itr.next());
			inSkelProxyCode(idsc.typeName, idsc.methods, typedescriptors);
		}
	}
}
