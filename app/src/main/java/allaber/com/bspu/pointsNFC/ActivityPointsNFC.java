package allaber.com.bspu.pointsNFC;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import allaber.com.bspu.R;

public class ActivityPointsNFC extends Activity implements View.OnClickListener {

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;
    int count = 0;
    ImageView imageViewBack;
    TextView textPoints;
    Button buttonShopping;
    SharedPreferences mSettings;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_nfc);


        imageViewBack = findViewById(R.id.imageViewBack);
        textPoints = findViewById(R.id.textPoints);
        buttonShopping = findViewById(R.id.buttonShopping);
        imageViewBack.setOnClickListener(this);
        buttonShopping.setOnClickListener(this);

        mSettings = getSharedPreferences("points", Context.MODE_PRIVATE);
        if(mSettings.contains("points")) {
            count = mSettings.getInt("points", 0);
            textPoints.setText("У вас: " + count + " баллов");
        }

        context = this;
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        readFromIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[] { tagDetected };
    }

        private void readFromIntent(Intent intent) {
            String action = intent.getAction();
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
                count = count + 10;
                Toast.makeText(this, "Вам начислено 10 баллов", Toast.LENGTH_SHORT).show();
                textPoints.setText("У вас: " + count + " баллов");
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putInt("points", count);
                editor.apply();
            }
        }

        @Override
        protected void onNewIntent(Intent intent) {
            setIntent(intent);
            readFromIntent(intent);
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
                myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            }
        }

        @Override
        public void onPause(){
            super.onPause();
            WriteModeOff();
        }

        @Override
        public void onResume(){
            super.onResume();
            if(mSettings.contains("points")) {
                count = mSettings.getInt("points", 0);
                textPoints.setText("У вас: " + count + " баллов");
            }
            WriteModeOn();
        }

        private void WriteModeOn(){
            writeMode = true;
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
        }
        private void WriteModeOff(){
            writeMode = false;
            nfcAdapter.disableForegroundDispatch(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageViewBack:
                    finish();
                    break;
                case R.id.buttonShopping:
                    Intent intent = new Intent(this, ActivityShopping.class);
                    startActivity(intent);
                    break;
            }
        }
}
