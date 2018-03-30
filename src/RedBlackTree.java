import java.util.Queue;

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
			System.out.println("My key is: " + insertKey + " And I just found a parent" +
					                   "whose key is: " + insertNode.getParent().getKey());
			insertNode.getParent().setRightChild(insertNode);
		}
		// Else we are smaller, set to left child
		else
		{
			System.out.println("My key is: " + insertKey + " And I just found a parent" +
					                   "whose key is: " + insertNode.getParent().getKey());
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

				if (insertNode.getParent().getParent() == head)
				{
					head.setColor(Color.BLACK);
				}
				break;
			case RRb:
//				greatGrandUpdate(insertNode);

				// Give my parent's left node to the grandparent's right child
				insertNode.getParent().getParent().setRightChild(insertNode.getParent().getLeftChild());

				// Set my parent's left child to my grandparent
				insertNode.getParent().setLeftChild(insertNode.getParent().getParent());
				insertNode.getParent().getLeftChild().setColor(Color.RED);

				// Set my grandparent's old parent to my parent's parent
				insertNode.getParent().setParent(insertNode.getParent().getParent().getParent());

				// Set my granparent's parent to my parent
				insertNode.getParent().getLeftChild().setParent(insertNode.getParent());

				insertNode.getParent().setColor(Color.BLACK);
				insertNode.getParent().getLeftChild().setColor(Color.RED);
				insertNode.getParent().getRightChild().setColor(Color.RED);

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

				if (insertNode.getParent().getParent() == head)
				{
					head.setColor(Color.BLACK);
				}
				break;
			case LLb:
				greatGrandUpdate(insertNode, "parent");
				// Give my parent's left node to the grandparent's right child
				insertNode.getParent().getParent().setLeftChild(insertNode.getParent().getRightChild());

				// Set my parent's right child to my grandparent
				insertNode.getParent().setRightChild(insertNode.getParent().getParent());
				insertNode.getParent().getRightChild().setColor(Color.RED);

				// Set my grandparent's old parent to my parent's parent
				insertNode.getParent().setParent(insertNode.getParent().getParent().getParent());

				// Set my granparent's parent to my parent
				insertNode.getParent().getRightChild().setParent(insertNode.getParent());

				insertNode.getParent().setColor(Color.BLACK);
				insertNode.getParent().getLeftChild().setColor(Color.RED);
				insertNode.getParent().getRightChild().setColor(Color.RED);

				if(insertNode.getParent().getParent() == rootNode)
				{
					head = insertNode.getParent();
					head.setColor(Color.BLACK);
				}
				break;
			case RLr:
				insertNode.getParent().setColor(Color.BLACK);
				insertNode.getParent().getParent().setColor(Color.RED);
				insertNode.getParent().getParent().getLeftChild().setColor(Color.BLACK);
				break;
			case RLb:
				greatGrandUpdate(insertNode ,"");

				// Set my parent's left to my right, and grandparent's right to my left
				insertNode.getParent().getParent().setRightChild(insertNode.getLeftChild());
				insertNode.getParent().setLeftChild(insertNode.getRightChild());

				// Set my right to my parent, and my left to my grandparent
				insertNode.setRightChild(insertNode.getParent());
				insertNode.setLeftChild(insertNode.getParent().getParent());

				// Hold onto my grandparent's old parent
				grandParentsParent = insertNode.getParent().getParent().getParent();

				// Set my parent and grandparent to myself
				insertNode.getRightChild().setParent(insertNode);
				insertNode.getLeftChild().setParent(insertNode);

				// Set my parent to my grandparent's old parent
				insertNode.setParent(grandParentsParent);

				insertNode.setColor(Color.BLACK);
				insertNode.getLeftChild().setColor(Color.RED);
				insertNode.getRightChild().setColor(Color.RED);

				if(insertNode.getParent() == rootNode)
				{
					System.out.println("New root node");
					head = insertNode;
					head.setColor(Color.BLACK);
				}
				break;
			case LRr:
				insertNode.getParent().setColor(Color.BLACK);
				insertNode.getParent().getParent().setColor(Color.RED);
				insertNode.getParent().getParent().getRightChild().setColor(Color.BLACK);
				break;
			case LRb:
				greatGrandUpdate(insertNode, "");

				// Set my parent's right to my left, and grandparent's left to my right
				insertNode.getParent().getParent().setLeftChild(insertNode.getRightChild());
				insertNode.getParent().setRightChild(insertNode.getLeftChild());

				// Set my left to my parent, and my right to my grandparent
				insertNode.setLeftChild(insertNode.getParent());
				insertNode.setRightChild(insertNode.getParent().getParent());

				// Hold onto my grandparent's old parent
				grandParentsParent = insertNode.getParent().getParent().getParent();

				// Set my parent and grandparent to myself
				insertNode.getLeftChild().setParent(insertNode);
				insertNode.getRightChild().setParent(insertNode);

				// Set my parent to my grandparent's old parent
				insertNode.setParent(grandParentsParent);

				insertNode.setColor(Color.BLACK);
				insertNode.getLeftChild().setColor(Color.RED);
				insertNode.getRightChild().setColor(Color.RED);

				if(insertNode.getParent() == rootNode)
				{
					System.out.println("New root node");
					head = insertNode;
					head.setColor(Color.BLACK);
				}
				break;
				default:
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

	/*
	 * All Rotations require the great grand parent to be set to the new
	 * node at the grand parent level. XXb calls for parent to be set.
	 * XYb calls for the node to be set because it moves up two levels
	 *
	 * @param insertNode node to check off of
	 * @param node parent or node
	 * @return which side the grand parent is of it's parent
	 */
	private Child greatGrandUpdate(RedBlackNode insertNode, String node)
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
			if(node.contains("parent"))
			{
				insertNode.getParent().getParent().getParent().setRightChild(insertNode.getParent());
			}
			else
			{
				insertNode.getParent().getParent().getParent().setRightChild(insertNode);
			}
		}
		else
		{
			if(node.contains("parent"))
			{
				insertNode.getParent().getParent().getParent().setLeftChild(insertNode.getParent());
			}
			else
			{
				insertNode.getParent().getParent().getParent().setLeftChild(insertNode);
			}

		}
		return greatToGrand;
	}


	public Job get(int id)
	{
		return null;
	}

	/**
	 * Prints the red black tree.
	 * @param node starting node to print from, if null, starts at head.
	 */
	public void printNodeStyle(RedBlackNode node)
	{
		if(node == null)
		{
			System.out.println("Head: " + head.getJob().toString() + " Color: " + head.getColor());
			System.out.println("Parent: " + head.getParent().getKey());
			System.out.println("Left: " + head.getLeftChild().getJob().toString() + " Color: " + head.getLeftChild().getColor());
			System.out.println("Right: " + head.getRightChild().getJob().toString() + " Color: " + head.getRightChild().getColor());
			printNodeStyle(head.getLeftChild());
			printNodeStyle(head.getRightChild());
			return;
		}
		if(node == externalNode)
		{
			return;
		}

		System.out.println("\n\n\t\t\t\tNode at top: " + node.getKey() + " Color: " + node.getColor());
		System.out.println("\t\t\t\tParent: " + node.getParent().getKey() + " Color: " + node.getParent().getColor());

		if(node.getLeftChild() != externalNode)
		{
			System.out.print("\t\tLeft: " + node.getLeftChild().getKey() + " Color: " + node.getLeftChild().getColor() + " ------ ");
		}

		if(node.getRightChild() != externalNode)
		{
			System.out.print("Right: " + node.getRightChild().getKey() + " Color: " + node.getRightChild().getColor());
		}

		printNodeStyle(node.getLeftChild());
		printNodeStyle(node.getRightChild());
	}
}


























