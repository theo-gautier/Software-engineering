package kd_tree;

import java.util.Arrays;

public class KdTree
{
	int dimension;
	KdNode node=new KdNode();
	
	public KdTree (int dimension) 
	{
		this.dimension=dimension;
	}
		
	public void initFromArray (int [][] listPoint, int vector)
	{
		this.node.initFromArray(listPoint, 0, this.dimension);
	}
	
	public void printTree()
	{
		System.out.println(node.printNode());
	}
	
	public void removePoint(int[] color)
	{
		// � compl�menter : il faut utiliser la m�thode removePoint de la classe Kdnode.
	}
	
	public void addPoint(int[] color)
	{
		this.node.addPoint(color);
	}
}

class KdNode
{
	int color[];
	int dimension;
	int vector; // D�finit la normale � l'hyperplan modulo k.
	            // Par exemple, dans un rep�re cart�sien, 0 vaut (1,0,0), 1 repr�sente (0,1,0).
                // Dans notre exemple, k vaut 3, 0 repr�sente le Rouge, 1 repr�sente le vert et 2 repr�sente le bleu.
	KdNode fg;
	KdNode fd;
	
	public KdNode ()
	{
		
	}
	
	public boolean isLeaf() // V�rifie si un noeud rep�sente une feuille.
	{
		if (this.fg==null && this.fd==null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void  initFromPoint(int[] Point, int vector, int dimension)
	{
		this.color=Point;
		this.vector=vector;
		this.dimension=dimension;
		this.fg=null;
		this.fd=null;
	}
	
	public void initFromArray(int[][] listPoint, int vector, int dimension)
	{
		if (listPoint.length==1)     // Construit une feuille.
		{
			this.color=listPoint[0];
			this.vector=vector;
			this.dimension=dimension;
			this.fg=null;
			this.fd=null;
		}
		else if (listPoint.length==2)  
		{
			MyQuickSort sorter = new MyQuickSort(listPoint, vector);
			listPoint=sorter.getSortedList();
			// Nous faisons le choix de former, pour ce cas, la configuration de noeud qui poss�de un fils droit seulement.
			this.color=listPoint[0];
			this.vector=vector;
			this.dimension=dimension;
			this.fg=null;
			this.fd=new KdNode();
			this.fd.initFromPoint(listPoint[1], (vector+1)%this.dimension, this.dimension);
		}
		else
		{
			MyQuickSort sorter = new MyQuickSort(listPoint, vector);
			listPoint=sorter.getSortedList();
			
			int len = listPoint.length;
			
			this.color=listPoint[len/2];
			this.vector=vector;
			this.dimension=dimension;
			this.fg=new KdNode();
			this.fg.initFromArray(Arrays.copyOfRange(listPoint, 0, len/2), (vector+1)%dimension, dimension);
			this.fd=new KdNode();
			this.fd.initFromArray(Arrays.copyOfRange(listPoint, len/2+1, len), (vector+1)%dimension, dimension);
		}
	}
	
	public String printNode()
	{
		if (this.isLeaf())
		{
			StringBuilder line =new StringBuilder();
			line.append("(");
			for (int i=0; i<this.dimension-1; i++)
			{
				line.append(String.valueOf(this.color[i]));
				line.append(",");
			}
			line.append(String.valueOf(this.color[this.dimension-1]));
			line.append(")");
			return line.toString();
		}
		else
		{
			StringBuilder line=new StringBuilder(); // Compl�ter son arbre
			line.append("(");
			for (int i=0; i<this.dimension-1; i++)
			{
				line.append(String.valueOf(this.color[i]));
				line.append(",");
			}
			line.append(String.valueOf(this.color[this.dimension-1]));
			line.append(")");
			line.append("(");
			if (this.fg!=null)
			{
				line.append(this.fg.printNode());
			}
			else
			{
				line.append(" ");
			}
			line.append(",");
			if (this.fd!=null)
			{
				line.append(this.fd.printNode());				
			}
			else
			{
				line.append(" ");
			}
			line.append(")");
			return line.toString();
		}
	}
	
	public void removePoint(int color[])
	{
		// � compl�ter
	{
	
	public void addPoint(int[] color)
	{
		int dimension = color.length;
		for (int p=0; p<dimension-1; p++)
		{
			int vector = 0;
			if (color[p+1] > color[p])
					vector = p;
		}
		KdNode a = new KdNode();
		a.initFromPoint(color,vector,dimension);
		if (this.isLeaf())    // si c'est une feuille on ajoute directement le noeud
			this.fd = a;
		if (this.fd == null)   // si l'un des deux fils est nul on y ajoute le noeud
			this.fd = a;
		if (this.fg == null)
			this.fg = a;
		else
			if (Math.random()<0.5)
				fd.addPoint(color);  // si le noeud a deux fils, on essaye d'appliquer la methode
			else                     // sur l'un des deux fils
				fg.addPoint(color);  // on utilise l'aleatoire pour équilibrer l'arbre sur un grand nombre d'ajout
			
			
			
			
		
		
				
		
	}
		
	
	

