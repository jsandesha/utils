package com.highpeaksw.utils.crypto;

import java.security.GeneralSecurityException;

public interface EncryptionService {

    String aesEncrypt( String plainText, String key ) throws GeneralSecurityException;

    String aesDecrypt( String encryptedText, String key ) throws GeneralSecurityException;
}
