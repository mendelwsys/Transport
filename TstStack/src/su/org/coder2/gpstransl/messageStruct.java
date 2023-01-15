package su.org.coder2.gpstransl;
public class messageStruct 
{
	public int clientid;
	public int msgnumber;
	public String message;

	public messageStruct ()
	{
	}

	public messageStruct (
			int clientid,
			int msgnumber,
			String message )
	{
			this.clientid=clientid;
			this.msgnumber=msgnumber;
			this.message=message;
	}

}
