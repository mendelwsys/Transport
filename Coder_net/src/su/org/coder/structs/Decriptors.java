package su.org.coder.structs;

import su.org.coder.translator.DescriptorGenerator;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 08.02.2005
 * Time: 13:08:52
 * To change this template use File | Settings | File Templates.
 */
public class Decriptors
{

	public final static String idlbasetypenames[]= {"double","long","string","short","boolean","octet"};
	public final static String reflectbasetypenames[]= {"double","int","String","short","bool","byte"};
	public final static String reflectwraptypenames[]= {"Double","Integer","String","Short","Boolean","Byte"};


	public final static String noobjects[]= {"double","int","short","bool","byte"};
	public final static String createBase[]= {"dis.ReadDouble()","SerialUtils.unserialint32(dis.ReadBytes(4))","SerialUtils.unserialint16(dis.ReadBytes(2))","dis.ReadBoolean()","dis.ReadByte()"};
	public final static String serialBase[]= {"dos.Write(#A)","dos.Write(SerialUtils.serialint32(#A))","dos.Write(SerialUtils.serialint16(#A))","dos.Write(#A)","dos.Write(#A)"};
    public final static Hashtable createBaseCodes= new Hashtable();
	 public final static Hashtable serialBaseCodes= new Hashtable();

    public final static String reflecttypenames[][]={reflectbasetypenames,reflectwraptypenames};
	public static Hashtable basereflang=new Hashtable();
//	public final static String toarraytypes[]={"double","long","int","short","boolean","byte","Double","Integer","String"};
//	public static Hashtable toarraytypesh=new Hashtable();

	static public void InitDecriptors ()
	{
		int iswrap=
				(DescriptorGenerator.options.get(DescriptorGenerator.optarr[DescriptorGenerator.O_wrap])!=null)?1:0;
		for (int i = 0; i < idlbasetypenames.length; i++)
			basereflang.put(idlbasetypenames[i],new TypeDesc(reflecttypenames[iswrap][i],0));
//		for (int i = 0; i < toarraytypes.length; i++)
//			toarraytypesh.put(toarraytypes[i],new Integer(i));

		for (int i = 0; i < noobjects.length; i++)
		{
			createBaseCodes.put(noobjects[i],createBase[i]);
			serialBaseCodes.put(noobjects[i],serialBase[i]);
		}
	}

	public static class TypeDesc
	{
		public String langTypeName;
		public int dim;

		public TypeDesc()
		{
		}

		public TypeDesc(String langTypeName,int dim)
		{

			this.langTypeName = langTypeName;
			this.dim = dim;
		}

		public void Print ()
		{
			System.out.println("reflName: "+langTypeName);
			System.out.println("dim: "+dim);
		}
	}

	public static class StructMemDesc
	{
		public int orderNum;
		public String typedesc;
		public StructMemDesc (int orderNum,String typedesc)
		{

			this.orderNum = orderNum;
			this.typedesc = typedesc;
		}

	}

	public static class StructDesc
	{
		public String structName;
		public Hashtable structrel;

		public StructDesc()
		{
		}

		public StructDesc(String structName,Hashtable structrel)
		{
			this.structName = structName;
			this.structrel = structrel;
		}

		public void Print ()
		{
			System.out.println("StructName: "+structName);
			Enumeration enm = structrel.keys();
			while (enm.hasMoreElements())
			{
				String identifier = (String)enm.nextElement();
				System.out.println("Type: "+((StructMemDesc)structrel.get(identifier)).typedesc);
				System.out.println("OrderId: "+((StructMemDesc)structrel.get(identifier)).orderNum);
				System.out.println("IdentName: "+identifier);
			}
		}
	}

	/**
	 * Структура описания аргумента объекта
	 */
	public static class ArgDesc
	{
		public String argType; //Тип в виде БАЗОВЫЙ_ТИП#РАЗМЕРНОСТЬ
		public String argName; //Обычное имя аргумента
		public String argMod; //Идентификатор входщего исходщего аргумента
		public void Print()
		{
			System.out.println("argMod:" + argMod);
			System.out.println("argType: " + argType);
			System.out.println("argName: " + argName);
		}
	}

	/**
	 * Структура описания метода объекта
	 */
	public static class MethodDesc
	{
		public String methodName; //Имя метода
		public List exceptionNames;//Исключения которые выбрасывает метод
		public String retType; //Тип возвращаемого знаяения в виде БАЗОВЫЙ_ТИП#РАЗМЕРНОСТЬ
		public List args; //Список аргументов в виде списка структур ArgDesc

		public void Print()
		{
			System.out.println("retType: "+retType);
			System.out.println("methodName: "+ methodName);

			System.out.println("Arguments:");
			for (int i = 0; i < args.size(); i++)
			{
				((ArgDesc) args.get(i)).Print();
			}

			System.out.println("Exceptions:");
			for (int i = 0; i < exceptionNames.size(); i++)
			{
				System.out.println((String)exceptionNames.get(i));
 			}
		}
	}

	public static class RemoteType
	{
		public String typeName;
		public List methods;
		public void Print()
		{
			System.out.println("typeName: "+typeName);
			System.out.println("Methods:");
			for (int i = 0; i < methods.size(); i++)
			{
				((MethodDesc) methods.get(i)).Print();
			}
		}
	}

	public static class ModuleDesc
	{
		public String packagename=""; //Имя пакета в которой генерировать соответсвующий код
		public  List structs= new LinkedList();//Описание структур модуля
		public  List exceptions= new LinkedList();//Описание исключений модуля
		public  List remoteTypeList= new LinkedList();//Описание типов для удаленного вызова

		public void Print()
		{
			System.out.println(packagename);
			PrintStrucList(structs);
			PrintStrucList(exceptions);
			for (int i = 0; i < remoteTypeList.size(); i++)
				((RemoteType) remoteTypeList.get(i)).Print();
		}

		private void PrintStrucList(List lstForPrint)
		{
			Iterator itr = lstForPrint.iterator();
			while (itr.hasNext())
				((StructDesc)itr.next()).Print();
		}

	}
}
