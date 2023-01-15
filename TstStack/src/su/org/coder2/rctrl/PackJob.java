package su.org.coder2.rctrl;
public class PackJob 
{
	public String path;
	public long time;
	public int type;

	public PackJob ()
	{
	}

	public PackJob (
			String path,
			long time,
			int type )
	{
			this.path=path;
			this.time=time;
			this.type=type;
	}

}
