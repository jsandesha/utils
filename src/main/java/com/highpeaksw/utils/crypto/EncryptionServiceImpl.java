package com.highpeaksw.utils.crypto;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.google.crypto.tink.subtle.AesGcmJce;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String aesEncrypt( String plainText, String key ) throws GeneralSecurityException
    {
        try
        {
            //TODO validate key byte size
            AesGcmJce agjEncryption = new AesGcmJce(key.getBytes());
            byte[] encrypted = agjEncryption.encrypt(plainText.getBytes(), null);
            return Base64.getEncoder().encodeToString((encrypted));
        }
        catch( GeneralSecurityException e )
        {
            //TODO handle exception
            log.error("Error {0}", e);
            throw e;
        }
    }

    @Override
    public String aesDecrypt( String encryptedText, String key ) throws GeneralSecurityException
    {
        try
        {
            //TODO validate key byte size
            AesGcmJce agjDecryption = new AesGcmJce(key.getBytes());
            return new String(agjDecryption.decrypt(Base64.getDecoder().decode(encryptedText), null),
                    StandardCharsets.UTF_8);
        }
        catch( GeneralSecurityException e )
        {
            //TODO handle exception
            log.error("Error {0}", e);
            throw e;
        }

    }
}
