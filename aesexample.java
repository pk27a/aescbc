import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class aesexample {
 public static byte[] encrypt(String original, String Key) throws Exception
 {
     byte[] convertedoriginal = original.getBytes();

     int size=16;
     byte[] iv = new byte[size];
     SecureRandom random= new SecureRandom();
     random.nextBytes(iv);
     IvParameterSpec ivParameterSpec= new IvParameterSpec(iv);
     MessageDigest digest=MessageDigest.getInstance("SHA-256");
     digest.update(Key.getBytes(StandardCharsets.UTF_8));
     byte[] keyBytes = new byte[16];
     System.arraycopy(digest.digest(),0, keyBytes,0,keyBytes.length);
     SecretKeySpec secretKeySpec= new SecretKeySpec(keyBytes,"AES");
     Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
     cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivParameterSpec);
     byte[] encrypted = cipher.doFinal(convertedoriginal);
     byte[] finalencrpytion= new byte[size + encrypted.length];
     System.arraycopy(iv,0,finalencrpytion,0,size);
     System.arraycopy(encrypted,0,finalencrpytion,size,encrypted.length);
     return finalencrpytion;

 }
 public static String decrypt(byte[] encrypted, String key) throws Exception
 {
     int size= 16;
     byte[] iv = new byte[size];
     System.arraycopy(encrypted,0,iv,0,iv.length);
     IvParameterSpec ivParameterSpec= new IvParameterSpec(iv);
     int actualsize= encrypted.length-size;
     byte[] encryptedbyte = new byte[actualsize];
     System.arraycopy(encrypted,size,encryptedbyte,0,actualsize);
     byte[] keyBytes= new byte[size];
     MessageDigest messageDigest= MessageDigest.getInstance("SHA-256");
     messageDigest.update(key.getBytes(StandardCharsets.UTF_8));
     System.arraycopy(messageDigest.digest(),0,keyBytes,0,keyBytes.length);
     SecretKeySpec secretKeySpec=new SecretKeySpec(keyBytes,"AES");
     Cipher cipher= Cipher.getInstance("AES/CBC/PKCS5PADDING");
     cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,ivParameterSpec);
     byte[] decrypted= cipher.doFinal(encryptedbyte);
     return  new String(decrypted);

 }
    public static void main(String[] args) throws Exception {
     String key = "password";
        String datastring = "username";
        String datastring2 = "username2";

        byte[] encrypted = encrypt(datastring, key);
        System.out.println(encrypted);
        String decrypted = decrypt(encrypted, key);
        System.out.println(decrypted);
        byte[] encrypted2 = encrypt(datastring2, key);
        System.out.println(encrypted2);
        String decrypted2 = decrypt(encrypted2, key);
        System.out.println(decrypted2);
    }
}
