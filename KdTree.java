package kd_tree;
import java.util.Arrays;
import java.lang.*;

public class KdTree {
	
	private static int k; // D�finit la dimension dans laquelle le KdTree est d�fini. 
	private KdTree fg;   // Sous arbre gauche.
	private KdTree fd;   // Sous arbre droit.
	private int vector;  // D�finit la normale � l'hyperplan modulo k.
						 // Par exemple, dans un rep�re cart�sien, 0 vaut (1,0,0) ou la couteur Rouge, 1 repr�sente (0,1,0).
	                     // Dans notre exemple, k vaut 3, 0 repr�sente le Rouge, 1 repr�sente le vert et 2 repr�sente le bleu.
	
	
	private int listPoint[][]; // Liste  de points qui permet de d�finir l'hyperplan 
	                            // et les coordonn�es  du noeud.
	private KdNode node;
	
	private static double minDistance=Math.sqrt(3)*255;   // Ces deux variables statiques
	private static KdNode nearestNeighbor;                // nous aideront pour l'ar
	
	public static void getDimension(int dim) // D�finit la dimension dans la laquelle on va travailler.
												   // La d�finition de k est imp�rative avant l'utilisation de la classe.
	{
		k=dim;
	}
	
	public boolean isLeaf(KdTree tree) // Renvoie un boolean pour savoir si l'arbre est une feuille ou non.
	{
		if (tree.fd==null && tree.fg==null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void intFromPoint(int[] point, int vector)  // Initialise l'arbre � partir d'un point.
	{
		this.vector=vector;
		node=new KdNode(point);
		this.listPoint=null;
		this.fg=null;
		this.fd=null;
	}
	
	public void initFromArray(int[][] listPoint, int vector) // Initialie l'arbre � partir d'une liste de points.
	{
		if(listPoint.length==1) // D�finit une feuille.
		{
			this.vector=vector;
			node=new KdNode(listPoint[0]);
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
			
			this.node = new KdNode(this.listPoint[len/2]);
			
			this.fg= new KdTree();
			fg.initFromArray(Arrays.copyOfRange(this.listPoint, 0, len/2), (vector+1)%k);
			this.fd= new KdTree();
			fg.initFromArray(Arrays.copyOfRange(this.listPoint, len/2+1, len), (vector+1)%k);
		}
	}
	
	public void getNearestPoint(KdNode point) 
	{
		if (isLeaf(this))     // Condition d'arr�t de la m�thode r�cursive.
		{
			if (this.node.getDistance(point)<minDistance)
			{
				minDistance=this.node.getDistance(point);
				nearestNeighbor=this.node;
			}
		}
		else
		{
			int counter=0; // Ce compteur sera utile pour �tudier le cas o� l'hypersph�re et l'hyperplan s'inters�cent.
			
			if (this.node.getDistance(point)<minDistance)
			{
				minDistance=this.node.getDistance(point);
				nearestNeighbor=this.node;
			}
			if (this.node.getPoint()[this.vector]<point.getPoint()[this.vector])
			{
				counter=1;
				this.fg.getNearestPoint(point);
			}
			else
			{
				counter=0;
				this.fd.getNearestPoint(point);
			}
			if (counter==1)
				if (Math.abs(this.node.getPoint()[this.vector]-point.getPoint()[this.vector])<minDistance)
				{
					this.fd.getNearestPoint(point);				
				}
			else
			{
				if (Math.abs(this.node.getPoint()[this.vector]-point.getPoint()[this.vector])<minDistance)
				{
					this.fg.getNearestPoint(point);				
				}
			}
		}
	}

}

class KdNode 
{
	private static int k;
	private int position[];
	
	public static void getDimension(int dim)  // De m�me, il es imp�ratif ici de d�finir la dimension de l'espace.
	{
		k=dim;
	}
	public KdNode(int[] position)
	{
		this.position=position;
	}
	
	public int[] getPoint()
	{
		return this.position;
	}
	
	public double getDistance(KdNode chosenPoint)
	{
		int p0[]=this.getPoint(), p1[]=chosenPoint.getPoint();
		double distance=0;
		for (int i=0; i<k; i++)
		{
			distance+=(p0[i]-p1[i])^2;
		}
		distance=Math.sqrt(distance);
		return distance;
	}
}
