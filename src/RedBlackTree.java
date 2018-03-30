import com.sun.org.apache.regexp.internal.REDebugCompiler;

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

	public enum DeleteRotation { Rb01, Rb02, Rb11, Rb12, Rb2, Rr0, Rr11, Rr12, Rr2,
								 Lb01, Lb02, Lb11, Lb12, Lb2, Lr0, Lr11, Lr12, Lr2}

	private RedBlackNode head;

	public RedBlackTree()
	{
		head = null;
	}

	public void delete(Job toDelete)
	{
		if(head == null)
		{
			return;
		}

		RedBlackNode next = head.getKey() > toDelete.getId()
				? head.getLeftChild()
				: head.getRightChild();

		while(next != externalNode)
		{
			if(next.getKey() == toDelete.getId())
			{
				break;
			}
			next = next.getKey() > toDelete.getId()
					? next.getLeftChild()
					: next.getRightChild();
		}

		// Failed to find the key
		if(next == externalNode)
		{
			return;
		}
		DeleteRotation deleteRotation =  classifyDeleteRotation(next);
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

		RedBlackNode conflictingNode = null;
		do
		{
			if (insertRotation == InsertRotation.LLr
					|| insertRotation == InsertRotation.LRr
					|| insertRotation == InsertRotation.RRr
					|| insertRotation == InsertRotation.RLr)
			{
				conflictingNode = checkForPropertyMaintained(insertNode);
				if(conflictingNode != null)
				{
					insertRotation = classifyInsertRotation(conflictingNode);
					performRotation(conflictingNode, insertRotation);
					System.out.println(insertRotation);
				}

			}
			else
			{
				conflictingNode = null;
			}
		} while (conflictingNode != null);
	}

	private RedBlackNode checkForPropertyMaintained(RedBlackNode node)
	{
		if(node.getParent().getParent().getColor() == Color.RED)
		{
			// Bad node
			return node.getParent().getParent();
		}
		return null;
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
				insertNode.getParent().getLeftChild().setParent(insertNode.getParent().getParent());

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
				insertNode.getParent().getRightChild().setParent(insertNode.getParent().getParent());

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
				insertNode.getLeftChild().setParent(insertNode.getParent().getParent());

				insertNode.getParent().setLeftChild(insertNode.getRightChild());
				insertNode.getRightChild().setParent(insertNode.getParent());

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
				insertNode.getRightChild().setParent(insertNode.getParent().getParent());

				insertNode.getParent().setRightChild(insertNode.getLeftChild());
				insertNode.getLeftChild().setParent(insertNode.getParent());

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

	private DeleteRotation classifyDeleteRotation(RedBlackNode deleteNode)
	{
		RedBlackNode parent = deleteNode.getParent();
		RedBlackNode parentLeftChild = parent.getLeftChild();
		RedBlackNode pLLeftChild = parentLeftChild.getLeftChild();
		RedBlackNode pLRightChild = parentLeftChild.getRightChild();
		RedBlackNode pLRRightChild = pLRightChild.getRightChild();
		RedBlackNode pLRLeftChild  = pLRightChild.getLeftChild();
		RedBlackNode pLRRRightChild = pLRRightChild.getRightChild();
		RedBlackNode pLRRLeftChild  = pLRRightChild.getLeftChild();

		Child parentToDelete = null;

		if(parent.getParent() != null)
		{
			parentToDelete = deleteNode.getKey() > parent.getKey()
					? Child.RIGHT
					: Child.LEFT;
		}

		if(parent.getColor() == Color.BLACK
				&& parentLeftChild.getColor() == Color.BLACK
				&& pLLeftChild.getColor() == Color.BLACK
				&& pLRightChild.getColor() = Color.BLACK)
		{
			return DeleteRotation.Rb01;
		}

		if(parent.getColor() == Color.RED
			&& parentLeftChild.getColor() == Color.BLACK
			&& pLLeftChild.getColor() == Color.BLACK
			&& pLRightChild.getColor() = Color.BLACK)
		{
			return DeleteRotation.Rb02;
		}

		if(parentLeftChild.getColor() == Color.BLACK
				&& pLLeftChild.getColor() == Color.RED
				&& pLRightChild.getColor() = Color.BLACK)
		{
			return DeleteRotation.Rb11;
		}

		if(parentLeftChild.getColor() == Color.BLACK
				&& pLLeftChild.getColor() == Color.BLACK
				&& pLRightChild.getColor() = Color.RED)
		{
			return DeleteRotation.Rb12;
		}

		if(parentLeftChild.getColor() == Color.BLACK
				&& pLLeftChild.getColor() == Color.RED
				&& pLRightChild.getColor() = Color.RED)
		{
			return DeleteRotation.Rb2;
		}

		if(parent.getColor() == Color.BLACK
				&& parentLeftChild.getColor() == Color.RED
				&& pLLeftChild.getColor() == Color.BLACK
				&& pLRightChild.getColor() = Color.BLACK)
		{
			return DeleteRotation.Rr0;
		}

		if(parent.getColor() == Color.BLACK
				&& parentLeftChild.getColor() == Color.RED
				&& pLLeftChild.getColor() == Color.BLACK
				&& pLRightChild.getColor() = Color.BLACK
				&& pLRLeftChild.getColor() = Color.RED)
		{
			return DeleteRotation.Rr11;
		}

		if(parent.getColor() == Color.BLACK
				&& parentLeftChild.getColor() == Color.RED
				&& pLLeftChild.getColor() == Color.BLACK
				&& pLRightChild.getColor() = Color.BLACK
				&& pLRLeftChild.getColor() = Color.BLACK
				&& pLRRightChild.getColor() = Color.RED)
		{
			return DeleteRotation.Rr12;
		}

		if(parent.getColor() == Color.BLACK
				&& parentLeftChild.getColor() == Color.RED
				&& pLLeftChild.getColor() == Color.BLACK
				&& pLRightChild.getColor() = Color.BLACK
				&& pLRLeftChild.getColor() = Color.RED
				&& pLRRightChild.getColor() = Color.RED)
		{
			return DeleteRotation.Rr2;
		}


		parent = deleteNode.getParent();
		RedBlackNode parentRightChild = parent.getRightChild();
		RedBlackNode pRLeftChild = parentRightChild.getLeftChild();
		RedBlackNode pRRightChild = parentRightChild.getRightChild();
		RedBlackNode pRRRightChild = pRRightChild.getRightChild();
//		RedBlackNode pRLRightChild  = pRLeftChild.getRightChild();
		RedBlackNode pRRLeftChild  = pRRightChild.getLeftChild();
		RedBlackNode pRRRRightChild = pRRRightChild.getRightChild();
		RedBlackNode pRRRLeftChild  = pRRRightChild.getLeftChild();

		Child parentToDelete = null;

		if(parent.getParent() != null)
		{
			parentToDelete = deleteNode.getKey() > parent.getKey()
					? Child.RIGHT
					: Child.LEFT;
		}

		if(parent.getColor() == Color.BLACK
				&& parentRightChild.getColor() == Color.BLACK
				&& pRLeftChild.getColor() == Color.BLACK
				&& pRRightChild.getColor() = Color.BLACK)
		{
			return DeleteRotation.Lb01;
		}

		if(parent.getColor() == Color.RED
				&& parentRightChild.getColor() == Color.BLACK
				&& pRLeftChild.getColor() == Color.BLACK
				&& pRRightChild.getColor() = Color.BLACK)
		{
			return DeleteRotation.Lb02;
		}

		if(parentRightChild.getColor() == Color.BLACK
				&& pRLeftChild.getColor() == Color.BLACK
				&& pRRightChild.getColor() = Color.RED)
		{
			return DeleteRotation.Lb11;
		}

		if(parentRightChild.getColor() == Color.BLACK
				&& pRLeftChild.getColor() == Color.RED
				&& pRRightChild.getColor() = Color.BLACK)
		{
			return DeleteRotation.Lb12;
		}

		if(parentRightChild.getColor() == Color.BLACK
				&& pRLeftChild.getColor() == Color.RED
				&& pRRightChild.getColor() = Color.RED)
		{
			return DeleteRotation.Lb2;
		}

		if(parent.getColor() == Color.BLACK
				&& parentRightChild.getColor() == Color.RED
				&& pRLeftChild.getColor() == Color.BLACK
				&& pRRightChild.getColor() = Color.BLACK)
		{
			return DeleteRotation.Lr0;
		}

		if(parent.getColor() == Color.BLACK
				&& parentRightChild.getColor() == Color.RED
				&& pRLeftChild.getColor() == Color.BLACK
				&& pRRightChild.getColor() = Color.BLACK
				&& pRL.getColor() = Color.RED)
		//
		{
			return DeleteRotation.Rr11;
		}

		if(parent.getColor() == Color.BLACK
				&& parentLeftChild.getColor() == Color.RED
				&& pLLeftChild.getColor() == Color.BLACK
				&& pLRightChild.getColor() = Color.BLACK
				&& pLRLeftChild.getColor() = Color.BLACK
				&& pLRRightChild.getColor() = Color.RED)
		{
			return DeleteRotation.Rr12;
		}

		if(parent.getColor() == Color.BLACK
				&& parentLeftChild.getColor() == Color.RED
				&& pLLeftChild.getColor() == Color.BLACK
				&& pLRightChild.getColor() = Color.BLACK
				&& pLRLeftChild.getColor() = Color.RED
				&& pLRRightChild.getColor() = Color.RED)
		{
			return DeleteRotation.Rr2;
		}

		// We are going to do right side deletes first then lefts

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


























