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
		return "Job ID: " + id + "Executed time: " + executedTime + "Total time: " + totalTime;
	}
}
