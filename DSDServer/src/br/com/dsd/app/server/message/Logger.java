package br.com.dsd.app.server.message;

import br.com.dsd.app.server.gui.Main;

/**
 * Classe que cuida de inserir as informa��es na �rea de texto
 * 
 * @author kl
 *
 */
public class Logger {

	/**
	 * Adiciona a String de texto ao final da �rea de Texto do Console
	 * 
	 * @param texto
	 */
	public static void log(String text) {
		Main.getPnlInfo().getTxtLog().append(text + "\n");
	}
}
