package kd_tree;
import java.util.Arrays;
import java.awt.Color;
import java.lang.*;
import principal.DisplayedImage;
import java.awt.image.BufferedImage;

public class KdTree {
	
	private static int k; // D�finit la dimension dans laquelle le KdTree est d�fini. 
	private KdTree fg;   // Sous arbre gauche.
	private KdTree fd;   // Sous arbre droit.
	private int vector;  // D�finit la normale � l'hyperplan modulo k.
						 // Par exemple, dans un rep�re cart�sien, 0 vaut (1,0,0) ou la couteur Rouge, 1 repr�sente (0,1,0).
	                     // Dans notre exemple, k vaut 3, 0 repr�sente le Rouge, 1 repr�sente le vert et 2 repr�sente le bleu.
	
	
	private int listPoint[][]; // Liste  de points qui permet de d�finir l'hyperplan 
	                            // et les coordonn�es  du noeud.
	
	private int[] meanColorTree; // Indique la couleur moyenne du sous-arbre, cet attribut sera utile pour la quantification
	                             // d'image.
	
	private static int[][] colorPalette;
	
	private KdNode node;
	
	private static double minDistance;          // Ces deux variables statiques
	private static KdNode nearestNeighbor;      // nous aideront pour la recherche du point le plus proche.
	
	
	public static void getDimension(int dim) // D�finit la dimension dans la laquelle on va travailler.
										     // La d�finition de k est imp�rative avant l'utilisation de la classe.
	{
		k=dim;
		KdNode.getDimension(k);
		colorPalette=new int[0][k];
		minDistance=Math.sqrt(3)*255;
	}
	
	public boolean isLeaf() // Renvoie un boolean pour savoir si l'arbre est une feuille ou non.
	{
		if (this.fd==null && this.fg==null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void addPoint(KdNode point)   // Ajoute un point � l'arbre.
	{
		if (this.isLeaf())
		{
			if (point.getPoint()[this.vector]<this.node.getPoint()[this.vector])
			{
				this.fg=new KdTree();
				this.fg.intFromPoint(point.getPoint(), (this.vector+1)% k);
			}
			else
			{
				this.fd=new KdTree();
				this.fd.intFromPoint(point.getPoint(), (this.vector+1)% k);
			}
		}
		else
		{
			if (point.getPoint()[this.vector]<this.node.getPoint()[this.vector])
			{
				this.fg.addPoint(point);
			}
			else
			{
				this.fd.addPoint(point);;
			}
		}
	}
	public void removePoint(KdNode point)  //On supprime un noeud de l'arbre : on supprime le sous-arbre correspondant � ce noeud.
	{
		if (this.node.getPoint()==point.getPoint())
		{
			this.node=null;
			this.fd=null;
			this.fg=null;
			this.listPoint=null;
		}
		else if (this.node.getPoint()!=point.getPoint() || this.isLeaf())
		{
			return;
		}
		else
		{
			this.fd.removePoint(point);
			this.fg.removePoint(point);
		}
		
	}
	
	public String treeToString() // Renvoie une repr�sentation en cha�ne de caract�re de l'arbre (aide au d�buggage).
	{
		if (this.isLeaf())
		{
			StringBuilder line =new StringBuilder();
			line.append("(");
			for (int i=0; i<k; i++)
			{
				line.append(this.node.getPoint()[i]);
				line.append(",");
			}
			line.append(")");
			return line.toString();
		}
		else
		{
			StringBuilder line=new StringBuilder();
			line.append("(");
			line.append(this.fg.treeToString());
			line.append(",");
			line.append(this.fd.treeToString());
			line.append(")");
			return line.toString();
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
		if (this.isLeaf())     // Condition d'arr�t de la m�thode r�cursive.
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

	public static KdNode giveNearestPoint()
	{
		return nearestNeighbor;
	}
	
	public int[] meanColor() // Renvoie la couleur moyenne d'un sous-arbre.
	{
		int meanColorPixel[]=new int[3];
		int red=0, green=0, blue=0;
		for (int i=0; i<this.listPoint.length; i++)
			{
				red+=listPoint[i][0];
				green+=listPoint[i][1];
				blue+=listPoint[i][2];
			}
		meanColorPixel[0]=red/listPoint.length;
		meanColorPixel[1]=green/listPoint.length;
		meanColorPixel[2]=blue/listPoint.length;
		return  meanColorPixel;
	}
	
	public void meanColorPalette(int depth) // D�finit la couleur moyenne
	                                        // des sous-arbres qui serviront de palette de couleur.
	                                        // On ne fait pas ce calcul sur tous les sous-arbres pour �viter des calculs superflus.
											// Dans notre arbre binaire, le niveau 0 correspond � un noeud (1 couleur)
	                                        // Le niveau 1 repr�sente 2 noeuds (2 couleurs), etc...
	{
		if (depth==0)
		{
			this.meanColor();
		}
		else
		{
			this.fd.meanColorPalette(depth-1);
			this.fg.meanColorPalette(depth-1);
		}
	}
	
	public int[] meanColorKdNode(int[] point, int depth) // D�termine � quelle couleur moyenne de la palette correspond le pixel point.
	{
		if (depth==0)
		{
			return this.meanColorTree;
		}
		else
		{
			if (point[this.vector]<this.node.getPoint()[this.vector])
			{
				 return this.fg.meanColorKdNode(point, depth-1);
			}
			else
			{
				 return this.fd.meanColorKdNode(point, depth-1);
			}			
		}
  	}
	
	
}



class KdNode       // Cette classe permet de d�finir les noeuds de l'arbre.
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
