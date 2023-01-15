package su.org.coder2.rctrl;
public class ChangePos 
{
	public double dscale;
	public int dxCrd;
	public int dyCrd;
	public GisObjectIds[] objids;

	public ChangePos ()
	{
	}

	public ChangePos (
			double dscale,
			int dxCrd,
			int dyCrd,
			GisObjectIds[] objids )
	{
			this.dscale=dscale;
			this.dxCrd=dxCrd;
			this.dyCrd=dyCrd;
			this.objids=objids;
	}

}
