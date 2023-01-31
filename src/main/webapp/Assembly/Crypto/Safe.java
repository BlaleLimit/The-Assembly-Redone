package Assembly.Crypto;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.*;

public class Safe implements Key{

    public static String encrypt(String msg){
        String encryptedString = null;
        try {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                final SecretKeySpec secretKey = new SecretKeySpec(KEY, "AES");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                encryptedString = Base64.encodeBase64String(cipher.doFinal(msg.getBytes()));
        } catch (Exception e){
                System.err.println(e.getMessage());
        }
        return encryptedString;
    }

    public static String decrypt(String code){
        String decryptedString = null;
        try{
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
                final SecretKeySpec secretKey = new SecretKeySpec(KEY, "AES");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                decryptedString = new String(cipher.doFinal(Base64.decodeBase64(code)));
        } catch (Exception e) {
                System.err.println(e.getMessage());
        }
        return decryptedString;
    }
}
