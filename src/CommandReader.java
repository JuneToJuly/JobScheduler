import java.io.*;

/**
 * @author Ian Thomas
 */
public class CommandReader
{
	private BufferedReader inputBufferedReader;
	private BufferedWriter outputBufferedWriter;
	private String nextLine;
	private String fileName;
	private Command currentCommand;
	private boolean firstNull;

	public CommandReader(String fileName)
	{
		this.fileName = fileName;
		try
		{
			outputBufferedWriter = new BufferedWriter(new FileWriter("output_file.txt"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		nextLine = "";
		openFile();
		currentCommand = new Command("",0,0,0);
		readNextCommand(0);
	}

	/**
	 * Used to access a command. The timestamp must match the
	 * command's timestamp to retrieve it.
	 * @param time
	 * @return
	 */
	public Command next(int time)
	{
		if(firstNull)
		{
			return null;
		}
		if (time != currentCommand.getTimestamp())
		{
			return new Command("", 0, 0, 0);
		}
		return readNextCommand(time);
	}

	/*
		Reads the next command from the file.
	 */
	private Command readNextCommand(int time)
	{
		int systemTime = 0;
		String name = "";
		int id = 0;
		int total_time = 0;
		Command currentCopy = new Command(currentCommand.getName(),  currentCommand.getTimestamp(), currentCommand.getId(),currentCommand.getJobExecutionTime());
		currentCommand = new Command();
		try
		{
			nextLine = inputBufferedReader.readLine();
			if(nextLine != null)
			{
				systemTime = Integer.valueOf(nextLine.substring(0, nextLine.indexOf(":")));
				name = nextLine.substring(nextLine.indexOf(":") + 2, nextLine.indexOf("("));
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
			}
			createCommand(systemTime, name, id, total_time);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		if(nextLine == null
				&& firstNull)
		{
			return null;
		}
		if(nextLine == null)
		{
			firstNull = true;
		}
		return currentCopy;
	}

	/*
		Creates the command from some given information.
	 */
	private void createCommand(int systemTime, String name, int id, int total_time)
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

	/*
		Opens the file.
	 */
	private void openFile()
	{
		try
		{
			inputBufferedReader = new BufferedReader(new FileReader(new File(fileName)));
		} catch (FileNotFoundException e)
		{
			System.out.println("Could not open the file: " + fileName);
			e.printStackTrace();

		}
	}
}
