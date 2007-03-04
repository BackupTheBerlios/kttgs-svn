/**
 * 
 */
package mtg.knottytom.gui.config;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author knottytom
 *
 */
public class MainGui {
    
    
	private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        // JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("knottyTom's Tour Guide System Configurator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel(new GridLayout(5,1));
        
        panel.add(new JLabel("Select a language from the list. This language will be used for texts in the tour description."));
        
        JPanel panelLang = new JPanel(new GridLayout(1,2));
        JLabel langLabel = new JLabel("Language:");
        String[] names = {"English", "German"};
        JComboBox langNames = new JComboBox(names);
        langNames.setSelectedIndex(0);
        panelLang.add(langLabel);
        panelLang.add(langNames);
        panel.add(panelLang);
        
        panel.add(new JLabel("Enter a name for the folder the tour will be stored in. Valid characters are: a-z A-Z 0-9"));
        
        JPanel panelTour = new JPanel(new GridLayout(1,2));
        JLabel tourLabel = new JLabel("Tour Folder Name:");
        JTextField  tourName = new JTextField();
        panelTour.add(tourLabel);
        panelTour.add(tourName);
        panel.add(panelTour);
        
        JPanel panelButtons = new JPanel(new GridLayout(1,2));
        panelButtons.add(new JButton("OK"));
        panelButtons.add(new JButton("Cancel"));
        panel.add(panelButtons);
        
        
        frame.getContentPane().add(panel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.setSize(600, 150);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    } 
}
