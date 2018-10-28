package edu.cmu.cs.cs214.hw4.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;

public class NumPlayersWindow extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private ScrabbleGame g;
	private static final String TITLE = "Number of Players";

	public NumPlayersWindow(ScrabbleGame g) {
		this.g = g;
		// Create the window and its components.
		setUp();
	}

	private void setUp() {

		// Set the title of the window
		setTitle(TITLE);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Create TOP panel
		JPanel topPanel = new JPanel();
		JLabel message = new JLabel(
		"Enter number of players.\nShould be between 2 and 4 (both inclusive)");
		topPanel.setVisible(true);
		topPanel.add(message);

		// Create MIDDLE panel
		JPanel middlePanel = new JPanel();

		// Create the components of MIDDLE panel
		final JTextField num = new JTextField(10);
		JButton button = new JButton("Enter");
		middlePanel.add(num);
		middlePanel.add(button);
		middlePanel.setVisible(true);

		// Create the LOWER panel
		JPanel lowerPanel = new JPanel();
		final JTextArea errorMessage = new JTextArea();
		errorMessage.setForeground(Color.RED);

		lowerPanel.add(errorMessage);
		lowerPanel.setVisible(true);

		// Add ActionListener to the JButton
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// STEP 1: Get the number from textfield

				String s = num.getText();
				String error = null;
				int n = 0;
				try {
					n = Integer.parseInt(s);
				} catch (NumberFormatException e1) {
					error = e1.getMessage();
				} finally {
					if (error != null) {
						errorMessage.setText(
						"Please input a number.");
						return;
					}

				}

				// STEP 2: Pass to g
				error = null;
				try {
					g.setNumPlayers(n);
				} catch (IllegalArgumentException ex) {
					error = ex.getMessage();
				} finally {
					if (error == null) {
						// implies valid input
						errorMessage.setText(null);
						move2NextWindow();
					} else {
						errorMessage.setText(error);
					}
				}
			}
		});
		// Add panels to frame
		setSize(100, 100);
		add(topPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(lowerPanel, BorderLayout.SOUTH);

		pack();
		setResizable(false);
		setVisible(true);
	}

	private void move2NextWindow() {
		new PlayerNamesWindow(g, this);
	}
}
