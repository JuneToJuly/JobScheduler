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
		Job job9 = new Job(21,22, 1);
		Job job10 = new Job(24,22, 1);
		Job job11 = new Job(28,22, 1);

		rbt.add(job);
		rbt.add(job1);
		rbt.add(job2);
		rbt.add(job3);
		rbt.add(job4);
		rbt.add(job5);
		rbt.add(job6);
		rbt.add(job7);
		rbt.delete(job7);
		rbt.add(job10);
		rbt.delete(job2);

//		rbt.delete(job2);
//		rbt.delete(job3);
//		rbt.delete(job7);
//		rbt.add(job8);
//		rbt.add(job9);
		rbt.printNodeStyle(null);
		new TestRedBlackTree();
	}

	public TestRedBlackTree()
	{
		testRb01();
		testRb02();
		testRb11();
		testRb12();
		testRb2();
		testRr0();
		testRr11();
		testRr12();
		testRr2();

		testLb01();
		testLb02();
		testLb11();
		testLb12();
		testLb2();
		testLr0();
		testLr11();
		testLr12();
		testLr2();

		testDeleteRedLeaf();
		testDeleteBlackLeaf();
		testDeleteBlackDegreeOneWithRedChild();
		testDeleteDegree2();
		testDeleteMin();

	}

	private void testRb01()
	{
		Job job = new Job(12,20, 4);
		Job job1 = new Job(13,22, 3);
		Job job2 = new Job(20,22, 2);
		Job job3 = new Job(44,22, 6);
		Job job4 = new Job(30,22, 90);
		Job job5 = new Job(1,22, 1);
		Job job6 = new Job(2,22, 1);
		Job job7 = new Job(19,22, 1);
		Job job8 = new Job(18,22, 1);
		Job job9 = new Job(21,22, 1);
		Job job10 = new Job(24,22, 1);
		Job job11 = new Job(28,22, 1);

	}


}
