package com.compilador.recife;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

public class ReadToken {
	public static void main(String[] args) {
        final JFrame frame = new JFrame("Visualizador de Tokens");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem openMenuItem = new JMenuItem("Abrir Arquivo");
        fileMenu.add(openMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        final JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);
        
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir")); // Define o diretório inicial para a pasta do projeto

                FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de código", "txt", "java");
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        textArea.setText("");
                        InputStream inputStream = new FileInputStream(fileChooser.getSelectedFile());
                        ANTLRInputStream antlrInputStream = new ANTLRInputStream(inputStream);
                        RecifeLexer lexer = new RecifeLexer(antlrInputStream);
                        CommonTokenStream tokens = new CommonTokenStream(lexer);
                        tokens.fill();
                        
                        for (Token t : tokens.getTokens()) {
                            textArea.append("<" + RecifeLexer.VOCABULARY.getDisplayName(t.getType()) + "," + t.getText() + ">\n");
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Ocorreu um erro ao processar o arquivo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        frame.setVisible(true);
    }

}
