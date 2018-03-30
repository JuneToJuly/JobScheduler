/**
 * @author Ian Thomas
 */
public class MinHeap
{
	private Job[] heapArray;
	private int lastHeapIndex;
	private int heapMaxSize;

	public MinHeap()
	{
		lastHeapIndex = -1;
		heapMaxSize = 1;
		heapArray = new Job[1];
	}

	/**
	 * Adds an element to the heap.
	 * @param currentJob
	 */
	public void add(Job currentJob)
	{
		// If we reach our max size
		if((++lastHeapIndex) == heapMaxSize)
		{
			doubleHeapArray();
		}

		boolean heapPropertyMaintained = true;

		heapArray[lastHeapIndex] = currentJob;
		heapPropertyMaintained = false;

		int currentIndex = lastHeapIndex;
		while(!heapPropertyMaintained)
		{
			// Current index is 0, we are at the root
			if(currentIndex == 0)
			{
				break;
			}

			Job current = heapArray[currentIndex];
			Job parent = heapArray[(currentIndex-1) / 2];

			if(parent.getExecutedTime() > current.getExecutedTime())
			{
				heapArray[(currentIndex-1) / 2] = current;
				heapArray[currentIndex] = parent;
				currentIndex = (currentIndex-1)/2;
			}
			else
			{
				heapPropertyMaintained = true;
			}
		}
	}

	/*
		Used to double the heaps size.
	 */
	private void doubleHeapArray()
	{
		// Double the size of the array
		Job[] arrayTemp = new Job[heapMaxSize * 2];

		// Copy current heap into this heap
		for (int i = 0; i < heapMaxSize; i++)
		{
			arrayTemp[i] = heapArray[i];
		}

		// Copy heap over and double max size
		heapArray = arrayTemp;
		heapMaxSize *= 2;
	}

	public Job extrackMin()
	{
		if(lastHeapIndex == -1)
		{
			return null;
		}

		Job min = heapArray[0];
		Job parent;
		Job leftChild = null;
		Job rightChild = null;

		int minChild;
		int parentIndex = 0;

		boolean heapPropertyMaintained = true;
		boolean leftExist;
		boolean rightExist;

		// Grab last element
		heapArray[0] = heapArray[lastHeapIndex];
		heapPropertyMaintained = false;
		heapArray[lastHeapIndex] = null;
		lastHeapIndex--;

		while(!heapPropertyMaintained)
		{
			parent = heapArray[parentIndex];
			minChild = 0;

			// Check for existing children
			leftExist = ((2 * parentIndex) + 1) <= lastHeapIndex;
			rightExist = ((2 * parentIndex) + 2) <= lastHeapIndex;


			if(leftExist)
			{
				leftChild = heapArray[(2 * parentIndex) + 1];
			}
			if(rightExist)
			{
				rightChild = heapArray[(2 * parentIndex) + 2];
			}


			// Left Child is the smallest child
			if(leftExist && (leftChild.getExecutedTime() < rightChild.getExecutedTime())
					&& parent.getExecutedTime() > leftChild.getExecutedTime())
			{
				minChild = ((2 * parentIndex) + 1);
			}
			// Right Child is the smallest
			else if(rightExist && rightChild.getExecutedTime() < leftChild.getExecutedTime()
					&& parent.getExecutedTime() > rightChild.getExecutedTime())
			{
				minChild = ((2 * parentIndex) + 2);
			}
			// The parent is smaller than both children, our heap property is now
			// maintained
			else
			{
				heapPropertyMaintained = true;
			}

			// Swap the parent and child
			if(!heapPropertyMaintained)
			{
				heapArray[parentIndex] = heapArray[minChild];
				heapArray[minChild] = parent;
				parentIndex = minChild;
			}

		}

		return min;
	}

	public void printHeap()
	{
		int count = 0;
		for (Job job:
		     heapArray)
		{
			if(job != null)
			{
				System.out.println("Index:  " + count++ + " " + job.toString());
			}
		}
	}


	public void increaseKey(Job runningJob, int i)
	{
	}
}
