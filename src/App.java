import java.io.*;

/**
 * @author Ian Thomas
 */
public class App
{
	private static BufferedReader inputBufferedReader;
	private static BufferedWriter outputBufferedWriter = new BufferedWriter(new FileWriter("output_file.txt"));
	private static String nextLine;

	public static void main(String[] args)
	{
		openFile(args);
		int nextCommandTime = 0;
		nextCommandTime = readInput();

		while ((System.currentTimeMillis() / 1000.0) <= nextCommandTime)
		{
			nextCommandTime = readInput();
		}
	}

	private static int readInput()
	{
		int systemTime = 0;
		/*
			Do something with the current command;
		 */
		executeCommand();


		/*
			Prepare the next command
		 */
		try
		{
			nextline = inputBufferedReader.readLine();
			systemTime = Integer.valueOf(nextline.substring(0, nextline.indexOf(":")));
			nextline = nextline.substring(nextline.indexOf(":")).trim();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return systemTime;
	}

	private static void executeCommand()
	{
		switch (nextLine.substring(0, nextLine.indexOf("(")-1).toLowerCase())
		{
			case "insert":
				break;
			case "printjob":
				break;
			case "nextjob":
				break;
			case  "previousjob":
				break;
			default:
				break;
		}
	}

	private static void openFile(String[] args)
	{
		try
		{
			inputBufferedReader = new BufferedReader(new FileReader(App.class.getResource(args[2]).toExternalForm()));
		} catch (FileNotFoundException e)
		{
			System.out.println("Could not open the file: " + args[2]);
			e.printStackTrace();

		}
	}
}
