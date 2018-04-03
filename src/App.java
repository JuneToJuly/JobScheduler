import java.io.*;
import java.util.ArrayList;

/**
 * @author Ian Thomas
 */
public class App
{

	public static void main(String[] args)
	{
		System.out.println(args[0]);
		Scheduler scheduler = new  Scheduler(new CommandReader(args[0]), "output_file.txt");
		scheduler.start();
		scheduler.stop();
	}
}
