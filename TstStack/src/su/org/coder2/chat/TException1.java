package su.org.coder2.chat;
public class TException1 extends Throwable
{
	public String Value;
	public int IsActual;

	public TException1 ()
	{
	}

	public TException1 (
			String Value,
			int IsActual )
	{
			this.Value=Value;
			this.IsActual=IsActual;
	}

}
