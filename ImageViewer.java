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
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class ImageViewer extends JFrame /*implements ActionListener*/
{

	private JMenuItem itemSave = new JMenuItem("Save");	
	
	//Création des images qui vont être affichées.
	private DisplayedImage inputImage = new DisplayedImage(); 
	private DisplayedImage ouputImage = new DisplayedImage();
	
	//Création des deux boutons clickables.
	private JButton buttonAction = new JButton("Action");
	private JButton buttonInversion = new JButton("Inversion");
	private JButton buttonBarChart = new JButton("Bar Chart");

	//Création du header où se trouve un bouton File, et un sous-bouton Close.
	private JMenuBar menuBar = new JMenuBar();
	private JMenuItem loadFile = new JMenuItem("Load"); /*  On définit le bouton charger */
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem itemClose = new JMenuItem("Close");
	

	//On s'attache ici à la création du Canvas pour accueillir les images.
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
		
		JPanel inversion = new JPanel();
		inversion.setLayout(new BoxLayout(inversion, BoxLayout.PAGE_AXIS));
		action.add(buttonInversion);
		// Defines action associated to buttons
		buttonInversion.addActionListener(new ButtonListener());
		
		JPanel barchart = new JPanel();
		barchart.setLayout(new BoxLayout(inversion, BoxLayout.PAGE_AXIS));
		action.add(buttonBarChart);
		buttonBarChart.addActionListener(new ButtonBarChartListener());

		JPanel output = new JPanel();
		output.setLayout(new BoxLayout(output, BoxLayout.PAGE_AXIS));
		output.add(ouputImage); 

		JPanel global = new JPanel();
		global.setLayout(new BoxLayout(global, BoxLayout.LINE_AXIS));
		global.add(input);
		global.add(action);
		global.add(output);

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
		
		this.menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
		this.setVisible(true);
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
			final BarChart bar = new BarChart("Poids des couleurs","Résultats");
			bar.pack();
			RefineryUtilities.centerFrameOnScreen(bar);
	        bar.setVisible(true);
		}
	}
}