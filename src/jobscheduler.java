/**
 * @author Ian Thomas
 */
public class jobscheduler
{
	public static void main(String[] args)
	{
		Scheduler scheduler = new  Scheduler(new CommandReader(args[0]), "output_file.txt");
		scheduler.start();
		scheduler.stop();
	}
}
