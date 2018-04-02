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

	/**
	 * Returns the job.
	 * @param newJob
	 * @return
	 */
	public Job search(Job newJob)
	{
		int key = newJob.getId();
		if(key == head.getKey())
		{
			return head.getJob();
		}

		if(key > head.getKey())
		{
			return search(head.getRightChild(), key).getJob();
		}
		else
		{
			return search(head.getLeftChild(), key).getJob();
		}
	}

	/*
		Searching algorithms
	 */
	private RedBlackNode search(RedBlackNode node, int key)
	{
		if(node == externalNode)
		{
			return externalNode;
		}

		if(key == node.getKey())
		{
			return node;
		}

		if(key > node.getKey())
		{
			return search(node.getRightChild(), key);
		}
		else
		{
			return search(node.getLeftChild(), key);
		}
	}

	/**
	 * Gets the next job.
	 * @param newJob
	 * @return
	 */
	public String next(Job newJob)
	{
		RedBlackNode foundNode = search(head, newJob.getId());
		if(foundNode.getRightChild() == externalNode
				&& foundNode.getParent() == rootNode)
		{
			return "(0,0,0)";
		}
		else if (foundNode.getRightChild() == externalNode)
		{
			while(foundNode.getParent().getLeftChild() != foundNode)
			{
				if(foundNode.getParent() == rootNode)
				{
					return "(0,0,0)";
				}
				foundNode = foundNode.getParent();
			}
			return foundNode.getParent().getJob().toString();
		}

		RedBlackNode next = foundNode.getRightChild();
		RedBlackNode smallest = next;

		while(next != externalNode)
		{
			smallest = next;
			next = next.getLeftChild();
		}
		return smallest.getJob().toString();
	}

	/**
	 * Gets the prv Job.
	 * @param newJob
	 * @return
	 */
	public String previous(Job newJob)
	{
		RedBlackNode foundNode = search(head, newJob.getId());
		if(foundNode.getLeftChild() == externalNode
				&& foundNode.getParent() == rootNode)
		{
			return "(0,0,0)";
		}
		else if (foundNode.getLeftChild() == externalNode)
		{
			while(foundNode.getParent().getRightChild() != foundNode)
			{
				foundNode = foundNode.getParent();
			}
			return foundNode.getParent().getJob().toString();
		}

		RedBlackNode next = foundNode.getLeftChild();
		RedBlackNode largest = next;

		while(next != externalNode)
		{
			largest = next;
			next = next.getRightChild();
		}

		return largest.getJob().toString();
	}

	public String searchInRange(int startId, int tailId)
	{
		return searchInRangeRecursive(head, startId, tailId);
	}

	private String searchInRangeRecursive(RedBlackNode node, int startId, int tailId)
	{
		boolean leftInRange = false;
		boolean rightInRange = false;
		boolean nodeInRange = false;
		String nodeString = node.getJob().toString();
		String left = "";
		String right = "";

		if(node.getKey() >= startId
				&& node.getKey() <= tailId)
		{
			nodeInRange = true;
		}

		if (node.getLeftChild() != externalNode)
		{
			leftInRange = node.getLeftChild().getKey() >= startId
					&& node.getLeftChild().getKey() <= tailId;
		}

		if (node.getRightChild() != externalNode)
		{
			rightInRange = node.getRightChild().getKey() >= startId
					&& node.getRightChild().getKey() <= tailId;
		}

		if(leftInRange)
		{
			left = searchInRangeRecursive(node.getLeftChild(), startId, tailId);
		}

		if(rightInRange)
		{
			right = searchInRangeRecursive(node.getRightChild(), startId, tailId);
		}

		// Both in range need a comma
		if(rightInRange && leftInRange)
		{
			right = "," + right;
		}

		// Node and one in range, need a comma
		if(nodeInRange)
		{
			if(rightInRange
					|| leftInRange)
			{
				nodeString = nodeString + ",";
			}
		}

		// We need to include the node
		if(nodeInRange)
		{
			return nodeString + left + right;
		}

		return left + right;
	}

	private String gatherSubTree(RedBlackNode node)
	{
		if(node.getRightChild() == externalNode
				&& node.getLeftChild() != externalNode)
		{
			return node.getJob().toString() + ","+ gatherSubTree(node.getLeftChild());
		}
		if(node.getLeftChild() == externalNode
				&& node.getRightChild() != externalNode)
		{
			return node.getJob().toString()  + "," + gatherSubTree(node.getRightChild());
		}

		if(node.getLeftChild() != externalNode
				&& node.getRightChild() != externalNode)
		{
			return node.getJob().toString() + gatherSubTree(node.getLeftChild()) + "," + gatherSubTree(node.getRightChild());
		}
		if(node == externalNode)
		{
			return "";
		}
		else
		{
			return node.getJob().toString();
		}
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

		if(head.getKey() == toDelete.getId())
		{
			next = head;
		}

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
		DeleteRotation deleteRotation = null;
		RedBlackNode deficientNode = null;
		do
		{

			int degree = checkDegreeOfDeletion(next);

			if (deficientNode == null)
			{
				deficientNode = findDeficientNode(degree, next);
			}

			// We have fixed the problem with a one degree black that has a red child
			// and a one degree red that has a black child.
			// In case with one degree black having a black child, or a two degree
			// black or red, we still have our deficiency, we need to correct this now.
			if (deficientNode == null)
			{
				return;
			}

			// Classify this one degree rotation
			deleteRotation = classifyDeleteRotation(deficientNode);
			performDeleteRotation(deficientNode, deleteRotation);

			// Only things that can keep the tree from working;
			if (deleteRotation == DeleteRotation.Rb01
					|| deleteRotation == DeleteRotation.Lb01)
			{

			}
			//  Delete the node
			// MAy be duplicate code but not sure
			if (next.getKey() > next.getParent().getKey())
			{
				next.getParent().setRightChild(externalNode);
			}
			else
			{
				next.getParent().setLeftChild(externalNode);
			}

			next = deficientNode.getParent();
		}while(deleteRotation == DeleteRotation.Rb01
				|| deleteRotation == DeleteRotation.Lb01
				&& next != rootNode);


	}

	private RedBlackNode findDeficientNode(int degree, RedBlackNode node)
	{
		if(node.getParent() == rootNode && degree ==0)
		{
			head = null;
			return null;
		}
		Child parentToDeletion = node.getKey() >
				node.getParent().getKey()
				? Child.RIGHT
				: Child.LEFT;

		RedBlackNode y = null;
		RedBlackNode defiecientNode = null;

		// Degree one node:
		// Black leaf node, we have a deficiency regardless.
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
				y.setParent(node.getParent());
				defiecientNode = null;
			}
			else if(node.getRightChild().getColor() == Color.RED)
			{
				y = node.getRightChild();
				y.setParent(node.getParent());
				defiecientNode = null;
			}
			if(y.getParent() == rootNode)
			{
				head = y;
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
			RedBlackNode largestNode = node.getLeftChild();
			RedBlackNode nextNode = largestNode.getRightChild();

			while(nextNode != externalNode)
			{
				largestNode = nextNode;
				nextNode = nextNode.getRightChild();
			}

			// Swap the keys because we don't ever delete this node
			node.setJob(largestNode.getJob());

			RedBlackNode deficientNode = null;

			// Change left child parent to largest nodes parent
			// Change parents right child to largest left node
			if (largestNode.getLeftChild() != externalNode)
			{
				deficientNode = largestNode.getLeftChild();
				deficientNode.setParent(largestNode.getParent());
			}
			else
			{
				deficientNode = new RedBlackNode(new Job(0,0,0), largestNode.getColor());
				deficientNode.setParent(largestNode.getParent());
				largestNode.setLeftChild(externalNode);
				largestNode.setRightChild(externalNode);
			}

			if(node.getLeftChild() != largestNode)
			{
				largestNode.getParent().setRightChild(deficientNode);
			}
			else
			{
				largestNode.getParent().setLeftChild(deficientNode);
			}

			// Largest node is red, could have a left child, it won't be red
			// We are done!
			if(largestNode.getColor() == Color.RED)
			{
				return null;
			}
			// Else the largest node was black and we have a deficiency when deleting the black node.
			return deficientNode;
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

				py = deletedNode.getParent();
				v = deletedNode.getParent().getLeftChild();
				a = deletedNode.getParent().getLeftChild().getLeftChild();
				b = deletedNode.getParent().getLeftChild().getRightChild();

				// V Parent
				if(parentToGrandParent == Child.RIGHT)
				{
					deletedNode.getParent().getParent().setRightChild(v);
				}
				else
				{
					deletedNode.getParent().getParent().setLeftChild(v);
				}
				v.setParent(py.getParent());


				// V right child now Old Parent
				v.setRightChild(py);
				py.setParent(v);

				// B Taken Care of
				b.setParent(py);
				py.setLeftChild(b);

				if(v.getParent() == rootNode)
				{
					head = v;
					v.setColor(Color.BLACK);
				}

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

				if(w.getParent() == rootNode)
				{
					head = w;
					w.setColor(Color.BLACK);
				}

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

				if(w.getParent() == rootNode)
				{
					head = w;
					w.setColor(Color.BLACK);
				}

				break;
			case Rr0:
				py = deletedNode.getParent();
				v = deletedNode.getParent().getLeftChild();
				a = deletedNode.getParent().getLeftChild().getLeftChild();
				b = deletedNode.getParent().getLeftChild().getRightChild();

				v.setColor(Color.BLACK);
				b.setColor(Color.RED);
				py.setRightChild(externalNode);

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

				if(v.getParent() == rootNode)
				{
					head = v;
					v.setColor(Color.BLACK);
				}
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

				if(w.getParent() == rootNode)
				{
					head = w;
					w.setColor(Color.BLACK);
				}

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

				if(x.getParent() == rootNode)
				{
					head = x;
					x.setColor(Color.BLACK);
				}

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

				if(x.getParent() == rootNode)
				{
					head = x;
					x.setColor(Color.BLACK);
				}
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

				if(v.getParent() == rootNode)
				{
					head = v;
					v.setColor(Color.BLACK);
				}
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

				py.setLeftChild(externalNode);

				if(w.getParent() == rootNode)
				{
					head = w;
					w.setColor(Color.BLACK);
				}
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

				py.setLeftChild(externalNode);

				if(w.getParent() == rootNode)
				{
					head = w;
					w.setColor(Color.BLACK);
				}
				break;
			case Lr0:
				py = deletedNode.getParent();
				v = deletedNode.getParent().getRightChild();
				a = deletedNode.getParent().getRightChild().getRightChild();
				b = deletedNode.getParent().getRightChild().getLeftChild();

				v.setColor(Color.BLACK);
				py.setColor(Color.BLACK);
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
				v.setParent(py.getParent());

				// B to Py
				b.setParent(py);
				py.setRightChild(b);

				//  Py to W
				v.setLeftChild(py);
				py.setParent(v);

				py.setParent(v);
				py.setLeftChild(externalNode);

				if(v.getParent() == rootNode)
				{
					head = v;
					v.setColor(Color.BLACK);
				}

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
				w.setParent(py.getParent());

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

				py.setLeftChild(externalNode);

				if(w.getParent() == rootNode)
				{
					head = w;
					w.setColor(Color.BLACK);
				}
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
				py.setLeftChild(externalNode);

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
				w.setLeftChild(c);

				// B to Py
				py.setRightChild(b);
				b.setParent(py);

				// V to X
				x.setRightChild(v);
				v.setParent(x);

				//  Py to X
				x.setLeftChild(py);
				py.setParent(x);

				d.setParent(w);
				w.setRightChild(d);


				if(x.getParent() == rootNode)
				{
					head = x;
					x.setColor(Color.BLACK);
				}
				break;
			case Lr2:
				py = deletedNode.getParent();
				v = deletedNode.getParent().getRightChild();
				a = deletedNode.getParent().getRightChild().getRightChild();
				w = deletedNode.getParent().getRightChild().getLeftChild();
				b = deletedNode.getParent().getRightChild().getLeftChild().getRightChild();
				x = w.getLeftChild();
				c = x.getRightChild();
				d = x.getLeftChild();

				py.setLeftChild(externalNode);

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
				w.setLeftChild(c);

				// B to Py
				py.setRightChild(b);
				b.setParent(py);

				// V to X
				x.setRightChild(v);
				v.setParent(x);

				//  Py to X
				x.setLeftChild(py);
				py.setParent(x);

				d.setParent(w);
				w.setRightChild(d);

				if(x.getParent() == rootNode)
				{
					head = x;
					x.setColor(Color.BLACK);
				}
				break;
			default:
		}

	}


	public void add(Job currentJob)
	{

		if(head == null)
		{
			head = new RedBlackNode(currentJob, Color.BLACK);
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
		// We set our GP to Red So if our GGp is red also, we need to keep going
		if(node.getParent().getParent().getParent().getColor() == Color.RED)
		{
			// Bad node, our Grandparent
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
		RedBlackNode x;
		RedBlackNode y;
		RedBlackNode z;
		RedBlackNode a;
		RedBlackNode b;
		RedBlackNode c;
		RedBlackNode d;

		Child greatToRootRotation;

		// Switch time
		switch (insertRotation)
		{
			// Nothing to do. This was the case for a black node. We have already set our parent
			// and we have set ourselves to our parent's child.
			case NONE:
				break;
			//  All 'r' are just color flips where we propigate the change up the tree
			case RRr:
				x = insertNode;
				y = insertNode.getParent();
				z = y.getParent();
				d = z.getLeftChild();
				y.setColor(Color.BLACK);
				z.setColor(Color.RED);
				d.setColor(Color.BLACK);

				if (z == head)
				{
					head.setColor(Color.BLACK);
				}
				break;
			case RRb:
				x = insertNode;
				y = insertNode.getParent();
				z = y.getParent();
				a = x.getRightChild();
				b = x.getLeftChild();
				c = y.getLeftChild();
				d = z.getLeftChild();

				// Set GGP child to new root of rotation
				if(y.getKey() > z.getParent().getKey())
				{
					z.getParent().setRightChild(y);
				}
				else
				{
					z.getParent().setLeftChild(y);
				}
				// Colors
				y.setColor(Color.BLACK);
				z.setColor(Color.RED);

				y.setLeftChild(z);
				y.setRightChild(x);
				y.setParent(z.getParent());

				z.setParent(y);
				z.setRightChild(c);

				c.setParent(z);

				if(y.getParent() == rootNode)
				{
					head = y;
					head.setColor(Color.BLACK);
				}
				break;
			case LLr:
				x = insertNode;
				y = insertNode.getParent();
				z = y.getParent();
				d = z.getRightChild();
				y.setColor(Color.BLACK);
				z.setColor(Color.RED);
				d.setColor(Color.BLACK);

				if (z == head)
				{
					head.setColor(Color.BLACK);
				}
				break;
			case LLb:
				x = insertNode;
				y = insertNode.getParent();
				z = y.getParent();
				a = x.getLeftChild();
				b = x.getRightChild();
				c = y.getRightChild();
				d = z.getRightChild();

				// Set GGP child to new root of rotation
				if(y.getKey() > z.getParent().getKey())
				{
					z.getParent().setRightChild(y);
				}
				else
				{
					z.getParent().setLeftChild(y);
				}

				// Colors
				y.setColor(Color.BLACK);
				z.setColor(Color.RED);

				y.setRightChild(z);
				y.setLeftChild(x);
				y.setParent(z.getParent());

				z.setParent(y);
				z.setLeftChild(c);

				c.setParent(z);

				if(y.getParent() == rootNode)
				{
					head = y;
					head.setColor(Color.BLACK);
				}
				break;
			case RLr:
				y = insertNode;
				x = insertNode.getParent();
				z = x.getParent();
				d = z.getLeftChild();
				x.setColor(Color.BLACK);
				z.setColor(Color.RED);
				d.setColor(Color.BLACK);

				if (z == head)
				{
					head.setColor(Color.BLACK);
				}
				break;
			case RLb:
				y = insertNode;
				x = insertNode.getParent();
				z = x.getParent();
				a = x.getRightChild();
				b = y.getRightChild();
				c = y.getLeftChild();
				d = z.getLeftChild();

				// Set GGP child to new root of rotation
				if(y.getKey() > z.getParent().getKey())
				{
					z.getParent().setRightChild(y);
				}
				else
				{
					z.getParent().setLeftChild(y);
				}

				// Colors
				y.setColor(Color.BLACK);
				z.setColor(Color.RED);

				y.setLeftChild(z);
				y.setRightChild(x);
				y.setParent(z.getParent());

				x.setParent(y);
				x.setLeftChild(b);

				z.setParent(y);
				z.setRightChild(c);

				c.setParent(z);
				b.setParent(x);

				if(y.getParent() == rootNode)
				{
					head = insertNode.getParent();
					head.setColor(Color.BLACK);
				}
				break;
			case LRr:
				y = insertNode;
				x = insertNode.getParent();
				z = x.getParent();
				d = z.getRightChild();
				x.setColor(Color.BLACK);
				z.setColor(Color.RED);
				d.setColor(Color.BLACK);

				if (z == head)
				{
					head.setColor(Color.BLACK);
				}
				break;
			case LRb:
				y = insertNode;
				x = insertNode.getParent();
				z = x.getParent();
				a = x.getLeftChild();
				b = y.getLeftChild();
				c = y.getRightChild();
				d = z.getRightChild();

				// Set GGP child to new root of rotation
				if(y.getKey() > z.getParent().getKey())
				{
					z.getParent().setRightChild(y);
				}
				else
				{
					z.getParent().setLeftChild(y);
				}

				// Colors
				y.setColor(Color.BLACK);
				z.setColor(Color.RED);

				y.setRightChild(z);
				y.setLeftChild(x);
				y.setParent(z.getParent());

				x.setParent(y);
				x.setRightChild(b);

				z.setParent(y);
				z.setLeftChild(c);

				c.setParent(z);
				b.setParent(x);

				if(y.getParent() == rootNode)
				{
					head = y;
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
		RedBlackNode py = deleteNode.getParent();

		Child parentToDelete = null;

		if(py.getParent() != null)
		{
			parentToDelete = deleteNode.getKey() > py.getKey()
					? Child.RIGHT
					: Child.LEFT;
		}

		if(parentToDelete == Child.RIGHT)
		{
			RedBlackNode v = py.getLeftChild();
			RedBlackNode a = v.getLeftChild();
			RedBlackNode b = v.getRightChild();
			RedBlackNode b_right = b.getRightChild();
			RedBlackNode b_left  = b.getLeftChild();

			if ((py.getColor() == Color.BLACK)
					&& (v.getColor() == Color.BLACK)
					&& (a.getColor() == Color.BLACK)
					&& (b.getColor() == Color.BLACK))
			{
				return DeleteRotation.Rb01;
			}

			if ((py.getColor() == Color.RED)
					&& (v.getColor() == Color.BLACK)
					&& (a.getColor() == Color.BLACK)
					&& (b.getColor() == Color.BLACK))
			{
				return DeleteRotation.Rb02;
			}

			if ((v.getColor() == Color.BLACK)
					&& (a.getColor() == Color.RED)
					&& (b.getColor() == Color.BLACK))
			{
				return DeleteRotation.Rb11;
			}

			if ((v.getColor() == Color.BLACK)
					&& (a.getColor() == Color.BLACK)
					&& (b.getColor() == Color.RED))
			{
				return DeleteRotation.Rb12;
			}

			if ((v.getColor() == Color.BLACK)
					&& (a.getColor() == Color.RED)
					&& (b.getColor() == Color.RED))
			{
				return DeleteRotation.Rb2;
			}

			if ((py.getColor() == Color.BLACK)
					&& (v.getColor() == Color.RED)
					&& (a.getColor() == Color.BLACK)
					&& (b.getColor() == Color.BLACK)
					&& (b_left.getColor() == Color.BLACK)
					&& (b_right.getColor() == Color.BLACK))
			{
				return DeleteRotation.Rr0;
			}

			if ((py.getColor() == Color.BLACK)
					&& (v.getColor() == Color.RED)
					&& (a.getColor() == Color.BLACK)
					&& (b.getColor() == Color.BLACK)
					&& (b_left.getColor() == Color.RED)
					&& (b_right.getColor() == Color.BLACK))
			{
				return DeleteRotation.Rr11;
			}

			if ((py.getColor() == Color.BLACK)
					&& (v.getColor() == Color.RED)
					&& (a.getColor() == Color.BLACK)
					&& (b.getColor() == Color.BLACK)
					&& (b_left.getColor() == Color.BLACK)
					&& (b_right.getColor() == Color.RED))
			{
				return DeleteRotation.Rr12;
			}

			if ((py.getColor() == Color.BLACK)
					&& (v.getColor() == Color.RED)
					&& (a.getColor() == Color.BLACK)
					&& (b.getColor() == Color.BLACK)
					&& (b_left.getColor() == Color.RED)
					&& (b_right.getColor() == Color.RED))
			{
				return DeleteRotation.Rr2;
			}

		}

		else
		{
			RedBlackNode v = py.getRightChild();
			RedBlackNode b = v.getLeftChild();
			RedBlackNode a = v.getRightChild();
			RedBlackNode b_left = b.getLeftChild();
			RedBlackNode b_right = b.getRightChild();

			if ((py.getColor() == Color.BLACK)
			&& (v.getColor() == Color.BLACK)
			&& (b.getColor() == Color.BLACK)
			&& (a.getColor() == Color.BLACK))
			{
				return DeleteRotation.Lb01;
			}

			if ((py.getColor() == Color.RED)
					&& (v.getColor() == Color.BLACK)
					&& (b.getColor() == Color.BLACK)
					&& (a.getColor() == Color.BLACK))
			{
				return DeleteRotation.Lb02;
			}

			if ((v.getColor() == Color.BLACK)
					&& (b.getColor() == Color.BLACK)
					&& (a.getColor() == Color.RED))
			{
				return DeleteRotation.Lb11;
			}

			if ((v.getColor() == Color.BLACK)
					&& (b.getColor() == Color.RED)
					&& (a.getColor() == Color.BLACK))
			{
				return DeleteRotation.Lb12;
			}

			if ((v.getColor() == Color.BLACK)
					&& (b.getColor() == Color.RED)
					&& (a.getColor() == Color.RED))
			{
				return DeleteRotation.Lb2;
			}

			if ((py.getColor() == Color.BLACK)
					&& (v.getColor() == Color.RED)
					&& (b.getColor() == Color.BLACK)
					&& (a.getColor() == Color.BLACK)
					&& (b_right.getColor() == Color.BLACK)
					&& (b_left.getColor() == Color.BLACK))
			{
				return DeleteRotation.Lr0;
			}

			if ((py.getColor() == Color.BLACK)
					&& (v.getColor() == Color.RED)
					&& (b.getColor() == Color.BLACK)
					&& (a.getColor() == Color.BLACK)
					&& (b_right.getColor() == Color.RED)
					&& (b_left.getColor() == Color.BLACK))
			{
				return DeleteRotation.Lr11;
			}

			if ((py.getColor() == Color.BLACK)
					&& (v.getColor() == Color.RED)
					&& (b.getColor() == Color.BLACK)
					&& (a.getColor() == Color.BLACK)
					&& (b_right.getColor() == Color.BLACK)
					&& (b_left.getColor() == Color.RED))
			{
				return DeleteRotation.Lr12;
			}

			if ((py.getColor() == Color.BLACK)
					&& (v.getColor() == Color.RED)
					&& (b.getColor() == Color.BLACK)
					&& (a.getColor() == Color.BLACK)
					&& (b_right.getColor() == Color.RED)
					&& (b_left.getColor() == Color.RED))
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
			if(grandParent.getRightChild() == externalNode
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
			if(grandParent.getRightChild() == externalNode
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
			if(grandParent.getLeftChild() == externalNode
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
			if(grandParent.getLeftChild() == externalNode
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





