package kd_tree;

public class Test {

	public static void main(String[] args)
	{
		KdTree a = new KdTree (3);
		int[][] list= {{5,2,3},{7,6,4},{1,2,5},{1,1,1},{2,2,2},{3,3,3}};
		a.initFromArray(list, 0);
		a.printTree();
		int[] b= {1,2,5};
		int[] c= {7,9,1};
		int[] d= {2,1,2};
		b=a.getNearestPoint(b);
		c=a.getNearestPoint(c);
		d=a.getNearestPoint(d);
		System.out.println(b[0]);
		System.out.println(b[1]);
		System.out.println(b[2]);
		System.out.println(c[0]);
		System.out.println(c[1]);
		System.out.println(c[2]);
		System.out.println(d[0]);
		System.out.println(d[1]);
		System.out.println(d[2]);
	}
	
}
