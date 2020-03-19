package com.algorithm;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSA {
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>(); // for storing the key pair

    public static void genKeyPair() throws NoSuchAlgorithmException {
        // Initialize
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024,new SecureRandom());  // Set size of the key
        // Generate the key pair
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // Get the public key and private key
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // Encode to string with base64
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // Save keys to the KeyMap
        keyMap.put(0,publicKeyString);
        keyMap.put(1,privateKeyString);
    }

    public static String get_privatekey(){
        return keyMap.get(1);
    }

    public static String get_publickey(){
        return keyMap.get(0);
    }

    public static String encrypt( String str, String publicKey ) throws Exception{
        // Base64 public key
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        // RSA encrypt
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    public static String decrypt(String str, String privateKey) throws Exception{
        // Base64 decode
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        // base64 private key
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        // RSA decrypt
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

}
