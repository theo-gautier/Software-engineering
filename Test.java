package kd_tree;

public class Test {

	public static void main(String[] args)
	{
		KdTree a = new KdTree (3);
		int[][] list= {{5,2,3},{7,6,4},{1,2,5},{1,1,1},{2,2,2},{3,3,3}};
		a.initFromArray(list, 0);
		a.printTree();
		int[] b= {2,1,2};
		b=a.getNearestPoint(b);
		Utilitary.printList(b);
		int[] color = {2,2,2};
		a.removePoint(color);
		a.printTree();
		b=a.getNearestPoint(b);
		Utilitary.printList(b);
	}
	
}
