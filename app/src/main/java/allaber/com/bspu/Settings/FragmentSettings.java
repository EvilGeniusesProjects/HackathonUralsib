package allaber.com.bspu.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import allaber.com.bspu.Authentication.FirebaseClassUser;
import allaber.com.bspu.R;

public class FragmentSettings extends Fragment implements View.OnClickListener {
    private static final String TAG = "FragmentSettings";

    ImageView imageViewBack;
    Button buttonExit;
    Button buttonChange;

    TextView text1;
    TextView text2;
    TextView text3;
    DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();



        SharedPreferences SharedPreferencesID;
        SharedPreferencesID = getActivity().getSharedPreferences("sID", Context.MODE_PRIVATE);
        String ans1 = SharedPreferencesID.getString("sID", String.valueOf(Context.MODE_PRIVATE));
        myRef = database.getReference("users/" + ans1);

        text1 = rootView.findViewById(R.id.email);
        text2 = rootView.findViewById(R.id.name);
        text3 = rootView.findViewById(R.id.lastname);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseClassUser value = dataSnapshot.getValue(FirebaseClassUser.class);
                text1.setText(value.email);
                text2.setText(value.name);
                text3.setText(value.lastname);
            }


            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        });


        imageViewBack = rootView.findViewById(R.id.imageViewBack);
        buttonExit = rootView.findViewById(R.id.exit);
        buttonChange = rootView.findViewById(R.id.change_user);

        imageViewBack.setOnClickListener(this);
        buttonExit.setOnClickListener(this);
        buttonChange.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageViewBack:
                goFragment(1);
                break;
            case R.id.exit:
                getActivity().finish();
                break;
            case R.id.change_user:
                FirebaseAuth.getInstance().signOut();
                SharedPreferences SharedPreferencesID;
                SharedPreferencesID = getActivity().getSharedPreferences("sID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = SharedPreferencesID.edit();
                editor2.putString("sID", null);
                editor2.apply();
                goFragment(6);
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
