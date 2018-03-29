/**
 * @author Ian Thomas
 */
public class RedBlackTree
{

	static RedBlackNode externalNode;
	static RedBlackNode rootNode;

	static
	{
		externalNode = new RedBlackNode(null, RedBlackTree.Color.BLACK);
		externalNode.setParent(externalNode);
		externalNode.setLeftChild(externalNode);
		externalNode.setRightChild(externalNode);

		rootNode = new RedBlackNode(null, RedBlackTree.Color.BLACK);
		rootNode.setParent(rootNode);
		rootNode.setLeftChild(externalNode);
		rootNode.setRightChild(externalNode);
	}


	public enum Color { RED, BLACK}

	public enum InsertRotation { NONE, RRr, RRb, LLr, LLb, RLr, RLb, LRr, LRb}

	public enum Child {LEFT, RIGHT}

	private RedBlackNode head;

	public RedBlackTree()
	{
		head = null;
	}

	public void add(Job currentJob)
	{

		if(head == null)
		{
			head = new RedBlackNode(currentJob, Color.BLACK);
			System.out.println("Null head");
			head.setParent(rootNode);
			head.setLeftChild(externalNode);
			head.setRightChild(externalNode);

			return;
		}

		RedBlackNode insertNode = new RedBlackNode(currentJob, Color.RED);
		insertNode.setParent(externalNode);
		insertNode.setLeftChild(externalNode);
		insertNode.setRightChild(externalNode);
		int insertKey = insertNode.getKey();


		// We are at the top of the tree. If our key is less than this value
		// We go to the left subtree, else we go to the right
		insertNode.setParent(head);
		RedBlackNode next = head.getKey() > insertKey
							? head.getLeftChild()
							: head.getRightChild();

		// While we don't fall off to a null node
		while(next != externalNode)
		{
			insertNode.setParent(next);
			next = next.getKey() > insertKey
					? next.getLeftChild()
					: next.getRightChild();
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
		System.out.println(insertRotation);
		performRotation(insertNode, insertRotation);
	}

	/*
		Perform the rotations in accordance with the classified rotation
	 */
	private void performRotation(RedBlackNode insertNode, InsertRotation insertRotation)
	{
		RedBlackNode grandParentsParent;
		// Switch time
		switch (insertRotation)
		{
			// Nothing to do. This was the case for a black node. We have already set our parent
			// and we have set ourselves to our parent's child.
			case NONE:
				break;
			//  All 'r' are just color flips where we propigate the change up the tree
			case RRr:
				insertNode.getParent().setColor(Color.BLACK);
				insertNode.getParent().getParent().setColor(Color.RED);
				insertNode.getParent().getParent().getLeftChild().setColor(Color.BLACK);
				break;
			case RRb:
				greatGrandUpdate(insertNode);

				// Give my parent's left node to the grandparent's right child
				insertNode.getParent().getParent().setRightChild(insertNode.getParent().getLeftChild());

				// Set my parent's left child to my grandparent
				insertNode.getParent().setLeftChild(insertNode.getParent().getParent());
				insertNode.getParent().getLeftChild().setColor(Color.RED);

				// Set my grandparent's old parent to my parent's parent
				insertNode.getParent().setParent(insertNode.getParent().getParent().getParent());

				if(insertNode.getParent().getParent() == rootNode)
				{
					head = insertNode.getParent();
					head.setColor(Color.BLACK);
				}

				break;
			case LLr:
				insertNode.getParent().setColor(Color.BLACK);
				insertNode.getParent().getParent().setColor(Color.RED);
				insertNode.getParent().getParent().getRightChild().setColor(Color.BLACK);
				break;
			case LLb:
				greatGrandUpdate(insertNode);
				// Temp so we can have the parent's right
				RedBlackNode parentRight  = insertNode.getParent().getRightChild();

				// Set right child of parent to grand parent
				insertNode.getParent().setRightChild(insertNode.getParent().getParent());

				// Set parent's parent to grandparent's old parent
				insertNode.getParent().setParent(insertNode.getParent().getRightChild().getParent());

				// Set grandparent's parent to parent
				insertNode.getParent().getRightChild().setParent(insertNode.getParent());

				// Set parent's old right child to the left of old grandparent
				insertNode.getParent().getRightChild().setLeftChild(parentRight);
				break;
			case RLr:
				insertNode.getParent().setColor(Color.BLACK);
				insertNode.getParent().getParent().setColor(Color.RED);
				insertNode.getParent().getParent().getLeftChild().setColor(Color.BLACK);
				break;
			case RLb:
				greatGrandUpdate(insertNode);
				// Set my parent's left to my right, and grandparent's right to my left
				insertNode.getParent().getParent().setRightChild(insertNode.getLeftChild());
				insertNode.getParent().setLeftChild(insertNode.getRightChild());

				// Set my right to my parent, and my left to my grandparent
				insertNode.setRightChild(insertNode.getParent());
				insertNode.setLeftChild(insertNode.getParent().getParent());

				// Hold onto my grandparent's old parent
				grandParentsParent = insertNode.getParent().getParent();

				// Set my parent and grandparent to myself
				insertNode.getRightChild().setParent(insertNode);
				insertNode.getLeftChild().setParent(insertNode);

				// Set my parent to my grandparent's old parent
				insertNode.setParent(grandParentsParent);
				break;
			case LRr:
				insertNode.getParent().setColor(Color.BLACK);
				insertNode.getParent().getParent().setColor(Color.RED);
				insertNode.getParent().getParent().getRightChild().setColor(Color.BLACK);
				break;
			case LRb:
				greatGrandUpdate(insertNode);

				// Set my parent's right to my left, and grandparent's left to my right
				insertNode.getParent().getParent().setLeftChild(insertNode.getRightChild());
				insertNode.getParent().setRightChild(insertNode.getLeftChild());

				// Set my left to my parent, and my right to my grandparent
				insertNode.setLeftChild(insertNode.getParent());
				insertNode.setRightChild(insertNode.getParent().getParent());

				// Hold onto my grandparent's old parent
				grandParentsParent = insertNode.getParent().getParent();

				// Set my parent and grandparent to myself
				insertNode.getLeftChild().setParent(insertNode);
				insertNode.getRightChild().setParent(insertNode);

				// Set my parent to my grandparent's old parent
				insertNode.setParent(grandParentsParent);
				break;
				default:
		}
		if(insertNode.getParent() == rootNode)
		{
			System.out.println("H");
			head = insertNode;
		}
	}

	/*
		Classify the rotations for an insert.
	 */
	private InsertRotation classifyInsertRotation(RedBlackNode insertNode)
	{
		RedBlackNode parent = insertNode.getParent();
		RedBlackNode grandParent = insertNode.getParent().getParent();
		Child grandParentToParent = null;
		Child parentToInsert = null;
		if(parent.getParent() != null)
		{
			grandParentToParent = parent.getKey() > grandParent.getKey()
					? Child.RIGHT
					: Child.LEFT;
			parentToInsert = insertNode.getKey() > parent.getKey()
					? Child.RIGHT
					: Child.LEFT;
		}


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
		return InsertRotation.NONE;
	}

	/**
	 *  Userd in a lot of calls, just set the great grandparent to the current
	 *  node when a rotation happens.
	 * @param insertNode node to set great grand parent's child to
	 * @return the child of the greatGrandparent
	 */
	private Child greatGrandUpdate(RedBlackNode insertNode)
	{

		if(insertNode.getParent().getParent().getParent() == rootNode)
		{
			return null;
		}

		Child greatToGrand = insertNode.getParent().getParent().getKey() >
				insertNode.getParent().getParent().getParent().getKey()
				? Child.RIGHT
				: Child.LEFT;




		// Need to set the child of great grand parent to our node
		if(greatToGrand == Child.RIGHT)
		{
			insertNode.getParent().getParent().getParent().setRightChild(insertNode);
		}
		else
		{
			insertNode.getParent().getParent().getParent().setLeftChild(insertNode);
		}
		return greatToGrand;
	}


	public Job get(int id)
	{
		return null;
	}

	public void print()
	{
		System.out.println(head.getJob().toString() + " Color: " + head.getColor());
		System.out.println(head.getRightChild().getJob() + " Color: " + head.getRightChild().getColor());
		System.out.println(head.getLeftChild().getJob() + " Color: " + head.getLeftChild().getColor());
//		System.out.println(head.getLeftChild().getJob().toString());
//		System.out.println(head.getRightChild().getJob().toString() + " Color: " + head.getRightChild().getColor());
	}
}


























