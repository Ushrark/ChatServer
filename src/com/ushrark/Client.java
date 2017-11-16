package com.ushrark;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.AbstractButton;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

public class Client 
{
	DataOutputStream out;
	BufferedReader in;
	String serverIp;
	JMenu mnChat;
	int port;
	String userName;
	Boolean flag = true;
	
	public Client(String ip, int port, JTextArea removeFrom, String userName, JMenu mnChat)
	{		
		this.serverIp = ip;
		this.port = port;
		this.userName = userName;
		this.mnChat = mnChat;
		try 
		{
			Socket socket = new Socket(this.serverIp, this.port);
			out = new DataOutputStream(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ServerInput wt = new ServerInput(socket, removeFrom, mnChat);
			Thread thr = new Thread(wt);
			thr.start();
			sendMessage("");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message)
	{
		try
		{
			out.writeUTF(userName + "@"+message);
			getDest();
		} catch(IOException e)
		{
		}
	}
	
	private String getDest()
	{
		String ans = "";
		Component[] rbs = mnChat.getComponents();
		System.out.println(((JTextComponent) rbs[0]).getText());
		
		return ans;
	}
}

class ServerInput implements Runnable
{
	Socket socket;
	JTextArea removeFrom;
	JMenu mnChat;
	public ServerInput(Socket socket,JTextArea removeFrom, JMenu mnChat) 
	{
		this.removeFrom = removeFrom;
		this.socket = socket;
		this.mnChat = mnChat;

	}
	
	public void run()
	{
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true)
			{
				String text = in.readLine();
				String[] str = getName(text);
				if(!str[0].equals("Server"))
				{
					removeFrom.setText(removeFrom.getText()+("\n" + str[0] +": "+ str[1]));
				}
				else
				{
					chatUpdate(text);
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	//If the incoming message looks like this
	//Server@@firstClient@@SecondClient@@ThirdClient@
	//add all those to the list of chat members in the chat tab
	private void chatUpdate(String text)
	{
		String[] str = getName(text);
		if(str[0].equals("Server") && String.valueOf(str[1].charAt(0)).equals("@"))
		{
			int first = 0;
			int second = 0;
			boolean flag = true;
			mnChat.removeAll();
			
			for(int i = 0; i < str[1].length();i++)
			{	
				if(String.valueOf(str[1].charAt(i)).equals("@") && !flag)
				{
					second = i;
					flag = true;
					JRadioButtonMenuItem rdbtnmntmPerson = new JRadioButtonMenuItem(str[1].substring(first+1, second));
					mnChat.add(rdbtnmntmPerson);
				}
				else if(String.valueOf(str[1].charAt(i)).equals("@"))
				{
					first = i;
					flag = false;
				}
				
			}
		}
	}
	
	private String[] getName(String text)
	{
		//Returns the name and the text without the name
		String ans[] = {"", ""};
		boolean flag = true;
		for(int i = 0; i < text.length(); i++)
		{
			if(flag && !String.valueOf(text.charAt(i)).equals("@"))
			{
				ans[0] += String.valueOf(text.charAt(i));
			}
			else
			{
				if(flag)
				{
					flag = false;
					i++;
				}
				ans[1] += String.valueOf(text.charAt(i));
			}
		}
		return ans;
	}
}