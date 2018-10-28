package edu.cmu.cs.cs214.hw4.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;

public class PlayerNamesWindow extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private ScrabbleGame g;
	private ArrayList<String> names;
	private static final String TITLE = "Player Names";

	public PlayerNamesWindow(ScrabbleGame g, JFrame parentFrame) {
		this.g = g;
		parentFrame.dispose();
		names = new ArrayList<>();
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
		"Enter each Player name and hit ENTER.");
		topPanel.setVisible(true);
		topPanel.add(message);

		// Create MIDDLE panel
		JPanel middlePanel = new JPanel();

		// Create the components of MIDDLE panel
		final JTextField name = new JTextField(10);
		JButton button = new JButton("Enter");

		middlePanel.add(name);
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

				String s = name.getText();

				// STEP 2: Add player name to names.
				names.add(s);
				name.setText(null);

				if (names.size() == g.getNumPlayers()) {
					g.setPlayers(names);
					move2NextWindow();
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
		new GameSubject(g, this);
	}
}
