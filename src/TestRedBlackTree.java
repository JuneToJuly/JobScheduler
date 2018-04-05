import sun.reflect.generics.tree.Tree;

public class TestRedBlackTree
{
	public RedBlackTree rbt = new RedBlackTree();
	StringBuilder sb = new StringBuilder();
	public static void main(String[] args)
	{
		new TestRedBlackTree();
	}

	public TestRedBlackTree()
	{
		sb.append("Test Results\n");
//		testRb01();
//		testRb02();
//		testRb11();
//		testRb12();
//		testRb2();
//		testRr0();
//		testRr11();
//		testRr12();
//		testRr2();

//		testLb01();
//		testLb02();
//		testLb11();
//		testLb12();
//		testLb2();
//		testLr0();
//		testLr11();
//		testLr12();
//		testLr2();

//		testDeleteRedLeaf();
//		testDeleteBlackDegreeOneWithRedChild();
//		testDeleteDegree2();
//		testDeleteColorChangePropagtation();
//		testDeleteMin();
		testDelete();


		System.out.println(sb.toString());
	}

	private void testDelete()
	{
		rbt = new RedBlackTree();
		rbt.add(new Job(3,0,0));
		rbt.add(new Job(20,0,0));
		rbt.add(new Job(25,0,0));
		rbt.add(new Job(36,0,0));
		rbt.add(new Job(15,0,0));
		rbt.add(new Job(2,0,0));
		rbt.add(new Job(16,0,0));
		rbt.add(new Job(28,0,0));
		rbt.add(new Job(27,0,0));
		rbt.add(new Job(29,0,0));
		rbt.add(new Job(31,0,0));
		rbt.add(new Job(37,0,0));

//		rbt.printNodeStyle(null);

//		rbt.delete(new Job(20,0,0));
//		rbt.delete(new Job(3,0,0));
//		rbt.delete(new Job(2,0,0));
//		rbt.delete(new Job(25,0,0));
//		rbt.delete(new Job(28,0,0));
		rbt.delete(new Job(17,0,0));
		rbt.delete(new Job(15,0,0));
		rbt.delete(new Job(16,0,0));
//		rbt.delete(new Job(28,0,0));

		System.out.println("After delete");
		rbt.printNodeStyle(null);
	}

	private void testDeleteMin()
	{

	}

	private void testDeleteColorChangePropagtation()
	{

	}

