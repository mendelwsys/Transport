package su.org.coder2.rctrl;
public class ProgressStatus 
{
	public int maxVal;
	public int val;
	public String nameoperation;

	public ProgressStatus ()
	{
	}

	public ProgressStatus (
			int maxVal,
			int val,
			String nameoperation )
	{
			this.maxVal=maxVal;
			this.val=val;
			this.nameoperation=nameoperation;
	}

}
