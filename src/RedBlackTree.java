/**
 * @author Ian Thomas
 */
public class RedBlackTree
{
	static RedBlackNode externalNode;
	static RedBlackNode rootNode;
	public RedBlackNode nullNode;
	boolean shouldDelete;
	boolean colorChanged;
	public RedBlackNode nodeeeee;

    // Static nodes that will be used for when we want to set children
    // to null to avoid nulls.
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
	 * Simple search for a job.
	 * @param newJob
	 * @return
	 */
	public Job search(Job newJob)
	{
		if(head == null)
		{
			return null;
		}

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
		if(node == externalNode || node == null)
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
		// No head
		if(head == null)
		{
			return null;
		}

		// Head is only element and it is larger
		// than our search job
		if(head.getJob().getId() > newJob.getId()
				&& head.getRightChild() == externalNode
				&& head.getLeftChild() == externalNode)
		{
			return head.getJob().toString();
		}

        // Finds the node
		RedBlackNode lastLeft = null;
		RedBlackNode searchNode = head;
		do
		{
			if (searchNode.getJob().getId() < newJob.getId())
			{
				searchNode = searchNode.getRightChild();
			}
			else if(searchNode.getJob().getId() > newJob.getId())
			{
				lastLeft = searchNode;
				searchNode = searchNode.getLeftChild();
			}
		} while(searchNode != externalNode
		        && searchNode.getJob().getId() != newJob.getId());

		// We never went left, and we have no external nodes
		// Therefore we have no previous value
		if(lastLeft == null
				&& searchNode.getRightChild() == externalNode)
		{
			return null;
		}

		// If we fall off, the parent of the last left child is
		// the previous node
		if(searchNode == externalNode)
		{
			return lastLeft.getJob().toString();
		}


		// We didn't fall off so we need to check if we have any
		// children, if so we need to go to right and farthest
		// down to the left
		// If not, we need to return the last left
		if(searchNode.getRightChild() == externalNode
				&& lastLeft != null)
		{
			return lastLeft.getJob().toString();
		}
		else
		{
			lastLeft = searchNode.getRightChild();
			searchNode = searchNode.getRightChild();
			while (searchNode.getLeftChild() != externalNode)
			{
				lastLeft = searchNode;
				searchNode = searchNode.getLeftChild();
			}
			return lastLeft.getJob().toString();
		}
	}

	/**
	 * Gets the prv Job.
	 * @param newJob
	 * @return
	 */
	public String previous(Job newJob)
	{
		// No head
		if(head == null)
		{
			return null;
		}

        // The head is less that the key, and it the only value in the tree.
		if(head.getJob().getId() < newJob.getId()
				&& head.getRightChild() == externalNode
				&& head.getLeftChild() == externalNode)
		{
			return head.getJob().toString();
		}
        // The head is larger than the key, and it is the only value in the tree.
		else if(head.getJob().getId() > newJob.getId()
				&& head.getRightChild() == externalNode
				&& head.getLeftChild() == externalNode)
		{
			return null;
		}

		RedBlackNode lastRight = null;
		RedBlackNode searchNode = head;

        // Search for nodes previous
		do
		{
			if (searchNode.getJob().getId() < newJob.getId())
			{
				lastRight = searchNode;
				searchNode = searchNode.getRightChild();
			}
			else
			{
				searchNode = searchNode.getLeftChild();
			}
		} while(searchNode != externalNode
				&& searchNode.getJob().getId() != newJob.getId());

		// We never went right, and we have no external nodes
		// Therefore we have not previous value
		if(searchNode.getLeftChild() == externalNode
				&& lastRight == null)
		{
			return null;
		}

		// If we fall off, the parent of the last right child is
		// the previous node
		if(searchNode == externalNode)
		{
			return lastRight.getJob().toString();
		}

		// We didn't fall off so we need to check if we have any
		// children, if so we need to go to left and farthest
		// down to the right
		// If not, we need to return the last right
		if(searchNode.getLeftChild() == externalNode)
		{
			return lastRight.getJob().toString();
		}
		else
		{
			lastRight = searchNode.getLeftChild();
			searchNode = searchNode.getLeftChild();
			while (searchNode.getRightChild() != externalNode)
			{
				lastRight = searchNode;
				searchNode = searchNode.getRightChild();
			}
			return lastRight.getJob().toString();
		}
	}

	/**
	 * Searches the tree for all nodes in a given range.
	 * @param startId
	 * @param tailId
	 * @return
	 */
	public String searchInRange(int startId, int tailId)
	{
		if(head == rootNode || head == null)
		{
			return "(0,0,0)";
		}
		return searchInRangeRecursive(head, startId, tailId);
	}

