package su.org.coder2.gpstransl;
public class dataStruct_old 
{
	public int dotnumber;
	public boolean isWaitPoint;
	public short lasttag;
	public double time;
	public double delta;
	public double velocity;
	public double deltaD;
	public double deltaS;
	public double absD;
	public double absS;

	public dataStruct_old ()
	{
	}

	public dataStruct_old (
			int dotnumber,
			boolean isWaitPoint,
			short lasttag,
			double time,
			double delta,
			double velocity,
			double deltaD,
			double deltaS,
			double absD,
			double absS )
	{
			this.dotnumber=dotnumber;
			this.isWaitPoint=isWaitPoint;
			this.lasttag=lasttag;
			this.time=time;
			this.delta=delta;
			this.velocity=velocity;
			this.deltaD=deltaD;
			this.deltaS=deltaS;
			this.absD=absD;
			this.absS=absS;
	}

}
