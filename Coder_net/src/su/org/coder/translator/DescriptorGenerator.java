package su.org.coder.translator;

import su.org.coder.structs.Decriptors;

import java.util.*;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 06.02.2005
 * Time: 15:04:21
 * To change this template use File | Settings | File Templates.
 */

public class DescriptorGenerator
{

	final static byte spaces[] = {' ', '\t', '\n', 0x0D};
	Hashtable spacesH = new Hashtable();
	final static byte addLiterals[] = {'_'};
	Hashtable addLiteralsH = new Hashtable();

	final static byte servLiterals[] = {';', ',', '[', ']', '{', '}', '(', ')'};
	Hashtable servLiteralsH = new Hashtable();

	final static int G_TDEF = 2;
	final static int G_STRUCT = 3;
	final static int G_EXCEP = 4;
	final static int G_INTRF = 5;

	final static String translatetokens[] = {"typedef", "struct", "exception", "interface"};
	final static int actions[] = {G_TDEF, G_STRUCT, G_EXCEP, G_INTRF};
	Hashtable reflactions = new Hashtable();


//	final static String idlbasetypenames[]= {"double","int","string"};
//	final static String reflecttypenames[]= {"Double","Integer","String"};
	Hashtable reflang = new Hashtable();

	final static String idlargmod[] = {"in", "out", "inout"};
	Hashtable argmods = new Hashtable();

	private static final String MOD_BEGIN = "module";

	private static final String DECL_DELIM = ";";
	private static final String ARG_DELIM = ",";
	private static final String ARR_BEG = "[";
	private static final String ARR_END = "]";
	private static final String DESC_BEG = "{";
	private static final String DESC_END = "}";
	private static final String ARG_BEG = "(";
	private static final String ARG_END = ")";
	private static final String EOF = "EOF";

	public Decriptors.ModuleDesc CurrentModule;

	static public Hashtable options = new Hashtable();

	public static final String optarr[] = {"-f", "-td", "-pkgPrefix","-wrap"};
	public static final int O_f=0;
	public static final int O_td=1;
	public static final int O_pkgPrefix=2;
	public static final int O_wrap=3;
	/*
		allTIE - генерировать скелетоны как композицию c implementation классом
		midlet - генерировать все как с allTIE но орентироваться на мидлет
		server - режим по умолчанию, генерировать абстактный класс который наследуется для имплементации
	*/
	public static final String emitarr[] = {"allTIE", "midlet", "server"};

	static public boolean isTIE()
	{
		String setopt = (String) options.get(optarr[0]);
		return (setopt != null) && (setopt.equals(emitarr[0]) || setopt.equals(emitarr[1]));
	}

	static public boolean isMob()
	{
		String setopt = (String) options.get(optarr[0]);
		return (setopt != null) && setopt.equals(emitarr[1]);
	}


	private void TranslateOption(String arg[])
	{
		if (arg == null)
			return;
		for (int i = 0; i < arg.length; i++)
		{
			if (arg[i] != null)
			{
				for (int j = 0; j < optarr.length; j++)
					if (arg[i].startsWith(optarr[j]))
					{
						String opt = arg[i].substring(optarr[j].length());
						options.put(optarr[j], opt);
						break;
					}
			}
		}
	}

	public DescriptorGenerator(String arg[])
	{
		TranslateOption(arg);
		Decriptors.InitDecriptors();
		Enumeration enkey = Decriptors.basereflang.keys();
		while (enkey.hasMoreElements())
		{
			String key = (String) enkey.nextElement();
			reflang.put(key, Decriptors.basereflang.get(key));
		}

//		for (int i = 0; i < idlbasetypenames.length; i++)
//			reflang.put(idlbasetypenames[i],new TypeDesc(reflecttypenames[i],0));


		for (int i = 0; i < translatetokens.length; i++)
			reflactions.put(translatetokens[i], new Integer(actions[i]));

		for (int i = 0; i < idlargmod.length; i++)
			argmods.put(idlargmod[i], new Integer(i));

		for (int i = 0; i < spaces.length; i++)
			spacesH.put(new Integer(spaces[i]), new Integer(i));

		for (int i = 0; i < addLiterals.length; i++)
			addLiteralsH.put(new Integer(addLiterals[i]), new Integer(i));

		for (int i = 0; i < servLiterals.length; i++)
			servLiteralsH.put(new Integer(servLiterals[i]), new Integer(i));

	}

