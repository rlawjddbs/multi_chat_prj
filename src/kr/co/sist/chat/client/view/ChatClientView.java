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

	private JTextArea jtaTalkDisplay; //채팅 내용을 기록할 JTextArea
	private JTextField jtfNick, jtfTalk; //IP입력란, 닉네임입력란, 대화입력란
	
	// 접속버튼, 캡쳐버튼, 접속자확인버튼, 채팅 클라이언트 종료 버튼
	private JButton jbtConnect, jbtCapture, jbtCheckUser, jbtClose;
	
	private JScrollPane jspTalkDisplay; // JTextArea를 감싸줄 스크롤바 생성
	
	
	public ChatClientView() {
		super("채팅 클라이언트");
		
		jtaTalkDisplay = new JTextArea();
		
		jtfNick = new JTextField(10);
		jtfTalk = new JTextField();
		jtfTalk.setBorder(new TitledBorder("대화"));
		
		jbtConnect = new JButton("접속");
		jbtCapture = new JButton("갈무리");
		jbtCheckUser = new JButton("접속자");
		jbtClose = new JButton("종료");
		
		jspTalkDisplay = new JScrollPane(jtaTalkDisplay);
		jspTalkDisplay.setBorder(new TitledBorder("대화내용"));
		
		jtaTalkDisplay.setEditable(false);
		
		JPanel jpNorth = new JPanel();
		jpNorth.add(new JLabel("대화명", JLabel.CENTER));
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
		
		//클라이언트 종료 이벤트
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
