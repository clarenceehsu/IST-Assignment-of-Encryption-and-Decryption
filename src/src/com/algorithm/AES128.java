package com.algorithm;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES128 {
    // keys
    private static String key = "abcdefghijklmnop";  // the private key
    private static String CBC_iv = "qrstuvwxyzabcdef";  // the CBC vector

    public static String getKey(){
        return key;
    }

    public static String encryptAES(String data, String EncryptMode, String key) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/"+EncryptMode+"/PKCS5Padding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;

            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            if (EncryptMode == "ECB") {
                cipher.init(Cipher.ENCRYPT_MODE, keyspec);  // ECB mode
            }
            else if (EncryptMode == "CBC"){
                IvParameterSpec ivspec = new IvParameterSpec(CBC_iv.getBytes());  // CBC mode
                cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);  // initialize with private key and CBC Iv
            }

            byte[] encrypted = cipher.doFinal(plaintext);  // Encrypt!

            return AES128.encode(encrypted).trim();  // Base64 the encrypted data

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptAES(String data, String EncryptMode, String Key) throws Exception {
        try
        {
            byte[] encrypted1 = AES128.decode(data);  // Decode with base64

            Cipher cipher = Cipher.getInstance("AES/"+EncryptMode+"/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");

            if (EncryptMode == "ECB") {
                cipher.init(Cipher.DECRYPT_MODE, keyspec);  // ECB mode
            }
            else if (EncryptMode == "CBC")
            {
                IvParameterSpec ivspec = new IvParameterSpec(CBC_iv.getBytes());  //CBC mode
                cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            }

            byte[] original = cipher.doFinal(encrypted1);  // Decrypt!
            String originalString = new String(original);
            return originalString.trim(); // Get the decrypted data
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Encode with base64
    public static String encode(byte[] byteArray) {
        return new String(new Base64().encode(byteArray));
    }
    // Decode with base64
    public static byte[] decode(String base64EncodedString) {
        return new Base64().decode(base64EncodedString);
    }
}
