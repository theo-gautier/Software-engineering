
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
 
public class DisplayedImage extends JPanel
{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
    
}