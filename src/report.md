# Advanced Data Structures Report

### Information:
1. Name: Ian Thomas
2. UFID:
3. EMAIL:

### Structure:
The program is separated into three components: the scheduler, the
command reader, and the backend data structures(Min Heap, Red Black Tree). The command reader reads a command and
holds it until the appropriate time stamp. The scheduler is querying the the command reader each time step to see if
the scheduler can execute the command. Additionally, the scheduler is dispatching a job whenever the processor has
opened up. The scheduler uses the data structures for efficient job dispatching.

Once all commands have been read, the scheduler finishes running jobs and exits.

**DataFlow Diagram:**

![classDiagram](dataflow.png)

**Class Diagram:**
![classDiagram](cd.png)

**RedBlackTree.java**
```java
    // Searching methods
	public Job search(Job newJob);
	public String next(Job newJob);
	public String previous(Job newJob);
	public String searchInRange(int startId, int tailId);

	// Util method for searches
	private String searchInRangeRecursive(RedBlackNode node, int startId, int tailId);
	private RedBlackNode search(RedBlackNode node, int key);

    // Enums for easy switches/compares
	public enum Color { RED, BLACK}
	public enum InsertRotation { NONE, RRr, RRb, LLr, LLb, RLr, RLb, LRr, LRb}
	public enum DeleteRotation { Rb01, Rb02, Rb11, Rb12, Rb2, Rr0, Rr11, Rr12, Rr2,
								 Lb01, Lb02, Lb11, Lb12, Lb2, Lr0, Lr11, Lr12, Lr2}

	public void delete(Job toDelete);
	public void add(Job currentJob);

	// Util methods
	private RedBlackNode findDeficientNode(int degree, RedBlackNode node);
	private int checkDegreeOfDeletion(RedBlackNode next);
	private void performDeleteRotation(RedBlackNode deletedNode, DeleteRotation deleteRotation);
	private RedBlackNode checkForPropertyMaintained(RedBlackNode node);
	private void performRotation(RedBlackNode insertNode, InsertRotation insertRotation);
	private DeleteRotation classifyDeleteRotation(RedBlackNode deleteNode);
	private InsertRotation classifyInsertRotation(RedBlackNode insertNode);
```

**MinHeap.java**
```java
	public MinHeap();
	public Job peak();
	public void add(Job currentJob);
	public Job extrackMin();
	public void printHeap();

    // Runs in O(n), would have been better off using a fib heap
	public void increaseKey(Job runningJob, int i);

    // Util methods
	private void doubleHeapArray();
	private void heapify(int parentIndex);
```

**Scheduler.java**
```java
	public Scheduler(CommandReader commandReader, String filename);
	public void write(String toWrite);
	public void start();
	public void stop();
	public String executeCommand(Command command);
	public boolean dispatchJob();
	public void addJobs(Command command);
	public void printJob(Command command);
	public void printNextJob(Command command);
	public void printPreviousJob(Command command);

	public MinHeap getMinHeap();
	public RedBlackTree getRbt();

	private void checkJobFinished(int currentTime);
	private void openWriter(String filename);
```

**Job.java**
```java
	public Job(int id, int totalTime);
	public void setTotalTime(int totalTime);
	public int getExecutedTime();
	public int getTotalTime();
	public int getId();
	public String toString();
	public boolean equals(Object obj);
	public void setExecutedTime(int executedTime);
```


