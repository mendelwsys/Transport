package su.org.coder2.rctrl;
public class PairOptions 
{
	public String optionname;
	public byte[] value;

	public PairOptions ()
	{
	}

	public PairOptions (
			String optionname,
			byte[] value )
	{
			this.optionname=optionname;
			this.value=value;
	}

}