	private void testDeleteDegree2()
	{
		Job a = new Job(4,22, 1);
		Job b = new Job(2,22, 1);
		Job c = new Job(15,22, 1);
		Job d = new Job(30,22, 1);
		Job e = new Job(40,22, 1);
		Job f = new Job(45,22, 1);
		Job g = new Job(33,22, 1);
		Job h = new Job(32,22, 90);
		Job i = new Job(35,22, 90);
		Job j = new Job(37,22, 90);

		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.RED);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.BLACK);
		RedBlackNode nodeC = new RedBlackNode(c, RedBlackTree.Color.BLACK);
		RedBlackNode nodeD = new RedBlackNode(d, RedBlackTree.Color.BLACK);
		RedBlackNode nodeE = new RedBlackNode(e, RedBlackTree.Color.BLACK);
		RedBlackNode nodeF = new RedBlackNode(f, RedBlackTree.Color.BLACK);
		RedBlackNode nodeG = new RedBlackNode(g, RedBlackTree.Color.RED);
		RedBlackNode nodeH = new RedBlackNode(h, RedBlackTree.Color.BLACK);
		RedBlackNode nodeI = new RedBlackNode(i, RedBlackTree.Color.BLACK);
		RedBlackNode nodeJ = new RedBlackNode(j, RedBlackTree.Color.RED);

		nodeD.setParent(RedBlackTree.rootNode);
		nodeD.setLeftChild(nodeC);
		nodeD.setRightChild(nodeE);

		nodeC.setParent(nodeD);
		nodeC.setLeftChild(nodeB);
		nodeC.setRightChild(RedBlackTree.externalNode);

		nodeB.setParent(nodeC);
		nodeB.setRightChild(nodeA);
		nodeB.setLeftChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeB);
		nodeA.setRightChild(RedBlackTree.externalNode);
		nodeA.setLeftChild(RedBlackTree.externalNode);

		nodeE.setParent(nodeD);
		nodeE.setRightChild(nodeF);
		nodeE.setLeftChild(nodeG);

		nodeF.setParent(nodeE);
		nodeF.setLeftChild(RedBlackTree.externalNode);
		nodeF.setRightChild(RedBlackTree.externalNode);

		nodeG.setParent(nodeE);
		nodeG.setLeftChild(nodeH);
		nodeG.setRightChild(nodeI);

		nodeH.setParent(nodeG);
		nodeH.setLeftChild(RedBlackTree.externalNode);
		nodeH.setRightChild(RedBlackTree.externalNode);

		nodeI.setParent(nodeG);
		nodeI.setLeftChild(RedBlackTree.externalNode);
		nodeI.setRightChild(nodeJ);

		nodeJ.setParent(nodeI);
		nodeJ.setLeftChild(RedBlackTree.externalNode);
		nodeJ.setRightChild(RedBlackTree.externalNode);

		rbt.setHead(nodeD);
		rbt.delete(g);

	}

	private void testDeleteBlackDegreeOneWithRedChild()
	{
		Job y = new Job(30,22, 1);
		Job v = new Job(15,22, 1);
		Job pyG = new Job(18,22, 90);
		Job py = new Job(25,20, 4);

		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.BLACK);
		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.RED);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, RedBlackTree.Color.BLACK);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(nodeV);

		nodeV.setParent(nodePYG);
		nodeV.setRightChild(RedBlackTree.externalNode);
		nodeV.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setRightChild(nodeY);
		nodePY.setLeftChild(RedBlackTree.externalNode);

		nodeY.setParent(nodePY);
		nodeY.setRightChild(RedBlackTree.externalNode);
		nodeY.setLeftChild(RedBlackTree.externalNode);

		rbt.setHead(nodePYG);
		rbt.delete(py);

		if(nodePYG.getRightChild() == nodeY
			&& nodeY.getParent() == nodePYG
				&& nodeY.getColor() == RedBlackTree.Color.BLACK)
		{
			sb.append("Delete Black Degree One with Red Leaf: passed\n");
		}
		else
		{
			sb.append("Delete Black Degree One with Red Leaf: failed\n");
		}
	}

	private void testDeleteRedLeaf()
	{
		Job y = new Job(15,22, 1);
		Job pyG = new Job(10,22, 90);
		Job py = new Job(25,20, 4);

		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, RedBlackTree.Color.RED);

		nodePYG.setParent(RedBlackTree.rootNode);
		nodePYG.setRightChild(nodePY);
		nodePYG.setLeftChild(RedBlackTree.externalNode);

		nodePY.setParent(nodePYG);
		nodePY.setRightChild(RedBlackTree.externalNode);
		nodePY.setLeftChild(RedBlackTree.externalNode);

		rbt.setHead(nodePYG);
		rbt.delete(py);

		if(nodePYG.getRightChild() == RedBlackTree.externalNode)
		{

			sb.append("Delete RedLeaf: passed\n");
		}
		else
		{
			sb.append("Delete RedLeaf: failed\n");
		}
	}

	private void testLr2()
	{
		Job y = new Job(15,22, 1);
		Job pyG = new Job(10,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(35,22, 3);
		Job a = new Job(40,22, 2);
		Job b = new Job(26,22, 6);
		Job w = new Job(29,22, 6);
		Job x = new Job(27,22, 6);
		Job c = new Job(28,22, 6);
		Job d = new Job(26,22, 6);

		// All Black
		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, RedBlackTree.Color.BLACK);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.RED);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.BLACK);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.RED);
		RedBlackNode nodeW = new RedBlackNode(w, RedBlackTree.Color.BLACK);
		RedBlackNode nodeC = new RedBlackNode(c, RedBlackTree.Color.BLACK);
		RedBlackNode nodeX = new RedBlackNode(x, RedBlackTree.Color.RED);
		RedBlackNode nodeD = new RedBlackNode(d, RedBlackTree.Color.BLACK);

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
		nodeW.setLeftChild(nodeX);
		nodeW.setRightChild(nodeB);

		nodeX.setParent(nodeW);
		nodeX.setLeftChild(nodeD);
		nodeX.setRightChild(nodeC);

		nodeB.setParent(nodeW);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);

		nodeC.setParent(nodeX);
		nodeC.setLeftChild(RedBlackTree.externalNode);
		nodeC.setRightChild(RedBlackTree.externalNode);

		nodeD.setParent(nodeX);
		nodeD.setLeftChild(RedBlackTree.externalNode);
		nodeD.setRightChild(RedBlackTree.externalNode);

		rbt.setHead(nodePYG);
		rbt.delete(y);

		if(nodeX.getParent() == nodePYG
				&& nodeX.getLeftChild() == nodePY
				&& nodeX.getRightChild() == nodeV
				&& nodePY.getParent() == nodeX
				&& nodePY.getLeftChild() == RedBlackTree.externalNode
				&& nodePY.getRightChild() == nodeB
				&& nodeV.getParent() == nodeX
				&& nodeV.getLeftChild() == nodeW
				&& nodeV.getRightChild() == nodeA
				&& nodeA.getParent() == nodeV
				&& nodeW.getParent() == nodeV
				&& nodeW.getLeftChild() == nodeC
				&& nodeW.getRightChild() == nodeD
				&& nodeC.getParent() == nodeW
				&& nodeD.getParent() == nodeW
				&& nodeB.getColor() == RedBlackTree.Color.RED
				&& nodeV.getColor() == RedBlackTree.Color.RED
				&& nodeX.getColor() == RedBlackTree.Color.BLACK)
		{
			sb.append("Lr2: Test passed\n");
		}
		else
		{
			sb.append("Lr2: Test failed\n");
		}
	}

	private void testLr12()
	{
		Job y = new Job(15,22, 1);
		Job pyG = new Job(10,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(35,22, 3);
		Job a = new Job(40,22, 2);
		Job b = new Job(26,22, 6);
		Job w = new Job(29,22, 6);
		Job x = new Job(27,22, 6);
		Job c = new Job(28,22, 6);
		Job d = new Job(26,22, 6);

		// All Black
		RedBlackNode nodeY = new RedBlackNode(y, RedBlackTree.Color.BLACK);
		RedBlackNode nodePYG = new RedBlackNode(pyG, RedBlackTree.Color.BLACK);
		RedBlackNode nodePY = new RedBlackNode(py, RedBlackTree.Color.BLACK);
		RedBlackNode nodeV = new RedBlackNode(v, RedBlackTree.Color.RED);
		RedBlackNode nodeA = new RedBlackNode(a, RedBlackTree.Color.BLACK);
		RedBlackNode nodeB = new RedBlackNode(b, RedBlackTree.Color.BLACK);
		RedBlackNode nodeW = new RedBlackNode(w, RedBlackTree.Color.BLACK);
		RedBlackNode nodeC = new RedBlackNode(c, RedBlackTree.Color.BLACK);
		RedBlackNode nodeX = new RedBlackNode(x, RedBlackTree.Color.RED);
		RedBlackNode nodeD = new RedBlackNode(d, RedBlackTree.Color.BLACK);

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
		nodeW.setLeftChild(nodeX);
		nodeW.setRightChild(nodeB);

		nodeX.setParent(nodeW);
		nodeX.setLeftChild(nodeD);
		nodeX.setRightChild(nodeC);

		nodeB.setParent(nodeW);
		nodeB.setLeftChild(RedBlackTree.externalNode);
		nodeB.setRightChild(RedBlackTree.externalNode);

		nodeA.setParent(nodeV);
		nodeA.setLeftChild(RedBlackTree.externalNode);
		nodeA.setRightChild(RedBlackTree.externalNode);

		nodeC.setParent(nodeX);
		nodeC.setLeftChild(RedBlackTree.externalNode);
		nodeC.setRightChild(RedBlackTree.externalNode);

		nodeD.setParent(nodeX);
		nodeD.setLeftChild(RedBlackTree.externalNode);
		nodeD.setRightChild(RedBlackTree.externalNode);

		rbt.setHead(nodePYG);
		rbt.delete(y);

		if(nodeX.getParent() == nodePYG
				&& nodeX.getLeftChild() == nodePY
				&& nodeX.getRightChild() == nodeV
				&& nodePY.getParent() == nodeX
				&& nodePY.getLeftChild() == RedBlackTree.externalNode
				&& nodePY.getRightChild() == nodeB
				&& nodeV.getParent() == nodeX
				&& nodeV.getLeftChild() == nodeW
				&& nodeV.getRightChild() == nodeA
				&& nodeA.getParent() == nodeV
				&& nodeW.getParent() == nodeV
				&& nodeW.getLeftChild() == nodeC
				&& nodeW.getRightChild() == nodeD
				&& nodeC.getParent() == nodeW
				&& nodeD.getParent() == nodeW
				&& nodeV.getColor() == RedBlackTree.Color.RED
				&& nodeX.getColor() == RedBlackTree.Color.BLACK)
		{
			sb.append("Lr12: Test passed\n");
		}
		else
		{
			sb.append("Lr12: Test failed\n");
		}
	}

	private void testLr11()
	{
		Job y = new Job(15,22, 1);
		Job pyG = new Job(10,22, 90);
		Job py = new Job(25,20, 4);

		Job v = new Job(25,22, 3);
		Job a = new Job(40,22, 2);
		Job b = new Job(32,22, 6);
		Job w = new Job(28,22, 6);
		Job c = new Job(27,22, 6);

		// All Black
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
				&& nodeB.getColor() == RedBlackTree.Color.BLACK
				&& nodeV.getColor() == RedBlackTree.Color.RED
				&& nodeW.getColor() == RedBlackTree.Color.BLACK
				&& (nodePY.getColor() == RedBlackTree.Color.BLACK))
		{
			sb.append("Lr11: Test passed\n");
		}
		else
		{
			sb.append("Lr11: Test failed\n");
		}
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
			sb.append("Lr0: Test passed\n");
		}
		else
		{
			sb.append("Lr0: Test failed\n");
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
			sb.append("LB2: Test passed\n");
		}
		else
		{
			sb.append("LB2: Test failed\n");
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
			sb.append("LB12: Test passed\n");
		}
		else
		{
			sb.append("LB12: Test failed\n");
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
			sb.append("LB11: Test passed\n");
		}
		else
		{
			sb.append("LB11: Test failed\n");
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
			sb.append("LB02: Test passed\n");
		}
		else
		{
			sb.append("LB02: Test failed\n");
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
			sb.append("LB01: Test passed\n");
		}
		else
		{
			sb.append("LB01: Test failed\n");
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
			sb.append("Rr2: Test passed\n");
		}
		else{
			sb.append("Rr2: Test Failed\n");
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
			sb.append("Rr12: Test passed\n");
		}
		else{
			sb.append("Rr12: Test Failed\n");
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
			sb.append("Rr11: Test passed\n");
		}
		else{
			sb.append("Rr11: Test Failed\n");
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
			sb.append("Rr0: Test passed\n");
		}
		else{
			sb.append("Rr0: Test Failed\n");
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
			sb.append("RB2: Test passed\n");
		}
		else{
			sb.append("RB2: Test Failed\n");
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
			sb.append("RB12: Test passed\n");
		}
		else{
			sb.append("RB12: Test Failed\n");
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
			sb.append("RB11: Test passed\n");
		}
		else{
			sb.append("RB11: Test Failed\n");
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
			sb.append("RB02: Test passed\n");
		}
		else{
			sb.append("RB02: Test Failed\n");
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
		rbt.printNodeStyle(null);
		rbt.delete(y);
		rbt.printNodeStyle(null);
		if((nodePY.getLeftChild() == nodeV)
			&& (nodePY.getRightChild() == RedBlackTree.externalNode)
			&& (nodeV.getLeftChild() == nodeA)
			&& (nodeV.getRightChild() == nodeB)
			&& (nodeV.getParent() == nodePY)
			&& (nodeV.getColor() == RedBlackTree.Color.RED))
		{
			sb.append("RB01: Test passed\n");
		}
		else
		{
			sb.append("RB01: Test failed\n");
		}

	}


}
