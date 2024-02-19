package ua;

public class CipherUtils {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static String encrypt(String plainText, int shift) {
        StringBuilder encryptedText = new StringBuilder();
        int j, k;
        for (int i = 0; i < plainText.length(); i++) {
            j = ALPHABET.indexOf(plainText.charAt(i));
            k = (j + shift) % 26;
            if (k < 0) {
                k += 26;
            }
            encryptedText.append(ALPHABET.charAt(k));
        }
        return encryptedText.toString();
    }

    static String decrypt(String encryptedText, int key) {
        StringBuilder decryptedText = new StringBuilder();
        int j, k;
        for (int i = 0; i < encryptedText.length(); i++) {
            j = ALPHABET.indexOf(encryptedText.charAt(i));
            k = (j + (26 - key)) % 26;
            decryptedText.append(ALPHABET.charAt(k));
        }
        return decryptedText.toString();
    }
}
