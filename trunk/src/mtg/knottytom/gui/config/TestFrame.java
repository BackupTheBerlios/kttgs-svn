package mtg.knottytom.gui.config;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;

public class TestFrame extends JFrame {

	private JPanel jContentPane = null;
	private JPanel descLanguage = null;
	private JPanel panelLanguage = null;
	private JPanel descProjectName = null;
	private JPanel panelProjectName = null;
	private JLabel helpLanguage = null;
	private JLabel jLabel = null;
	private JLabel selLangLabel = null;
	private JPanel panelButtons = null;
	private JButton OK = null;
	private JButton Cancel = null;
	private JList listLangs = null;
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (descLanguage == null) {
			helpLanguage = new JLabel();
			helpLanguage.setText("Help for Language");
			descLanguage = new JPanel();
			descLanguage.add(helpLanguage, null);
		}
		return descLanguage;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (panelLanguage == null) {
			GridLayout gridLayout = new GridLayout(2, 1);
			gridLayout.setColumns(2);
			gridLayout.setRows(1);
			selLangLabel = new JLabel();
			selLangLabel.setText("Select Language for Project");
			panelLanguage = new JPanel();
			panelLanguage.setLayout(gridLayout);
			panelLanguage.add(selLangLabel, null);
			panelLanguage.add(getListLangs(), null);
			
		}
		return panelLanguage;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (descProjectName == null) {
			jLabel = new JLabel();
			jLabel.setText("Help For Project Name");
			descProjectName = new JPanel();
			descProjectName.add(jLabel, null);
		}
		return descProjectName;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (panelProjectName == null) {
			panelProjectName = new JPanel();
		}
		return panelProjectName;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.add(getOK(), null);
			panelButtons.add(getCancel(), null);
		}
		return panelButtons;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOK() {
		if (OK == null) {
			OK = new JButton();
			OK.setText("OK");
		}
		return OK;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancel() {
		if (Cancel == null) {
			Cancel = new JButton();
			Cancel.setText("Cancel");
		}
		return Cancel;
	}

	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getListLangs() {
		if (listLangs == null) {
			listLangs = new JList();
			listLangs.add("English", null);
			listLangs.add("German", null);
		}
		return listLangs;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * This is the default constructor
	 */
	public TestFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(500, 300);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.addWindowListener(new java.awt.event.WindowAdapter() {   
			public void windowOpened(java.awt.event.WindowEvent e) {    
				System.out.println("windowOpened()"); // TODO Auto-generated Event stub windowOpened()
			}
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.out.println("windowClosing()"); // TODO Auto-generated Event stub windowClosing()
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridLayout gridLayout = new GridLayout(1, 5);
			gridLayout.setRows(5);
			gridLayout.setColumns(1);
			jContentPane = new JPanel();
			jContentPane.setLayout(gridLayout);
			jContentPane.add(getJPanel());
			jContentPane.add(getJPanel1());
			jContentPane.add(getJPanel2());
			jContentPane.add(getJPanel3());
			jContentPane.add(getJPanel4(), null);
		}
		return jContentPane;
	}

}
