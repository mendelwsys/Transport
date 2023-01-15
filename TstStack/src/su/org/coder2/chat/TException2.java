package su.org.coder2.chat;
public class TException2 extends Throwable
{
	public String Value2;
	public int IsActual2;

	public TException2 ()
	{
	}

	public TException2 (
			String Value2,
			int IsActual2 )
	{
			this.Value2=Value2;
			this.IsActual2=IsActual2;
	}

}
