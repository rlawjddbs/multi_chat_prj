package kr.co.sist.chat.server.evt;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import kr.co.sist.chat.server.helper.ChatServerHelper;
import kr.co.sist.chat.server.view.ChatServerView;

/**
 *	�����带 ������ �ʰ� �ϳ��� ������� �װ��� ������ ���� �� ������? �Ͽ� ���� Ŭ���� (�̱���)
 * @author owner
 */
public class MultiThreadServer extends Thread {
	
	private ChatServerView csv;
	private ChatServerEvt cse;
	
	private List<ChatServerHelper> listClient; 
	
	private int port;
	
	public MultiThreadServer(ChatServerEvt cse, ChatServerView csv, int port){
		this.cse = cse;
		this.csv = csv;
		listClient = new ArrayList<ChatServerHelper>();
		
		this.port = port;
	}//ThreadServer1
	
	@Override
	public void run() {
		try {
			
			cse.setSs1(new ServerSocket(port));
			DefaultListModel<String>dlmTemp = csv.getDlmChatList1();
			csv.getDlmChatList1().addElement("������ "+cse.getSs1().getLocalPort()+"PORT�� ���� ���� ��...");
			
			Socket someClient = null; // ������ ������ ������ ��ü
			InetAddress ia = null; //�������� IP(Internet Protocol) address
			
			ChatServerHelper csh = null;
			
			for(int cnt = 0; ; cnt++) {
				someClient = cse.getSs1().accept();
				ia = someClient.getInetAddress();
				
				csh = new ChatServerHelper(someClient, dlmTemp, cnt, csv, listClient, csv.getJspList1()); 
				
				// ������ ���� �̸��� ���ϰ�ü�� ������ �����ϱ� ���� List�� �߰�
				listClient.add(csh);
				
				// �������� �޼����� �о��� ���� Thread ����
				csh.start();
			}//end for
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//run
	
}//class
