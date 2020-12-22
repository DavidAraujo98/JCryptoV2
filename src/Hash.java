//package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public enum Hash {

    MD5("MD5"), SHA1("SHA-1"), SHA256("SHA-256"), SHA512("SHA-512");

    private String a = "";

    private Hash(String a) {
        this.a = a;
    }

    public String getName() {
        return a;
    }

    public String checksum(File input) {
        try (InputStream in = new FileInputStream(input)) {
            MessageDigest digest = MessageDigest.getInstance(getName());
            byte[] block = new byte[4096];
            int length;
            while ((length = in.read(block)) > 0) {
                digest.update(block, 0, length);
            }
            byte[] result = digest.digest();

            StringBuffer hex = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                hex.append(Integer.toHexString(0xFF & result[i]));
            }
            return hex.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}