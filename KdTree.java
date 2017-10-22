package kd_tree;
import java.util.Arrays;
import java.lang.*;

public class KdTree {
	
	private static int k; // Définit la dimension dans laquelle le KdTree est défini. 
	private KdTree fg;   // Sous arbre gauche.
	private KdTree fd;   // Sous arbre droit.
	private int vector;  // Définit la normale à l'hyperplan modulo k.
						 // Par exemple, dans un repère cartésien, 0 vaut (1,0,0) ou la couteur Rouge, 1 représente (0,1,0).
	                     // Dans notre exemple, k vaut 3, 0 représente le Rouge, 1 représente le vert et 2 représente le bleu.
	
	
	private int listPoint[][]; // Liste  de points qui permet de définir l'hyperplan 
	                            // et les coordonnées  du noeud.
	private KdNode node;
	
	private static double minDistance=Math.sqrt(3)*255;   // Ces deux variables statiques
	private static KdNode nearestNeighbor;                // nous aideront pour l'ar
	
	public static void getDimension(int dim) // Définit la dimension dans la laquelle on va travailler.
												   // La définition de k est impérative avant l'utilisation de la classe.
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
	
	public void intFromPoint(int[] point, int vector)  // Initialise l'arbre à partir d'un point.
	{
		this.vector=vector;
		node=new KdNode(point);
		this.listPoint=null;
		this.fg=null;
		this.fd=null;
	}
	
	public void initFromArray(int[][] listPoint, int vector) // Initialie l'arbre à partir d'une liste de points.
	{
		if(listPoint.length==1) // Définit une feuille.
		{
			this.vector=vector;
			node=new KdNode(listPoint[0]);
			this.listPoint=null;
			this.fg=null;
			this.fd=null;
		}
		else                    // Définit les sous-arbres par récursion.
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
		if (isLeaf(this))     // Condition d'arrêt de la méthode récursive.
		{
			if (this.node.getDistance(point)<minDistance)
			{
				minDistance=this.node.getDistance(point);
				nearestNeighbor=this.node;
			}
		}
		else
		{
			int counter=0; // Ce compteur sera utile pour étudier le cas où l'hypersphère et l'hyperplan s'intersècent.
			
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
	
	public static void getDimension(int dim)  // De même, il es impératif ici de définir la dimension de l'espace.
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
