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
		
		//��Ʈ�� ���
		try {
			readStream = new DataInputStream(someClient.getInputStream());
			writeStream = new DataOutputStream(someClient.getOutputStream());
			
			//�����ڰ� �������� nick�� �޴´�.
			nick = readStream.readUTF();
			
			//����â�� ���� �޼��� ���
			dlm.addElement("[" + someClient.getInetAddress() + " : [" + nick +
					"] ���� "+someClient.getLocalPort()+"��Ʈ�� ä�ù濡 �����Ͽ����ϴ�.");
			//�������� ���ڷ� �Ѱܹ��� JScrollPane �� ��ũ�Ѱ��� �ִ밪(���� �Ʒ�)���� ����
			jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
			
		}catch(IOException ioe) {
			JOptionPane.showMessageDialog(jf, "������ ���� �� ���� �߻�....");
			ioe.printStackTrace();
		}//end catch
		
	}//ChatSErverHelper
	
	@Override
	public void run() {
		if(readStream != null) {
			
			try {
				String revMsg = "";
				while(true) { // �������� �������� ��� �޼����� �о, ��� �����ڿ��� �Ѹ���.
					revMsg = readStream.readUTF();
					broadcast(revMsg);
				}//end while
			}catch(IOException ioe) {
				connectList.remove( this );
				dlm.addElement(cnt + " / " + nick + " ������ ���");
				broadcast("["+nick+"]���� ����Ͽ����ϴ�.");
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
			JOptionPane.showMessageDialog(jf, "["+cnt+"]��° �����ڿ��� �޽����� ���� �� �����ϴ�.");
			ioe.printStackTrace();
		}
	}//broadcast
	
}//class
