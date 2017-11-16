package com.ushrark;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JEditorPane;
import javax.swing.JSplitPane;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

public class MainWindow {

	private JFrame frame;
	Client cl;
	private JTextField txtUsername;
	private JTextField txtIpaddress;
	private JTextField txtPort;
	/**
	 * Create the application.
	 */
	public MainWindow() 
	{
		initialize();
		
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 457, 354);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JDesktopPane desktopPane = new JDesktopPane();
		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);
	
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(0, 230, 346, 95);
		desktopPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea(5, 30);
		scrollPane.setViewportView(textArea);
		textArea.setEditable(true);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 32, 451, 199);
		desktopPane.add(scrollPane_1);
		
		JTextArea txtrDDD = new JTextArea();
		txtrDDD.setText("Hello World!");
		txtrDDD.setEditable(false);
		scrollPane_1.setViewportView(txtrDDD);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 451, 32);
		desktopPane.add(menuBar);
		
		JMenu mnChat = new JMenu("Chat");
		menuBar.add(mnChat);
				
		txtUsername = new JTextField();
		txtUsername.setText("Username");
		menuBar.add(txtUsername);
		txtUsername.setColumns(10);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String name = txtUsername.getText();
				cl = new Client(txtIpaddress.getText(), Integer.parseInt(txtPort.getText()), txtrDDD, name, mnChat);
			}
		});
		
		txtIpaddress = new JTextField();
		txtIpaddress.setText("localhost");
		menuBar.add(txtIpaddress);
		txtIpaddress.setColumns(10);
		
		txtPort = new JTextField();
		txtPort.setText("1111");
		menuBar.add(txtPort);
		txtPort.setColumns(10);
		menuBar.add(btnConnect);
		

		JButton btnNewButton = new JButton("Send");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendButtonEvent(txtrDDD, textArea);
			}
		});
		
		btnNewButton.setBounds(345, 230, 106, 95);
		desktopPane.add(btnNewButton);
		
	}
	
	public void sendButtonEvent(JTextArea removeFrom, JTextArea sendTo)
	{
		String text = sendTo.getText();
		sendTo.setText("");
		if(!text.equals(""))
		{
			cl.sendMessage(text);
		}
		
	}
}
