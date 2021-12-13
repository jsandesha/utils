package com.highpeaksw.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.highpeaksw.utils.crypto.EncryptionService;
import com.highpeaksw.utils.crypto.EncryptionServiceImpl;
import com.highpeaksw.utils.exception.DataException;

@RunWith( MockitoJUnitRunner.class )
public class EncryptionServiceImplTest {

    private static final String VALID_AES_KEY_128_BIT = "qwertyuiopoiuytr";
    private static final String INVALID_AES_KEY_128_BIT = "qwertypoiuytr";
    private static final String PLAIN_TEXT = "HPS_TEST";
    private static final String VALID_AES_ENCRYPTED_TEXT = "LSp+aZf7gEidbN1X06LY/Q==";

    private final EncryptionService encryptionService = new EncryptionServiceImpl();

    @Test
    public void testAesEncryptWith128BitKeySuccess() throws DataException
    {
        encryptionService.aesEncrypt(PLAIN_TEXT, VALID_AES_KEY_128_BIT);
    }

    @Test
    public void testAesEncryptWithKeyAsStringNoPlainTextPresent()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> encryptionService.aesEncrypt(null, VALID_AES_KEY_128_BIT));
        assertEquals("Plain text is missing", dataException.getErrorMessage());
    }

    @Test
    public void testAesEncryptWithKeyAsStringNoEncryptionKeyPresent()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> encryptionService.aesEncrypt(PLAIN_TEXT, ""));
        assertEquals("Encryption key is missing", dataException.getErrorMessage());
    }

    @Test
    public void testAesEncryptWithKeyAsStringInvalidKeySize()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> encryptionService.aesEncrypt(PLAIN_TEXT, INVALID_AES_KEY_128_BIT));
        assertEquals("AES key size must be 16 characters for 128-bit enc and 32 characters for 256-bit enc",
                dataException.getErrorMessage());
    }
}
