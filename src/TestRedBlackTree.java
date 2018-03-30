public class TestRedBlackTree
{
	public static void main(String[] args)
	{
		RedBlackTree rbt = new RedBlackTree();

		Job job = new Job(12,20, 4);
		Job job1 = new Job(13,22, 3);
		Job job2 = new Job(20,22, 2);
		Job job3 = new Job(44,22, 6);
		Job job4 = new Job(30,22, 90);
		Job job5 = new Job(1,22, 1);
		Job job6 = new Job(2,22, 1);
		Job job7 = new Job(19,22, 1);
		Job job8 = new Job(18,22, 1);

		rbt.add(job);
		rbt.add(job1);
		rbt.add(job2);
		rbt.add(job3);
		rbt.add(job4);
		rbt.add(job5);
		rbt.add(job6);
		rbt.add(job7);
		rbt.add(job8);
		rbt.printNodeStyle(null);

	}
}
