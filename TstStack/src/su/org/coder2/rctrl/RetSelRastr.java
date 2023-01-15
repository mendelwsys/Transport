package su.org.coder2.rctrl;
public class RetSelRastr 
{
	public int xCrd;
	public int yCrd;
	public int xP4;
	public int yP4;
	public byte[] pict;

	public RetSelRastr ()
	{
	}

	public RetSelRastr (
			int xCrd,
			int yCrd,
			int xP4,
			int yP4,
			byte[] pict )
	{
			this.xCrd=xCrd;
			this.yCrd=yCrd;
			this.xP4=xP4;
			this.yP4=yP4;
			this.pict=pict;
	}

}
