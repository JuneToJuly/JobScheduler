/**
 * @author Ian Thomas
 */
public class RedBlackTree
{

	public enum Color { RED, BLACK}

	public enum InsertRotation { NONE, RRr, RRb, LLr, LLb, RLr, RLb, LRr, LRb}

	public enum Child {LEFT, RIGHT}

	public RedBlackNode head;

	public RedBlackTree()
	{
		head = null;
	}

	public void add(Job currentJob)
	{

		if(head == null)
		{
			head = new RedBlackNode(currentJob, Color.BLACK);
			return;
		}

		RedBlackNode insertNode = new RedBlackNode(currentJob, Color.RED);
		int insertKey = insertNode.getKey();


		// We are at the top of the tree. If our key is less than this value
		// We go to the left subtree, else we go to the right
		RedBlackNode next = head.getKey() > insertKey
							? head.getLeftChild()
							: head.getRightChild();


		// While we don't fall off to a null node
		while(next != null)
		{
			insertNode.setParent(next);
			next = next.getKey() > insertKey
					? head.getLeftChild()
					: head.getRightChild();
		}

		// We have found our parent
		// Set ourselves to our parent's right child because we are larger
		if(insertKey > insertNode.getParent().getKey())
		{
			insertNode.getParent().setRightChild(insertNode);
		}
		// Else we are smaller, set to left child
		else
		{
			insertNode.getParent().setLeftChild(insertNode);
		}

		InsertRotation insertRotation = classifyInsertRotation(insertNode);

	}

	/*
		Classify the rotations for an insert.
	 */
	private InsertRotation classifyInsertRotation(RedBlackNode insertNode)
	{
		RedBlackNode parent = insertNode.getParent();
		RedBlackNode grandParent = insertNode.getParent().getParent();

		Child grandParentToParent = parent.getKey() > grandParent.getKey()
				? Child.RIGHT
				: Child.LEFT;
		Child parentToInsert = insertNode.getKey() > parent.getKey()
				? Child.RIGHT
				: Child.LEFT;


		// The node is black, we can insert with no problems
		if (parent.getColor() == Color.BLACK)
		{
			return InsertRotation.NONE;
		}

		// From hear on out we assume that all parents are red

		// LL Rotations
		if(grandParentToParent == Child.LEFT && parentToInsert == Child.LEFT)
		{
			if(grandParent.getRightChild() == null
					|| grandParent.getRightChild().getColor() == Color.BLACK)
			{
				return InsertRotation.LLb;
			}
			else
			{
				return InsertRotation.LLr;
			}

		}

		// LR rotations
		if(grandParentToParent == Child.LEFT && parentToInsert == Child.RIGHT)
		{
			if(grandParent.getRightChild() == null
					|| grandParent.getRightChild().getColor() == Color.BLACK)
			{
				return InsertRotation.LRb;
			}
			else
			{
				return InsertRotation.LRr;
			}

		}


		// RR rotations
		if(grandParentToParent == Child.RIGHT && parentToInsert == Child.RIGHT)
		{
			if(grandParent.getLeftChild() == null
					|| grandParent.getLeftChild().getColor() == Color.BLACK)
			{
				return InsertRotation.RRb;
			}
			else
			{
				return InsertRotation.RRr;
			}

		}

		// RL Rotations
		if(grandParentToParent == Child.RIGHT && parentToInsert == Child.LEFT)
		{
			if(grandParent.getLeftChild() == null
					|| grandParent.getLeftChild().getColor() == Color.BLACK)
			{
				return InsertRotation.RLb;
			}
			else
			{
				return InsertRotation.RLr;
			}

		}
	}


	public Job get(int id)
	{
		return null;
	}
}

























