/**
 * @author Ian Thomas
 */
public class Job
{
	private int id;
	private int executedTime;
	private int totalTime;

	public Job(int id, int totalTime)
	{
		this.id = id;
		this.executedTime = 0;
		this.totalTime = totalTime;
	}

	/** Test constructor
	 *
	 * @param id
	 * @param totalTime
	 */
	public Job(int id, int totalTime, int executedTime)
	{
		this.id = id;
		this.executedTime = executedTime;
		this.totalTime = totalTime;
	}

	/**
	 *  Sets the total time for a job.
	 * @param totalTime total time the job should take in ms.
	 */
	public void setTotalTime(int totalTime)
	{
		this.totalTime = totalTime;
	}

	/**
	 * Gets the current executed time.
	 * @return executed time for the job.
	 */
	public int getExecutedTime()
	{
		return executedTime;
	}

	/**
	 * Gets the total time of the job.
	 * @return total time for the job.
	 */
	public int getTotalTime()
	{
		return totalTime;
	}

	/**
	 * Gets the id for the job.
	 * @return id for the job.
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Returns the job as a string in the format: Id + executed time + total time;
	 */
	@Override
	public String toString()
	{
		return "(" + id + "," + executedTime + "," + totalTime + ")";
	}

	/**
	 * Checks if two Jobs equal each other based off of:
	 * executed time
	 * total time
	 * id
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj)
	{

		if(!(obj instanceof  Job))
		{
			return false;
		}

		Job job = (Job) obj;

		return (this.executedTime == job.getExecutedTime()
				&& this.totalTime == job.getTotalTime()
				&& this.id == job.getId());
	}

	public void setExecutedTime(int executedTime)
	{
		this.executedTime = executedTime;
	}
}
