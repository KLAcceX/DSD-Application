package br.com.dsd.app.server.message;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import br.com.dsd.app.server.gui.Main;
import br.com.dsd.app.server.gui.dialog.Message;

/**
 * Classe que cuida de inserir as informações na Área de texto
 * 
 * @author kl
 *
 */
public class Logger {

	private Logger() {

	}

	/**
	 * Adiciona a String de texto ao final da Área de Texto do Console
	 * 
	 * @param texto
	 */
	public static void log(String text) {
		adicionarTexto(text, "logStyle");
	}

	public static void err(String text) {
		adicionarTexto(text, "errStyle");
	}
	
	private static void adicionarTexto(String text, String style) {
		JTextPane logPane = Main.getPnlInfo().getTxtLog();
		StyledDocument doc = logPane.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), text + "\n", logPane.getStyle(style));
		} catch (BadLocationException e) {
			Message.createMessage("Problema ao definir posição do documento.");
		}
	}
}
