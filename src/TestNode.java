
public class TestNode
{
	public static void main(String[] args)
	{
		new TestNode();
	}

	public TestNode()
	{
		RedBlackNode nodeA = new RedBlackNode(new Job(20,20, 20), RedBlackTree.Color.RED);
		RedBlackNode nodeB = new RedBlackNode(new Job(2,2, 2), RedBlackTree.Color.RED);
		RedBlackNode nodeC = new RedBlackNode(new Job(3,3, 3), RedBlackTree.Color.RED);
		RedBlackNode nodeD = new RedBlackNode(new Job(4,4, 4), RedBlackTree.Color.RED);
		RedBlackNode nodeE = new RedBlackNode(new Job(5,5, 5), RedBlackTree.Color.RED);
		RedBlackNode nodeF = new RedBlackNode(new Job(6,6, 6), RedBlackTree.Color.RED);


		nodeA.setLeftChild(nodeB);
		nodeA.setRightChild(nodeC);
		nodeA.getLeftChild().setLeftChild(nodeD);
		nodeA.getLeftChild().setRightChild(nodeE);

		nodeD.setLeftChild(nodeE);
		nodeE.setLeftChild(nodeA);


		System.out.println(nodeA.getLeftChild().getJob().toString());
		System.out.println(nodeA.getLeftChild().getLeftChild().getJob().toString());
		System.out.println(nodeA.getLeftChild().getLeftChild().getLeftChild().getLeftChild().getJob().toString());




	}
}
