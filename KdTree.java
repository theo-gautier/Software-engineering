package kd_tree;
import java.util.Arrays;


public class KdTree {
	
	private static int k; // D�finit la dimension dans laquelle le KdTree est d�fini. 
	private KdTree fg;   // Sous arbre gauche.
	private KdTree fd;   // Sous arbre droit.
	private int vector;  // D�finit la normale � l'hyperplan modulo k.
						 // Par exemple, dans un rep�re cart�sien, 0 vaut (1,0,0), 1 repr�sente (0,1,0).
	
	
	private int listPoint[][]; // Liste  de points qui permet de d�finir l'hyperplan 
	                            // et les coordonn�es  du noeud.
	private KdNode node;
	
	public static void getDimension(int dimension) // D�finit la dimension dans la laquelle on va travailler.
												   // La d�finition de k est imp�rative avant l'utilisation de la classe.
	{
		k=dimension;
	}
	
	public void intFromPoint(int[] point, int vector)
	{
		this.vector=vector;
		node=new KdNode(vector, point);
		this.listPoint=null;
		this.fg=null;
		this.fd=null;
	}
	
	public void initFromArray(int[][] listPoint, int vector) 
	{
		if(listPoint.length==1) // D�finit une feuille.
		{
			this.vector=vector;
			node=new KdNode(vector, listPoint[0]);
			this.listPoint=null;
			this.fg=null;
			this.fd=null;
		}
		else                    // D�finit les sous-arbres par r�cursion.
		{
			this.vector=vector;
			
			MyQuickSort sorter= new MyQuickSort(listPoint, vector);
			this.listPoint=sorter.getSortedList();
			
			int len=this.listPoint.length;
			
			this.node = new KdNode(vector, this.listPoint[len/2]);
			
			this.fg= new KdTree();
			fg.initFromArray(Arrays.copyOfRange(this.listPoint, 0, len/2), (vector+1)%k);
			this.fd= new KdTree();
			fg.initFromArray(Arrays.copyOfRange(this.listPoint, len/2+1, len), (vector+1)%k);
		}
	}

}

class KdNode {
	private int vector;
	private int position[];
	
	public KdNode( int vector, int[] position)
	{
		this.vector=vector;
		this.position=position;
	}
}
