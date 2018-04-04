/**
 * @author Ian Thomas
 */
public class TestSearchAlgorithms
{
	public RedBlackTree rbt;
	public static void main(String[] args)
	{
		new TestSearchAlgorithms();
	}

	public TestSearchAlgorithms()
	{
		rbt = new RedBlackTree();
		rbt.add(new Job(3,0,0));
		rbt.add(new Job(20,0,0));
		rbt.add(new Job(25,0,0));
		rbt.add(new Job(36,0,0));
		rbt.add(new Job(15,0,0));
		rbt.add(new Job(2,0,0));
		rbt.add(new Job(16,0,0));
		rbt.add(new Job(28,0,0));
		rbt.add(new Job(27,0,0));
		rbt.add(new Job(29,0,0));


		rbt.printNodeStyle(null);

		testNext();
		testPrev();
		testPrint();
		testPrintRange();
	}

	private void testPrintRange()
	{
		System.out.println("Testing SearchInRange: ");
		System.out.println(rbt.searchInRange(2,3));
		System.out.println(rbt.searchInRange(2,15));
		System.out.println(rbt.searchInRange(3,15));
		System.out.println(rbt.searchInRange(15,20));
		System.out.println(rbt.searchInRange(2,40));
		System.out.println(rbt.searchInRange(17,28));
	}

	private void testPrint()
	{
		System.out.println("Testing Print: ");
		System.out.println(rbt.search(new Job(20,0,0)).toString());
		System.out.println(rbt.search(new Job(25,0,0)).toString());
		System.out.println(rbt.search(new Job(3,0,0)).toString());
		System.out.println(rbt.search(new Job(36,0,0)).toString());
		System.out.println(rbt.search(new Job(15,0,0)).toString());
		System.out.println(rbt.search(new Job(2,0,0)).toString());
	}

	private void testPrev()
	{
		System.out.println("Testing Prev: ");
		System.out.println(rbt.previous(new Job(20,0,0)));
		System.out.println(rbt.previous(new Job(25,0,0)));
		System.out.println(rbt.previous(new Job(3,0,0)));
		System.out.println(rbt.previous(new Job(36,0,0)));
		System.out.println(rbt.previous(new Job(15,0,0)));
		System.out.println(rbt.previous(new Job(2,0,0)));
	}

	private void testNext()
	{
		System.out.println("Testing Next: ");
		System.out.println(rbt.next(new Job(20,0,0)));
		System.out.println(rbt.next(new Job(25,0,0)));
		System.out.println(rbt.next(new Job(3,0,0)));
		System.out.println(rbt.next(new Job(36,0,0)));
		System.out.println(rbt.next(new Job(15,0,0)));
		System.out.println(rbt.next(new Job(2,0,0)));
		System.out.println("");
	}
}
