package allaber.com.bspu.pointsNFC;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import allaber.com.bspu.R;

public class ActivityShopping extends AppCompatActivity implements View.OnClickListener {

    int count = 0;
    ImageView imageViewBack;
    TextView textPoints;
    SharedPreferences mSettings;

    Button buy1;
    Button buy2;
    Button buy3;
    Button buy4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        textPoints = findViewById(R.id.textPoints);
        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(this);

        buy1 = findViewById(R.id.buy1);
        buy2 = findViewById(R.id.buy2);
        buy3 = findViewById(R.id.buy3);
        buy4 = findViewById(R.id.buy4);
        buy1.setOnClickListener(this);
        buy2.setOnClickListener(this);
        buy3.setOnClickListener(this);
        buy4.setOnClickListener(this);

        mSettings = getSharedPreferences("points", Context.MODE_PRIVATE);
        if (mSettings.contains("points")) {
            count = mSettings.getInt("points", 0);
            textPoints.setText("У вас: " + count + " баллов");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewBack:
                finish();
                break;
            case R.id.buy1:
                buyItems(50);
                break;
            case R.id.buy2:

                break;
            case R.id.buy3:

                break;
            case R.id.buy4:

                break;
        }
    }

    public void buyItems(int points){
        if(count >= points){
            count = count - points;
            textPoints.setText("У вас: " + count + " баллов");
            Toast.makeText(this, "Заявка отправлена", Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putInt("points", count);
            editor.apply();
        }else{
            Toast.makeText(this, "У Вас недостаточно баллов", Toast.LENGTH_LONG).show();
        }


    }


}











