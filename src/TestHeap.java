/**
 * @author Ian
 */
public class TestHeap
{
	public static void main(String[] args)
	{
		MinHeap heap = new MinHeap();
		Job job = new Job(12,20, 4);
		Job job1 = new Job(13,22, 3);
		Job job2 = new Job(20,22, 2);
		Job job3 = new Job(20,22, 6);
		Job job4 = new Job(20,22, 90);
		Job job5 = new Job(20,22, 1);

		heap.add(job);
		heap.printHeap();
		System.out.println("This is new add\n");

		heap.add(job1);
		heap.printHeap();

		System.out.println("This is new add\n");

		heap.add(job2);
		heap.printHeap();
		System.out.println("This is new add\n");

		heap.add(job3);
		heap.printHeap();
		System.out.println("This is new add\n");

		heap.add(job4);
		heap.printHeap();

		System.out.println("This is new add\n");

		heap.add(job5);
		heap.printHeap();
		System.out.println("This is new add\n");

		heap.extrackMin();
		heap.printHeap();

		heap.extrackMin();
		heap.printHeap();

		heap.extrackMin();
		heap.printHeap();
	}
}