	private boolean isAllowedLiteral(byte ch)
	{
		return ('A' <= ch && ch <= 'Z') || ('a' <= ch && ch <= 'z') || ('0' <= ch && ch <= '9')
				|| (addLiteralsH.get(new Integer(ch))) != null;
	}

	private boolean isAllowedBeginLiteral(byte ch)
	{
		return ('A' <= ch && ch <= 'Z') || ('a' <= ch && ch <= 'z')
				|| (addLiteralsH.get(new Integer(ch))) != null;
	}

	private boolean isIdentifier(String token)
	{
		return isAllowedBeginLiteral((byte) token.charAt(0));
	};

	public void GenerateTokens(List tokenList, DataInputStream dis) throws LexException, IOException
	{
		try
		{

			boolean isToken = false;
			boolean isLineComment = false;
			boolean isMultiLineComment = false;
			String token = "";
			byte[] btToken = new byte[100];
			int length = 0;
			for (; ;)
			{

				byte ch = dis.readByte();

				while (isLineComment || isMultiLineComment)
				{
					if (isLineComment && ch == '\n')
						isLineComment = false;
					else
						if (isMultiLineComment)
						{
							if (ch == '*')
							{
								ch = dis.readByte();
								if (ch == '/')
									isMultiLineComment = false;
								else
									continue;
							}
						}
					ch = dis.readByte();
				}

				if (ch == '/')
				{
					ch = dis.readByte();
					if (ch == '*')
						isMultiLineComment = true;
					else
						if (ch == '/')
							isLineComment = true;
						else
							throw new LexException();
				}
				else
					if (spacesH.get(new Integer(ch)) != null)
					{
						if (ch == 0x0D && dis.readByte() != '\n')
							throw new LexException();

						if (isToken)
						{
							token += new String(btToken, 0, length);
							tokenList.add(token);
							isToken = false;
							length = 0;
							token = "";
						}
					}
					else
						if (!isToken && isAllowedLiteral(ch))
						{
							isToken = true;
							btToken[0] = ch;
							length = 1;
						}
						else
							if (!isToken && servLiteralsH.get(new Integer(ch)) != null)
							{
								byte[] bt = {ch};
								tokenList.add(new String(bt));
								token = new String();
							}
							else
								if (isToken && isAllowedLiteral(ch))
								{
									if (length >= 100)
									{
										token += new String(btToken, 0, length);
										length = 0;
									}
									btToken[length] = ch;
									length++;
								}
								else
									if (isToken && servLiteralsH.get(new Integer(ch)) != null)
									{
										token += new String(btToken, 0, length);
										tokenList.add(token);
										byte[] bt = {ch};
										tokenList.add(new String(bt));

										isToken = false;
										length = 0;
										token = "";
									}
									else
									{
										byte[] bt = {ch};
										System.out.println(new String(bt));
										throw new LexException();
									}
			}
		}
		catch (EOFException e)
		{
			tokenList.add(EOF);
		}

	}

	private int CreateArrayDesc(String[] tokens, int beg, Decriptors.TypeDesc tdsc) throws TranslException
	{
		while (!finishSacan(tokens[++beg], DECL_DELIM))
		{
			if (tokens[beg].equalsIgnoreCase(ARR_BEG))
			{
				++beg;//TODO Проверить что считали цифирь
				if (!tokens[++beg].equalsIgnoreCase(ARR_END))
					throw new TranslException();
				tdsc.dim++;
			}
			else
				throw new TranslException();
		}
		return ++beg;
	}

	private int CreateMemeber(String[] memberTokens, int beg, Hashtable structrel, int orderNum) throws TranslException
	{
		Decriptors.TypeDesc tbasedsc = (Decriptors.TypeDesc) reflang.get(memberTokens[beg]);
		if (tbasedsc == null)
			throw new TranslException();
		String identif = memberTokens[++beg];
		if (!isIdentifier(identif))
			throw new TranslException();
		Decriptors.TypeDesc tdesc = new Decriptors.TypeDesc(tbasedsc.langTypeName, tbasedsc.dim);
		beg = CreateArrayDesc(memberTokens, beg, tdesc);
		if (structrel.put(identif, new Decriptors.StructMemDesc(orderNum, tdesc.langTypeName + "#" + tdesc.dim)) != null)
			throw new TranslException();
		return beg;
	}

