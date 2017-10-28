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
		//TODO : Cas où le KdTree est vide.
		if (this.node.color==color)
		{
			this.node=null;
		}
		else
		{
			this.node.removePoint(color);
		}
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
	int vector; // Définit la normale à l'hyperplan modulo k.
	            // Par exemple, dans un repère cartésien, 0 vaut (1,0,0), 1 représente (0,1,0).
                // Dans notre exemple, k vaut 3, 0 représente le Rouge, 1 représente le vert et 2 représente le bleu.
	KdNode fg;
	KdNode fd;
	
	public KdNode ()
	{
		
	}
	
	public boolean isLeaf() // Vérifie si un noeud repésente une feuille.
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
			// Nous faisons le choix de former, pour ce cas, la configuration de noeud qui posséde un fils droit seulement.
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
			StringBuilder line=new StringBuilder(); // Compléter son arbre
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
	
	public void printList(int color[]) {
		int i;
		System.out.print("(");
		for (i = 0; i < dimension -1; i++) {
			System.out.print(color[i] + ",");
		}
		System.out.print(color[i]);
		System.out.println(")");

	}
	public boolean eqColor(int ext_color[]) {
		int i;
		for (i = 0; i < dimension; i++) {
			if(this.color[i] != ext_color[i]) {
				return false;
			}
		}
		return true;
	}
	public void removePoint(int color[])
	{
		if ( !this.isLeaf()){
			if(fg != null) {
				if(fg.eqColor(color)) {
					fg = null;
				}
				else if(!fg.eqColor(color)) {
					fg.removePoint(color);
					}
					
				}
			}
			
			if(fd != null) {
				if(fd.eqColor(color)) {
					fd = null;
				}
				else if(!fd.eqColor(color)) {
						fd.removePoint(color);
					
				}
			}
		}
	
	public void addPoint(int[] color)
	{
		// à compléter
	}
	
}
