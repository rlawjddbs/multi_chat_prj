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

public class ThreadServer3 extends Thread {
	
	private ChatServerView csv;
	private ChatServerEvt cse;
	
	private List<ChatServerHelper> listClient; 
	
	public ThreadServer3(ChatServerEvt cse, ChatServerView csv){
		this.cse = cse;
		this.csv = csv;
		listClient = new ArrayList<ChatServerHelper>();
	}//ThreadServer3
	
	@Override
	public void run() {
		try {
			cse.setSs3(new ServerSocket(25000));
			DefaultListModel<String>dlmTemp = csv.getDlmChatList3();
			csv.getDlmChatList3().addElement("서버가 "+cse.getSs3().getLocalPort()+"PORT를 열고 가동 중...");
			
			Socket someClient = null; // 접속자 소켓을 저장할 객체
			InetAddress ia = null; //접속자의 IP(Internet Protocol) address
			
			ChatServerHelper csh = null;
			
			for(int cnt = 0; ; cnt++) { //무한루프
				someClient = cse.getSs3().accept();
				ia = someClient.getInetAddress();
				
				csh = new ChatServerHelper(someClient, dlmTemp, cnt, csv, listClient, csv.getJspList3()); 
				
				// 생성된 같은 이름의 소켓객체를 여러개 관리하기 위해 List에 추가
				listClient.add(csh);
				
				// 접속자의 메세지를 읽어들기 위한 Thread 시작
				csh.start();
			}//end for
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
	}//run
	
}//class
