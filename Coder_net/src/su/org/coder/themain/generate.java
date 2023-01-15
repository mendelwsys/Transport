package su.org.coder.themain;

import su.org.coder.translator.CheckTransl;

import java.io.File;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 08.02.2005
 * Time: 16:15:51
 * To change this template use File | Settings | File Templates.
 */
public class generate
{
	public static void main(String args[]) throws FileNotFoundException
	{
		if (args.length==0)
			throw new FileNotFoundException();
		File f = new File(args[0]);
		DataInputStream dis = new DataInputStream(new FileInputStream(f));
  	    new CheckTransl().chekItTransl2(dis,args);
	}

}
