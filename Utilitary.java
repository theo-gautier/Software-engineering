package kd_tree;

import java.awt.Color;
import java.awt.image.BufferedImage;

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
	
	public static int[][] fromImageToArray(BufferedImage image, int dimension)
	{
		int[][] pixelList=new int[0][dimension];
		for(int x =0; x < image.getWidth();x++)
		{
			for (int y =0; y < image.getHeight(); y++) 
			{	
				Color pixelcolor = new Color (image.getRGB(x,y));
				int red= pixelcolor.getRed();
				int green = pixelcolor.getGreen();
				int blue = pixelcolor.getBlue();
				int[] pixel= {red,green,blue};
				pixelList=Utilitary.concatenateArrays(pixelList, pixel);
			}
		}
		return pixelList;
	}
}