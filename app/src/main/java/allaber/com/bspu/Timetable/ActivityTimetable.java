package allaber.com.bspu.Timetable;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import org.joda.time.DateTime;

import allaber.com.bspu.ActivityMain;
import allaber.com.bspu.R;
import allaber.com.bspu.Timetable.ui.main.SectionsPagerAdapter;


public class ActivityTimetable extends AppCompatActivity implements View.OnClickListener{
    ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DateTime datetime = DateTime.now();
        int week = datetime.getDayOfWeek();


        setContentView(R.layout.activity_timetable);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(week - 1);
        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageViewBack:
                Intent intent = new Intent(this, ActivityMain.class);
                startActivity(intent);
                break;
        }
    }
}