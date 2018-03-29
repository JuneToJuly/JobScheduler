public class TestRedBlackTree
{
	public static void main(String[] args)
	{
		RedBlackTree rbt = new RedBlackTree();

		Job job = new Job(12,20, 4);
		Job job1 = new Job(13,22, 3);
		Job job2 = new Job(20,22, 2);
		Job job3 = new Job(20,22, 6);
		Job job4 = new Job(20,22, 90);
		Job job5 = new Job(20,22, 1);

		rbt.add(job);
		rbt.add(job1);
		rbt.add(job2);
		rbt.print();

	}
}
