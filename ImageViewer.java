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
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.OutputDeviceAssigned;
import javax.swing.JFileChooser;

import kd_tree.*;

public class ImageViewer extends JFrame /*implements ActionListener*/
{

	//Création des images qui vont être affichées.
	private DisplayedImage inputImage = new DisplayedImage(); 
	private DisplayedImage ouputImage = new DisplayedImage();
	
	//Création des boutons clickables.
	private JButton buttonAction = new JButton("Action");
	private JButton buttonInversion = new JButton("Inversion");
	private JButton buttonBarChart = new JButton("Bar Chart");
	private JButton buttonQuantification= new JButton("Quantification");

	//Création du header où se trouve un bouton File, et un sous-bouton Close.
	private JMenuBar menuBar = new JMenuBar();
	private JMenuItem loadFile = new JMenuItem("Load"); /*  On définit le bouton charger */
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem itemClose = new JMenuItem("Close");
	private JMenuItem itemSave = new JMenuItem("Save");	

	private JMenuItem itemSaveQuantification = new JMenuItem("Save Quantification");
	private JMenuItem itemLoadQuantification = new JMenuItem("Load Quantification");

	
	

	//On s'attache ici à la création du Canvas pour accueillir les images.
	public ImageViewer () {
		
		ByteBuffer buff = ByteBuffer.allocate(4000000*Integer.BYTES); /*buffer dans lequel on 
		inscrit les différents bytes que l'on va écrire. On ne traitera pas des images plus grandes que 200*200 */
		
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
		quantification.setLayout(new BoxLayout(quantification, BoxLayout.PAGE_AXIS));
		quantification.add(buttonQuantification);
		
		buttonQuantification.addActionListener(new ButtonListener(){
			public void actionPerformed(ActionEvent arg0) {
				BufferedImage quant;
				try {
					quant = inputImage.getImage();
					int[][] pixelList=DisplayedImage.fromImageToArray(quant);
					KdTree tree=new KdTree(3);
					tree.initFromArray(pixelList, 0);
					tree.buildColorRange(4);
					tree.colorRangeTree();
					
					String info_str = "P3 " + quant.getWidth() + " " + quant.getHeight() + "\n255 \n";
					byte[] info = (info_str).getBytes();
					/*On écrit dans info les informations qui ne dépendent pas des couleurs utilisées, comme ça on inscrit
					par la suite les codes couleurs dans le buffer */
					
					buff.put(info);
					
					for(int x =0; x < quant.getWidth();x++) {
						for (int y =0; y < quant.getHeight(); y++) {
							
							Color pixelcolor = new Color (quant.getRGB(x,y));
							
							int red= pixelcolor.getRed();
							int green = pixelcolor.getGreen();
							int blue = pixelcolor.getBlue();
							
							int[] tab= {red, green, blue};
							
							
							tab=tree.quantifyColor(tab);
							int rgb = new Color(tab[0],tab[1],tab[2]).getRGB();
							
							/* Ici on écrit l'entier dans le buffer
							String rgb_str = "" + rgb;
							byte[] rgb_bytes = (rgb_str).getBytes(); 
							buff.put(rgb_bytes); //TODO : Ecrire l'indice.
							buff.put(" ".getBytes()); */
							
							quant.setRGB(x, y, rgb);
							 
						}
					}					
					ouputImage.ChangeImage(quant);
					output.updateUI();
					output.repaint();
					ImageIO.write(quant, "png", new File ("quantified.png"));				
				} catch (IOException e) {
					// Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});		
		
		
		JPanel global = new JPanel();
		global.setLayout(new BoxLayout(global, BoxLayout.LINE_AXIS));
		global.add(input);
		global.add(action);
		global.add(inversion);
		global.add(quantification);
		global.add(output);
		
		
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
					// Auto-generated catch block
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
		
		this.itemSave.addActionListener(new ActionListener() {
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
		
		this.loadFile.addActionListener(new ActionListener(){ /* On ajoute au bouton load un détecteur de click */
			public void actionPerformed(ActionEvent arg0) 
				{
				JFileChooser jfc = new JFileChooser(); /*  Affiche une fenêtre qui permet de parcourir les différents répertoires */
				String path;
				if (arg0.getSource() == loadFile) {
				    int returnVal = jfc.showOpenDialog(null);
				        
				    if (returnVal == JFileChooser.APPROVE_OPTION) {
				    	path=jfc.getSelectedFile().getAbsolutePath();
				        inputImage.refreshImage(path);
				        ouputImage.refreshImage(path);
				        	
				    	input.revalidate(); /* On rafraîchit l'image de gauche */ 
				    	input.repaint();
				    	output.revalidate(); /* On rafraîchit l'image de droite */
				    	output.repaint();		
				    }
				}
			}
			});
		
		this.itemSaveQuantification.addActionListener(new ActionListener() {
			// Enregistrer l'image de droite au format ppm.
			public void actionPerformed(ActionEvent e) {
				try {
					FileOutputStream file_output = new FileOutputStream("imageQuantified.ppm");
					file_output.write(buff.array());
					buff.clear();
					file_output.close();
				} catch(IOException excep) {
					System.out.println(excep);
				}
			}
		});
		
		this.fileMenu.add(loadFile); // On ajoute au bouton File le sous-bouton "load"
		this.fileMenu.add(itemSave);
		this.fileMenu.add(itemClose);  
		
		this.fileMenu.add(itemSaveQuantification);
		this.fileMenu.add(itemLoadQuantification);

		
		
		this.menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
		this.setVisible(true);
	}
	
	byte[] intToByteArray(int value) {
	    return new byte[] {
	            (byte)(value >>> 24),
	            (byte)(value >>> 16),
	            (byte)(value >>> 8),
	            (byte)value};}
	
	DisplayedImage getOutputImage() {
		return ouputImage;
		}

	
	/*
	 * Class listening to a given button
	 * Attends qu'un click soit effectué sur un bouton pour afficher le message qui suit.
	 */
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) 
		{
			System.out.println("Action Performed");
		}
	}
		
	class ButtonBarChartListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) 
		{
			ouputImage.repaint();
			final BarChart bar = new BarChart(inputImage.getImage(),"Poids des couleurs","Résultats");
			bar.pack();
			RefineryUtilities.centerFrameOnScreen(bar);
	        bar.setVisible(true);
		}
	}
}