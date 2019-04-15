package kr.co.sist.chat.client.view;

import java.awt.GridLayout;
import java.net.ServerSocket;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import kr.co.sist.chat.client.evt.ChatClientSelectServerEvt;

@SuppressWarnings("serial")
public class ChatClientSelectServerView extends JFrame {
	
	private ButtonGroup bg;
	private JRadioButton jrbt1, jrbt2, jrbt3, jrbt4;
	private JPanel serverSelectP;
	private JButton connect;
	
	private ServerSocket userSelected;
	
	public ChatClientSelectServerView() {
		super("ä�� �� ����");
		
		jrbt1 = new JRadioButton("1��", true);
		jrbt2 = new JRadioButton("2��");
		jrbt3 = new JRadioButton("3��");
		jrbt4 = new JRadioButton("4��");
		
		// ���� ��ư�� ������ ButtonGroup ����
		bg = new ButtonGroup();
		bg.add(jrbt1);
		bg.add(jrbt2);
		bg.add(jrbt3);
		bg.add(jrbt4);
		
		connect = new JButton("����");
		
		serverSelectP = new JPanel();
		serverSelectP.setLayout(new GridLayout(4, 1));
		serverSelectP.add(jrbt1);
		serverSelectP.add(jrbt2);
		serverSelectP.add(jrbt3);
		serverSelectP.add(jrbt4);
		serverSelectP.setVisible(true);
		
		add("Center", serverSelectP);
		add("South", connect);
		
		ChatClientSelectServerEvt ccsse = new ChatClientSelectServerEvt(this);
		jrbt1.addItemListener(ccsse);
		jrbt2.addItemListener(ccsse);
		jrbt3.addItemListener(ccsse);
		jrbt4.addItemListener(ccsse);
		connect.addActionListener(ccsse);
		
		
		setBounds(100, 100, 300, 400);
		setVisible(true);
		
		setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		
	}//ChatClientSelectServerView


	public JRadioButton getJrbt1() {
		return jrbt1;
	}//getJrbt1


	public JRadioButton getJrbt2() {
		return jrbt2;
	}//getJrbt2


	public JRadioButton getJrbt3() {
		return jrbt3;
	}//getJrbt3


	public JRadioButton getJrbt4() {
		return jrbt4;
	}//getJrbt4


	public JButton getConnect() {
		return connect;
	}//getConnect


	public ServerSocket getUserSelected() {
		return userSelected;
	}//ServerSocket getUserSelected
	
	public void setUserSelected(ServerSocket ss) {
		userSelected = ss;
	}//ServerSocket getUserSelected
	
	
}//class
