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

public class ThreadServer4 extends Thread {
	
	private ChatServerView csv;
	private ChatServerEvt cse;
	
	private List<ChatServerHelper> listClient; 
	
	public ThreadServer4(ChatServerEvt cse, ChatServerView csv){
		this.cse = cse;
		this.csv = csv;
		listClient = new ArrayList<ChatServerHelper>();
	}//ThreadServer4
	
	@Override
	public void run() {
		try {
			cse.setSs4(new ServerSocket(30000));
			DefaultListModel<String>dlmTemp = csv.getDlmChatList4();
			csv.getDlmChatList4().addElement("������ "+cse.getSs4().getLocalPort()+"PORT�� ���� ���� ��...");
			
			Socket someClient = null; // ������ ������ ������ ��ü
			InetAddress ia = null; //�������� IP(Internet Protocol) address
			
			ChatServerHelper csh = null;
			
			for(int cnt = 0; ; cnt++) { //���ѷ���
				someClient = cse.getSs4().accept();
				ia = someClient.getInetAddress();
				
				csh = new ChatServerHelper(someClient, dlmTemp, cnt, csv, listClient, csv.getJspList4()); 
				
				// ������ ���� �̸��� ���ϰ�ü�� ������ �����ϱ� ���� List�� �߰�
				listClient.add(csh);
				
				// �������� �޼����� �о��� ���� Thread ����
				csh.start();
			}//end for
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
	}//run
	
}//class
