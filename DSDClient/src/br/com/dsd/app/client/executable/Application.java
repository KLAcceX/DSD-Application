package br.com.dsd.app.client.executable;

import java.awt.EventQueue;

import javax.swing.JFrame;

import br.com.dsd.app.client.gui.frame.LoginFrame;
import br.com.dsd.app.client.gui.frame.MainFrame;

public class Application {

	private static JFrame jframe;

	private Application() {
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jframe = new LoginFrame();
					jframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void changeFrameMain() {
		jframe.dispose();
		jframe = MainFrame.getInstance();
		jframe.setVisible(true);
	}
	
	public static void changeFrameLogin() {
		jframe.dispose();
		jframe = LoginFrame.getInstance();
		jframe.setVisible(true);
	}

}
