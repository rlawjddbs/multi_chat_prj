package kr.co.sist.chat.client.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import kr.co.sist.chat.client.view.ChatClientSelectServerView;
import kr.co.sist.chat.client.view.ChatClientView;

public class ChatClientSelectServerEvt extends WindowAdapter implements ItemListener, ActionListener, Runnable {

	private ChatClientSelectServerView ccssv;
	private ChatClientView ccv;

	static private int serverPort = 15000;
	private Socket client;

	private Thread threadMsg;

	private String nick;
	private DataInputStream readStream;
	private DataOutputStream writeStream;

	public ChatClientSelectServerEvt(ChatClientSelectServerView ccssv) {
		this.ccssv = ccssv;
	}// ChatClientSelectServerEvt

	public ChatClientSelectServerEvt(ChatClientView ccv) {
		this.ccv = ccv;
	}// ChatClientSelectServerEvt

	@Override
	public void itemStateChanged(ItemEvent ie) {
		/*
		 * ItemEvent는 getStateChange()라는 메서드를 가지고 있습니다. 상태변화를 가져오는데 그 값은 단지 1과 2이죠. 선택은
		 * 1, 선택해제는 2죠. 이것을 굳이 숫자로 검사하기 보단 ItemEvent에 미리 선언되어있는 ItemEvent.SELECTED와
		 * ItemEvent.DESELECTED로 알아보기 쉽게 비교할 수 있을 것입니다.
		 */
		if (ie.getStateChange() == 1) {
			if (ie.getItem() == ccssv.getJrbt1()) {
				serverPort = 15000;
			} // end if
			if (ie.getItem() == ccssv.getJrbt2()) {
				serverPort = 20000;
			} // end if
			if (ie.getItem() == ccssv.getJrbt3()) {
				serverPort = 25000;
			} // end if
			if (ie.getItem() == ccssv.getJrbt4()) {
				serverPort = 30000;
			} // end if
			System.out.println(serverPort);
		} // end if
	}// itemStateChanged

