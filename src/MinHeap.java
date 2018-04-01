import jdk.nashorn.internal.scripts.JO;

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

	public Job peak()
	{
		if(lastHeapIndex != -1)
		{
			return heapArray[0];
		}
		else
		{
			return null;
		}
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
		int parentIndex = 0;
		Job min = heapArray[parentIndex];

		// Grab last element
		heapArray[parentIndex] = heapArray[lastHeapIndex];
		heapArray[lastHeapIndex] = null;
		lastHeapIndex--;

		heapify(parentIndex);
		return min;
	}

	private void heapify(int parentIndex)
	{
		Job parent;
		Job leftChild = null;
		Job rightChild = null;

		int minChild;

		boolean heapPropertyMaintained = false;
		boolean leftExist;
		boolean rightExist;

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
			if(leftExist && rightExist && (leftChild.getExecutedTime() < rightChild.getExecutedTime())
					&& parent.getExecutedTime() > leftChild.getExecutedTime())
			{
				minChild = ((2 * parentIndex) + 1);
			}
			else if (leftExist && parent.getExecutedTime() > leftChild.getExecutedTime())
			{
				minChild = ((2 * parentIndex) + 1);
			}
			// Right Child is the smallest
			else if(rightExist && leftExist && rightChild.getExecutedTime() < leftChild.getExecutedTime()
					&& parent.getExecutedTime() > rightChild.getExecutedTime())
			{
				minChild = ((2 * parentIndex) + 2);
			}
			else if(rightExist && parent.getExecutedTime() > rightChild.getExecutedTime())
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
		int key = runningJob.getExecutedTime();
		int currentIndex = 0;
		int foundIndex = -1;


		// O(n) to find the value...
		for (Job job: heapArray)
		{
			if(job.getExecutedTime() == key)
			{
				foundIndex = currentIndex;
				break;
			}
			currentIndex++;
		}

		heapArray[foundIndex].setExecutedTime(heapArray[foundIndex].getExecutedTime() + 5);
		if(foundIndex == -1)
		{
			return;
		}
		heapify(foundIndex);
	}
}
