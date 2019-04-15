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
 *	스레드를 나누지 않고 하나의 스레드로 네개의 서버를 돌릴 순 없을까? 하여 만들어본 클래스 (미구현)
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
			csv.getDlmChatList1().addElement("서버가 "+cse.getSs1().getLocalPort()+"PORT를 열고 가동 중...");
			
			Socket someClient = null; // 접속자 소켓을 저장할 객체
			InetAddress ia = null; //접속자의 IP(Internet Protocol) address
			
			ChatServerHelper csh = null;
			
			for(int cnt = 0; ; cnt++) {
				someClient = cse.getSs1().accept();
				ia = someClient.getInetAddress();
				
				csh = new ChatServerHelper(someClient, dlmTemp, cnt, csv, listClient, csv.getJspList1()); 
				
				// 생성된 같은 이름의 소켓객체를 여러개 관리하기 위해 List에 추가
				listClient.add(csh);
				
				// 접속자의 메세지를 읽어들기 위한 Thread 시작
				csh.start();
			}//end for
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//run
	
}//class
