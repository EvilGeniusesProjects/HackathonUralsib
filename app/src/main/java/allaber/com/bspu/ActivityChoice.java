package allaber.com.bspu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityChoice extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        Button buttonEnrollee = findViewById(R.id.buttonEnrollee);
        Button buttonStudent = findViewById(R.id.buttonStudent);
        Button buttonTeacher = findViewById(R.id.buttonTeacher);

        buttonTeacher.setOnClickListener(this);
        buttonStudent.setOnClickListener(this);
        buttonEnrollee.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonEnrollee:
                Intent intent1 = new Intent(this, ActivityEnrollee.class);
                startActivity(intent1);
                break;

            case R.id.buttonStudent:
                Intent intent2 = new Intent(this,  ActivityMain.class);
                startActivity(intent2);
                break;

            case R.id.buttonTeacher:
                Intent intent3 = new Intent(this,  ActivityMain.class);
                startActivity(intent3);
                break;
        }
    }
}
