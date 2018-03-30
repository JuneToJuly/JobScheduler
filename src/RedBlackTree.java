import com.sun.org.apache.regexp.internal.REDebugCompiler;
import sun.plugin.dom.core.CoreConstants;

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

		int degree = checkDegreeOfDeletion(next);
		RedBlackNode deficientNode = findDeficientNode(degree, next);


		// We have fixed the problem with a one degree black that has a red child
		// and a one degree red that has a black child.
		// In case with one degree black having a black child, or a two degree
		// black or red, we still have our deficiency, we need to correct this now.
		if(deficientNode == null)
		{
			return;
		}

		System.out.println("I am a deficient node: " + deficientNode.getKey());
		System.out.println("My Parent is: " + deficientNode.getParent().getKey());

		// Classify this one degree rotation
		DeleteRotation deleteRotation =  classifyDeleteRotation(deficientNode);
		System.out.println("Rotation is: " + deleteRotation);
		performDeleteRotation(deficientNode, deleteRotation);

		//  Delete the node
		if (next.getKey() > next.getParent().getKey())
		{
			next.getParent().setRightChild(externalNode);
		}
		else
		{
			next.getParent().setLeftChild(externalNode);
		}


	}

	private RedBlackNode findDeficientNode(int degree, RedBlackNode node)
	{
		Child parentToDeletion = node.getKey() >
				node.getParent().getKey()
				? Child.RIGHT
				: Child.LEFT;

		RedBlackNode y = null;
		RedBlackNode defiecientNode = null;

		// Degree one node:

		// Black leaf node, we have a deficiency regardless.
		System.out.println(node.getColor() + node.getJob().toString());
		if(degree == 0 && node.getColor() == Color.BLACK)
		{
			return node;
		}
		// Red Leaf node, we are done.
		else if(degree == 0 && node.getColor() == Color.RED)
		{
			if(parentToDeletion == Child.RIGHT)
			{
				node.getParent().setRightChild(externalNode);
			}
			else
			{
				node.getParent().setLeftChild(externalNode);
			}
			return null;
		}

		/*
			Degree 1 Node

			Node is black:
				If Child is red, change to black.
				Set this child to deleted nodes child and
				set parent of child to parent of deleted node.
				We are done!
				If Child is black, do some thing with pointers,
				but now we have a deficiency.

			Node is red:
				Child should be black, otherwise we have a problem.
				WE set this child to the deleted nodes child
				and set parent of child to parent of deleted node.
				We are done!
		 */
		if(degree == 1)
		{
			// Deleting Black node, we have red child
			if(node.getLeftChild().getColor() == Color.RED)
			{
				y = node.getLeftChild();
//				y.setColor(Color.BLACK);
				y.setParent(node.getParent());
				defiecientNode = null;
			}
			else if(node.getRightChild().getColor() == Color.RED)
			{
				y = node.getRightChild();
//				y.setColor(Color.BLACK);
				y.setParent(node.getParent());
				defiecientNode = null;
			}

			// Deleting Red Node, we have black child
			if(node.getColor() == Color.RED
					&& node.getRightChild() != externalNode
					&& node.getLeftChild().getColor() == Color.BLACK)
			{
				y = node.getRightChild();
				y.setParent(node.getParent());
				defiecientNode = null;
			}
			else if(node.getColor() == Color.RED
					&& node.getLeftChild() != externalNode
					&& node.getRightChild().getColor() == Color.BLACK)
			{
				y = node.getLeftChild();
				y.setParent(node.getParent());
				defiecientNode = null;
			}

			// We are a deleting a black node and the child is
			// black. We still have a deficiency.
			if(node.getColor() == Color.BLACK
					&& node.getRightChild() != externalNode
					&& node.getRightChild().getColor() == Color.BLACK)
			{
				y = node.getRightChild();
				y.setParent(node.getParent());
				defiecientNode = y;
			}

			if(node.getColor() == Color.BLACK
					&& node.getLeftChild() != externalNode
					&& node.getLeftChild().getColor() == Color.BLACK)
			{
				y = node.getLeftChild();
				y.setParent(node.getParent());
				defiecientNode = y;
			}

			// Regardless, we need to set the parent of deleted
			// node to our deleted nodes child, and our child node's
			// parent needs to be set to our deleted nodes parent.
			if(parentToDeletion == Child.RIGHT)
			{
				node.getParent().setRightChild(y);
			}
			else
			{
				node.getParent().setLeftChild(y);
			}
			y.setColor(Color.BLACK);

			return defiecientNode;
		}

		if(degree == 2)
		{
			RedBlackNode leftSubStree = node.getLeftChild();
			RedBlackNode largestNode = leftSubStree;
			RedBlackNode nextNode = leftSubStree;

			while(nextNode != externalNode)
			{
				largestNode = nextNode;
				nextNode.getRightChild();
			}

			// Swap the keys because we don't ever delete this node
			node.setJob(largestNode.getJob());

			// Change left child parent to largest nodes parent
			// Change parents right child to largest left node
			largestNode.getLeftChild().setParent(largestNode.getParent());
			largestNode.getParent().setRightChild(largestNode.getLeftChild());

			// Largest node is red, could have a left child, it won't be red
			// We are done!
			if(largestNode.getColor() == Color.RED)
			{
				return null;
			}
			// Else the largest node was black and we have a deficiency when deleting the black node.
			return largestNode.getLeftChild();
		}
		return null;
	}

	private int checkDegreeOfDeletion(RedBlackNode next)
	{
		int degree = 0;
		if(next.getLeftChild() != externalNode)
		{
			degree++;
		}
		if(next.getRightChild()!= externalNode)
		{
			degree++;
		}
		System.out.println("I am a degree: " + degree + " node.");
		return degree;
	}

	private void performDeleteRotation(RedBlackNode deletedNode, DeleteRotation deleteRotation)
	{
		Color initRootColor = null;

		RedBlackNode nodeC = null;
		RedBlackNode nodeB = null;
		RedBlackNode nodeD = null;

		// node according to slides

		RedBlackNode py = null;
		RedBlackNode v = null;
		RedBlackNode a = null;
		RedBlackNode b = null;
		RedBlackNode w = null;
		RedBlackNode c = null;
		RedBlackNode d = null;
		RedBlackNode x = null;
		Child parentToGrandParent = deletedNode.getParent().getKey() >
				deletedNode.getParent().getParent().getKey()
				? Child.RIGHT
				: Child.LEFT;

		Child parentToDeletion = deletedNode.getParent().getKey() >
				deletedNode.getParent().getParent().getKey()
				? Child.RIGHT
				: Child.LEFT;
		switch (deleteRotation)
		{
			case Rb01:
				deletedNode.getParent().getLeftChild().setColor(Color.RED);
				deletedNode.getParent().setRightChild(externalNode);
				break;
			case Rb02:
				deletedNode.getParent().setColor(Color.BLACK);
				deletedNode.getParent().getLeftChild().setColor(Color.RED);
				break;
			case Rb11:
				// Change colors first
				initRootColor = deletedNode.getParent().getColor();
				deletedNode.getParent().getLeftChild().getLeftChild().setColor(Color.BLACK);
				deletedNode.getParent().setColor(Color.BLACK);
				deletedNode.getParent().getLeftChild().setColor(initRootColor);

				// V Parent
				if(parentToGrandParent == Child.RIGHT)
				{
					deletedNode.getParent().getParent().setRightChild(deletedNode.getParent().getLeftChild());
				}
				else
				{
					deletedNode.getParent().getParent().setLeftChild(deletedNode.getParent().getLeftChild());
				}
				deletedNode.getParent().getLeftChild().setParent(deletedNode.getParent().getParent());

				RedBlackNode nodeBb = deletedNode.getParent().getLeftChild().getRightChild();

				// V right child now Old Parent
				deletedNode.getParent().getLeftChild().setRightChild(deletedNode.getParent());
				deletedNode.getParent().setParent(deletedNode.getParent().getLeftChild());

				// B Taken Care of
				deletedNode.getParent().setLeftChild(nodeBb);
				deletedNode.getParent().getLeftChild().setParent(deletedNode.getParent());

				break;
			case Rb12:
				py = deletedNode.getParent();
				v = deletedNode.getParent().getLeftChild();
				a = deletedNode.getParent().getLeftChild().getLeftChild();
				w = deletedNode.getParent().getLeftChild().getRightChild();
				b = deletedNode.getParent().getLeftChild().getRightChild().getLeftChild();
				c = deletedNode.getParent().getLeftChild().getRightChild().getRightChild();


				initRootColor = deletedNode.getParent().getColor();
				w.setColor(initRootColor);
				py.setColor(Color.BLACK);

				// W to GP
				if(parentToGrandParent == Child.RIGHT)
				{
					deletedNode.getParent().getParent().setRightChild(w);
				}
				else
				{
					deletedNode.getParent().getParent().setLeftChild(w);
				}

				w.setParent(py.getParent());

				// B to V
				b.setParent(v);
				v.setRightChild(b);

				// C to py
				py.setLeftChild(c);
				c.setParent(py);

				// V to W
				w.setLeftChild(v);
				v.setParent(w);

				//  Py to W
				w.setRightChild(py);
				py.setParent(w);

				py.setRightChild(externalNode);

				System.out.println(py.getKey());

				break;
			case Rb2:
				py = deletedNode.getParent();
				v = deletedNode.getParent().getLeftChild();
				a = deletedNode.getParent().getLeftChild().getLeftChild();
				w = deletedNode.getParent().getLeftChild().getRightChild();
				b = deletedNode.getParent().getLeftChild().getRightChild().getLeftChild();
				c = deletedNode.getParent().getLeftChild().getRightChild().getRightChild();


				initRootColor = deletedNode.getParent().getColor();
				w.setColor(initRootColor);
				py.setColor(Color.BLACK);

				// W to GP
				if(parentToGrandParent == Child.RIGHT)
				{
					deletedNode.getParent().getParent().setRightChild(w);
				}
				else
				{
					deletedNode.getParent().getParent().setLeftChild(w);
				}

				w.setParent(py.getParent());

				// B to V
				b.setParent(v);
				v.setRightChild(b);

				// C to py
				py.setLeftChild(c);
				c.setParent(py);

				// V to W
				w.setLeftChild(v);
				v.setParent(w);

				//  Py to W
				w.setRightChild(py);
				py.setParent(w);

				py.setRightChild(externalNode);

				System.out.println(py.getKey());

				break;
			case Rr0:
				py = deletedNode.getParent();
				v = deletedNode.getParent().getLeftChild();
				a = deletedNode.getParent().getLeftChild().getLeftChild();
				b = deletedNode.getParent().getLeftChild().getRightChild();

				v.setColor(Color.BLACK);
				b.setColor(Color.RED);

				// W to GP
				if(parentToGrandParent == Child.RIGHT)
				{
					deletedNode.getParent().getParent().setRightChild(v);
				}
				else
				{
					deletedNode.getParent().getParent().setLeftChild(v);
				}
				v.setParent(deletedNode.getParent().getParent());

				// B to Py
				b.setParent(py);
				py.setLeftChild(b);

				//  Py to W
				v.setRightChild(py);
				py.setParent(v);
				break;
			case Rr11:

				py = deletedNode.getParent();
				v = deletedNode.getParent().getLeftChild();
				a = deletedNode.getParent().getLeftChild().getLeftChild();
				w = deletedNode.getParent().getLeftChild().getRightChild();
				b = deletedNode.getParent().getLeftChild().getRightChild().getLeftChild();
				c = deletedNode.getParent().getLeftChild().getRightChild().getRightChild();

				w.setColor(Color.BLACK);
				py.setColor(Color.BLACK);
				b.setColor(Color.BLACK);

				// W to GP
				if(parentToGrandParent == Child.RIGHT)
				{
					deletedNode.getParent().getParent().setRightChild(w);
				}
				else
				{
					deletedNode.getParent().getParent().setLeftChild(w);
				}
				w.setParent(py.getParent());

				// B to V
				b.setParent(v);
				v.setRightChild(b);

				// C to py
				py.setLeftChild(c);
				c.setParent(py);

				// V to W
				w.setLeftChild(v);
				v.setParent(w);

				//  Py to W
				w.setRightChild(py);
				py.setParent(w);

				py.setRightChild(externalNode);

				break;
			case Rr12:
				py = deletedNode.getParent();
				v = deletedNode.getParent().getLeftChild();
				a = deletedNode.getParent().getLeftChild().getLeftChild();
				w = deletedNode.getParent().getLeftChild().getRightChild();
				b = deletedNode.getParent().getLeftChild().getRightChild().getLeftChild();
				x = w.getRightChild();
				c = x.getLeftChild();
				d = x.getRightChild();

				x.setColor(Color.BLACK);

				// X to GP
				if(parentToGrandParent == Child.RIGHT)
				{
					deletedNode.getParent().getParent().setRightChild(x);
				}
				else
				{
					deletedNode.getParent().getParent().setLeftChild(x);
				}
				x.setParent(py.getParent());

				// C to W
				c.setParent(w);
				w.setRightChild(c);

				// D to Py
				py.setLeftChild(d);
				d.setParent(py);

				// V to X
				x.setLeftChild(v);
				v.setParent(x);

				//  Py to X
				x.setRightChild(py);
				py.setParent(x);
				py.setRightChild(RedBlackTree.externalNode);
				break;
			case Rr2:
				py = deletedNode.getParent();
				v = deletedNode.getParent().getLeftChild();
				a = deletedNode.getParent().getLeftChild().getLeftChild();
				w = deletedNode.getParent().getLeftChild().getRightChild();
				b = deletedNode.getParent().getLeftChild().getRightChild().getLeftChild();
				x = w.getRightChild();
				c = x.getLeftChild();
				d = x.getRightChild();

				x.setColor(Color.BLACK);

				// X to GP
				if(parentToGrandParent == Child.RIGHT)
				{
					deletedNode.getParent().getParent().setRightChild(x);
				}
				else
				{
					deletedNode.getParent().getParent().setLeftChild(x);
				}
				x.setParent(py.getParent());

				// C to W
				c.setParent(w);
				w.setRightChild(c);

				// D to Py
				py.setLeftChild(d);
				d.setParent(py);

				// V to X
				x.setLeftChild(v);
				v.setParent(x);

				//  Py to X
				x.setRightChild(py);
				py.setParent(x);
				py.setRightChild(RedBlackTree.externalNode);
				break;
			case Lb01:
				// Color Change
				deletedNode.getParent().getRightChild().setColor(Color.RED);
				deletedNode.getParent().setLeftChild(RedBlackTree.externalNode);
				break;
			case Lb02:
				deletedNode.getParent().setColor(Color.BLACK);
				deletedNode.getParent().getRightChild().setColor(Color.RED);
				deletedNode.getParent().setLeftChild(RedBlackTree.externalNode);
				break;
			case Lb11:
				py = deletedNode.getParent();
				v = deletedNode.getParent().getRightChild();
				a = deletedNode.getParent().getRightChild().getRightChild();
				b = deletedNode.getParent().getRightChild().getLeftChild();

				initRootColor = deletedNode.getParent().getColor();
				v.setColor(initRootColor);
				a.setColor(Color.BLACK);
				py.setColor(Color.BLACK);

				// V to GP
				if(parentToGrandParent == Child.RIGHT)
				{
					deletedNode.getParent().getParent().setRightChild(v);
				}
				else
				{
					deletedNode.getParent().getParent().setLeftChild(v);
				}
				v.setParent(py.getParent());

				// B to py
				b.setParent(py);
				py.setRightChild(b);

				py.setParent(v);
				v.setLeftChild(py);

				py.setLeftChild(RedBlackTree.externalNode);
				break;
			case Lb12:
				py = deletedNode.getParent();
				v = deletedNode.getParent().getRightChild();
				a = deletedNode.getParent().getRightChild().getRightChild();
				w = deletedNode.getParent().getRightChild().getLeftChild();
				b = deletedNode.getParent().getRightChild().getLeftChild().getRightChild();
				c = deletedNode.getParent().getRightChild().getLeftChild().getLeftChild();

				initRootColor = deletedNode.getParent().getColor();
				w.setColor(initRootColor);

				// W to GP
				if(parentToGrandParent == Child.RIGHT)
				{
					deletedNode.getParent().getParent().setRightChild(w);
				}
				else
				{
					deletedNode.getParent().getParent().setLeftChild(w);
				}
				w.setParent(deletedNode.getParent().getParent());

				// B to V
				b.setParent(v);
				v.setLeftChild(b);

				// C to py
				py.setRightChild(c);
				c.setParent(py);

				// V to W
				w.setRightChild(v);
				v.setParent(w);

				//  Py to W
				w.setLeftChild(py);
				py.setParent(w);
				break;
			case Lb2:
				py = deletedNode.getParent();
				v = deletedNode.getParent().getRightChild();
				a = deletedNode.getParent().getRightChild().getRightChild();
				w = deletedNode.getParent().getRightChild().getLeftChild();
				b = deletedNode.getParent().getRightChild().getLeftChild().getRightChild();
				c = deletedNode.getParent().getRightChild().getLeftChild().getLeftChild();

				initRootColor = deletedNode.getParent().getColor();
				w.setColor(initRootColor);

				// W to GP
				if(parentToGrandParent == Child.RIGHT)
				{
					deletedNode.getParent().getParent().setRightChild(w);
				}
				else
				{
					deletedNode.getParent().getParent().setLeftChild(w);
				}
				w.setParent(deletedNode.getParent().getParent());

				// B to V
				b.setParent(v);
				v.setLeftChild(b);

				// C to py
				py.setRightChild(c);
				c.setParent(py);

				// V to W
				w.setRightChild(v);
				v.setParent(w);

				//  Py to W
				w.setLeftChild(py);
				py.setParent(w);
				break;
			case Lr0:
				py = deletedNode.getParent();
				v = deletedNode.getParent().getRightChild();
				a = deletedNode.getParent().getRightChild().getRightChild();
				b = deletedNode.getParent().getRightChild().getLeftChild();

				v.setColor(Color.BLACK);
				b.setColor(Color.RED);

				// W to GP
				if(parentToGrandParent == Child.RIGHT)
				{
					deletedNode.getParent().getParent().setRightChild(v);
				}
				else
				{
					deletedNode.getParent().getParent().setLeftChild(v);
				}
				v.setParent(deletedNode.getParent().getParent());

				// B to Py
				b.setParent(py);
				py.setRightChild(b);

				//  Py to W
				v.setLeftChild(py);
				py.setParent(v);
				break;
			case Lr11:
				py = deletedNode.getParent();
				v = deletedNode.getParent().getRightChild();
				a = deletedNode.getParent().getRightChild().getRightChild();
				w = deletedNode.getParent().getRightChild().getLeftChild();
				b = deletedNode.getParent().getRightChild().getLeftChild().getRightChild();
				c = deletedNode.getParent().getRightChild().getLeftChild().getLeftChild();

				b.setColor(Color.BLACK);

				// W to GP
				if(parentToGrandParent == Child.RIGHT)
				{
					deletedNode.getParent().getParent().setRightChild(w);
				}
				else
				{
					deletedNode.getParent().getParent().setLeftChild(w);
				}
				w.setParent(deletedNode.getParent().getParent());

				// B to V
				b.setParent(v);
				v.setLeftChild(b);

				// C to py
				py.setRightChild(c);
				c.setParent(py);

				// V to W
				w.setRightChild(v);
				v.setParent(w);

				//  Py to W
				w.setLeftChild(py);
				py.setParent(w);
				break;
			case Lr12:
				py = deletedNode.getParent();
				v = deletedNode.getParent().getRightChild();
				a = deletedNode.getParent().getRightChild().getRightChild();
				w = deletedNode.getParent().getRightChild().getLeftChild();
				b = deletedNode.getParent().getRightChild().getLeftChild().getRightChild();
				x = w.getLeftChild();
				c = x.getRightChild();
				d = x.getLeftChild();


				x.setColor(Color.BLACK);

				// X to GP
				if(parentToGrandParent == Child.RIGHT)
				{
					deletedNode.getParent().getParent().setRightChild(x);
				}
				else
				{
					deletedNode.getParent().getParent().setLeftChild(x);
				}
				x.setParent(deletedNode.getParent().getParent());

				// C to W
				c.setParent(w);
				c.setLeftChild(w);

				// D to Py
				py.setRightChild(d);
				d.setParent(py);

				// V to X
				x.setRightChild(v);
				v.setParent(x);

				//  Py to X
				x.setLeftChild(py);
				py.setParent(x);
				break;
			case Lr2:
				py = deletedNode.getParent();
				v = deletedNode.getParent().getRightChild();
				a = deletedNode.getParent().getRightChild().getRightChild();
				w = deletedNode.getParent().getRightChild().getLeftChild();
				b = deletedNode.getParent().getRightChild().getLeftChild().getRightChild();
				x = w.getLeftChild();
				c = x.getLeftChild();
				d = x.getRightChild();


				x.setColor(Color.BLACK);

				// X to GP
				if(parentToGrandParent == Child.RIGHT)
				{
					deletedNode.getParent().getParent().setRightChild(x);
				}
				else
				{
					deletedNode.getParent().getParent().setLeftChild(x);
				}
				x.setParent(deletedNode.getParent().getParent());

				// C to W
				c.setParent(w);
				c.setLeftChild(w);

				// D to Py
				py.setRightChild(d);
				d.setParent(py);

				// V to X
				x.setRightChild(v);
				v.setParent(x);

				//  Py to X
				x.setLeftChild(py);
				py.setParent(x);
				break;

			default:
		}

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

	/*
		Classifies the deletes
	 */
	private DeleteRotation classifyDeleteRotation(RedBlackNode deleteNode)
	{
		RedBlackNode parent = deleteNode.getParent();

		Child parentToDelete = null;

		if(parent.getParent() != null)
		{
			parentToDelete = deleteNode.getKey() > parent.getKey()
					? Child.RIGHT
					: Child.LEFT;
		}

		if(parentToDelete == Child.RIGHT)
		{
			RedBlackNode parentLeftChild = parent.getLeftChild();
			RedBlackNode pLLeftChild = parentLeftChild.getLeftChild();
			RedBlackNode pLRightChild = parentLeftChild.getRightChild();
			RedBlackNode pLRRightChild = pLRightChild.getRightChild();
			RedBlackNode pLRLeftChild  = pLRightChild.getLeftChild();

			if ((parent.getColor() == Color.BLACK)
					&& (parentLeftChild.getColor() == Color.BLACK)
					&& (pLLeftChild.getColor() == Color.BLACK)
					&& (pLRightChild.getColor() == Color.BLACK))
			{
				return DeleteRotation.Rb01;
			}

			if ((parent.getColor() == Color.RED)
					&& (parentLeftChild.getColor() == Color.BLACK)
					&& (pLLeftChild.getColor() == Color.BLACK)
					&& (pLRightChild.getColor() == Color.BLACK))
			{
				return DeleteRotation.Rb02;
			}

			if ((parentLeftChild.getColor() == Color.BLACK)
					&& (pLLeftChild.getColor() == Color.RED)
					&& (pLRightChild.getColor() == Color.BLACK))
			{
				return DeleteRotation.Rb11;
			}

			if ((parentLeftChild.getColor() == Color.BLACK)
					&& (pLLeftChild.getColor() == Color.BLACK)
					&& (pLRightChild.getColor() == Color.RED))
			{
				return DeleteRotation.Rb12;
			}

			if ((parentLeftChild.getColor() == Color.BLACK)
					&& (pLLeftChild.getColor() == Color.RED)
					&& (pLRightChild.getColor() == Color.RED))
			{
				return DeleteRotation.Rb2;
			}

			if ((parent.getColor() == Color.BLACK)
					&& (parentLeftChild.getColor() == Color.RED)
					&& (pLLeftChild.getColor() == Color.BLACK)
					&& (pLRightChild.getColor() == Color.BLACK)
					&& (pLRLeftChild.getColor() == Color.BLACK)
					&& (pLRRightChild.getColor() == Color.BLACK))
			{
				return DeleteRotation.Rr0;
			}

			if ((parent.getColor() == Color.BLACK)
					&& (parentLeftChild.getColor() == Color.RED)
					&& (pLLeftChild.getColor() == Color.BLACK)
					&& (pLRightChild.getColor() == Color.BLACK)
					&& (pLRLeftChild.getColor() == Color.RED)
					&& (pLRRightChild.getColor() == Color.BLACK))
			{
				return DeleteRotation.Rr11;
			}

			if ((parent.getColor() == Color.BLACK)
					&& (parentLeftChild.getColor() == Color.RED)
					&& (pLLeftChild.getColor() == Color.BLACK)
					&& (pLRightChild.getColor() == Color.BLACK)
					&& (pLRLeftChild.getColor() == Color.BLACK)
					&& (pLRRightChild.getColor() == Color.RED))
			{
				return DeleteRotation.Rr12;
			}

			if ((parent.getColor() == Color.BLACK)
					&& (parentLeftChild.getColor() == Color.RED)
					&& (pLLeftChild.getColor() == Color.BLACK)
					&& (pLRightChild.getColor() == Color.BLACK)
					&& (pLRLeftChild.getColor() == Color.RED)
					&& (pLRRightChild.getColor() == Color.RED))
			{
				return DeleteRotation.Rr2;
			}

		}

		else
		{
			RedBlackNode parentRightChild = parent.getRightChild();
			RedBlackNode pRLeftChild = parentRightChild.getLeftChild();
			RedBlackNode pRRightChild = parentRightChild.getRightChild();
			RedBlackNode pRLLeftChild = pRLeftChild.getLeftChild();
			RedBlackNode pRLRightChild = pRLeftChild.getRightChild();

			if ((parent.getColor() == Color.BLACK)
			&& (parentRightChild.getColor() == Color.BLACK)
			&& (pRLeftChild.getColor() == Color.BLACK)
			&& (pRRightChild.getColor() == Color.BLACK))
			{
				return DeleteRotation.Lb01;
			}

			if ((parent.getColor() == Color.RED)
					&& (parentRightChild.getColor() == Color.BLACK)
					&& (pRLeftChild.getColor() == Color.BLACK)
					&& (pRRightChild.getColor() == Color.BLACK))
			{
				return DeleteRotation.Lb02;
			}

			if ((parentRightChild.getColor() == Color.BLACK)
					&& (pRLeftChild.getColor() == Color.BLACK)
					&& (pRRightChild.getColor() == Color.RED))
			{
				return DeleteRotation.Lb11;
			}

			if ((parentRightChild.getColor() == Color.BLACK)
					&& (pRLeftChild.getColor() == Color.RED)
					&& (pRRightChild.getColor() == Color.BLACK))
			{
				return DeleteRotation.Lb12;
			}

			if ((parentRightChild.getColor() == Color.BLACK)
					&& (pRLeftChild.getColor() == Color.RED)
					&& (pRRightChild.getColor() == Color.RED))
			{
				return DeleteRotation.Lb2;
			}

			if ((parent.getColor() == Color.BLACK)
					&& (parentRightChild.getColor() == Color.RED)
					&& (pRLeftChild.getColor() == Color.BLACK)
					&& (pRRightChild.getColor() == Color.BLACK))
			{
				return DeleteRotation.Lr0;
			}

			if ((parent.getColor() == Color.BLACK)
					&& (parentRightChild.getColor() == Color.RED)
					&& (pRLeftChild.getColor() == Color.BLACK)
					&& (pRRightChild.getColor() == Color.BLACK)
					&& (pRLRightChild.getColor() == Color.RED)
					&& (pRLLeftChild.getColor() == Color.BLACK))
			{
				return DeleteRotation.Lr11;
			}

			if ((parent.getColor() == Color.BLACK)
					&& (parentRightChild.getColor() == Color.RED)
					&& (pRLeftChild.getColor() == Color.BLACK)
					&& (pRRightChild.getColor() == Color.BLACK)
					&& (pRLRightChild.getColor() == Color.BLACK)
					&& (pRLLeftChild.getColor() == Color.RED))
			{
				return DeleteRotation.Lr12;
			}

			if ((parent.getColor() == Color.BLACK)
					&& (parentRightChild.getColor() == Color.RED)
					&& (pRLeftChild.getColor() == Color.BLACK)
					&& (pRRightChild.getColor() == Color.BLACK)
					&& (pRLRightChild.getColor() == Color.RED)
					&& (pRLLeftChild.getColor() == Color.RED))
			{
				return DeleteRotation.Lr2;
			}

		}
		return null;
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
			if(head.getLeftChild() != externalNode)
			{
				System.out.println("Left: " + head.getLeftChild().getJob().toString() + " Color: " + head.getLeftChild().getColor());
			}
			if(head.getRightChild() != externalNode)
			{
				System.out.println("Right: " + head.getRightChild().getJob().toString() + " Color: " + head.getRightChild().getColor());
			}
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

	public void setHead(RedBlackNode head)
	{
		this.head = head;
	}
}


























