package kr.co.sist.chat.server.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JOptionPane;

import kr.co.sist.chat.server.view.ChatServerView;

/**
 * ä�ü��� ������ȭ�鿡�� �߻��ϴ� �̺�Ʈ�� ó�����ִ� ��.
 * @author owner
 */
public class ChatServerEvt extends WindowAdapter
								implements ActionListener, Runnable {

	private ChatServerView csv;
	
	private ThreadServer1 ts1;
	private ThreadServer2 ts2;
	private ThreadServer3 ts3;
	private ThreadServer4 ts4;
	
	private ServerSocket ss1, ss2, ss3, ss4;
	
	public ChatServerEvt(ChatServerView csv) {
		this.csv = csv;
	}//ChatServerEvt

	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		//���� ���� ��ư�� Ŭ������ ��
		if( ae.getSource() == csv.getJbtOpenServer()) {
			if(threadNullCheck()) {
				ts1 = new ThreadServer1(this, csv);
				ts2 = new ThreadServer2(this, csv);
				ts3 = new ThreadServer3(this, csv);
				ts4 = new ThreadServer4(this, csv);
				threadsRun();
			} else {
				JOptionPane.showMessageDialog(csv, "�̹� �������� ������ �ֽ��ϴ�.");
			}//end else
		}//end if
		
		//���� ���� ��ư�� Ŭ������ ��
		if( ae.getSource() == csv.getJbtCloseServer()) {
			closeServer();
		}//end if
		
		
	}//actionPerformed
	
	private void threadsRun() {
		ts1.start();
		ts2.start();
		ts3.start();
		ts4.start();
	}//threadsRun
	
	private boolean threadNullCheck() {
		return (ts1 == null) && 
				(ts2 == null) && 
				(ts3 == null) && 
				(ts4 == null);
	}//threadNullCheck

	// ������ �ݴ� ��ư�� Ŭ������ �� �߻��ϴ� �̺�Ʈ�� ó���ϴ� ��
	private void closeServer() {
		switch(JOptionPane.showConfirmDialog(csv, 
				"���� ����� ��� ��Ʈ ���� ����˴ϴ�.\n"
				+ "��� �Ͻðڽ��ϱ�?")) {
		case JOptionPane.OK_OPTION:
			csv.dispose();
		}//end switch
	}//closeServer

	// �������� X ��ư�� Ŭ���Ͽ� ���α׷��� �� �� �߻��ϴ� �̺�Ʈ�� ó���ϴ� �� (���� �ݱ� ��ư�� �ϴ� �ϰ� �����)
	public void windowClosing(WindowEvent we) {
		csv.dispose();
	}//windowClosing
	
	// �����찡 ���� ���¿��� �߻��ϴ� �̺�Ʈ�� ó���ϴ� ��(������ ������ �ݴ´�.)
	public void windowClosed(WindowEvent we) {
		try {
		if(ss1 != null) { ss1.close(); }//end if
		if(ss2 != null) { ss2.close(); }//end if
		if(ss3 != null) { ss3.close(); }//end if
		if(ss4 != null) { ss4.close(); }//end if
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
	}//windowClosed
	
	
	@Override
	public void run() {
		
	}//run
	
	
	public ServerSocket getSs1() {
		return ss1;
	}//getSs1

	public void setSs1(ServerSocket ss1) {
		this.ss1 = ss1;
	}//setSs1


	public ServerSocket getSs2() {
		return ss2;
	}//getSs2


	public void setSs2(ServerSocket ss2) {
		this.ss2 = ss2;
	}//setSs2


	public ServerSocket getSs3() {
		return ss3;
	}//getSs3


	public void setSs3(ServerSocket ss3) {
		this.ss3 = ss3;
	}//setSs3


	public ServerSocket getSs4() {
		return ss4;
	}//getSs4


	public void setSs4(ServerSocket ss4) {
		this.ss4 = ss4;
	}//setSs4

	
	

	
}//class
