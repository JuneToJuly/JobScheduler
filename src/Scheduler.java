/**
 * @author Ian Thomas
 */
public class Scheduler
{
	private MinHeap<Job> minHeap;
	private RedBlackTree<Job> rbt;

	/**
	 * Creates the min-heap and red black tree.
	 */
	public Scheduler()
	{
		minHeap = new MinHeap<>();
		rbt = new RedBlackTree<>();
	}

	/**
	 * 1
	 *
	 */
	public void dispatchJob()
	{
		// Get the job with least amount of runtime
		// Run job for 5ms
	}

	/**
	 * 2
	 * @param jobs
	 */
	public void addJobs(Job... jobs)
	{
		for (Job currentJob: jobs)
		{
			minHeap.add(currentJob);
			rbt.add(currentJob);
		}
	}

	/**
	 * 3
	 * @param id
	 */
	public void printJob(int id)
	{
		System.out.println(rbt.get(id).toString());
	}

	/**
	 * 4
	 * @param low
	 * @param high
	 */
	public void printJobsInRange(int low, int high)
	{
		Job currentJob;
		for (int i = low; i < high; i++)
		{
			if((currentJob = rbt.get(i)) != null)
			{
				System.out.println(currentJob.toString());
			}
		}
	}

	/**
	 * 5
	 * @param id
	 */
	public void printNextJob(int id)
	{
		// Not sure how to find the smallest next job id, must be something in RBTs that I don't
		// know of.

	}

	/**
	 * 6
	 * @param id
	 */
	public void printPreviousJob(int id)
	{
		// same as the printNextJob
	}



}
