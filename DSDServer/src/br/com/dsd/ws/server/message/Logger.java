package br.com.dsd.ws.server.message;

import br.com.dsd.ws.server.gui.Main;

/**
 * Classe que cuida de inserir as informações na Área de texto
 * 
 * @author kl
 *
 */
public class Logger {

	/**
	 * Adiciona a String de texto ao final da Área de Texto do Console
	 * 
	 * @param texto
	 */
	public static void log(String text) {
		Main.getPnlForm().getTxtLog().append(text);
	}
}
