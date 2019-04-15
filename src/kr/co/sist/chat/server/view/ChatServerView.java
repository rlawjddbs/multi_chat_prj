package kr.co.sist.chat.server.view;

import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import kr.co.sist.chat.server.evt.ChatServerEvt;

/**
 *	채팅방 관리자 화면을 구성하는 일
 * @author owner
 */
@SuppressWarnings("serial")
public class ChatServerView extends JFrame{

	private JList<String> jlChatList1, jlChatList2, jlChatList3, jlChatList4;
	private JScrollPane jspList1, jspList2, jspList3, jspList4;
	private JButton jbtOpenServer, jbtCloseServer;
	private JPanel jpBtnPanel, jpViewPanel;
	
	private DefaultListModel<String> dlmChatList1, dlmChatList2, dlmChatList3, dlmChatList4;
	
	public ChatServerView() {
		super("채팅 서버 관리자");
		
		//리스트 모델 생성
		dlmChatList1 = new DefaultListModel<String>();
		dlmChatList2 = new DefaultListModel<String>();
		dlmChatList3 = new DefaultListModel<String>();
		dlmChatList4 = new DefaultListModel<String>();
		
		//생성된 리스트 모델을 JList에 삽입
		jlChatList1 = new JList<String>(dlmChatList1);
		jlChatList2 = new JList<String>(dlmChatList2);
		jlChatList3 = new JList<String>(dlmChatList3);
		jlChatList4 = new JList<String>(dlmChatList4);

		// JScrollPane 생성 및 번호에 맞춰 JList 담기
		jspList1 = new JScrollPane(jlChatList1); 
		jspList2 = new JScrollPane(jlChatList2); 
		jspList3 = new JScrollPane(jlChatList3); 
		jspList4 = new JScrollPane(jlChatList4); 
		
		// 각 포트별 화면을 담을 JPanel(그리드 레이아웃) 생성
		jpViewPanel = new JPanel();
		jpViewPanel.setLayout(new GridLayout(2, 2));
		jpViewPanel.add(jspList1);
		jpViewPanel.add(jspList2);
		jpViewPanel.add(jspList3);
		jpViewPanel.add(jspList4);
		
		// 버튼 컴포넌트 생성
		jbtOpenServer = new JButton("서버 가동");
		jbtCloseServer = new JButton("서버 종료");
		
		
		// 서버 가동, 종료 버튼을 담기 위한 JPanel 생성
		jpBtnPanel = new JPanel();
		jpBtnPanel.add(jbtOpenServer);
		jpBtnPanel.add(jbtCloseServer);
		
		add("Center", jpViewPanel);
		add("South", jpBtnPanel);
		
		// 가시화 전, 이벤트 등록
		ChatServerEvt cse = new ChatServerEvt(this);
		jbtOpenServer.addActionListener(cse);
		jbtCloseServer.addActionListener(cse);
		
		// ServerView 윈도우가 닫히거나 강제종료되면 서버1,2,3,4 또한 끊어야 한다.
		// 윈도우가 종료된 시점에서 발생하는 이벤트 처리는 windowClosed() 메서드를 통해
		// 처리하므로 addWindowListener()를 이용해 이벤트 등록을 한다.
		addWindowListener(cse);
		
		// 위치 선정, 크기 설정 및 가시화
		setBounds(300, 10, 600, 1000);
		setVisible(true);
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}//ChatServerView

	public JList<String> getJlChatList1() {
		return jlChatList1;
	}//getJlChatList1

	public JList<String> getJlChatList2() {
		return jlChatList2;
	}//getJlChatList2

	public JList<String> getJlChatList3() {
		return jlChatList3;
	}//getJlChatList3

	public JList<String> getJlChatList4() {
		return jlChatList4;
	}//getJlChatList4

	public JScrollPane getJspList1() {
		return jspList1;
	}//getJspList1

	public JScrollPane getJspList2() {
		return jspList2;
	}//getJspList2

	public JScrollPane getJspList3() {
		return jspList3;
	}//getJspList3

	public JScrollPane getJspList4() {
		return jspList4;
	}//getJspList4

	public JButton getJbtOpenServer() {
		return jbtOpenServer;
	}//getJbtOpenServer

	public JButton getJbtCloseServer() {
		return jbtCloseServer;
	}//getJbtCloseServer

	public DefaultListModel<String> getDlmChatList1() {
		return dlmChatList1;
	}//getDlmChatList1

	public DefaultListModel<String> getDlmChatList2() {
		return dlmChatList2;
	}//getDlmChatList2

	public DefaultListModel<String> getDlmChatList3() {
		return dlmChatList3;
	}//getDlmChatList3

	public DefaultListModel<String> getDlmChatList4() {
		return dlmChatList4;
	}//getDlmChatList4
	
	
}//class
