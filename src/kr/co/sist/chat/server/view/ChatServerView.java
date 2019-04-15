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
 *	ä�ù� ������ ȭ���� �����ϴ� ��
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
		super("ä�� ���� ������");
		
		//����Ʈ �� ����
		dlmChatList1 = new DefaultListModel<String>();
		dlmChatList2 = new DefaultListModel<String>();
		dlmChatList3 = new DefaultListModel<String>();
		dlmChatList4 = new DefaultListModel<String>();
		
		//������ ����Ʈ ���� JList�� ����
		jlChatList1 = new JList<String>(dlmChatList1);
		jlChatList2 = new JList<String>(dlmChatList2);
		jlChatList3 = new JList<String>(dlmChatList3);
		jlChatList4 = new JList<String>(dlmChatList4);

		// JScrollPane ���� �� ��ȣ�� ���� JList ���
		jspList1 = new JScrollPane(jlChatList1); 
		jspList2 = new JScrollPane(jlChatList2); 
		jspList3 = new JScrollPane(jlChatList3); 
		jspList4 = new JScrollPane(jlChatList4); 
		
		// �� ��Ʈ�� ȭ���� ���� JPanel(�׸��� ���̾ƿ�) ����
		jpViewPanel = new JPanel();
		jpViewPanel.setLayout(new GridLayout(2, 2));
		jpViewPanel.add(jspList1);
		jpViewPanel.add(jspList2);
		jpViewPanel.add(jspList3);
		jpViewPanel.add(jspList4);
		
		// ��ư ������Ʈ ����
		jbtOpenServer = new JButton("���� ����");
		jbtCloseServer = new JButton("���� ����");
		
		
		// ���� ����, ���� ��ư�� ��� ���� JPanel ����
		jpBtnPanel = new JPanel();
		jpBtnPanel.add(jbtOpenServer);
		jpBtnPanel.add(jbtCloseServer);
		
		add("Center", jpViewPanel);
		add("South", jpBtnPanel);
		
		// ����ȭ ��, �̺�Ʈ ���
		ChatServerEvt cse = new ChatServerEvt(this);
		jbtOpenServer.addActionListener(cse);
		jbtCloseServer.addActionListener(cse);
		
		// ServerView �����찡 �����ų� ��������Ǹ� ����1,2,3,4 ���� ����� �Ѵ�.
		// �����찡 ����� �������� �߻��ϴ� �̺�Ʈ ó���� windowClosed() �޼��带 ����
		// ó���ϹǷ� addWindowListener()�� �̿��� �̺�Ʈ ����� �Ѵ�.
		addWindowListener(cse);
		
		// ��ġ ����, ũ�� ���� �� ����ȭ
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
