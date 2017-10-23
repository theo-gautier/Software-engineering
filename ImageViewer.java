
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.jfree.ui.RefineryUtilities;

import javax.swing.JButton;
import javax.swing.BoxLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.print.attribute.standard.OutputDeviceAssigned;
import javax.swing.JFileChooser;

import kd_tree.*;

public class ImageViewer extends JFrame /*implements ActionListener*/
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenuItem itemSave = new JMenuItem("Save");	
	
	//Cr�ation des images qui vont �tre affich�es.
	private DisplayedImage inputImage = new DisplayedImage(); 
	private DisplayedImage ouputImage = new DisplayedImage();
	
	//Cr�ation des boutons clickables.
	private JButton buttonAction = new JButton("Action");
	private JButton buttonInversion = new JButton("Inversion");
	private JButton buttonBarChart = new JButton("Bar Chart");
	private JButton buttonQuantification = new JButton("Quantificaion");

	//Cr�ation du header o� se trouve un bouton File, et un sous-bouton Close.
	private JMenuBar menuBar = new JMenuBar();
	private JMenuItem loadFile = new JMenuItem("Load"); /*  On d�finit le bouton charger */
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem itemClose = new JMenuItem("Close");
	

	//On s'attache ici � la cr�ation du Canvas pour accueillir les images.
	public ImageViewer () {
		
		
		this.setTitle("Image Viewer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 400);

		JPanel input = new JPanel();
		input.setLayout(new BoxLayout(input, BoxLayout.PAGE_AXIS));
		input.add(inputImage);

		JPanel action = new JPanel();
		action.setLayout(new BoxLayout(action, BoxLayout.PAGE_AXIS));
		action.add(buttonAction);
		// Defines action associated to buttons
		buttonAction.addActionListener(new ButtonListener());

		JPanel output = new JPanel();
		output.setLayout(new BoxLayout(output, BoxLayout.PAGE_AXIS));
		output.add(ouputImage); 

		JPanel inversion = new JPanel();
		inversion.setLayout(new BoxLayout(inversion, BoxLayout.PAGE_AXIS));
		action.add(buttonInversion);
		
		JPanel barchart = new JPanel();
		barchart.setLayout(new BoxLayout(inversion, BoxLayout.PAGE_AXIS));
		action.add(buttonBarChart);
		buttonBarChart.addActionListener(new ButtonBarChartListener());
		
		JPanel quantification = new JPanel(); 
		quantification.setLayout(new BoxLayout(quantification, BoxLayout.PAGE_AXIS)); // Je ne suis pas s�r que la quantification
		quantification.add(buttonQuantification);                                     // sera compatible avec l'inversion.
		
		
		JPanel global = new JPanel();
		global.setLayout(new BoxLayout(global, BoxLayout.LINE_AXIS));
		global.add(input);
		global.add(action);
		global.add(inversion);
		global.add(output);
		global.add(quantification);
		
		
		// Defines action associated to buttons
		buttonInversion.addActionListener(new ButtonListener(){
			public void actionPerformed(ActionEvent arg0) {
				BufferedImage inv;
				try {
					inv = inputImage.getImage();
				
					for(int x =0; x < inv.getWidth();x++) {
						for (int y =0; y < inv.getHeight(); y++) {
							
							Color pixelcolor = new Color (inv.getRGB(x,y));
							
							int red= pixelcolor.getRed();
							int green = pixelcolor.getGreen();
							int blue = pixelcolor.getBlue();
							
							red = Math.abs(red-255);
							green = Math.abs(green-255);
							blue = Math.abs(blue-255);
							
							int rgb = new Color(red,green,blue).getRGB();
							
							inv.setRGB(x, y, rgb);
							 
						}
						
					}
					ouputImage.ChangeImage(inv);
					/*output.updateUI();*/
					output.repaint();
					ImageIO.write(inv, "png", new File ("inverse.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		
		this.getContentPane().add(global);

		this.fileMenu.addSeparator();
		itemClose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}        
		});
		
		itemSave.addActionListener(new ActionListener() {
				// faire une capture d'ecran de l'image a droite   
		public void actionPerformed(ActionEvent e) {
			BufferedImage image = new BufferedImage(output.getWidth(), output.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			output.printAll(g);
			g.dispose();
			try {
				ImageIO.write(image, "png", new File("newImage.png"));
			} catch (IOException exp) {
				exp.printStackTrace();
				}
			}
		});
		this.fileMenu.add(loadFile); // On ajoute au bonton File l'action le sous-bouton charger
		this.fileMenu.add(itemSave);
		this.fileMenu.add(itemClose);  
		
		this.loadFile.addActionListener(new ActionListener(){ /* On ajoute au bouton load un d�tecteur de click */
		public void actionPerformed(ActionEvent arg0) 
			{
			JFileChooser jfc = new JFileChooser(); /*  Affiche une fen�tre qui permet de parcourir les diff�rents r�pertoires */
			String path;
			if (arg0.getSource() == loadFile) {
			    int returnVal = jfc.showOpenDialog(null);
			        
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			    	path=jfc.getSelectedFile().getAbsolutePath();
			        inputImage.refreshImage(path);
			        ouputImage.refreshImage(path);
			        	
			    	input.revalidate(); /* On rafra�chit l'image de gauche */ 
			    	input.repaint();
			    	output.revalidate(); /* On rafra�chit l'image de droite */
			    	output.repaint();		
			    }
			}
		}
		});
		
		buttonQuantification.addActionListener(new ButtonListener(){
			public void actionPerformed(ActionEvent arg0) 
			{
				BufferedImage image;
				KdTree.getDimension(3);
				KdTree tree= new KdTree();
				try
				{
					image=ouputImage.getImage();
					int[][] PixelInArray=Utilitary.fromImageToArray(image, 3);
					tree.initFromArray(PixelInArray, 0);
					tree.meanColorPalette(8);
			    	for (int i=0; i<PixelInArray.length; i++)
			    	{
			    		PixelInArray[i]=tree.meanColorKdNode(PixelInArray[i], 8);
			    	}
			    	for (int x=0; x<image.getWidth(); x++)
		    		{
			    		for(int y=0; y<image.getHeight(); y++)
		    			{
			    			int i= x*image.getWidth()+y;
							int rgb = new Color(PixelInArray[i][0],PixelInArray[i][1],PixelInArray[i][2]).getRGB();
							image.setRGB(x, y, rgb);
		    			}
		    		}
					ouputImage.ChangeImage(image);
					/*output.updateUI();*/
					output.repaint();
					ImageIO.write(image, "png", new File ("inverse.png"));
				  }
				  catch (IOException exp)
				  {
					exp.printStackTrace();
				  }
				}
			});
		
		
		this.menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
		this.setVisible(true);
	}
	
	DisplayedImage getOutputImage() {
		return ouputImage;
		}

	/*
	 * Class listening to a given button
	 * Attends qu'un click soit effectu� sur un bouton pour afficher le message qui suit.
	 */
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) 
		{
			System.out.println("Action Performed");
		}
	}
		
	class ButtonBarChartListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			ouputImage.repaint();
			final BarChart bar = new BarChart(inputImage.getImage(),"Poids des couleurs","R�sultats");
			bar.pack();
			RefineryUtilities.centerFrameOnScreen(bar);
	        bar.setVisible(true);
		}
	}
}