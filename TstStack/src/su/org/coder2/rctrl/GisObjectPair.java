package su.org.coder2.rctrl;
public class GisObjectPair 
{
	public String nameobj;
	public GisObjectIds[] objids;

	public GisObjectPair ()
	{
	}

	public GisObjectPair (
			String nameobj,
			GisObjectIds[] objids )
	{
			this.nameobj=nameobj;
			this.objids=objids;
	}

}
