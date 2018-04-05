/**
 * @author Ian Thomas
 */
public class TestCommandReader
{
	public static void main(String[] args)
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
		CommandReader reader = new CommandReader(args[0]);
		Command command = reader.next(0);
		System.out.println(command.toString());
		command = reader.next(49950);
		System.out.println(command.toString());
		command = reader.next(99950);
		System.out.println(command.toString());
		command = reader.next(125900);
		System.out.println(command.toString());
		command = reader.next(199500);
		System.out.println(command.toString());
		command = reader.next(229500);
		System.out.println(command.toString());
		command = reader.next(230000);
		System.out.println(command.toString());
		command = reader.next(235000);
		System.out.println(command.toString());
		command = reader.next(236000);
		System.out.println(command.toString());
		command = reader.next(236000);
		System.out.println(command.toString());
	}
}