	/*
		Performs a recursive search.
	 */
	private String searchInRangeRecursive(RedBlackNode node, int startId, int tailId)
	{
		boolean leftInRange = false;
		boolean rightInRange = false;
		boolean nodeInRange = false;
		String nodeString = node.getJob().toString();
		String left = "";
		String right = "";
		String additional = "";

		// Smaller than start Id
		if(node.getLeftChild()!= externalNode
				&&  node.getLeftChild().getKey() < startId
				&& node.getKey() > startId
				&& node.getLeftChild().getRightChild() != externalNode)
		{
			additional = searchInRangeRecursive(node.getLeftChild(), startId, tailId);
		}
		if(node.getRightChild() != externalNode
				&& node.getRightChild().getKey() < startId
				&& node.getRightChild().getRightChild() != externalNode)
		{
			additional = searchInRangeRecursive(node.getRightChild(), startId, tailId);
		}

		// Greater than tail Id
		if(node.getRightChild()!= externalNode
				&& node.getRightChild().getKey() > tailId
				&& node.getKey() < tailId
				&& node.getRightChild().getLeftChild() != externalNode)
		{
			additional = searchInRangeRecursive(node.getRightChild(), startId, tailId);
		}
		if(node.getLeftChild() != externalNode
				&& node.getLeftChild().getKey() > tailId
				&& node.getLeftChild().getLeftChild() != externalNode)
		{
			additional = searchInRangeRecursive(node.getLeftChild(), startId, tailId);
		}


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

		if(!additional.isEmpty() && rightInRange)
		{
			additional = "," + additional;
		}
		else if(!additional.isEmpty() && leftInRange)
		{
			additional = "," + additional;
		}

		// Node and one in range, need a comma
		if(nodeInRange)
		{
			if(rightInRange
					|| leftInRange
					|| !additional.isEmpty())
			{
				nodeString = nodeString + ",";
			}
		}

		// We need to include the node
		if(nodeInRange)
		{
			return nodeString + left + right + additional;
		}
		return left + right + additional;
	}

	// Color Enum
	public enum Color { RED, BLACK}

	// Insert Rotation Enum
	public enum InsertRotation { NONE, RRr, RRb, LLr, LLb, RLr, RLb, LRr, LRb}

	// Easier way to classify children
	public enum Child {LEFT, RIGHT}

	// Delete Rotation Enum
	public enum DeleteRotation { Rb01, Rb02, Rb11, Rb12, Rb2, Rr0, Rr11, Rr12, Rr2,
								 Lb01, Lb02, Lb11, Lb12, Lb2, Lr0, Lr11, Lr12, Lr2}

	private RedBlackNode head;

	public RedBlackTree()
	{
		head = null;
	}

	/**
	 * Deletes a node in the RBT.
	 * @param toDelete
	 */
	public void delete(Job toDelete)
	{
		if(externalNode.getRightChild() != externalNode
				|| externalNode.getLeftChild() != externalNode)
		{
			System.out.println();
		}
		if(toDelete.getId() == 13166)

		{
			System.out.println();
		}
		if(toDelete.getId() == 13166)
		{
			System.out.println();
		}
		if(head == null)
		{
			return;
		}

		RedBlackNode next = head.getKey() > toDelete.getId()
				? head.getLeftChild()
				: head.getRightChild();

		// Deleting head
		if(head.getKey() == toDelete.getId())
		{
			next = head;
		}

		// Looking for element to delete
		while(next != null && next != externalNode)
		{
			if(next.getKey() == toDelete.getId())
			{
				break;
			}
			next = next.getKey() > toDelete.getId()
					? next.getLeftChild()
					: next.getRightChild();
		}

		// Failed to find the key, exit
		if(next == externalNode || next == null)
		{
			return;
		}

		DeleteRotation deleteRotation = null;
		RedBlackNode deficientNode = null;
		do
		{
			// Check the degree of the deletion
			int degree = checkDegreeOfDeletion(next);

			// We don't want to do any more deleting when color changes
			// we are just making tree adjustments

			// We need find the deficient node, some cases for example degree two
			// the deficient node is not the node we are deleting, but the one we
			// move to its place
			if (deficientNode == null
					&& !colorChanged)
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

			// We have solved provlem
			if(deleteRotation == null)
			{break;}

			performDeleteRotation(deficientNode, deleteRotation);


			next = deficientNode.getParent();

			/*
				Some deletions have already had their nodes deleted
			 */
			if(shouldDelete)
			{
				if(deficientNode.getParent().getRightChild() == deficientNode)
				{
					deficientNode.getParent().setRightChild(externalNode);
					shouldDelete = false;
				}
				else
				{
					deficientNode.getParent().setLeftChild(externalNode);
					shouldDelete = false;
				}
			}

		}while(deleteRotation == DeleteRotation.Rb01
				|| deleteRotation == DeleteRotation.Lb01
				&& next != rootNode);

		shouldDelete = false;
		colorChanged = false;
	}

