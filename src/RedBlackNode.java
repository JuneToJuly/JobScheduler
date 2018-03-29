public class RedBlackNode
{
	private Job job;
	private int key;
	private RedBlackTree.Color color;
	private RedBlackNode parent;
	private RedBlackNode leftChild;
	private RedBlackNode rightChild;

	public RedBlackNode(Job job, RedBlackTree.Color color)
	{
		this.job = job;
		this.key = job.getId();
		this.color = color;

		parent = null;
		leftChild = null;
		rightChild = null;
	}

	public RedBlackTree.Color getColor()
	{
		return color;
	}

	public void setColor(RedBlackTree.Color color)
	{
		this.color = color;
	}

	public int getKey()
	{
		return key;
	}

	public Job getJob()
	{
		return job;
	}

	public RedBlackNode getParent()
	{
		return parent;
	}

	public void setParent(RedBlackNode parent)
	{
		this.parent = parent;
	}

	public RedBlackNode getLeftChild()
	{
		return leftChild;
	}

	public void setLeftChild(RedBlackNode leftChild)
	{
		this.leftChild = leftChild;
	}

	public RedBlackNode getRightChild()
	{
		return rightChild;
	}

	public void setRightChild(RedBlackNode rightChild)
	{
		this.rightChild = rightChild;
	}
}

