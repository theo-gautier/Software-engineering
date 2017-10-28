package kd_tree;

public class Test {

	public static void main(String[] args)
	{
		KdTree a = new KdTree (3);
		int[][] list= {{5,2,3},{7,6,4},{1,2,5},{1,1,1},{2,2,2},{3,3,3}};
		a.initFromArray(list, 0);
		System.out.print("Nous construisons un arbre :");
		a.printTree();
		int[] b= {2,1,2};
		b=a.getNearestPoint(b);
		System.out.print("Le point le plus proche de (2, 1, 2) est :");
		Utilitary.printList(b);
		int[] color = {2,2,2};
		System.out.print("On enlève le point (2,2,2) de l'arbre ci-dessus :");
		a.removePoint(color);
		a.printTree();
		b=a.getNearestPoint(b);
		System.out.print("Le point le plus proche de (2,1,2) est maintenant :");
		Utilitary.printList(b);
	}
	
}
