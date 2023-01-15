package su.org.coder.translator;

import su.org.coder.coder.Coder;

import java.util.List;
import java.util.LinkedList;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 08.02.2005
 * Time: 12:46:09
 * To change this template use File | Settings | File Templates.
 */
public class CheckTransl
{

	public void chekItTransl2(DataInputStream dis,String args[])
	{
		try
		{
			DescriptorGenerator gener = new DescriptorGenerator(args);
			List lst=new LinkedList();

			gener.GenerateTokens(lst,dis);

        	String[] moduleToken= new String[lst.size()];
			for (int i = 0; i < lst.size(); i++)
				moduleToken[i]=(String) lst.get(i);
			gener.CreateModule(moduleToken,0);
			gener.CurrentModule.Print();
			new Coder().GeneratebyModuleDesc(gener.CurrentModule);
		}
		catch (LexException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		catch (TranslException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public void chekItTransl1()
	{
		try
		{
			String instr="module gener2\n" +
					"{\n" +
					"\n" +
			        "struct TCORBAMetaField\n" +
					"{\n" +
					"  string FieldName;\n" +
					"  int DataType;   \n" +
					"  string Ru_FieldName;" +
					"};"+
					"exception TException1\n" +
					"{\n" +
					"  string Value; \n" +
					"  int IsActual;\n" +
					"};\n" +
					"\n" +
					"exception TException2\n" +
					"{\n" +
					"  string Value2; \n" +
					"  int IsActual2;\n" +
					"};\n" +
					"\n" +
					"typedef string arrr [10];\n" +
					"interface ChatInterface {\n" +
					"    void Put (in string message) raises (TException1,TException2);\n" +
					"    string Get (in int timeOut);\n" +
					"    int DestroyObject (in TCORBAMetaField param);\n" +
					"};\n" +
					"\n" +
					"};";


			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(instr.getBytes()));

			DescriptorGenerator gener = new DescriptorGenerator(null);
			List lst=new LinkedList();

			gener.GenerateTokens(lst,dis);

        	String[] moduleToken= new String[lst.size()];
			for (int i = 0; i < lst.size(); i++)
				moduleToken[i]=(String) lst.get(i);
			gener.CreateModule(moduleToken,0);
			gener.CurrentModule.packagename="su.org.coder."+gener.CurrentModule.packagename;
			gener.CurrentModule.Print();

			new Coder().GeneratebyModuleDesc(gener.CurrentModule);

/*
		String [] moduleToken=
				{
			        "typedef","int","TINTA1","[","10","]",";",
					"struct","Struct1","{","double","DBLA0MEMB",";",
					"TINTA1", "INTA2MEMB","[","10","]",";","}",";",
					"exception","Exception1","{","double","DBLA0MEMB",";",
					"TINTA1", "INTA2MEMB","[","10","]",";","}",";",
					"exception","Exception2","{","int","INTA0MEMB",";","}",";",
					"interface", "ClassName1","{",
					"TINTA1","Mehod1","(","in","double","DBLA0ARG",",",
					"out","TINTA1", "INTA1ARG",")",
					"raises","(","Exception1",",","Exception2",")",";",
					"double","Mehod2","(","in","double","DBLA0ARG",",",
					"out","int", "INTA0ARG",")",
					"raises","(","Exception1",")",";"
					,"}",";","EOF"
				};

			File f = new File("D:\\Vlad\\JavaLrn\\Corba\\Proj1\\src\\MyIDL_File.idl");
			FileInputStream fi = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fi);
			System.out.println(dis.readChar());
			System.out.println(dis.readChar());

*/

		}

		catch (LexException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		catch (TranslException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

}
