package allaber.com.bspu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManagerNonConfig;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import allaber.com.bspu.Authentication.FragmentEnter;
import allaber.com.bspu.Authentication.FragmentRegistration;
import allaber.com.bspu.Calculator.FragmentCalculator;
import allaber.com.bspu.NavigationView.FragmentMenu;
import allaber.com.bspu.News.FragmentNews;
import allaber.com.bspu.Other.FragmentMain;
import allaber.com.bspu.Sections.FragmenSection;
import allaber.com.bspu.Settings.FragmentSettings;
import allaber.com.bspu.Timetable.ActivityTimetable;
import allaber.com.bspu.Сhat.ActivityChat;
import allaber.com.bspu.Сhat.ActivityRoleChat;

public class ActivityMain extends AppCompatActivity implements FragmentMenu.ChangeFragment, FragmentMain.ChangeFragment, FragmentNews.ChangeFragment, FragmentCalculator.ChangeFragment, FragmentEnter.ChangeFragment, FragmentRegistration.ChangeFragment, FragmentSettings.ChangeFragment, FragmenSection.ChangeFragment{

    private FlowingDrawer mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawer = findViewById(R.id.drawerlayout);
        mDrawer = findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        setupMenu();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fr = fragmentManager.beginTransaction();


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FragmentMain fragmentMain = new FragmentMain();
            fr.replace(R.id.container, fragmentMain, "fragmentMain");
        } else {
            FragmentEnter fragmentEnter = new FragmentEnter();
            fr.replace(R.id.container, fragmentEnter, "fragment_enter");
        }
        fr.commit();
    }

    private void setupMenu() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentMenu mMenuFragment = (FragmentMenu) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null) {
            mMenuFragment = new FragmentMenu();
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit();
        }
    }


    @Override
    public void onButtonSelected(int fragIndex) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FragmentMain fragmentMain = new FragmentMain();
        FragmentNews fragmentNews = new FragmentNews();
        FragmentCalculator fragmentCalculator = new FragmentCalculator();
        FragmentRegistration fragmentRegistration = new FragmentRegistration();
        FragmentEnter fragmentEnter = new FragmentEnter();
        FragmentSettings fragmentSettings = new FragmentSettings();
        FragmenSection fragmenSection = new FragmenSection();

        switch (fragIndex) {
            case 1:
                mDrawer.toggleMenu();
                break;
            case 2:
                ft.replace(R.id.container, fragmentMain, "fragmentMain");
                mDrawer.closeMenu();
                break;
            case 3:
                ft.replace(R.id.container, fragmentNews, "fragmentNews");
                mDrawer.closeMenu();
                break;
            case 4:
                Intent intent11 = new Intent(this, ActivityDecanat.class);
                startActivity(intent11);
                break;
            case 5:
                ft.replace(R.id.container, fragmentRegistration, "fragmentRegistration");
                mDrawer.closeMenu();
                break;
            case 6:
                ft.replace(R.id.container, fragmentEnter, "fragmentEnter");
                mDrawer.closeMenu();
                break;
            case 7:
                ft.replace(R.id.container, fragmentSettings, "fragmentSettings");
                mDrawer.closeMenu();
                break;
            case 8:
                mDrawer.closeMenu();
                SharedPreferences SharedPreferencesID;
                SharedPreferencesID = getSharedPreferences("sID", Context.MODE_PRIVATE);
                String title = SharedPreferencesID.getString("listRole", String.valueOf(Context.MODE_PRIVATE));
                if(title.equals("Преподаватель")){
                    Intent intent = new Intent(this, ActivityRoleChat.class);
                    startActivity(intent);
                }else{
                    Intent intent1 = new Intent(this, ActivityChat.class);
                    startActivity(intent1);
                }
                break;
            case 9:
                ft.replace(R.id.container, fragmenSection, "fragmenSection");
                mDrawer.closeMenu();
                break;
        }
        ft.commit();
    }
}
