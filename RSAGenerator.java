import java.security.*;

public class RSAGenerator {
    public RSAGenerator() {
        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();

            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();
            //System.out.println("Private: " + privateKey);
            //System.out.println("Public: " + publicKey);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public String GenerateKeyPair() {
        return "Hello, " + name + "!";
    }

    private String name;
    private KeyPairGenerator generator = null;
}