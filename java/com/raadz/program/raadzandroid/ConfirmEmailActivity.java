package com.raadz.program.raadzandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.raadz.program.raadzandroid.R;

public class ConfirmEmailActivity extends AppCompatActivity {

    Button bBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_email);
        bBack = (Button)findViewById(R.id.bBack);
        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ConfirmEmailActivity.this, LoginActivity.class);
                startActivity(in);
            }
        });
    }
    @Override
    public void onBackPressed() {
        //This disables the back button
    }
}
