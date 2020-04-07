package allaber.com.bspu.News;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;

import allaber.com.bspu.R;

public class ActivityNews extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(" ");

        textView = findViewById(R.id.textView);
        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        String description = intent.getStringExtra("description");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
        }else{
            textView.setText(Html.fromHtml(description));
        }

        InputStream ims = getClass().getResourceAsStream("/res/drawable/" + image + ".png");
        AppBarLayout app_bar = findViewById(R.id.app_bar);
        app_bar.setBackground(Drawable.createFromStream(ims, null));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                this.finish();
                break;
        }

    }
}
