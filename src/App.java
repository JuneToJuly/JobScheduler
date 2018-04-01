import java.io.*;
import java.util.ArrayList;

/**
 * @author Ian Thomas
 */
public class App
{

	public static void main(String[] args)
	{
		Scheduler scheduler = new  Scheduler(new CommandReader(args[0]), "Outter.txt");
		scheduler.start();
		scheduler.stop();
	}
}
