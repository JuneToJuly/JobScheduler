import java.io.*;

public class CommandReader
{
	private BufferedReader inputBufferedReader;
	private BufferedWriter outputBufferedWriter = new BufferedWriter(new FileWriter("output_file.txt"));
	private String nextLine;
	private String fileName;
	private Command currentCommand;
	private boolean hasNext;

	public CommandReader(String[] args)
	{
		this.fileName = args[1];
		nextLine = "";
		openFile();
		currentCommand = null;
		hasNext = true;
	}

	private Command next(int time)
	{
		if (time != currentCommand.getTimestamp())
		{
			return new Command("", 0, 0, 0);
		}
		return readNextCommand(time);
	}

	private Command readNextCommand(int time)
	{
		int systemTime = 0;
		String name = "";
		int id = 0;
		int total_time = 0;
		Command currentCopy = new Command("", 0, 0, 0);
		currentCopy = currentCommand;
		currentCopy = new Command();
		try
		{
			nextLine = inputBufferedReader.readLine();
			systemTime = Integer.valueOf(nextLine.substring(0, nextLine.indexOf(":")));
			name = nextLine.substring(nextLine.indexOf(":") + 1, nextLine.indexOf("("));
			nextLine = nextLine.substring(nextLine.indexOf(":")).trim();

			if (!nextLine.contains(","))
			{
				id = Integer.valueOf(nextLine.substring(nextLine.indexOf('(') + 1, nextLine.indexOf(")")));
				total_time = -1;
			}
			else
			{
				id = Integer.valueOf(nextLine.substring(nextLine.indexOf('(') + 1, nextLine.indexOf(",")));
				total_time = Integer.valueOf(nextLine.substring(nextLine.indexOf(',') + 1, nextLine.indexOf(")")));
			}
			createCommand(systemTime, name, id, total_time);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		if (nextLine.isEmpty())
		{
			return null;
		}
		return currentCopy;
	}

	private Command createCommand(int systemTime, String name, int id, int total_time)
	{
		switch (name.toLowerCase())
		{
			case "insert":
				currentCommand.setTimestamp(systemTime);
				currentCommand.setName(name);
				currentCommand.setId(id);
				currentCommand.setJobExecutionTime(total_time);
				break;
			case "printjob":
				currentCommand.setTimestamp(systemTime);
				currentCommand.setName(name);
				currentCommand.setId(id);
				currentCommand.setJobExecutionTime(total_time);
				break;
			case "nextjob":
				currentCommand.setTimestamp(systemTime);
				currentCommand.setName(name);
				currentCommand.setId(id);
				break;
			case "previousjob":
				currentCommand.setTimestamp(systemTime);
				currentCommand.setName(name);
				currentCommand.setId(id);
				break;
			default:
				break;
		}

	}

	private void executeCommand()
	{
		switch (nextLine.substring(0, nextLine.indexOf("(") - 1).toLowerCase())
		{
			case "insert":
				break;
			case "printjob":
				break;
			case "nextjob":
				break;
			case "previousjob":
				break;
			default:
				break;
		}
	}

	private void openFile()
	{
		try
		{
			inputBufferedReader = new BufferedReader(new FileReader(CommandReader.class.getResource(fileName).toExternalForm()));
		} catch (FileNotFoundException e)
		{
			System.out.println("Could not open the file: " + fileName);
			e.printStackTrace();

		}
	}
}
