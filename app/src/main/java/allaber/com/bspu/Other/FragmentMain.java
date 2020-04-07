package allaber.com.bspu.Other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;

import allaber.com.bspu.Authentication.FirebaseClassUser;
import allaber.com.bspu.R;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class FragmentMain extends Fragment implements View.OnClickListener {

    TextView textView;
    TextView textViewLecture;
    ImageView imageViewMenu;
    private Handler mHandler;
    DatabaseReference myRef;

    final int[] belltime = {30600, 36300, 36900, 42600, 44400, 50100, 50700, 56400, 57600, 63300, 63900, 69600, 70200, 75900};

    @SuppressLint("HandlerLeak")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        SharedPreferences SharedPreferencesID;
        SharedPreferencesID = getActivity().getSharedPreferences("sID", Context.MODE_PRIVATE);
        String ans1 = SharedPreferencesID.getString("sID", String.valueOf(Context.MODE_PRIVATE));
        myRef = database.getReference("users/" + ans1);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseClassUser value = dataSnapshot.getValue(FirebaseClassUser.class);

                SharedPreferences SharedPreferencesID;
                SharedPreferencesID = getActivity().getSharedPreferences("sID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = SharedPreferencesID.edit();
                editor2.putString("specialty", value.specialty);
                editor2.putString("listRole", value.role);
                editor2.apply();
            }


            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        });

        textView = rootView.findViewById(R.id.textView);
        textViewLecture = rootView.findViewById(R.id.textViewLecture);
        imageViewMenu = rootView.findViewById(R.id.imageViewMenu);
        imageViewMenu.setOnClickListener(this);

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handlerUpdate();
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }
        };
        mHandler.sendEmptyMessage(0);
        return rootView;
    }


    public void handlerUpdate(){

        DateTime datetime = DateTime.now();

        int currentTime;
        int timeToLecture;

        int timeToLectureHour;
        int timeToLectureMin;
        int timeToLectureSec;

        int hour = datetime.getHourOfDay();
        int min = datetime.getMinuteOfHour();
        int sec = datetime.getSecondOfMinute();

        int belltimeToLecture = 0;

        int Lecture = 0;

        currentTime = hour * 60 * 60 + min * 60 + sec;

        for (int i = 0; i < 14; i++) {

            if (i % 2 == 0) {
                Lecture++;
                textViewLecture.setText("До начала " + Lecture + " пары:");
            }else{
                textViewLecture.setText("До конца " + Lecture + " пары:");
            }


            if (belltime[i] > currentTime) {
                belltimeToLecture = belltime[i];
                break;
            }

            if(i == 13){
                textViewLecture.setText("Пары закончены");
                textView.setVisibility(View.GONE);
            }else{
                textView.setVisibility(View.VISIBLE);
            }
        }

        timeToLecture = belltimeToLecture - currentTime;

        timeToLectureHour = timeToLecture / 3600;
        timeToLectureMin = timeToLecture / 60 - timeToLectureHour * 60;
        timeToLectureSec = timeToLecture - timeToLectureHour * 3600 - timeToLectureMin * 60;

        String StringTimeToLectureHour;
        String StringTimeToLectureMin;
        String StringTimeToLectureSec;

        if (timeToLectureHour < 10) {
            StringTimeToLectureHour = "0" + timeToLectureHour + "ч. ";
        } else {
            StringTimeToLectureHour = timeToLectureHour + "ч. ";
        }
        if (timeToLectureMin < 10) {
            StringTimeToLectureMin = "0" + timeToLectureMin + "м. ";
        } else {
            StringTimeToLectureMin = timeToLectureMin + "м. ";
        }
        if (timeToLectureSec < 10) {
            StringTimeToLectureSec = "0" + timeToLectureSec + "с.";
        } else {
            StringTimeToLectureSec = timeToLectureSec + "с.";
        }

        if (StringTimeToLectureHour.equals("00ч. ") && StringTimeToLectureMin.equals("00м. ")) {
            textView.setText(StringTimeToLectureSec);
        } else {
            if (StringTimeToLectureHour.equals("00ч. ")) {
                textView.setText(StringTimeToLectureMin + StringTimeToLectureSec);
            } else {
                textView.setText(StringTimeToLectureHour + StringTimeToLectureMin + StringTimeToLectureSec);
            }
        }
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageViewMenu:
                goFragment(1);
                break;
        }
    }

    public void goFragment(int index) {
        ChangeFragment listener = (ChangeFragment) getActivity();
        listener.onButtonSelected(index);
    }


    public interface ChangeFragment {
        void onButtonSelected(int fragmentIndex);
    }
}































