package kd_tree;

public class Utilitary  // La classe ci-dessous fournit des méthodes statiques utiles à Kdtree
{
	/* Permet de concatener un tableau de tableau d'entier avec un tableau d'entier */
	public static int[][] concatenateArrays(int[][] a,int[] b) 
	{
		
		int [][] c= new int[a.length+1][b.length];
		for (int i=0; i<a.length; i++)
		{
			c[i]=a[i];
		}
		c[a.length]=b;
		return c;
	}
}