import java.io.*;
import java.util.ArrayList;

/**
 * @author Ian Thomas
 */
public class App
{

	public static void main(String[] args)
	{
		CommandReader commandReader = new CommandReader(args[2]);
		MinHeap heap = new MinHeap();
		RedBlackTree rbt = new RedBlackTree();

		Job runningJob = null;
		int runningJobCounter = 5;
		Command command = new Command();
		int  currentTime = 0;
		while((command = commandReader.next(currentTime)) != null)
		{
			if((runningJobCounter == 0) && ((runningJob.getTotalTime()-runningJob.getExecutedTime() <= 5)))
			{
				rbt.delete(runningJob);
				heap.extrackMin();
			}
			else if((runningJobCounter == 0) && ((runningJob.getTotalTime()-runningJob.getExecutedTime() > 5)))
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
							Job job = rbt.search(newJob);
						}
						else
						{
							String jobs = rbt.searchInRange(command.getId(), command.getJobExecutionTime());
						}
						break;
					case "nextjob":
						String next = rbt.next(newJob);
						break;
					case "previousjob":
						String previous = rbt.previous(newJob);
						break;
					default:
						break;
				}
				runningJobCounter++;
			}
		}
}
