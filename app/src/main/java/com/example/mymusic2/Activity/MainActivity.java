package com.example.mymusic2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mymusic2.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button signin,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        signin.setOnClickListener(this);
        signup.setOnClickListener(this);
//

    }

    @Override
    public void onClick(View v) {
        if(v == signin){
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
        if (v == signup){
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        }
    }
}