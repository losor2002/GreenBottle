package it.unisa.greenbottle.controller.accessoControl.util;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Classe EncryptorUtil, utilizzata per la cifratura e decifratura di stringhe.
 */
public class EncryptorUtil {
  private static final String ALGORITHM = "AES";
  private static final String ENCRYPTION_KEY = "1234567890123456"; // solo per scopo di test

  /**
   * Metodo per cifrare una stringa.
   *
   * @param text stringa da cifrare
   * @return stringa cifrata
   */
  public static String encrypt(String text) {
    try {
      SecretKey secretKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), ALGORITHM);
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      byte[] encryptedBytes = cipher.doFinal(text.getBytes());
      return Base64.getEncoder().encodeToString(encryptedBytes);
    } catch (Exception e) {
      throw new RuntimeException("Errore durante la crittografia", e);
    }
  }

  /**
   * Metodo per decifrare una stringa.
   *
   * @param encryptedText stringa cifrata
   * @return stringa decifrata
   */
  public static String decrypt(String encryptedText) {
    try {
      SecretKey secretKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), ALGORITHM);
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
      return new String(decryptedBytes);
    } catch (Exception e) {
      throw new RuntimeException("Errore durante la decrittografia", e);
    }
  }
}