	/*
		Finds the deficient node. There are many different situations where
		a node can become deficient from a deletion. If a black node is
		deleted and cannot be replaced by a red node it becomes deficient.
	 */
	private RedBlackNode findDeficientNode(int degree, RedBlackNode node)
	{
		if(node.getKey() == 13166)
		{
			System.out.println();
		}
		// If we delete the head and it is only node
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

		/*
			Degree zero node:
			Red or Black
		 */
		// Black leaf node, we have a deficiency regardless.
		if(degree == 0
				&& node.getColor() == Color.BLACK)
		{
			shouldDelete = true;
			return node;
		}
		// Red Leaf node, we delete this node and we are done.
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
			shouldDelete = false;
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
			if(node.getLeftChild() != externalNode
			&& node.getLeftChild().getColor() == Color.RED)
			{
				y = node.getLeftChild();
				y.setParent(node.getParent());
				defiecientNode = null;
			}
			else if(node.getRightChild() != externalNode
			&& node.getRightChild().getColor() == Color.RED)
			{
				y = node.getRightChild();
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

			if(y != null && y.getParent() == rootNode)
			{
				head = y;
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
			else if(node.getColor() == Color.BLACK
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

			if(y != null)
			{
				y.setColor(Color.BLACK);
			}

			shouldDelete = false;
			return defiecientNode;
		}

		// Degree two deletions
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
			degree = checkDegreeOfDeletion(largestNode);
			if(degree == 0)
			{
				node.setLeftChild(externalNode);
				externalNode.setParent(node);
				return externalNode;
			}
			return findDeficientNode(degree, largestNode);
		}
		return null;
	}

	/*
		Checks the degree of a node.
	 */	private int checkDegreeOfDeletion(RedBlackNode next)
	{
		int degree = 0;
		if(next.getLeftChild() != externalNode)
		{
			degree++;
		}
		if(next.getRightChild() != externalNode)
		{
			degree++;
		}
		return degree;
	}

	/*
		Performs the deletion rotations.
	 */
	private void performDeleteRotation(RedBlackNode deletedNode, DeleteRotation deleteRotation)
	{
		Color initRootColor = null;

		// node according to slides
		RedBlackNode py = externalNode;
		RedBlackNode v = externalNode;
		RedBlackNode a = externalNode;
		RedBlackNode b = externalNode;
		RedBlackNode w = externalNode;
		RedBlackNode c = externalNode;
		RedBlackNode d = externalNode;
		RedBlackNode x = externalNode;

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
				if(deletedNode.getParent().getLeftChild() != externalNode)
				{
					deletedNode.getParent().getLeftChild().setColor(Color.RED);
					colorChanged = true;
				}
				break;
			case Rb02:
				deletedNode.getParent().setColor(Color.BLACK);
				if(deletedNode.getParent().getLeftChild() != externalNode)
				{
					deletedNode.getParent().getLeftChild().setColor(Color.RED);
					colorChanged = true;
				}
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
				if(b!= externalNode)
				{
					b.setColor(Color.RED);
				}

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

				if(x.getParent() == rootNode)
				{
					head = x;
					x.setColor(Color.BLACK);
				}
				break;
			case Lb01:
				// Color Change
				if(deletedNode.getParent().getRightChild() !=externalNode)
				{
					deletedNode.getParent().getRightChild().setColor(Color.RED);
					colorChanged = true;
				}
				break;
			case Lb02:
				deletedNode.getParent().setColor(Color.BLACK);
				if(deletedNode.getParent().getRightChild() != externalNode)
				{
					deletedNode.getParent().getRightChild().setColor(Color.RED);
					colorChanged = true;
				}
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
				if(b != externalNode)
				{
					b.setColor(Color.RED);
				}

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


	/**
	 * Adds a job to the RBT.
	 * @param currentJob
	 */
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

		if(externalNode.getRightChild() != externalNode
				|| externalNode.getLeftChild() != externalNode)
		{
			nullNode = insertNode;
		}

		if(currentJob.getId() == 13166)
		{
			nodeeeee = insertNode;
		}

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

	/*
		Checks to see if RBT propertyis maintained.
	 */
	private RedBlackNode checkForPropertyMaintained(RedBlackNode node)
	{
		// We set our GP to Red So if our GGp is red also, we need to keep going
		if(node.getParent().getParent().getParent().getColor() == Color.RED
				&& node.getParent().getParent().getColor() == Color.RED)
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
		RedBlackNode grandParentsParent = externalNode;
		RedBlackNode x = externalNode;
		RedBlackNode y = externalNode;
		RedBlackNode z = externalNode;
		RedBlackNode a = externalNode;
		RedBlackNode b = externalNode;
		RedBlackNode c = externalNode;
		RedBlackNode d = externalNode;

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
				if (y.getKey() > z.getParent().getKey())
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
					head = y;
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
		grandParentsParent = externalNode;
		x = externalNode;
		y = externalNode;
		z = externalNode;
		a = externalNode;
		b = externalNode;
		c = externalNode;
		d = externalNode;
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
					&& (v != externalNode)
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
					&& (b != externalNode)
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
					&& (v != externalNode)
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
					&& b != externalNode
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
		if(node == null && head != null)
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