	private int CreateStruct(String[] tokens, int beg, Hashtable structrel) throws TranslException
	{
		if (tokens[beg].equalsIgnoreCase(DESC_BEG))
		{
			++beg;
			int i = 0;
			do
			{
				beg = CreateMemeber(tokens, beg, structrel, i);
				i++;
				if (tokens.length <= beg)
					throw new TranslException();
			}
			while (!finishSacan(tokens[beg], DESC_END));
			if (tokens[++beg].equals(DECL_DELIM))
				return ++beg;
		}
		throw new TranslException();
	}

	private int CreateTypeDef(String[] typeTokens, int beg) throws TranslException
	{
		Decriptors.TypeDesc tbasedsc = (Decriptors.TypeDesc) reflang.get(typeTokens[beg]);
		if (tbasedsc == null)
			throw new TranslException();
		String typeidentif = typeTokens[++beg];

		if (!isIdentifier(typeidentif))
			throw new TranslException();

		Decriptors.TypeDesc tdesc = new Decriptors.TypeDesc(tbasedsc.langTypeName, tbasedsc.dim);
		beg = CreateArrayDesc(typeTokens, beg, tdesc);
		reflang.put(typeidentif, tdesc);
		return beg;
	}

	private int CreateExceptList(String[] tokens, int beg, List exceptions) throws TranslException
	{
		if (tokens[beg].equals(DECL_DELIM))
			return ++beg;

		if (!tokens[beg].equals("raises") || !tokens[++beg].equals(ARG_BEG))
			throw new TranslException();
		do
		{
			Decriptors.TypeDesc tdsc = (Decriptors.TypeDesc) reflang.get(tokens[++beg]);
			if (tdsc == null || tdsc.dim != 0)
				throw new TranslException();
			exceptions.add(tdsc.langTypeName);
		}
		while (finishSacan(tokens[++beg], ARG_DELIM));

		if (tokens[beg].equals(ARG_END) && tokens[++beg].equals(DECL_DELIM))
			return ++beg;
		throw new TranslException();
	}

	private int CreateArgList(String[] tokens, int beg, List args) throws TranslException
	{
		if (tokens[beg].equals(ARG_BEG))
		{
			if (tokens[beg + 1].equals(ARG_END))
				return beg + 2;
			do
			{
				Decriptors.ArgDesc adsc = new Decriptors.ArgDesc();
				if (argmods.get(tokens[++beg]) == null)
					throw new TranslException();
				adsc.argMod = tokens[beg];

				Decriptors.TypeDesc tdsc = (Decriptors.TypeDesc) reflang.get(tokens[++beg]);
				if (tdsc == null)
					throw new TranslException();
				adsc.argType = tdsc.langTypeName + "#" + tdsc.dim;
				adsc.argName = tokens[++beg];
				if (!isIdentifier(adsc.argName))
					throw new TranslException();
				args.add(adsc);
			}
			while (finishSacan(tokens[++beg], ARG_DELIM));
			if (tokens[beg].equals(ARG_END))
				return ++beg;
		}
		throw new TranslException();
	}

	private int CreateMethod(String[] typeTokens, int beg, List methods) throws TranslException
	{

		Decriptors.MethodDesc mdsc = new Decriptors.MethodDesc();
		if (typeTokens[beg].equals("void"))
		{
			mdsc.retType = "void#0";
		}
		else
		{
			Decriptors.TypeDesc tbasedsc = (Decriptors.TypeDesc) reflang.get(typeTokens[beg]);
			if (tbasedsc == null)
				throw new TranslException();
			mdsc.retType = tbasedsc.langTypeName + "#" + tbasedsc.dim;
		}
		mdsc.methodName = typeTokens[++beg];
		if (!isIdentifier(mdsc.methodName))
			throw new TranslException();

		mdsc.args = new LinkedList();
		beg = CreateArgList(typeTokens, ++beg, mdsc.args);
		mdsc.exceptionNames = new LinkedList();
		beg = CreateExceptList(typeTokens, beg, mdsc.exceptionNames);
		methods.add(mdsc);
		return beg;
	}

