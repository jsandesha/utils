package com.highpeaksw.utils.crypto;

import com.google.crypto.tink.subtle.AesGcmJce;
import com.highpeaksw.utils.exception.DataException;

public interface EncryptionService {

    String aesEncrypt( String plainText, String key ) throws DataException;

    String aesEncrypt(String plainText, AesGcmJce keyInstance) throws DataException;

    String aesDecrypt(String encryptedText, String key ) throws DataException;

    String aesDecrypt(String encryptedText, AesGcmJce keyInstance) throws DataException;
}
