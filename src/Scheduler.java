/**
 * @author Ian Thomas
 */
public class Scheduler
{
	private MinHeap minHeap;
	private RedBlackTree rbt;
	private CommandReader commandReader;

	/**
	 * Creates the min-heap and red black tree.
	 */
	public Scheduler(CommandReader commandReader)
	{
		this.commandReader = commandReader;
		minHeap = new MinHeap();
		rbt = new RedBlackTree();
	}

	/**
	 * Starts the scheduler
	 */
	public void start()
	{
		Job runningJob = null;
		int runningJobCounter = 5;
		Command command = new Command();
		int  currentTime = 0;

		while((command = commandReader.next(currentTime)) != null)
		{
			checkJobStatus(runningJob, runningJobCounter, currentTime);

			executeCommand(command);

			dispatchJob();
			runningJobCounter++;
		}

	}

	/**
	 * I want to do this externally.
	 * @param command
	 */
	public String executeCommand(Command command)
	{
		String commandType = "";
		switch (command.getName().toLowerCase())
		{
			case "insert":
				addJobs(command);
				commandType = "insert";
				break;
			case "printjob":
				printJob(command);
				commandType = "printjob";
				break;
			case "nextjob":
				printNextJob(command);
				commandType = "printNextJob";
				break;
			case "previousjob":
				printPreviousJob(command);
				commandType = "prinPrevJob";
				break;
			default:
				break;
		}
		return commandType;
	}

	/**
	 *
	 * @param runningJob
	 * @param runningJobCounter
	 * @param currentTime
	 */
	public void checkJobStatus(Job runningJob, int runningJobCounter, int currentTime)
	{
		if((runningJobCounter == 0) && ((runningJob.getTotalTime()-runningJob.getExecutedTime() <= 5)))
		{
			rbt.delete(runningJob);
			minHeap.extrackMin();
		}
		else if((runningJobCounter == 0) && ((runningJob.getTotalTime()-runningJob.getExecutedTime() > 5)))
		{
			minHeap.increaseKey(runningJob, 5);
		}
	}

	public void dispatchJob()
	{

	}

	/**
	 *
	 * @param command
	 */
	public void addJobs(Command command)
	{
		Job newJob = new Job(command.getId(),command.getJobExecutionTime());
		minHeap.add(newJob);
		rbt.add(newJob);
	}

	/**
	 *
	 * @param command
	 */
	public void printJob(Command command)
	{
		Job newJob = new Job(command.getId(),command.getJobExecutionTime());
		if(command.getJobExecutionTime() == -1)
		{
			Job job = rbt.search(newJob);
			System.out.println(job.toString());
		}
		else
		{
			String jobs = rbt.searchInRange(command.getId(), command.getJobExecutionTime());
			System.out.println(jobs.toString());
		}
	}

	/**
	 *
	 * @param command
	 */
	public void printNextJob(Command command)
	{
		Job newJob = new Job(command.getId(),command.getJobExecutionTime());
		String next = rbt.next(newJob);
		System.out.println(next);
	}

	/**
	 *
	 * @param command
	 */
	public void printPreviousJob(Command command)
	{
		Job newJob = new Job(command.getId(),command.getJobExecutionTime());
		String previous = rbt.previous(newJob);
		System.out.println(previous);
	}


	public MinHeap getMinHeap()
	{
		return minHeap;
	}

	public RedBlackTree getRbt()
	{
		return rbt;
	}
}
