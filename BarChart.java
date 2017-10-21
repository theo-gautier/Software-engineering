package principal;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import java.awt.image.BufferedImage;

public class BarChart extends ApplicationFrame {
	   
	BufferedImage image;
	
	public BarChart(BufferedImage img, String applicationTitle , String chartTitle ) {
		
		
		super(applicationTitle);   
		image=img;
      //Création de l'histogramme.
		JFreeChart barChart = ChartFactory.createBarChart(
		chartTitle,           
        "Couleurs",            
        "Pourcentage",            
        createDataset(),          
        PlotOrientation.VERTICAL,           
        true, true, false);
         
		ChartPanel chartPanel = new ChartPanel(barChart);        
		chartPanel.setPreferredSize(new java.awt.Dimension(560,367));        
		setContentPane(chartPanel); 
	}
   
	public int[] getBarCount() {
    	int x; int y; int color;
    	int c_r = 0; int c_g = 0; int c_b = 0;
    	int tab[] = new int[3]; //Tableau contenant les compteurs de rouge vert et bleu dans l'image.

    	for (y = 0; y < image.getHeight(); y ++) {
    		System.out.println("lol");

    		for (x = 0; x < image.getWidth(); x++) {
    			color = image.getRGB(x,y);
    			int r = (color>>16)&0xFF; //red
    			int g = (color>>8)&0xFF; //green
    			int b = (color>>0)&0xFF; //blue
    			if(r > g) {
    				if(r > b) {
    					c_r++;
    				}
    				else {
    					c_b++;
    				}
    			}
    				else {
    					if (g > b) {
    						c_g++;
    					}
    					else {
    						c_b++;
    					}
    				}
    		}
    	}
    	int total_pixels = c_r + c_g + c_b;
    	tab[0] = (c_r*100)/total_pixels; tab[1] = (c_g*100)/total_pixels;
    	tab[2] = (c_b*100)/total_pixels; 
    	return tab;
    }
    
	private CategoryDataset createDataset( ) {
		final String red = "RED";        
		final String green = "GREEN";        
		final String blue = "BLUE";
		final String couleurs = "COULEURS";
     	final DefaultCategoryDataset dataset = 
     	new DefaultCategoryDataset( ); //On créer le jeu de données.

     	int[] count = getBarCount();
      
     	dataset.addValue( count[0] , red , couleurs );        

     	dataset.addValue( count[2] , blue , couleurs );

     	dataset.addValue( count[1] , green , couleurs );    

     	return dataset; 
   }
}