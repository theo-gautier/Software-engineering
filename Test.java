package kd_tree;

public class Test {

	public static void main(String[] args)
	{
		KdTree a = new KdTree (3);
		int[][] list= {{5,2,3},{7,6,4},{1,2,5},{1,1,1},{2,2,2},{3,3,3},{2,2,2}};
		a.initFromArray(list, 0);
		System.out.print("Nous construisons un arbre :");
		a.printTree();
		System.out.print("Nous ajoutons le point (6,7,6) : ");
		int[] t= {6,7,6};
		a.addPoint(t);
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
		a.initFromArray(list, 0);
		System.out.print("Nous construisons un arbre :");
		a.printTree();
		a.buildColorRange(1);
		a.colorRangeTree();
		System.out.print("L'arbre de couleur moyenne : ");
		a.printColorRangeTree();
		
	}
	
}
