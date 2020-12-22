//package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

abstract class Encryptor {

    private static IvParameterSpec randomIV() {
        byte[] x = new byte[128 / 8];
        new SecureRandom().nextBytes(x);
        return new IvParameterSpec(x);
    }

    private static SecretKey AES() throws NoSuchAlgorithmException {
        KeyGenerator x = KeyGenerator.getInstance("AES");
        x.init(128);
        return x.generateKey();
    }

    private static KeyPair RSA() throws NoSuchAlgorithmException {
        KeyPairGenerator x = KeyPairGenerator.getInstance("RSA");
        x.initialize(2048);
        return x.genKeyPair();
    }

    public static void outputRSAKeyPair(KeyPair x) throws NoSuchAlgorithmException, IOException {
        if (x == null) {
            x = RSA();
        }

        Path y = FileHandler.getPath(true);

        Path pub = Paths.get(y + "\\public_key.pub");
        Files.write(pub, Base64.getEncoder().encodeToString(x.getPublic().getEncoded()).getBytes());

        Path priv = Paths.get(y + "\\private_key.priv");
        Files.write(priv, Base64.getEncoder().encodeToString(x.getPrivate().getEncoded()).getBytes());
    }

    public static byte[][] loadKey() throws IOException {
        Path x = FileHandler.getPath(false);
        String y = x.toString();
        String z = y.substring(y.lastIndexOf("."));

        byte[] in = new FileInputStream(x.toString()).readAllBytes();

        byte[][] d = null;

        if (z.equals(".pub")) {
            byte[] a = Base64.getDecoder().decode(in);
            String b = ".pub";
            byte[] c = b.getBytes();

            d = new byte[2][];
            d[0] = c;
            d[1] = a;

            return d;
        } else if (z.equals(".priv")) {
            byte[] a = Base64.getDecoder().decode(in);
            String b = ".priv";
            byte[] c = b.getBytes();

            d = new byte[2][];
            d[0] = c;
            d[1] = a;

            return d;
        }

        return d;
    }

    public static void encrypt(File file, byte[][] key) throws Exception {

        Key rsa = RSA().getPrivate();

        if (key == null) {
            KeyPair y = RSA();
            rsa = y.getPrivate();
            outputRSAKeyPair(y);
        } else {
            if (new String(key[0]).equals(".pub")) {
                X509EncodedKeySpec p = new X509EncodedKeySpec(key[1]);
                KeyFactory q = KeyFactory.getInstance("RSA");
                rsa = q.generatePublic(p);
            } else if (new String(key[0]).equals(".priv")) {
                PKCS8EncodedKeySpec p = new PKCS8EncodedKeySpec(key[1]);
                KeyFactory q = KeyFactory.getInstance("RSA");
                rsa = q.generatePrivate(p);
            }
        }

        SecretKey aes = AES();
        IvParameterSpec iv = randomIV();

        FileOutputStream out = new FileOutputStream(
                file.getName().substring(0, file.getName().lastIndexOf(".")) + ".enc");

        byte[] ext = new byte[16];
        byte[] extb = file.getName().substring(file.getName().lastIndexOf(".")).getBytes();
        for (int i = 0; i < extb.length; i++) {
            ext[i] = extb[i];
        }
        System.out.println("done");
        out.write(ext);

        Cipher x = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        x.init(Cipher.ENCRYPT_MODE, rsa);
        out.write(x.doFinal(aes.getEncoded()));

        out.write(iv.getIV());

        x = Cipher.getInstance("AES/CBC/PKCS5Padding");
        x.init(Cipher.ENCRYPT_MODE, aes, iv);

        try (FileInputStream in = new FileInputStream(file)) {
            processFile(x, in, out);
        }

        out.close();
    }

    public static void decrypt(File file, byte[][] key) throws Exception {

        Key rsa = RSA().getPrivate();

        if (new String(key[0]).equals(".pub")) {
            X509EncodedKeySpec p = new X509EncodedKeySpec(key[1]);
            KeyFactory q = KeyFactory.getInstance("RSA");
            rsa = q.generatePublic(p);
        } else if (new String(key[0]).equals(".priv")) {
            PKCS8EncodedKeySpec p = new PKCS8EncodedKeySpec(key[1]);
            KeyFactory q = KeyFactory.getInstance("RSA");
            rsa = q.generatePrivate(p);
        }

        FileInputStream in = new FileInputStream(file.toPath().toString());

        byte[] extb = new byte[16];
        in.read(extb);
        String ext = new String(extb).trim();
        System.out.println(ext);

        byte[] y = new byte[256];
        in.read(y);
        Cipher x = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        x.init(Cipher.DECRYPT_MODE, rsa);
        SecretKeySpec aes = new SecretKeySpec(x.doFinal(y), "AES");

        byte[] iv = new byte[128 / 8];
        in.read(iv);
        IvParameterSpec ivx = new IvParameterSpec(iv);

        x = Cipher.getInstance("AES/CBC/PKCS5Padding");
        x.init(Cipher.DECRYPT_MODE, aes, ivx);

        try (FileOutputStream out = new FileOutputStream(
                file.getName().substring(0, file.getName().lastIndexOf(".")) + ext)) {
            processFile(x, in, out);
        }

        in.close();
    }

    static private void processFile(Cipher ci, InputStream in, OutputStream out)
            throws javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException, java.io.IOException {
        byte[] ibuf = new byte[1024];
        int len;
        while ((len = in.read(ibuf)) != -1) {
            byte[] obuf = ci.update(ibuf, 0, len);
            if (obuf != null)
                out.write(obuf);
        }
        byte[] obuf = ci.doFinal();
        if (obuf != null)
            out.write(obuf);

        out.flush();
        out.close();
    }

}
