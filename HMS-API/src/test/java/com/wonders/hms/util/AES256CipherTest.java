package com.wonders.hms.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AES256CipherTest {

//    @Value("${aes256.secret.key}")
//    private String AES256_SECRET_KEY;

    String id = "testid";
    String custrnmNo = "111111";
    String custNm = "테스트";

    @Test
    public void encDesTest() throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        final String AES256_SECRET_KEY = "5596aa60469f64834338be571fa9fef2";
        AES256Cipher a256 = AES256Cipher.getInstance(AES256_SECRET_KEY);

        String enId = a256.encode(id);
        String enYyyymmdd = a256.encode(custrnmNo);
        String enCustNm = a256.encode(custNm);

        String desId = a256.decode(enId);
        String desYyyymmdd = a256.decode(enYyyymmdd);
        String desCustNm = a256.decode(enCustNm);

        System.out.println(enId);
        System.out.println(desId);
        System.out.println(desYyyymmdd);
        System.out.println(desCustNm);

        assert id.equals(desId);
        assert custrnmNo.equals(desYyyymmdd);
        assert custNm.equals(desCustNm);
    }
}
