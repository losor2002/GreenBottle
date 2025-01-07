package it.unisa.greenbottle.controller.accessoControl.util;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class JasyptUtil {
  /*
    private static final String ENCRYPTION_KEY = "tua-chiave-segreta";

    public static String encrypt(String text) {
      StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
      encryptor.setPassword(ENCRYPTION_KEY); // Chiave segreta
      encryptor.setAlgorithm("PBEWithMD5AndDES"); // Algoritmo deterministico
      encryptor.setIvGenerator(new NoIvGenerator()); // Rimuove l'uso dell'IV
      return encryptor.encrypt(text);
    }

    public static String decrypt(String encryptedText) {
      StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
      encryptor.setPassword(ENCRYPTION_KEY); // Chiave segreta
      encryptor.setAlgorithm("PBEWithMD5AndDES"); // Algoritmo deterministico
      encryptor.setIvGenerator(new NoIvGenerator()); // Rimuove l'uso dell'IV
      return encryptor.decrypt(encryptedText);
    }
  }
   */

  private static final String ALGORITHM = "AES";
  private static final String ENCRYPTION_KEY = "1234567890123456";

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
