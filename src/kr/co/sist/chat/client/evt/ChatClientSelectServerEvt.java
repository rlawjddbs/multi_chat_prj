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
		 * ItemEvent�� getStateChange()��� �޼��带 ������ �ֽ��ϴ�. ���º�ȭ�� �������µ� �� ���� ���� 1�� 2����. ������
		 * 1, ���������� 2��. �̰��� ���� ���ڷ� �˻��ϱ� ���� ItemEvent�� �̸� ����Ǿ��ִ� ItemEvent.SELECTED��
		 * ItemEvent.DESELECTED�� �˾ƺ��� ���� ���� �� ���� ���Դϴ�.
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
	// ���� ��ư�� Ŭ���� ��� �߻��ϴ� �̺�Ʈ�� ó���ϴ� ��
	public void actionPerformed(ActionEvent ae) {

		// ccssv���� ���� ��ư�� Ŭ������ ��
		if (ccssv != null && ae.getSource() == ccssv.getConnect()) {

			// ���߿� �ٸ� ������ư�� ���õ��� �ʰ� ���´�.
			ccssv.getJrbt1().setEnabled(false);
			ccssv.getJrbt2().setEnabled(false);
			ccssv.getJrbt3().setEnabled(false);
			ccssv.getJrbt4().setEnabled(false);

			ccssv.dispose();
			new ChatClientView();

		} // end if

		// ccv���� ���� ��ư�� Ŭ������ ��
		if (ccv != null && ae.getSource() == ccv.getJbtConnect()) {
			connectToServer();
		} // end if

		// ccv���� ä�ó��� �Է��� ���͸� ���� ��
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
				JOptionPane.showMessageDialog(ccv, "������ ���� �Ǿ����ϴ�.");
				ie.printStackTrace();
			} // end catch
		} // end if
	}// run

	public void connectToServer() {
		if (client == null) {
			// ����ڰ� �Է��� ��ȭ���� nick ������ �Ҵ��Ѵ�.
			nick = ccv.getJtfNick().getText().trim();
			// ��ȭ���� ������� ��� ���â
			if (nick.equals("")) {
				JOptionPane.showMessageDialog(ccv, "��ȭ���� �Է����ּ���.");
				ccv.getJtfNick().requestFocus();
				return;
			} // end if

			// ���� ������ �ּ� ����
			String serverIp = "211.63.89.153";
			try {

				client = new Socket(serverIp, serverPort); // ������ ip address�� ��ǻ�Ϳ� ����
				// ����� ��ǻ�ͷκ��� IO ��Ʈ�� ����
				readStream = new DataInputStream(client.getInputStream());
				writeStream = new DataOutputStream(client.getOutputStream());

				writeStream.writeUTF(nick);
				writeStream.flush();

				// ���� �˸��� client view J.T.A �� ���
				ccv.getJtaTalkDisplay().setText("[" + nick + "]���� ������ �����Ͽ����ϴ�.\n��ſ� ä�õǼ���~\n");

				// �޼��� �б�
				threadMsg = new Thread(this);

				threadMsg.start();

			} catch (UnknownHostException uhe) {
				JOptionPane.showMessageDialog(ccv, "������ �� �� �����ϴ�.");
				uhe.printStackTrace();
			} catch (IOException ie) {
				JOptionPane.showMessageDialog(ccv, "���� ����.");
				ie.printStackTrace();
				System.out.println(serverIp + " / " + serverPort);
			} // end catch

		} else {
			JOptionPane.showMessageDialog(ccv, client.getInetAddress().getHostAddress() + "�̹� ������ ���� �� �Դϴ�.");
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
			// JTF�� �Է��� �޼����� �о� ���δ�.
			String msg = jtf.getText().trim();
			if (!msg.isEmpty()) {
				// ��Ʈ���� ����
				writeStream.writeUTF("[ " + nick + " ] " + msg);
				// �������� ����
				writeStream.flush();
			} // end if
			jtf.setText("");
		} // end if
	}// sendMsg
	
	public void capture() throws IOException {
		switch (JOptionPane.showConfirmDialog(ccv, "��ȭ ������ �����Ͻðڽ��ϱ�?")) {
		case JOptionPane.OK_OPTION:
			// ��Ʈ���� ����Ͽ� ����
			File saveDir = new File("c:/dev/chat");
			saveDir.mkdirs();
			File saveFile = new File(saveDir.getAbsolutePath() + "/java_chat[" + System.currentTimeMillis() + "].dat");

			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(saveFile));
				bw.write(ccv.getJtaTalkDisplay().getText()); // ��Ʈ���� ��ȭ���� ���
				bw.flush(); // ��Ʈ���� ��ϵ� ������ ����
				JOptionPane.showMessageDialog(ccv, saveDir + "�� ��ȭ ������ ��ϵǾ����ϴ�.");
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
