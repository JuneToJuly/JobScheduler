/**
 * @author Ian Thomas
 */
public class Command
{
	private String name;
	private int timestamp;
	private int id;
	private int jobExecutionTime;

	public Command()
	{
		this.name = "";
		this.timestamp = 0;
		this.id = 0;
		this.jobExecutionTime = 0;
	}
	public Command(String name, int timestamp, int id, int jobExecutionTime)
	{
		this.name = name;
		this.timestamp = timestamp;
		this.id = id;
		this.jobExecutionTime = jobExecutionTime;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(int timestamp)
	{
		this.timestamp = timestamp;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getJobExecutionTime()
	{
		return jobExecutionTime;
	}

	public void setJobExecutionTime(int jobExecutionTime)
	{
		this.jobExecutionTime = jobExecutionTime;
	}

	@Override
	public String toString()
	{
		return "Name: " + name + " Timestamp: " + timestamp + " Id: " + id + " ExecutionTime: " + jobExecutionTime;
	}
}
