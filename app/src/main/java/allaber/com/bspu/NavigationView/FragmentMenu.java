package allaber.com.bspu.NavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;
import allaber.com.bspu.R;
import allaber.com.bspu.Timetable.ActivityTimetable;
import allaber.com.bspu.pointsNFC.ActivityPointsNFC;

public class FragmentMenu extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        NavigationView vNavigation = view.findViewById(R.id.vNavigation);
        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menuTimetable:
                        goFragment(1);
                        Intent intent1 = new Intent(getContext(), ActivityTimetable.class);
                        startActivity(intent1);
                        break;
                    case R.id.menuNews:
                        goFragment(3);
                        break;
                    case R.id.menuCalculator:
                        goFragment(4);
                        break;
                    case R.id.menuNFC:
                        goFragment(1);
                        Intent intent2 = new Intent(getContext(), ActivityPointsNFC.class);
                        startActivity(intent2);
                        break;
                    case R.id.menuChat:
                        goFragment(8);
                        break;
                    case R.id.menuSettings:
                        goFragment(7);
                        break;
                    case R.id.menuAboutTheBSPU:
                        goFragment(2);
                        break;

                    case R.id.menuSections:
                        goFragment(9);
                        break;
                }
                return false;
            }
        });
        return view;
    }

    public void goFragment(int index) {
        ChangeFragment listener = (ChangeFragment) getActivity();
        listener.onButtonSelected(index);
    }

    public interface ChangeFragment {
        void onButtonSelected(int fragmentIndex);
    }
}
