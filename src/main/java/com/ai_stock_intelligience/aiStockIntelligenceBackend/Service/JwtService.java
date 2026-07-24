package com.ai_stock_intelligience.aiStockIntelligenceBackend.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private String secretKey = "";

    public JwtService() {
        //Random Generation of secret key typically it stays constant for 30-90 days
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey key = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(key.getEncoded()); //byte key generation to string
//            secretKey = "mR9#vW2x!zP5*kQ8bC4nE7rT0yU1iO3pAsDfGhJkLlM6N9bV"; //static Key
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String email, Long userId){
        //Creates token based on email, userid, creation time, and expiration time
        Map<String, Object> claims = new HashMap<>(); //creates empty json object

        return Jwts.builder().claims()
                .add(claims)
                .subject(email)
                .add("userId", userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (60*60*30)))
                .and()
                .signWith(getKey()) //Tells algorithm used in generating key
                .compact(); //build json object by adding subject field, userid field, creation, expiration time, and key and makes it compact
    }

    //method to convert string key into bytes and creating secure object so other parts of code cannot modify it
    public Key getKey(){
        System.out.println(secretKey);
//        byte[] keyBytes = Decoders.BASE64
//                .decode(secretKey); //converts string key to bytes, (Assumption the plain english text is not the right password, its like a secret code)
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8); //converts plain english key to bytes
        return Keys.hmacShaKeyFor(keyBytes); //verifies the size of the key and creates security object so no other parts of code can modify it
    }
}