	@Override
	// 접속 버튼을 클릭할 경우 발생하는 이벤트를 처리하는 일
	public void actionPerformed(ActionEvent ae) {

		// ccssv에서 접속 버튼을 클릭했을 때
		if (ccssv != null && ae.getSource() == ccssv.getConnect()) {

			// 도중에 다른 라디오버튼이 선택되지 않게 막는다.
			ccssv.getJrbt1().setEnabled(false);
			ccssv.getJrbt2().setEnabled(false);
			ccssv.getJrbt3().setEnabled(false);
			ccssv.getJrbt4().setEnabled(false);

			ccssv.dispose();
			new ChatClientView();

		} // end if

		// ccv에서 접속 버튼을 클릭했을 때
		if (ccv != null && ae.getSource() == ccv.getJbtConnect()) {
			connectToServer();
		} // end if

		// ccv에서 채팅내용 입력후 엔터를 쳤을 때
		if (ccv != null && ae.getSource() == ccv.getJtfTalk()) {
			try {
				sendMsg();
			} catch (IOException e) {
				e.printStackTrace();
			}//end catch
		} // end if
		
		if (ccv != null && ae.getSource() == ccv.getJbtCapture()) {
			try {
				capture();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//end if

		if (ccv != null && ae.getSource() == ccv.getJbtClose()) {
			ccv.dispose();
		}//end if
		
		if ( ccv != null && ae.getSource() == ccv.getJbtCheckUser() ) {
			checkUsers();
		}//end if
		
		
	}// actionPerformed

	@Override
	public void run() {
		if (readStream != null) {
			try {

				String revMsg = "";

				while (true) {
					revMsg = readStream.readUTF();
					if(revMsg.equals("123")) {continue;}
					ccv.getJtaTalkDisplay().append(revMsg + "\n");
				} // end while

			} catch (IOException ie) {
				JOptionPane.showMessageDialog(ccv, "서버가 종료 되었습니다.");
				ie.printStackTrace();
			} // end catch
		} // end if
	}// run

	public void connectToServer() {
		if (client == null) {
			// 사용자가 입력한 대화명을 nick 변수에 할당한다.
			nick = ccv.getJtfNick().getText().trim();
			// 대화명이 비어있을 경우 경고창
			if (nick.equals("")) {
				JOptionPane.showMessageDialog(ccv, "대화명을 입력해주세요.");
				ccv.getJtfNick().requestFocus();
				return;
			} // end if

			// 서버 아이피 주소 지정
			String serverIp = "211.63.89.153";
			try {

				client = new Socket(serverIp, serverPort); // 지정한 ip address의 컴퓨터에 연결
				// 연결된 컴퓨터로부터 IO 스트림 생성
				readStream = new DataInputStream(client.getInputStream());
				writeStream = new DataOutputStream(client.getOutputStream());

				writeStream.writeUTF(nick);
				writeStream.flush();

				// 접속 알림을 client view J.T.A 에 출력
				ccv.getJtaTalkDisplay().setText("[" + nick + "]님이 서버에 접속하였습니다.\n즐거운 채팅되세요~\n");

				// 메세지 읽기
				threadMsg = new Thread(this);

				threadMsg.start();

			} catch (UnknownHostException uhe) {
				JOptionPane.showMessageDialog(ccv, "서버를 알 수 없습니다.");
				uhe.printStackTrace();
			} catch (IOException ie) {
				JOptionPane.showMessageDialog(ccv, "연결 실패.");
				ie.printStackTrace();
				System.out.println(serverIp + " / " + serverPort);
			} // end catch

		} else {
			JOptionPane.showMessageDialog(ccv, client.getInetAddress().getHostAddress() + "이미 서버에 접속 중 입니다.");
		} // end else
	}// connectToServer

	@Override
	public void windowClosing(WindowEvent we) {
		ccv.dispose();
	}// windowClosing

	@Override
	public void windowClosed(WindowEvent we) {
		if (readStream != null) {
			try {

				if (readStream != null) {	readStream.close(); } // end if
				if (writeStream != null) { writeStream.close(); } // end if
				if (client != null) {	client.close(); } // end if

			} catch (IOException ie) {
				
			} finally {
				System.exit(0);
			}//end finally
		} // end if
	}// windowClosed

	public void sendMsg() throws IOException {
		if (writeStream != null) {
			JTextField jtf = ccv.getJtfTalk();
			// JTF에 입력한 메세지를 읽어 들인다.
			String msg = jtf.getText().trim();
			if (!msg.isEmpty()) {
				// 스트림에 쓰고
				writeStream.writeUTF("[ " + nick + " ] " + msg);
				// 목적지로 분출
				writeStream.flush();
			} // end if
			jtf.setText("");
		} // end if
	}// sendMsg
	
	public void capture() throws IOException {
		switch (JOptionPane.showConfirmDialog(ccv, "대화 내용을 저장하시겠습니까?")) {
		case JOptionPane.OK_OPTION:
			// 스트림을 사용하여 저장
			File saveDir = new File("c:/dev/chat");
			saveDir.mkdirs();
			File saveFile = new File(saveDir.getAbsolutePath() + "/java_chat[" + System.currentTimeMillis() + "].dat");

			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(saveFile));
				bw.write(ccv.getJtaTalkDisplay().getText()); // 스트림에 대화내용 기록
				bw.flush(); // 스트림에 기록된 내용을 분출
				JOptionPane.showMessageDialog(ccv, saveDir + "에 대화 내용이 기록되었습니다.");
			} finally {
				if (bw != null) {
					bw.close();
				} // end if
			} // end finally
		} // end switch
	} // capture
	
	public void checkUsers() {
		try {
			writeStream.writeUTF("123");
			writeStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//checkUsers
	
	
}// class
