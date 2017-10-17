import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;

public class ImageViewer extends JFrame /*implements ActionListener*/
{
	private String path; 
	private DisplayedImage inputImage = new DisplayedImage(); 
	private DisplayedImage ouputImage = new DisplayedImage();
	private JButton buttonAction = new JButton("Action");

	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem loadFile = new JMenuItem("Load"); /*  On d�finit le bouton charger */
	private JMenuItem itemClose = new JMenuItem("Close");
	JFileChooser jfc = new JFileChooser(); /*  Affiche une fen�tre qui permet de parcourir les diff�rents r�pertoires */


	public ImageViewer () {
		this.setTitle("Image Viewer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 400);

		// Cr�ation d'un s�lecteur de fichier.
		
		
		
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
		this.fileMenu.add(itemClose);  
		this.fileMenu.add(loadFile); /* On ajoute au bonton File l'action le sous-bouton charger */
		this.loadFile.addActionListener(new ActionListener(){ /* On ajoute au bouton load un d�tecteur de click */
			public void actionPerformed(ActionEvent arg0) 
			{
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
		this.menuBar.add(fileMenu); 
		this.setJMenuBar(menuBar);
		this.setVisible(true);
		
	}

	/**
	 * Class listening to a given button
	 */
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) 
		{
			System.out.println("Action Performed");
		}
	}
}