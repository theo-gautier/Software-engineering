package kd_tree;

public class MyQuickSort {
	/* La classe ci-dessous contient les m�thodes n�cessaires pour trier un tableau. */
	
	private int vector; // D�finit sur quelle dimension nous allons travailler.
	private int array[][];
	private int length;
	
	public MyQuickSort(int[][] inputArr, int vector)
	{
		this.vector=vector;
		this.array=inputArr;
		this.length=inputArr.length;
	}

	public int[][] getSortedList()
	
	{
		quickSort(0, this.length-1);
		return array;
	}
	
	private void quickSort(int lowerIndex, int higherIndex)
	{
		int i=lowerIndex;
		int j=higherIndex;
		// Le pivot est d�finit comme le milieu de la liste.
		int pivot=array[lowerIndex + (higherIndex-lowerIndex)/2][vector];
		/*
		 * A chaque it�ration, nous identifions un �l�ment de coordonn�e k plus grande
		 * que celle du pivot (� gauche du pivot) et un �l�ment de coordonn�e k plus petite
		 * que celle du pivot (� droite du pivot).
		 */
		while(i<=j) 
		{
			while(array[i][vector]<pivot)
			{
				i++;
			}
			while(array[j][vector]>pivot)
			{
				j--;
			}
			if(i<=j)
			{
				exchangeElements(i, j);
				i++;
				j++;
			}
		}
		// Appel r�cursif de la m�thode quicksort.
		if (lowerIndex<j)
		{
			quickSort(lowerIndex, j);
		}
		if (higherIndex<i)
		{
			quickSort(i, higherIndex);
		}
	}
	
	private void exchangeElements(int i, int j)
	{
		int temp[]=array[i];
		array[i]=array[j];
		array[j]=temp;
	}
} 

