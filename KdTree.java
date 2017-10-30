package kd_tree;

import java.util.Arrays;

public class KdTree
{
	int dimension;
	KdNode node=new KdNode();
	ColorRange range;
	KdNode colorTree=new KdNode();
	
	public KdTree (int dimension) 
	{
		this.dimension=dimension;;
		this.range=new ColorRange();
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
		// Cas où le KdTree est vide.
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
	
	public int[] getNearestPoint(int[] color)
	{
		KdNode chosenPoint=new KdNode();
		chosenPoint.initFromPoint(color, -1, this.dimension);
		this.node.getNearestPoint(chosenPoint);
		return chosenPoint.nearestColor; 
	}
	
	public void buildColorRange(int depth)

	{
		this.node.buildColorRange(this.range, depth);
	}
	
	public void colorRangeTree()
	{
		this.colorTree.initFromArray(this.range.getColorRange(), 0,this.dimension);
	}
	
	public int[] quantifyColor(int [] color)
	{
		KdNode chosenPoint=new KdNode();
		chosenPoint.initFromPoint(color, -1, this.dimension);
		this.colorTree.getNearestPoint(chosenPoint);
		return chosenPoint.nearestColor; 
	}
	
	public void printColorRangeTree()
	{
		System.out.println(colorTree.printNode());
	}
	
}

class KdNode
{
	public int color[];
	public int dimension;
	public int vector; // Définit la normale à l'hyperplan modulo k.
	                   // Par exemple, dans un repère cartésien, 0 vaut (1,0,0), 1 représente (0,1,0).
                       // Dans notre exemple, k vaut 3, 0 représente le Rouge, 1 représente le vert et 2 représente le bleu.
	public KdNode fg;
	public KdNode fd;
	// Les attributs suivants serviront pour la méthode getNearestPoint;
	int[] nearestColor;
	double minDistance=Math.sqrt(3)*255;
	
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
		KdNode a = new KdNode();
		int index=(this.vector+1)%this.dimension;
		a.initFromPoint(color,index,this.dimension);
		if (this.isLeaf())    // si c'est une feuille on ajoute directement le noeud
		{
			if(this.color[index]<=color[index])
			{
				this.fd = a;
			}
			else
			{
				this.fg=a;
			}
		}
		else if (this.fd == null)  
			if (this.color[index]<=color[index])
			{
				this.fd = a;
			}
			else
			{
				this.fg.addPoint(color);
			}
		else if (this.fg == null) 
			if (this.color[index]>color[index])
			{
				this.fg = a;
			}
			else
			{
				this.fd.addPoint(color);
			}
		else
		{
			if (this.color[index]<=color[index])
			{
				this.fd.addPoint(color);
			}
			else
			{
				this.fg.addPoint(color);
			}
		}
	}
	
	public double getDistance(KdNode chosenPoint)
	{
		int p0[]=this.color, p1[]=chosenPoint.color;
		double distance=0;
		for(int i=0; i<this.dimension; i++)
		{
			distance+=(p0[i]-p1[i])*(p0[i]-p1[i]);
		}
		distance=Math.sqrt(distance);
		return distance;
	}
	
	public void getNearestPoint(KdNode chosenPoint)	
	{
		if (this.isLeaf())
		{
			double distance = this.getDistance(chosenPoint);
			if (distance<chosenPoint.minDistance)
			{	
				chosenPoint.minDistance=distance;
				chosenPoint.nearestColor=this.color;
			}
		}
		else
		{
			int counter; // Ce compteur sera utile pour étudier le cas où l'hypersphère et l'hyperplan s'intersècent.
			double distance = this.getDistance(chosenPoint);
			if (distance < chosenPoint.minDistance)
			{
				chosenPoint.minDistance=distance;
				chosenPoint.nearestColor=this.color;
			}
			if (this.color[this.vector]<chosenPoint.color[this.vector])
			{
				counter=1;
				if (this.fd!=null)
				{
					this.fd.getNearestPoint(chosenPoint);
				}
			}
			else
			{
				counter=0;
				if (this.fg!=null)
				{
					this.fg.getNearestPoint(chosenPoint);
				}
			}
			if(counter==1)
			{
				if(Math.abs(this.color[this.vector]-chosenPoint.color[this.vector])<chosenPoint.minDistance)
				{
					if (this.fg!=null)
					{
						this.fg.getNearestPoint(chosenPoint);
					}
				}
			}
			else
			{
				if(Math.abs(this.color[this.vector]-chosenPoint.color[this.vector])<chosenPoint.minDistance)
				{
					if (this.fd!=null)
					{
						this.fd.getNearestPoint(chosenPoint);
					}
				}				
			}
		}
	}
		public int[] meanColor()
		{
			if(this.isLeaf())
			{
				return this.color;
			}
			else
			{
				int[] a=new int[3];
				int b[]= {0,0,0};
				if(this.fd!=null)
				{
					b=this.fd.meanColor();
				}
				int c[]= {0,0,0};
				if (this.fg!=null)
				{
					c=this.fg.meanColor();
				}
				for (int i=0;i<3; i++)
				{
					a[i]=(b[i]+c[i])/2;
				}
				return a;
			}
		}
	
	
	public void buildColorRange(ColorRange range, int depth)
	{
		if (depth==1)
		{
			if (this.fg!=null)
			{
				range.addColor(this.fg.meanColor());
			}
			if (this.fd!=null)
			{
				range.addColor(this.fd.meanColor());		
			}	
		}
		else
		{
			if (this.fg!=null)
			{
				this.fg.buildColorRange(range, depth-1);
			}
			if (this.fd!=null)
			{
				this.fd.buildColorRange(range, depth-1);				
			}	
		}
	}
}

class ColorRange
{
	private int[][] colorRange;
	
	public ColorRange()
	{
		int[][] a = {{-1,-1,-1}};
		this.colorRange=a;
	}
	public void addColor(int[] color)
	{
		this.colorRange=Utilitary.concatenateArrays(this.colorRange, color);
	}
	
	public int[][] getColorRange()
	{
		return Arrays.copyOfRange(this.colorRange, 1, this.colorRange.length);
	}
	
}