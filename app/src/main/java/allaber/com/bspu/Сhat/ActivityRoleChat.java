package allaber.com.bspu.Сhat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import allaber.com.bspu.R;

public class ActivityRoleChat extends AppCompatActivity implements View.OnClickListener {

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;

    String specialty;

    ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_chat);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        imageViewBack = findViewById(R.id.imageViewBack);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() != R.id.imageViewBack) {
            switch (v.getId()) {
                case R.id.button1:
                    specialty = String.valueOf(button1.getText());
                    break;

                case R.id.button2:
                    specialty = String.valueOf(button2.getText());
                    break;

                case R.id.button3:
                    specialty = String.valueOf(button3.getText());
                    break;

                case R.id.button4:
                    specialty = String.valueOf(button4.getText());
                    break;

                case R.id.button5:
                    specialty = String.valueOf(button5.getText());
                    break;

                case R.id.button6:
                    specialty = String.valueOf(button6.getText());
                    break;

                case R.id.button7:
                    specialty = String.valueOf(button7.getText());
                    break;

                case R.id.button8:
                    specialty = String.valueOf(button8.getText());
                    break;

                case R.id.button9:
                    specialty = String.valueOf(button9.getText());
                    break;
            }


            SharedPreferences SharedPreferencesID;
            SharedPreferencesID = getSharedPreferences("sID", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = SharedPreferencesID.edit();
            editor2.putString("specialty", specialty);
            editor2.apply();

            Intent intent1 = new Intent(this, ActivityChat.class);
            startActivity(intent1);
        }else{
            finish();
        }
    }
}
