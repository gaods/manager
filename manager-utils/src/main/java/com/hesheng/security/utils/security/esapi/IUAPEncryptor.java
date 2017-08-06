//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.security.utils.security.esapi;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.Encryptor;
import org.owasp.esapi.crypto.CipherText;
import org.owasp.esapi.crypto.PlainText;
import org.owasp.esapi.errors.EncryptionException;
import org.owasp.esapi.errors.IntegrityException;

import javax.crypto.SecretKey;
import java.io.IOException;

public class IUAPEncryptor {
    private static final Encryptor encryptor = ESAPI.encryptor();
    private static final Encoder encoder = ESAPI.encoder();
    private static final int hash_iterations = 1024;

    public IUAPEncryptor() {
    }

    public String encrypt(String text) throws EncryptException {
        PlainText plainText = new PlainText(text);

        try {
            CipherText e = encryptor.encrypt(plainText);
            return encoder.encodeForBase64(e.asPortableSerializedByteArray(), false);
        } catch (EncryptionException var4) {
            throw new EncryptException(var4);
        }
    }

    public String encrypt(SecretKey key, String text) throws EncryptException {
        PlainText plainText = new PlainText(text);
        CipherText cipherText = null;

        try {
            cipherText = encryptor.encrypt(key, plainText);
            return encoder.encodeForBase64(cipherText.asPortableSerializedByteArray(), false);
        } catch (EncryptionException var6) {
            throw new EncryptException(var6);
        }
    }

    public String decrypt(byte[] data) throws EncryptException {
        try {
            PlainText e = encryptor.decrypt(CipherText.fromPortableSerializedBytes(data));
            return e.toString();
        } catch (EncryptionException var3) {
            throw new EncryptException(var3);
        }
    }

    public String decrypt(String text) throws IOException, EncryptException {
        byte[] data = encoder.decodeFromBase64(text);
        return this.decrypt(data);
    }

    public String decrypt(SecretKey key, byte[] data) throws EncryptException {
        try {
            PlainText e = encryptor.decrypt(key, CipherText.fromPortableSerializedBytes(data));
            return e.toString();
        } catch (EncryptionException var4) {
            throw new EncryptException(var4);
        }
    }

    public String decrypt(SecretKey key, String text) throws EncryptException, IOException {
        byte[] data = encoder.decodeFromBase64(text);
        return this.decrypt(key, data);
    }

    public String hash(String text, String salt) throws EncryptException {
        return this.hash(text, salt, 1024);
    }

    public String hash(String text, String salt, int iterations) throws EncryptException {
        try {
            return encryptor.hash(text, salt, iterations);
        } catch (EncryptionException var5) {
            throw new EncryptException(var5);
        }
    }

    public String sign(String text) throws EncryptException {
        try {
            return encryptor.sign(text);
        } catch (EncryptionException var3) {
            throw new EncryptException(var3);
        }
    }

    public boolean verifySignature(String sign, String data) {
        return encryptor.verifySignature(sign, data);
    }

    public String seal(String text, long expiration) throws EncryptException {
        try {
            return encryptor.seal(text, expiration);
        } catch (IntegrityException var5) {
            throw new EncryptException(var5);
        }
    }

    public String unseal(String text) throws EncryptException {
        try {
            return encryptor.unseal(text);
        } catch (EncryptionException var3) {
            throw new EncryptException(var3);
        }
    }

    public long getCurrentTime() {
        return encryptor.getTimeStamp();
    }

    public long getRelativeTime(long offset) {
        return encryptor.getRelativeTimeStamp(offset);
    }
}
