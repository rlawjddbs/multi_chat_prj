package kr.co.sist.chat.server.helper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class ChatServerHelper extends Thread {

	private Socket someClient;
	private DataInputStream readStream;
	private DataOutputStream writeStream;
	private DefaultListModel<String> dlm;
	private int cnt;
	private List<ChatServerHelper> connectList;
	private JFrame jf;
	private JScrollPane jsp;
	private String nick;
	
	public ChatServerHelper(Socket socket, 
									DefaultListModel<String> dlm, 
									int cnt, 
									JFrame jf,
									List<ChatServerHelper> list,
									JScrollPane jsp) {
		someClient = socket;
		this.dlm = dlm;
		this.cnt = cnt;
		this.jf = jf;
		connectList = list;
		this.jsp = jsp;
		
		//스트림 얻기
		try {
			readStream = new DataInputStream(someClient.getInputStream());
			writeStream = new DataOutputStream(someClient.getOutputStream());
			
			//접속자가 보내오는 nick을 받는다.
			nick = readStream.readUTF();
			
			//서버창에 접속 메세지 출력
			dlm.addElement("[" + someClient.getInetAddress() + " : [" + nick +
					"] 님이 "+someClient.getLocalPort()+"포트로 채팅방에 접속하였습니다.");
			//생성자의 인자로 넘겨받은 JScrollPane 의 스크롤값을 최대값(제일 아래)으로 설정
			jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
			
		}catch(IOException ioe) {
			JOptionPane.showMessageDialog(jf, "접속자 연결 중 문제 발생....");
			ioe.printStackTrace();
		}//end catch
		
	}//ChatSErverHelper
	
	@Override
	public void run() {
		if(readStream != null) {
			
			try {
				String revMsg = "";
				while(true) { // 서버에서 보내오는 모든 메세지를 읽어서, 모든 접속자에게 뿌린다.
					revMsg = readStream.readUTF();
					broadcast(revMsg);
				}//end while
			}catch(IOException ioe) {
				connectList.remove( this );
				dlm.addElement(cnt + " / " + nick + " 접속자 퇴실");
				broadcast("["+nick+"]님이 퇴실하였습니다.");
//				ioe.printStackTrace();
			}//end catch
			
			
		}//end if
	}//run
	
	public synchronized void broadcast(String msg) {
		ChatServerHelper csh = null;
		try {
			for(int i=0; i < connectList.size(); i++) {
				csh = connectList.get(i);
				csh.writeStream.writeUTF(msg);
				csh.writeStream.flush();
			}//end for
		} catch(IOException ioe) {
			JOptionPane.showMessageDialog(jf, "["+cnt+"]번째 접속자에게 메시지를 보낼 수 없습니다.");
			ioe.printStackTrace();
		}
	}//broadcast
	
}//class
