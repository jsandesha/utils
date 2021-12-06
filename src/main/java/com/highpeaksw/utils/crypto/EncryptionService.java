package com.highpeaksw.utils.crypto;

import com.highpeaksw.utils.exception.DataException;

public interface EncryptionService {

    String aesEncrypt( String plainText, String key ) throws DataException;

    String aesDecrypt( String encryptedText, String key ) throws DataException;
}