	private int CreateRemoteType(String[] tokens, int beg, Decriptors.RemoteType remoteType) throws TranslException
	{
		remoteType.methods = new LinkedList();
		if (!tokens[beg].equals(DESC_BEG))
			throw new TranslException();
		++beg;
		do
		{
			beg = CreateMethod(tokens, beg, remoteType.methods);
		}
		while (!finishSacan(tokens[beg], DESC_END));
		if (tokens[++beg].equals(DECL_DELIM))
			return ++beg;
		throw new TranslException();
	}

	private int CreateDescriptor(String[] moduleTokens, int beg) throws TranslException
	{
		Integer reflID = ((Integer) reflactions.get(moduleTokens[beg]));
		if (reflID == null)
			throw new TranslException();
		switch (reflID.intValue())
		{
			case G_TDEF:
				{
					beg = CreateTypeDef(moduleTokens, ++beg);
					break;
				}
			case G_STRUCT:
				{
					Decriptors.StructDesc desc = new Decriptors.StructDesc(moduleTokens[++beg], new Hashtable());
					if (!isIdentifier(desc.structName))
						throw new TranslException();
					beg = CreateStruct(moduleTokens, ++beg, desc.structrel);
					reflang.put(desc.structName, new Decriptors.TypeDesc(desc.structName, 0));
					CurrentModule.structs.add(desc);
					break;
				}
			case G_EXCEP:
				{
					Decriptors.StructDesc desc = new Decriptors.StructDesc(moduleTokens[++beg], new Hashtable());
					if (!isIdentifier(desc.structName))
						throw new TranslException();
					if (!moduleTokens[beg + 1].equalsIgnoreCase(DESC_BEG)
							|| !moduleTokens[beg + 2].equalsIgnoreCase(DESC_END))
						beg = CreateStruct(moduleTokens, ++beg, desc.structrel);
					reflang.put(desc.structName, new Decriptors.TypeDesc(desc.structName, 0));
					CurrentModule.exceptions.add(desc);
					break;
				}
			case G_INTRF:
				{
					Decriptors.RemoteType remoteType = new Decriptors.RemoteType();
					remoteType.typeName = moduleTokens[++beg];
					if (!isIdentifier(remoteType.typeName))
						throw new TranslException();
					beg = CreateRemoteType(moduleTokens, ++beg, remoteType);
					CurrentModule.remoteTypeList.add(remoteType);
					break;
				}
			default:
				throw new TranslException();
		}
		return beg;
	}

	public int CreateModule(String[] tokens, int beg) throws TranslException
	{
		int retval=_CreateModule(tokens,beg);
		String prefix=(String) DescriptorGenerator.options.get(DescriptorGenerator.optarr[O_pkgPrefix]);
		if (prefix!=null && prefix.length()>0)
			CurrentModule.packagename =prefix+"."+CurrentModule.packagename;
		return retval;
	}

	private int _CreateModule(String[] tokens, int beg) throws TranslException
	{
		if (tokens[beg].equals(MOD_BEGIN))
		{
			CurrentModule = new Decriptors.ModuleDesc();
			CurrentModule.packagename = tokens[++beg]; //TODO Выяснить возможный формат модуля

			if (tokens[++beg].equals(DESC_BEG))
			{
				++beg;
				do
				{
					beg = CreateDescriptor(tokens, beg);
				}
				while (!finishSacan(tokens[beg], DESC_END));
				if (tokens[++beg].equals(DECL_DELIM) && tokens[++beg].equals(EOF))
					return beg;
			}
		}
		else
		{
			CurrentModule = new Decriptors.ModuleDesc();
			while (!tokens[beg].equals(EOF))
				beg = CreateDescriptor(tokens, beg);
			return beg;
		}
		throw new TranslException();
	}

	/**
	 * @param token       текущая лексема
	 * @param sampleToken лексема с которой надо сравнивать
	 * @return истенно если token и sampleToken совпадают
	 * @throws TranslException
	 */
	private boolean finishSacan(String token, String sampleToken) throws TranslException
	{
		if (token.equals(EOF)) throw new TranslException(); //Ради этого собственно все и затевалось
		return sampleToken.equals(token);
	}

}
