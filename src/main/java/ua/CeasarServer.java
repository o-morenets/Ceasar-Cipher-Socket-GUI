package ua;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class CeasarServer extends JFrame implements ActionListener {

    private static JLabel lblStarted;
    private static JLabel lblClientConnected;
    private static JLabel lblClientText;
    private static JLabel lblDecryptedText;
    private static JLabel lblKey;
    JTextField fldKey;
    private JButton btnClose;
    private JButton btnDecrypt;
    private static String encryptedText;
    private JProgressBar progress;

    CeasarServer() {
        super("SERVER");

        JPanel p = new JPanel(new GridLayout(10, 1));

        lblStarted = new JLabel("");
        lblClientConnected = new JLabel("");
        lblClientText = new JLabel("");
        lblKey = new JLabel("Enter the Key:");
        fldKey = new JTextField(30);
        btnDecrypt = new JButton("Decrypt");
        btnDecrypt.setMnemonic('D');
        btnDecrypt.addActionListener(this);
        progress = new JProgressBar(0, 20);
        progress.setValue(0);
        progress.setStringPainted(true);
        lblDecryptedText = new JLabel("");
        btnClose = new JButton("Close");
        btnClose.setMnemonic('C');
        btnClose.setPreferredSize(new Dimension(300, 25));
        btnClose.addActionListener(this);

        p.add(lblStarted);
        p.add(lblClientConnected);
        p.add(lblClientText);
        p.add(lblKey);
        p.add(fldKey);
        p.add(btnDecrypt);
        p.add(progress);
        p.add(lblDecryptedText);
        p.add(btnClose);

        add(p);

        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnClose)
            System.exit(0);
        else if (e.getSource() == btnDecrypt) {
            int key = 0;
            try {
                key = Integer.parseInt(fldKey.getText());
            } catch (NumberFormatException ex) {
                fldKey.setText("0");
            }

/*
            // progress bar
            for (int num = 0; num < 21; num++) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException ignored) {
				}
				progress.setValue(num);
				Rectangle progressRect = progress.getBounds();
				progressRect.x = 0;
				progressRect.y = 0;
				progress.paintImmediately(progressRect);
			}
*/

            String decryptedText = CipherUtils.decrypt(encryptedText, key);
            lblDecryptedText.setText("Decrypted text: " + decryptedText);
        }
    }

    public static void main(String args[]) {
        new CeasarServer();
        ServerSocket server = null;
        try {
            server = new ServerSocket(4321);
            lblStarted.setText("Secure data transfer Server Started....");
        } catch (IOException e) {
            lblStarted.setText("ERROR");
        }

        Socket s = null;
        if (server != null) {
            try {
                s = server.accept();
                lblClientConnected.setText("Client Connected !");
            } catch (IOException e) {
                lblClientConnected.setText("ERROR");
            }
        }

        if (s != null) {
            try (Scanner scanner = new Scanner(s.getInputStream())) {
                while (scanner.hasNextLine()) {
                    encryptedText = scanner.nextLine();
                    lblClientText.setText("Client: " + encryptedText);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}