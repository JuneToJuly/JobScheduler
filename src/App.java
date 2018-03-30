import java.io.*;

/**
 * @author Ian Thomas
 */
public class App
{

	public static void main(String[] args)
	{
		CommandReader commandReader = new CommandReader(args);
		MinHeap heap = new MinHeap();
		RedBlackTree rbt = new RedBlackTree();

		Job runningJob = null;
		int runningJobCounter = 5;
		Command command = new Command();
		int  currentTime = 0;
		while(command = commandReader.next(currentTime) != null)
		{
			if(runningJob == 0 && (runningJob.getTotalTime()-runningJob.getExecutedTime() <= 5))
			{
				rbt.delete(runningJob);
				heap.extrackMin();
			}
			else if(runningJob == 0 && (runningJob.getTotalTime()-runningJob.getExecutedTime() > 5))
			{
				heap.increaseKey(runningJob, 5);
			}

				Job newJob = new Job(command.getId(), command.getJobExecutionTime());

				switch (command.getName().toLowerCase())
				{
					case "insert":
						rbt.add(newJob);
						heap.add(newJob);
						break;
					case "printjob":
						if(command.getJobExecutionTime() == -1)
						{
							String job = rbt.search(newJob);
							// print this
						}
						else
						{
							// We have to print in a range
						}
						break;
					case "nextjob":
							String job = rbt.next(newJob);
							// print the next job
						break;
					case "previousjob":
						String job = rbt.previous(newJob);
						// Print the previous job
						break;
					default:
						break;
				}
				runningJobCounter++;
			}
		}


	}


}
