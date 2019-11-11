package br.com.dsd.app.server.executable;

import java.awt.EventQueue;

import br.com.dsd.app.server.gui.Main;

public class ServerApplication {

	private ServerApplication() {
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
