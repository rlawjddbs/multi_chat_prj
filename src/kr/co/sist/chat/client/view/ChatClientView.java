package kr.co.sist.chat.client.view;

import java.net.ServerSocket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.co.sist.chat.client.evt.ChatClientSelectServerEvt;

@SuppressWarnings("serial")
public class ChatClientView extends JFrame {

	private JTextArea jtaTalkDisplay; //ä�� ������ ����� JTextArea
	private JTextField jtfNick, jtfTalk; //IP�Է¶�, �г����Է¶�, ��ȭ�Է¶�
	
	// ���ӹ�ư, ĸ�Ĺ�ư, ������Ȯ�ι�ư, ä�� Ŭ���̾�Ʈ ���� ��ư
	private JButton jbtConnect, jbtCapture, jbtCheckUser, jbtClose;
	
	private JScrollPane jspTalkDisplay; // JTextArea�� ������ ��ũ�ѹ� ����
	
	
	public ChatClientView() {
		super("ä�� Ŭ���̾�Ʈ");
		
		jtaTalkDisplay = new JTextArea();
		
		jtfNick = new JTextField(10);
		jtfTalk = new JTextField();
		jtfTalk.setBorder(new TitledBorder("��ȭ"));
		
		jbtConnect = new JButton("����");
		jbtCapture = new JButton("������");
		jbtCheckUser = new JButton("������");
		jbtClose = new JButton("����");
		
		jspTalkDisplay = new JScrollPane(jtaTalkDisplay);
		jspTalkDisplay.setBorder(new TitledBorder("��ȭ����"));
		
		jtaTalkDisplay.setEditable(false);
		
		JPanel jpNorth = new JPanel();
		jpNorth.add(new JLabel("��ȭ��", JLabel.CENTER));
		jpNorth.add(jtfNick);
		
		jpNorth.add(jbtConnect);
		jpNorth.add(jbtCapture);
		jpNorth.add(jbtCheckUser);
		jpNorth.add(jbtClose);
		
		add("North", jpNorth);
		add("Center", jspTalkDisplay);
		add("South", jtfTalk);
		
		ChatClientSelectServerEvt ccsse = new ChatClientSelectServerEvt(this);
		jbtConnect.addActionListener(ccsse);
		jbtCapture.addActionListener(ccsse);
		jbtCheckUser.addActionListener(ccsse);
		jbtClose.addActionListener(ccsse);
		jtfTalk.addActionListener(ccsse);
		
		//Ŭ���̾�Ʈ ���� �̺�Ʈ
		addWindowListener(ccsse);
		
		setBounds(100, 100, 700, 350);
		setVisible(true);
		
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}//ChatClientView()


	public JTextField getJtfTalk() {
		return jtfTalk;
	}//getJtfTalk

	
	public JTextField getJtfNick() {
		return jtfNick;
	}//getJtfNick
	

	public JButton getJbtConnect() {
		return jbtConnect;
	}//getJbtConnect


	public JButton getJbtCapture() {
		return jbtCapture;
	}//getJbtCapture


	public JButton getJbtCheckUser() {
		return jbtCheckUser;
	}//getJbtCheckUser


	public JButton getJbtClose() {
		return jbtClose;
	}//getJbtClose


	public JTextArea getJtaTalkDisplay() {
		return jtaTalkDisplay;
	}//getJtaTalkDisplay


	

	
	
}//class
