import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Ian Thomas
 */
public class Scheduler
{
	private MinHeap minHeap;
	private RedBlackTree rbt;
	private CommandReader commandReader;
	private int runningJobCounter;
	private Job runningJob;
	private PrintWriter writer;
	private boolean started;
	private String jobFinish;

	/**
	 * Creates the min-heap and red black tree.
	 */
	public Scheduler(CommandReader commandReader, String filename)
	{
		this.commandReader = commandReader;
		openWriter(filename);
		minHeap = new MinHeap();
		rbt = new RedBlackTree();
		runningJob = null;
		runningJobCounter = 5;
	}

	/**
	 * Writes to the buffer.
	 * @param toWrite
	 */
	public void write(String toWrite)
	{
		writer.println(toWrite);
		writer.flush();
	}

	// Opens writer
	private void openWriter(String filename)
	{
		try
		{
			writer = new PrintWriter(new FileWriter(filename));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Starts the scheduler
	 */
	public void start()
	{
		if(started){return;}
		started = true;
		runningJob = null;
		runningJobCounter = 5;
		Command command = new Command();
		int currentTime = 0;
		boolean stillJobs = true;
		boolean dispatcherFinished = false;

		do
		{
			if(!stillJobs)
			{
				runningJobCounter = 0;
			}
			else
			{
				runningJobCounter--;
				checkJobFinished(currentTime);
			}
			if((command = commandReader.next(currentTime)) == null)
			{
				dispatcherFinished = true;
			}
			else
			{
				executeCommand(command);
			}

			// We can dispatch a new job
			if(runningJobCounter == 0)
			{
				stillJobs = dispatchJob();
			}
			if (runningJobCounter < 0)
			{
				System.out.println(currentTime);
				System.out.println(runningJob.toString());
			}
			currentTime++;
		}while(stillJobs || !dispatcherFinished);
		System.out.println();
	}

	/*
		Checks for jobs to still needing to run
	 */
	private void checkJobFinished(int currentTime)
	{
		if(currentTime == 7420)
		{
			System.out.println();
			minHeap.printHeap();
			System.out.println();
		}
		if(runningJobCounter == 0 && runningJob != null)
		{
			if((runningJob.getExecutedTime() + 5) >= runningJob.getTotalTime())
			{
				System.out.println("Deleting: " + runningJob + " At time: " + currentTime);
//				rbt.delete(runningJob);
//				rbt.printNodeStyle(null);
//				minHeap.extractMin();
			}
			else
			{
				// Increase running time
				minHeap.increaseKey(runningJob, 5);
			}
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

	/*
		Dispatches jobs.
	 */
	private boolean dispatchJob()
	{
		// Get next job
		runningJob = minHeap.peak();

		// No more jobs
		if(runningJob == null)
		{
			return false;
		}

		if(runningJob.getExecutedTime() + 5 >= runningJob.getTotalTime())
		{
			// This means we have less that 5 seconds
			runningJobCounter = runningJob.getTotalTime() - runningJob.getExecutedTime();
				rbt.delete(runningJob);
				minHeap.extractMin();
		}
		else
		{
			// We just run a normal 5 seconds
			runningJobCounter = 5;
		}
		return true;
	}

	/**
	 *  Command to add a job.
	 * @param command
	 */
	public void addJobs(Command command)
	{
		Job newJob = new Job(command.getId(),command.getJobExecutionTime());
		if(command.getId() == 11071)
		{
			System.out.println();
		}
		minHeap.add(newJob);
		rbt.add(newJob);
	}

	/**
	 * Command to print a job, either printing the single job or
	 * a range of jobs.
	 * @param command
	 */
	public void printJob(Command command)
	{
		Job newJob = new Job(command.getId(),command.getJobExecutionTime());
		if(command.getJobExecutionTime() == -1)
		{
			Job job = rbt.search(newJob);
			if (job == null)
			{
				write("(0,0,0)");
			}
			else
			{
				write(job.toString());
			}
		}
		else
		{
			String jobs = rbt.searchInRange(command.getId(), command.getJobExecutionTime());
			if(jobs == null
					|| jobs.isEmpty())
			{
				write("(0,0,0)");
			}
			else
			{
				write(jobs.toString());
			}
		}
	}

	/**
	 * Prints the next largest job.
	 * @param command
	 */
	public void printNextJob(Command command)
	{
		Job newJob = new Job(command.getId(),command.getJobExecutionTime());
		String next = rbt.next(newJob);
		if(next == null)
		{
			write("(0,0,0)");
		}
		else
		{
			write(next);
		}
	}

	/**
	 * Prints the greatest job before the current job.
	 * @param command
	 */
	public void printPreviousJob(Command command)
	{
		Job newJob = new Job(command.getId(),command.getJobExecutionTime());
		String previous = rbt.previous(newJob);
		if(previous == null)
		{
			write("(0,0,0)");
		}
		else
		{
			write(previous);
		}
	}

	public MinHeap getMinHeap()
	{
		return minHeap;
	}

	public RedBlackTree getRbt()
	{
		return rbt;
	}

	public void stop()
	{
		writer.close();
	}
}
