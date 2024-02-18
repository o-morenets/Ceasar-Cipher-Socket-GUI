import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class CeasarClient extends JFrame {
    private static PrintWriter pw;
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";

    private static JLabel lblWelcome;
    private static JLabel lblText;
    private static JLabel lblEncryptedTextSent;
    private static JLabel lblKey;
    private JButton btnSend;
    private JButton btnClose;
    private JButton btnEncrypt;
    private JTextField fldText;
    private JTextField fldKey;
    private JProgressBar progress;
    private String encryptedText = "";

    CeasarClient(Socket s) {
        super("CLIENT");
        setSize(500, 500);

        JPanel p = new JPanel(new GridLayout(10, 1));

        lblWelcome = new JLabel("Welcome to Secure Data transfer!");
        lblText = new JLabel("Enter the word here:");
        fldText = new JTextField(30);
        lblKey = new JLabel("Enter the Key:");
        fldKey = new JTextField(30);
        btnEncrypt = new JButton("Encrypt");
        btnEncrypt.setMnemonic('E');
        progress = new JProgressBar(0, 20);
        progress.setValue(0);
        progress.setStringPainted(true);
        btnSend = new JButton("Send");
        btnSend.setMnemonic('S');
        lblEncryptedTextSent = new JLabel("");
        btnClose = new JButton("Close");
        btnClose.setMnemonic('C');

        p.add(lblWelcome);
        p.add(lblText);
        p.add(fldText);
        p.add(lblKey);
        p.add(fldKey);
        p.add(btnEncrypt);
        p.add(progress);
        p.add(btnSend);
        p.add(lblEncryptedTextSent);
        p.add(btnClose);

        add(p);

        btnEncrypt.addActionListener(e -> {
            lblEncryptedTextSent.setText("");
            encryptedText = "";

            int key = 0;
            try {
                key = Integer.parseInt(fldKey.getText());
            } catch (NumberFormatException ignored) {
            }

            String text = fldText.getText();
            int i = 0, j, k;
            while (i < text.length()) {
                j = alphabet.indexOf(text.charAt(i));
                k = (j + key) % 26;
                encryptedText = encryptedText + alphabet.charAt(k);
                i++;
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
        });

        btnSend.addActionListener(e -> {
            pw.println(encryptedText);
            lblEncryptedTextSent.setText("Encrypted Text Sent.");
        });

        btnClose.addActionListener(e -> System.exit(0));

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        Socket s;
        try {
            s = new Socket(InetAddress.getLocalHost(), 4321);
            pw = new PrintWriter(s.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new CeasarClient(s);
    }
}