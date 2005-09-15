package de.andy.bookmark.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChooserPane extends JPanel {

	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JButton jButton = null;
	private JTextField jTextField1 = null;
	private JButton jButton1 = null;
	private JPanel jPanel = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
	/**
	 * This is the default constructor
	 */
	public ChooserPane() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {		
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints4.gridwidth = 2;
		gridBagConstraints4.insets = new java.awt.Insets(0,0,0,0);
		gridBagConstraints4.gridy = 3;
		gridBagConstraints4.weighty = 1.0;
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridx = 1;
		gridBagConstraints21.gridy = 2;
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints11.gridy = 2;
		gridBagConstraints11.weightx = 1.0;
		gridBagConstraints11.gridx = 0;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.gridy = 1;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints1.gridy = 1;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.gridx = 0;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.gridy = 0;
		jLabel = new JLabel();
		jLabel.setText("Please choose Bookmarkfiles");
		this.setLayout(new GridBagLayout());
		this.setSize(481, 314);
		this.add(jLabel, gridBagConstraints);
		this.add(getJTextField(), gridBagConstraints1);
		this.add(getJButton(), gridBagConstraints2);
		this.add(getJTextField1(), gridBagConstraints11);
		this.add(getJButton1(), gridBagConstraints21);
		this.add(getJPanel(), gridBagConstraints4);
	}
	
	private void addTxtLine() {
		gridBagConstraints4.gridy++;
//		this.add(getJTextField(), gridBagConstraints1);
//		this.add(getJButton(), gridBagConstraints2);
		invalidate();
	}
	
	private void removeTxtLine() {
		gridBagConstraints4.gridy--;
		invalidate();
	}
	
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
		}
		return jTextField;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("File...");
		}
		return jButton;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
		}
		return jTextField1;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("File...");
		}
		return jButton1;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton3(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("more");
			jButton2.setEnabled(false);
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("less");
			jButton3.setEnabled(false);
		}
		return jButton3;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
