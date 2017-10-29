import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;

import kd_tree.*;
import java.util.ArrayList;

 
public class DisplayedImage extends JPanel {
	
    private BufferedImage image;
    
    public DisplayedImage() {
    		try {
    			image = ImageIO.read(new File("img.png"));
        	} catch (IOException e) {
        		e.printStackTrace();
        	}   

    }
    

    public void refreshImage(String path){ /* Remplace l'ancienne image par la nouvelle */
		try {
			image = ImageIO.read(new File(path));
    	} catch (IOException e) {
    		e.printStackTrace();
    	}     
    }
    
    public void ChangeImage(BufferedImage img){ /* Ancienne image est remplacée*/
		image =img;
		repaint();
    }
    
    public BufferedImage getImage() {
    	return image;
    }

    public void paintComponent(Graphics g){
    		//g.drawImage(image, 0, 0, this); // draw as much as possible
    		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this); // draw full image
    }                   
    
    public static int[][] fromImageToArray(BufferedImage img)
    {
    	ArrayList<int[]> pixelList= new ArrayList<int[]>();
    	int i=0;
		for(int x =0; x < img.getWidth();x++) 
		{
			for (int y =0; y < img.getHeight(); y++)
			{
				Color pixelcolor = new Color (img.getRGB(x,y));
				
				int red= pixelcolor.getRed();
				int green = pixelcolor.getGreen();
				int blue = pixelcolor.getBlue();
				
				int[] pixel= {red, green, blue};
				pixelList.add(pixel);
				i+=1;
			}
		}	
		int a[][]=new int[i][];
		System.out.println(a.length);
		for(int m=0; m<a.length; m++ ) 
		{
			a[m]=pixelList.get(m);
		}
			
		return a;
    }
}