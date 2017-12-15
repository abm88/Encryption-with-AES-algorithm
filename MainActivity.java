package com.example.abdulbasith.aescryptodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "SymmetricAlgorithmAES";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Orginal Text
        String theTEstText = "This is plain text";
        TextView tvorig = (TextView)findViewById(R.id.tvorig);
        tvorig.setText("\n[ORIGINAL]:\n" + theTEstText + "\n");

        //Set up secret key spec for 128-bit AES ecryption and decryption

        SecretKeySpec sks = null;

        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128, sr);

            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (Exception e){
            Log.e( TAG, "AES secret key spec error");
        }

        // Encode the Original data with AES
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, sks);
            encodedBytes = c.doFinal(theTEstText.getBytes());

        } catch (Exception e){
            Log.e(TAG,"AES encryption error");
        }
        TextView tvencoded = (TextView)findViewById(R.id.tvencoded);
        tvencoded.setText("[ENCODED]:\n" +
                Base64.encodeToString(encodedBytes, Base64.DEFAULT) + "\n");


        //Decoding data with AES

        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, sks);
            decodedBytes = c.doFinal(encodedBytes);
        } catch (Exception e){
            Log.e(TAG, "AES decryption error");
        }

        TextView tvdecoded = (TextView)findViewById(R.id.tvdecoded);
        tvdecoded.setText("[DECODED]: \n" + new String(decodedBytes) + "\n");

    }
}
