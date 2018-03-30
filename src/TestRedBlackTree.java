public class TestRedBlackTree
{
	public RedBlackTree rbt = new RedBlackTree();
	public static void main(String[] args)
	{
		RedBlackTree rbt = new RedBlackTree();

		Job job = new Job(12,20, 4);
		Job job1 = new Job(13,22, 3);
		Job job2 = new Job(20,22, 2);
		Job job3 = new Job(44,22, 6);
		Job job4 = new Job(30,22, 90);
		Job job5 = new Job(1,22, 1);
		Job job6 = new Job(2,22, 1);
		Job job7 = new Job(19,22, 1);
		Job job8 = new Job(18,22, 1);
		Job job9 = new Job(21,22, 1);
		Job job10 = new Job(24,22, 1);
		Job job11 = new Job(28,22, 1);

		new TestRedBlackTree();
	}

	public TestRedBlackTree()
	{
		testRb01();
		testRb02();
		testRb11();
		testRb12();
		testRb2();
		testRr0();
		testRr11();
		testRr12();
		testRr2();

		testLb01();
		testLb02();
		testLb11();
		testLb12();
		testLb2();
		testLr0();
		testLr11();
//		testLr12();
//		testLr2();
//
//		testDeleteRedLeaf();
//		testDeleteBlackLeaf();
//		testDeleteBlackDegreeOneWithRedChild();
//		testDeleteDegree2();
//		testDeleteMin();

		Job job6 = new Job(2,22, 1);
		Job job7 = new Job(19,22, 1);
		Job job8 = new Job(18,22, 1);
		Job job9 = new Job(21,22, 1);
		Job job10 = new Job(24,22, 1);
		Job job11 = new Job(28,22, 1);
	}

	private void testLr11()
	{

	}

	private void testLr0()
	{
		Job y = new Job(15,22, 1);
		Job pyG = new Job(10,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(25,22, 3);
		Job a = new Job(40,22, 2);
		Job b = new Job(32,22, 6);

		// All Black
		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, RedBlackTree.Color.BLACK);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.RED);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.BLACK);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.BLACK);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setRightChild(nodeV);
		nodePY.setLeftChild(nodeY);

		nodeY.setParent(nodePY);
		nodeY.setLeftChild(RedBlackTree.externalNode);
		nodeY.setRightChild(RedBlackTree.externalNode);

		nodeV.setParent(nodePY);
		nodeV.setLeftChild(nodeB);
		nodeV.setRightChild(nodeA);

		nodeB.setParent(nodeV);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);


		rbt.setHead(nodePYG);
		rbt.delete(y);

		if((nodePY.getLeftChild() == RedBlackTree.externalNode)
//				&& (nodePY.getRightChild() == nodeB)
//				&& (nodePY.getParent() == nodeV)
//				&& nodeV.getParent() == nodePYG
//				&& (nodeV.getRightChild() == nodeA)
//				&& (nodeV.getLeftChild() == nodePY)
//				&& nodeB.getParent() == nodePY
				&& (nodeV.getColor() == RedBlackTree.Color.BLACK)
				&& (nodeB.getColor() == RedBlackTree.Color.RED)
				&& (nodePY.getColor() == RedBlackTree.Color.BLACK))
		{
			System.out.println("Lr0: Test passed");
		}
		else
		{
			System.out.println("Lr0: Test failed");
		}

	}

	private void testLb2()
	{
		Job y = new Job(15,22, 1);
		Job pyG = new Job(10,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(25,22, 3);
		Job a = new Job(40,22, 2);
		Job b = new Job(32,22, 6);
		Job w = new Job(28,22, 6);
		Job c = new Job(27,22, 6);

		RedBlackTree.Color initStartColor = RedBlackTree.Color.RED;
		// All Black
		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, initStartColor);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.BLACK);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.RED);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.BLACK);
		RedBlackNode nodeW = new RedBlackNode(w, RedBlackTree.Color.RED);
		RedBlackNode nodeC = new RedBlackNode(c, RedBlackTree.Color.BLACK);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setRightChild(nodeV);
		nodePY.setLeftChild(nodeY);

		nodeY.setParent(nodePY);
		nodeY.setLeftChild(RedBlackTree.externalNode);
		nodeY.setRightChild(RedBlackTree.externalNode);

		nodeV.setParent(nodePY);
		nodeV.setLeftChild(nodeW);
		nodeV.setRightChild(nodeA);

		nodeW.setParent(nodeV);
		nodeW.setLeftChild(nodeC);
		nodeW.setRightChild(nodeB);

		nodeB.setParent(nodeW);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);

		nodeC.setParent(nodeW);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);

		rbt.setHead(nodePYG);
		rbt.delete(y);

		if(nodeW.getParent() == nodePYG
				&& nodeW.getLeftChild() == nodePY
				&& nodeW.getRightChild() == nodeV
				&& nodeV.getParent() == nodeW
				&& nodeV.getRightChild() == nodeA
				&& nodeV.getLeftChild() == nodeB
				&& nodePY.getParent() == nodeW
				&& nodePY.getLeftChild() == RedBlackTree.externalNode
				&& nodePY.getRightChild() == nodeC
				&& nodeC.getParent() == nodePY
				&& nodeB.getParent() == nodeV
				&& nodeA.getParent() == nodeV
				&& nodeW.getColor() == initStartColor
				&& nodeA.getColor() == RedBlackTree.Color.RED
				&& (nodePY.getColor() == RedBlackTree.Color.BLACK))
		{
			System.out.println("LB2: Test passed");
		}
		else
		{
			System.out.println("LB2: Test failed");
		}
	}

	private void testLb12()
	{
		Job y = new Job(15,22, 1);
		Job pyG = new Job(10,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(25,22, 3);
		Job a = new Job(40,22, 2);
		Job b = new Job(32,22, 6);
		Job w = new Job(28,22, 6);
		Job c = new Job(27,22, 6);

		RedBlackTree.Color initStartColor = RedBlackTree.Color.RED;
		// All Black
		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, initStartColor);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.BLACK);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.BLACK);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.BLACK);
		RedBlackNode nodeW = new RedBlackNode(w, RedBlackTree.Color.RED);
		RedBlackNode nodeC = new RedBlackNode(c, RedBlackTree.Color.BLACK);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setRightChild(nodeV);
		nodePY.setLeftChild(nodeY);

		nodeY.setParent(nodePY);
		nodeY.setLeftChild(RedBlackTree.externalNode);
		nodeY.setRightChild(RedBlackTree.externalNode);

		nodeV.setParent(nodePY);
		nodeV.setLeftChild(nodeW);
		nodeV.setRightChild(nodeA);

		nodeW.setParent(nodeV);
		nodeW.setLeftChild(nodeC);
		nodeW.setRightChild(nodeB);

		nodeB.setParent(nodeW);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);

		nodeC.setParent(nodeW);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);

		rbt.setHead(nodePYG);
		rbt.delete(y);

		if(nodeW.getParent() == nodePYG
				&& nodeW.getLeftChild() == nodePY
				&& nodeW.getRightChild() == nodeV
				&& nodeV.getParent() == nodeW
				&& nodeV.getRightChild() == nodeA
				&& nodeV.getLeftChild() == nodeB
				&& nodePY.getParent() == nodeW
				&& nodePY.getLeftChild() == RedBlackTree.externalNode
				&& nodePY.getRightChild() == nodeC
				&& nodeC.getParent() == nodePY
				&& nodeB.getParent() == nodeV
				&& nodeA.getParent() == nodeV
				&& nodeW.getColor() == initStartColor
				&& (nodePY.getColor() == RedBlackTree.Color.BLACK))
		{
			System.out.println("LB12: Test passed");
		}
		else
		{
			System.out.println("LB12: Test failed");
		}
	}

	private void testLb11()
	{
		Job y = new Job(15,22, 1);
		Job pyG = new Job(10,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(25,22, 3);
		Job a = new Job(40,22, 2);
		Job b = new Job(32,22, 6);

		RedBlackTree.Color initStartColor = RedBlackTree.Color.RED;
		// All Black
		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, initStartColor);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.BLACK);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.RED);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.BLACK);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setRightChild(nodeV);
		nodePY.setLeftChild(nodeY);

		nodeY.setParent(nodePY);
		nodeY.setLeftChild(RedBlackTree.externalNode);
		nodeY.setRightChild(RedBlackTree.externalNode);

		nodeV.setParent(nodePY);
		nodeV.setLeftChild(nodeB);
		nodeV.setRightChild(nodeA);

		nodeB.setParent(nodeV);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);


		rbt.setHead(nodePYG);
		rbt.delete(y);

		if((nodePY.getLeftChild() == RedBlackTree.externalNode)
				&& (nodePY.getRightChild() == nodeB)
				&& (nodePY.getParent() == nodeV)
				&& nodeV.getParent() == nodePYG
				&& (nodeV.getRightChild() == nodeA)
				&& (nodeV.getLeftChild() == nodePY)
				&& nodeB.getParent() == nodePY
				&& (nodeV.getColor() == initStartColor)
				&& (nodeA.getColor() == RedBlackTree.Color.BLACK)
				&& (nodePY.getColor() == RedBlackTree.Color.BLACK))
		{
			System.out.println("LB11: Test passed");
		}
		else
		{
			System.out.println("LB11: Test failed");
		}
	}

	private void testLb02()
	{
		Job y = new Job(15,22, 1);
		Job pyG = new Job(10,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(25,22, 3);
		Job a = new Job(40,22, 2);
		Job b = new Job(32,22, 6);

		// All Black
		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, RedBlackTree.Color.RED);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.BLACK);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.BLACK);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.BLACK);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setRightChild(nodeV);
		nodePY.setLeftChild(nodeY);

		nodeY.setParent(nodePY);
		nodeY.setLeftChild(RedBlackTree.externalNode);
		nodeY.setRightChild(RedBlackTree.externalNode);

		nodeV.setParent(nodePY);
		nodeV.setLeftChild(nodeB);
		nodeV.setRightChild(nodeA);

		nodeB.setParent(nodeV);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);


		rbt.setHead(nodePYG);
		rbt.delete(y);

		if((nodePY.getLeftChild() == RedBlackTree.externalNode)
				&& (nodePY.getRightChild() == nodeV)
				&& (nodeV.getRightChild() == nodeA)
				&& (nodeV.getLeftChild() == nodeB)
				&& (nodeV.getParent() == nodePY)
				&& (nodeV.getColor() == RedBlackTree.Color.RED)
				&& (nodePY.getColor() == RedBlackTree.Color.BLACK))
		{
			System.out.println("LB02: Test passed");
		}
		else
		{
			System.out.println("LB02: Test failed");
		}

	}

	private void testLb01()
	{
		Job y = new Job(15,22, 1);
		Job pyG = new Job(10,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(25,22, 3);
		Job a = new Job(40,22, 2);
		Job b = new Job(32,22, 6);

		// All Black
		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, RedBlackTree.Color.BLACK);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.BLACK);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.BLACK);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.BLACK);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setRightChild(nodeV);
		nodePY.setLeftChild(nodeY);

		nodeY.setParent(nodePY);
		nodeY.setLeftChild(RedBlackTree.externalNode);
		nodeY.setRightChild(RedBlackTree.externalNode);

		nodeV.setParent(nodePY);
		nodeV.setLeftChild(nodeB);
		nodeV.setRightChild(nodeA);

		nodeB.setParent(nodeV);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);


		rbt.setHead(nodePYG);
		rbt.delete(y);

		if((nodePY.getLeftChild() == RedBlackTree.externalNode)
				&& (nodePY.getRightChild() == nodeV)
				&& (nodeV.getRightChild() == nodeA)
				&& (nodeV.getLeftChild() == nodeB)
				&& (nodeV.getParent() == nodePY)
				&& (nodeV.getColor() == RedBlackTree.Color.RED))
		{
			System.out.println("LB01: Test passed");
		}
		else
		{
			System.out.println("LB01: Test failed");
		}

	}



	private void testRr2()
	{
		Job y = new Job(30,22, 1);
		Job pyG = new Job(15,22, 90);
		Job py = new Job(28,20, 4);

		Job v = new Job(20,22, 3);
		Job a = new Job(19,22, 2);
		Job w = new Job(23, 22 ,2);
		Job b = new Job(22, 22 ,2);
		Job x = new Job(26, 22 ,2);
		Job c = new Job(24, 22 ,2);
		Job d = new Job(27, 22 ,2);

		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, RedBlackTree.Color.BLACK);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.RED);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.BLACK);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.RED);
		RedBlackNode nodeW = new RedBlackNode(w, RedBlackTree.Color.BLACK);
		RedBlackNode nodeC = new RedBlackNode(c, RedBlackTree.Color.BLACK);
		RedBlackNode nodeD = new RedBlackNode(d, RedBlackTree.Color.BLACK);
		RedBlackNode nodeX = new RedBlackNode(x, RedBlackTree.Color.RED);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setLeftChild(nodeV);
		nodePY.setRightChild(nodeY);

		nodeY.setParent(nodePY);
		nodeY.setLeftChild(RedBlackTree.externalNode);
		nodeY.setRightChild(RedBlackTree.externalNode);

		nodeV.setParent(nodePY);
		nodeV.setRightChild(nodeW);
		nodeV.setLeftChild(nodeA);

		nodeB.setParent(nodeW);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);

		nodeW.setParent(nodeV);
		nodeW.setLeftChild(nodeB);
		nodeW.setRightChild(nodeX);

		nodeX.setParent(nodeW);
		nodeX.setLeftChild(nodeC);
		nodeX.setRightChild(nodeD);

		nodeC.setParent(nodeX);
		nodeC.setLeftChild(RedBlackTree.externalNode);
		nodeC.setRightChild(RedBlackTree.externalNode);

		nodeD.setParent(nodeX);
		nodeD.setLeftChild(RedBlackTree.externalNode);
		nodeD.setRightChild(RedBlackTree.externalNode);

		rbt.setHead(nodePYG);
		rbt.delete(y);

		if (nodeX.getParent() == nodePYG
				&& nodeX.getLeftChild() == nodeV
				&& nodeX.getRightChild() == nodePY
				&& nodePY.getParent() == nodeX
				&& nodePY.getLeftChild() == nodeD
				&& nodePY.getRightChild() == RedBlackTree.externalNode
				&& nodeD.getParent() == nodePY
				&& nodeV.getParent() == nodeX
				&& nodeV.getLeftChild() == nodeA
				&& nodeV.getRightChild() == nodeW
				&& nodeW.getParent() == nodeV
				&& nodeW.getLeftChild() == nodeB
				&& nodeW.getRightChild() == nodeC
				&& nodeX.getColor() == RedBlackTree.Color.BLACK
				&& nodeB.getColor() == RedBlackTree.Color.RED
				&& nodeV.getColor() == RedBlackTree.Color.RED
				&& (nodePY.getColor() == RedBlackTree.Color.BLACK))
		{
			System.out.println("Rr2: Test passed");
		}
		else{
			System.out.println("Rr2: Test Failed");
			rbt.printNodeStyle(null);
		}
	}

	private void testRr12()
	{
		Job y = new Job(30,22, 1);
		Job pyG = new Job(15,22, 90);
		Job py = new Job(28,20, 4);

		Job v = new Job(20,22, 3);
		Job a = new Job(19,22, 2);
		Job w = new Job(23, 22 ,2);
		Job b = new Job(22, 22 ,2);
		Job x = new Job(26, 22 ,2);
		Job c = new Job(24, 22 ,2);
		Job d = new Job(27, 22 ,2);

		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, RedBlackTree.Color.BLACK);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.RED);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.BLACK);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.BLACK);
		RedBlackNode nodeW = new RedBlackNode(w, RedBlackTree.Color.BLACK);
		RedBlackNode nodeC = new RedBlackNode(c, RedBlackTree.Color.BLACK);
		RedBlackNode nodeD = new RedBlackNode(d, RedBlackTree.Color.BLACK);
		RedBlackNode nodeX = new RedBlackNode(x, RedBlackTree.Color.RED);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setLeftChild(nodeV);
		nodePY.setRightChild(nodeY);

		nodeY.setParent(nodePY);
		nodeY.setLeftChild(RedBlackTree.externalNode);
		nodeY.setRightChild(RedBlackTree.externalNode);

		nodeV.setParent(nodePY);
		nodeV.setRightChild(nodeW);
		nodeV.setLeftChild(nodeA);

		nodeB.setParent(nodeW);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);

		nodeW.setParent(nodeV);
		nodeW.setLeftChild(nodeB);
		nodeW.setRightChild(nodeX);

		nodeX.setParent(nodeW);
		nodeX.setLeftChild(nodeC);
		nodeX.setRightChild(nodeD);

		nodeC.setParent(nodeX);
		nodeC.setLeftChild(RedBlackTree.externalNode);
		nodeC.setRightChild(RedBlackTree.externalNode);

		nodeD.setParent(nodeX);
		nodeD.setLeftChild(RedBlackTree.externalNode);
		nodeD.setRightChild(RedBlackTree.externalNode);

		rbt.setHead(nodePYG);
		rbt.delete(y);

		if (nodeX.getParent() == nodePYG
				&& nodeX.getLeftChild() == nodeV
				&& nodeX.getRightChild() == nodePY
				&& nodePY.getParent() == nodeX
				&& nodePY.getLeftChild() == nodeD
				&& nodePY.getRightChild() == RedBlackTree.externalNode
				&& nodeD.getParent() == nodePY
				&& nodeV.getParent() == nodeX
				&& nodeV.getLeftChild() == nodeA
				&& nodeV.getRightChild() == nodeW
				&& nodeW.getParent() == nodeV
				&& nodeW.getLeftChild() == nodeB
				&& nodeW.getRightChild() == nodeC
				&& nodeX.getColor() == RedBlackTree.Color.BLACK
				&& nodeV.getColor() == RedBlackTree.Color.RED
				&& (nodePY.getColor() == RedBlackTree.Color.BLACK))
		{
			System.out.println("Rr12: Test passed");
		}
		else{
			System.out.println("Rr12: Test Failed");
			rbt.printNodeStyle(null);
		}
	}

	private void testRr11()
	{
		Job y = new Job(30,22, 1);
		Job pyG = new Job(15,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(20,22, 3);
		Job a = new Job(19,22, 2);
		Job w = new Job(23, 22 ,2);
		Job b = new Job(22, 22 ,2);
		Job c = new Job(24, 22 ,2);


		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, RedBlackTree.Color.BLACK);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.RED);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.BLACK);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.RED);
		RedBlackNode nodeW = new RedBlackNode(w, RedBlackTree.Color.BLACK);
		RedBlackNode nodeC = new RedBlackNode(c, RedBlackTree.Color.BLACK);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setLeftChild(nodeV);
		nodePY.setRightChild(nodeY);

		nodeY.setParent(nodePY);
		nodeY.setLeftChild(RedBlackTree.externalNode);
		nodeY.setRightChild(RedBlackTree.externalNode);

		nodeV.setParent(nodePY);
		nodeV.setRightChild(nodeW);
		nodeV.setLeftChild(nodeA);

		nodeB.setParent(nodeW);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);

		nodeW.setParent(nodeV);
		nodeW.setLeftChild(nodeB);
		nodeW.setRightChild(nodeC);

		nodeC.setParent(nodeW);
		nodeC.setLeftChild(RedBlackTree.externalNode);
		nodeC.setRightChild(RedBlackTree.externalNode);

		rbt.setHead(nodePYG);
		rbt.delete(y);

		if (nodePY.getRightChild() == RedBlackTree.externalNode
				&& nodePY.getLeftChild() == nodeC
				&& nodePY.getParent() == nodeW
				&& nodeC.getParent() == nodePY
				&& nodeW.getParent() == nodePYG
				&& nodeW.getLeftChild() == nodeV
				&& nodeW.getRightChild() == nodePY
				&& nodeV.getParent() == nodeW
				&& nodeV.getLeftChild() == nodeA
				&& nodeV.getRightChild() == nodeB
				&& nodeB.getParent() == nodeV
				&& nodeA.getParent() == nodeV
				&& nodeW.getColor() == RedBlackTree.Color.BLACK
				&& nodeB.getColor() == RedBlackTree.Color.BLACK
				&& nodeV.getColor() == RedBlackTree.Color.RED
				&& (nodePY.getColor() == RedBlackTree.Color.BLACK))
		{
			System.out.println("Rr11: Test passed");
		}
		else{
			System.out.println("Rr11: Test Failed");
			rbt.printNodeStyle(null);
		}
	}

	private void testRr0()
	{
		Job y = new Job(30,22, 1);
		Job pyG = new Job(15,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(20,22, 3);
		Job a = new Job(19,22, 2);
		Job b = new Job(22,22, 6);

		// All Black
		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, RedBlackTree.Color.BLACK);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.RED);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.BLACK);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.BLACK);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setLeftChild(nodeV);
		nodePY.setRightChild(nodeY);

		nodeY.setParent(nodePY);
		nodeY.setLeftChild(RedBlackTree.externalNode);
		nodeY.setRightChild(RedBlackTree.externalNode);

		nodeV.setParent(nodePY);
		nodeV.setRightChild(nodeB);
		nodeV.setLeftChild(nodeA);

		nodeB.setParent(nodeV);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);


		rbt.setHead(nodePYG);
		rbt.delete(y);

		if((nodePY.getRightChild() == RedBlackTree.externalNode)
				&& nodePY.getLeftChild() == nodeB
				&& nodePY.getParent() == nodeV
				&& (nodeV.getLeftChild() == nodeA)
				&& (nodeV.getRightChild() == nodePY)
				&& (nodeV.getParent() == nodePYG)
				&& nodeB.getParent() == nodePY
				&& (nodeV.getColor() == RedBlackTree.Color.BLACK)
				&& (nodePY.getColor() == RedBlackTree.Color.BLACK)
				&& (nodeB.getColor() == RedBlackTree.Color.RED))
		{
			System.out.println("Rr0: Test passed");
		}
		else{
			System.out.println("Rr0: Test Failed");
		}
	}

	private void testRb2()
	{
		Job y = new Job(30,22, 1);
		Job pyG = new Job(15,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(20,22, 3);
		Job a = new Job(19,22, 2);
		Job w = new Job(23, 22 ,2);
		Job b = new Job(22, 22 ,2);
		Job c = new Job(24, 22 ,2);


		// Need to test this for both colors
		RedBlackTree.Color startingColor = RedBlackTree.Color.RED;
		// All Black
		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, startingColor);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.BLACK);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.RED);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.BLACK);
		RedBlackNode nodeW = new RedBlackNode(w, RedBlackTree.Color.RED);
		RedBlackNode nodeC = new RedBlackNode(c, RedBlackTree.Color.BLACK);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setLeftChild(nodeV);
		nodePY.setRightChild(nodeY);

		nodeY.setParent(nodePY);
		nodeY.setLeftChild(RedBlackTree.externalNode);
		nodeY.setRightChild(RedBlackTree.externalNode);

		nodeV.setParent(nodePY);
		nodeV.setRightChild(nodeW);
		nodeV.setLeftChild(nodeA);

		nodeB.setParent(nodeW);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);

		nodeW.setParent(nodeV);
		nodeW.setLeftChild(nodeB);
		nodeW.setRightChild(nodeC);

		nodeC.setParent(nodeW);
		nodeC.setLeftChild(RedBlackTree.externalNode);
		nodeC.setRightChild(RedBlackTree.externalNode);

		rbt.setHead(nodePYG);
		rbt.printNodeStyle(null);
		rbt.delete(y);

		if (nodePY.getRightChild() == RedBlackTree.externalNode
				&& nodePY.getLeftChild() == nodeC
				&& nodePY.getParent() == nodeW
				&& nodeC.getParent() == nodePY
				&& nodeW.getParent() == nodePYG
				&& nodeW.getLeftChild() == nodeV
				&& nodeW.getRightChild() == nodePY
				&& nodeV.getParent() == nodeW
				&& nodeV.getLeftChild() == nodeA
				&& nodeV.getRightChild() == nodeB
				&& nodeB.getParent() == nodeV
				&& nodeA.getParent() == nodeV
				&& nodeA.getColor() == RedBlackTree.Color.RED
				&& nodeW.getColor() == startingColor
				&& (nodePY.getColor() == RedBlackTree.Color.BLACK))
		{
			System.out.println("RB2: Test passed");
		}
		else{
			System.out.println("RB2: Test Failed");
			rbt.printNodeStyle(null);
		}

	}

	private void testRb12()
	{
		Job y = new Job(30,22, 1);
		Job pyG = new Job(15,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(20,22, 3);
		Job a = new Job(19,22, 2);
		Job w = new Job(23, 22 ,2);
		Job b = new Job(22, 22 ,2);
		Job c = new Job(24, 22 ,2);


		// Need to test this for both colors
		RedBlackTree.Color startingColor = RedBlackTree.Color.RED;
		// All Black
		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, startingColor);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.BLACK);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.BLACK);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.BLACK);
		RedBlackNode nodeW = new RedBlackNode(w, RedBlackTree.Color.RED);
		RedBlackNode nodeC = new RedBlackNode(c, RedBlackTree.Color.BLACK);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setLeftChild(nodeV);
		nodePY.setRightChild(nodeY);

		nodeY.setParent(nodePY);
		nodeY.setLeftChild(RedBlackTree.externalNode);
		nodeY.setRightChild(RedBlackTree.externalNode);

		nodeV.setParent(nodePY);
		nodeV.setRightChild(nodeW);
		nodeV.setLeftChild(nodeA);

		nodeB.setParent(nodeW);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);

		nodeW.setParent(nodeV);
		nodeW.setLeftChild(nodeB);
		nodeW.setRightChild(nodeC);

		nodeC.setParent(nodeW);
		nodeC.setLeftChild(RedBlackTree.externalNode);
		nodeC.setRightChild(RedBlackTree.externalNode);

		rbt.setHead(nodePYG);
		rbt.printNodeStyle(null);
		rbt.delete(y);

		if (nodePY.getRightChild() == RedBlackTree.externalNode
				&& nodePY.getLeftChild() == nodeC
				&& nodePY.getParent() == nodeW
				&& nodeC.getParent() == nodePY
				&& nodeW.getParent() == nodePYG
				&& nodeW.getLeftChild() == nodeV
				&& nodeW.getRightChild() == nodePY
				&& nodeV.getParent() == nodeW
				&& nodeV.getLeftChild() == nodeA
				&& nodeV.getRightChild() == nodeB
				&& nodeB.getParent() == nodeV
				&& nodeA.getParent() == nodeV
				&& nodeW.getColor() == startingColor
				&& (nodePY.getColor() == RedBlackTree.Color.BLACK))
		{
			System.out.println("RB12: Test passed");
		}
		else{
			System.out.println("RB12: Test Failed");
			rbt.printNodeStyle(null);
		}
	}

	private void testRb11()
	{
		Job y = new Job(30,22, 1);
		Job pyG = new Job(15,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(20,22, 3);
		Job a = new Job(19,22, 2);
		Job b = new Job(22,22, 6);

		// Need to test this for both colors
		RedBlackTree.Color startingColor = RedBlackTree.Color.RED;
		// All Black
		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, startingColor);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.BLACK);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.RED);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.BLACK);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setLeftChild(nodeV);
		nodePY.setRightChild(nodeY);

		nodeY.setParent(nodePY);
		nodeY.setLeftChild(RedBlackTree.externalNode);
		nodeY.setRightChild(RedBlackTree.externalNode);

		nodeV.setParent(nodePY);
		nodeV.setRightChild(nodeB);
		nodeV.setLeftChild(nodeA);

		nodeB.setParent(nodeV);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);


		rbt.setHead(nodePYG);
		rbt.delete(y);

		if (nodePY.getRightChild() == RedBlackTree.externalNode
				&& (nodeV.getLeftChild() == nodeA)
				&& (nodeV.getRightChild() == nodePY)
				&& (nodePY.getLeftChild() == nodeB)
				&& (nodeB.getParent() == nodePY)
				&& (nodeV.getParent() == nodePYG)
				&& (nodePYG.getRightChild() == nodeV)
				&& (nodePY.getParent() == nodeV)
				&& (nodeA.getParent() == nodeV)
				&& (nodeV.getColor() == startingColor)
				&& (nodePY.getColor() == RedBlackTree.Color.BLACK))
		{
			System.out.println("RB11: Test passed");
		}
		else{
			System.out.println("RB11: Test Failed");
		}
	}

	private void testRb02()
	{
		Job y = new Job(30,22, 1);
		Job pyG = new Job(15,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(20,22, 3);
		Job a = new Job(19,22, 2);
		Job b = new Job(22,22, 6);

		// All Black
		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, RedBlackTree.Color.RED);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.BLACK);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.BLACK);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.BLACK);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setLeftChild(nodeV);
		nodePY.setRightChild(nodeY);

		nodeY.setParent(nodePY);
		nodeY.setLeftChild(RedBlackTree.externalNode);
		nodeY.setRightChild(RedBlackTree.externalNode);

		nodeV.setParent(nodePY);
		nodeV.setRightChild(nodeB);
		nodeV.setLeftChild(nodeA);

		nodeB.setParent(nodeV);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);


		rbt.setHead(nodePYG);
		rbt.delete(y);

		if((nodePY.getLeftChild() == nodeV)
				&& (nodePY.getRightChild() == RedBlackTree.externalNode)
				&& (nodeV.getLeftChild() == nodeA)
				&& (nodeV.getRightChild() == nodeB)
				&& (nodeV.getParent() == nodePY)
				&& (nodeV.getColor() == RedBlackTree.Color.RED)
				&& (nodePY.getColor() == RedBlackTree.Color.BLACK))
		{
			System.out.println("RB02: Test passed");
		}
		else{
			System.out.println("RB02: Test Failed");
		}
	}

	private void testRb01()
	{
		Job y = new Job(30,22, 1);
		Job pyG = new Job(15,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(20,22, 3);
		Job a = new Job(19,22, 2);
		Job b = new Job(22,22, 6);

		// All Black
		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, RedBlackTree.Color.BLACK);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.BLACK);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.BLACK);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.BLACK);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setLeftChild(nodeV);
		nodePY.setRightChild(nodeY);

		nodeY.setParent(nodePY);
		nodeY.setLeftChild(RedBlackTree.externalNode);
		nodeY.setRightChild(RedBlackTree.externalNode);

		nodeV.setParent(nodePY);
		nodeV.setRightChild(nodeB);
		nodeV.setLeftChild(nodeA);

		nodeB.setParent(nodeV);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);


		rbt.setHead(nodePYG);
		rbt.delete(y);

		if((nodePY.getLeftChild() == nodeV)
			&& (nodePY.getRightChild() == RedBlackTree.externalNode)
			&& (nodeV.getLeftChild() == nodeA)
			&& (nodeV.getRightChild() == nodeB)
			&& (nodeV.getParent() == nodePY)
			&& (nodeV.getColor() == RedBlackTree.Color.RED))
		{
			System.out.println("RB01: Test passed");
		}

	}


}
