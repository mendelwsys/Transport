package su.org.coder.coder;

import su.org.coder.structs.Decriptors;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 05.02.2005
 * Time: 19:22:03
 * To change this template use File | Settings | File Templates.
 */
public class CheckCoder
{
	public static void main(String args[]) throws IOException
	{


		Coder coder = new Coder();

		Hashtable typedescriptors = new Hashtable();
		{
			Hashtable structrel = new Hashtable();
			structrel.put("dat_1",new Decriptors.StructMemDesc (1,"String#0"));
			structrel.put("dat_2",new Decriptors.StructMemDesc (2,"Integer#2"));
			structrel.put("dat_3",new Decriptors.StructMemDesc (3,"Double#0"));
			coder.inHelperCode("SuperType", "", structrel, typedescriptors);

			Hashtable structrel2 = new Hashtable();
			structrel2.put("dat1",new Decriptors.StructMemDesc (1,"String#1"));
			structrel2.put( "dat2",new Decriptors.StructMemDesc (2,"Double#0"));
			coder.inHelperCode("Exception1", "extends Throwable", structrel2, typedescriptors);

			Hashtable structrel3 = new Hashtable();
			structrel3.put("dat1",new Decriptors.StructMemDesc (1,"String#0"));
			structrel3.put("dat2",new Decriptors.StructMemDesc (2,"String#0"));
			coder.inHelperCode("Exception2", "extends Throwable", structrel3, typedescriptors);

		}

		List structDescTable = new LinkedList();
		Decriptors.MethodDesc sd = new Decriptors.MethodDesc();

		sd.methodName = "method1";

		sd.exceptionNames=new LinkedList();
		sd.exceptionNames.add("Exception1");
		sd.retType = "Integer#2";

		sd.args = new LinkedList();

		Decriptors.ArgDesc agd = new Decriptors.ArgDesc();
		agd.argType = "Integer#2";
		agd.argName = "integer2A_arg1";

		sd.args.add(agd);

		agd = new Decriptors.ArgDesc();
		agd.argType = "SuperType#2";
		agd.argName = "type12A_arg2";

		sd.args.add(agd);

		structDescTable.add(sd);

		coder.inSkelProxyCode("RemoteType1", structDescTable, typedescriptors);


		structDescTable = new LinkedList();
		sd = new Decriptors.MethodDesc();
		sd.methodName = "method0";
		sd.exceptionNames=new LinkedList();
		sd.exceptionNames.add("Exception1");
		sd.exceptionNames.add("Exception2");
		sd.retType = "Integer#0";

		{
			sd.args = new LinkedList();

			agd = new Decriptors.ArgDesc();
			agd.argType = "SuperType#0";
			agd.argName = "superType10A_arg2";

			sd.args.add(agd);
		}

		structDescTable.add(sd);

		coder.inSkelProxyCode("RemoteType2", structDescTable, typedescriptors);

	}

}
