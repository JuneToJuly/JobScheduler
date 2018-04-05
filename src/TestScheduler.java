/**
 * @author Ian Thomas
 */
public class TestScheduler
{
		/*
		99950: Insert(30,58000)
		125900: PrintJob(19)
		199500: Insert(1250,47000)
		229500: Insert(1350,37000)
		230000: PrintJob(30,5200)
		235000: NextJob(1350)
		236000: PreviousJob(1350)
		*/
	public static void main(String[] args)
	{
		CommandReader reader = new CommandReader(args[0]);
		Command command = reader.next(0);
		Scheduler scheduler = new Scheduler(reader, "out.txt");
		String commandType = scheduler.executeCommand(command);

		RedBlackTree rbt = scheduler.getRbt();

		command = reader.next(49950);
		commandType = scheduler.executeCommand(command);

		command = reader.next(99950);
		commandType = scheduler.executeCommand(command);

		command = reader.next(125900);
		commandType = scheduler.executeCommand(command);

		command = reader.next(199500);
		commandType = scheduler.executeCommand(command);

		command = reader.next(229500);
		commandType = scheduler.executeCommand(command);

		command = reader.next(230000);
		commandType = scheduler.executeCommand(command);

		command = reader.next(235000);
		commandType = scheduler.executeCommand(command);

		command = reader.next(236000);
		commandType = scheduler.executeCommand(command);

		scheduler.stop();
	}
}
