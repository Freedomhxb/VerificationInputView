package com.hxb.verificationinputviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hxb.verification.VerificationInputView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private VerificationInputView mVerificationInputView1;
    private VerificationInputView mVerificationInputView3;

    private String mTag= MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVerificationInputView1 = findViewById(R.id.verification_input_view_1);
        mVerificationInputView1.setListener(new VerificationInputView.Listener() {
            @Override
            public void onChange(String[] strings) {
                Log.d(mTag,"onChange: "+ Arrays.toString(strings));
            }

            @Override
            public void onComplete(String string) {
                Log.d(mTag,"onComplete: "+string);
            }
        });


        mVerificationInputView3 = findViewById(R.id.verification_input_view_3);
        mVerificationInputView3.setChildCanClickable(false);



    }

}
