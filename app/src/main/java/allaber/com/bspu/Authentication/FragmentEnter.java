package allaber.com.bspu.Authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import allaber.com.bspu.R;

public class FragmentEnter extends Fragment implements View.OnClickListener {

    private final String TAG = "FragmentEnter";
    private FirebaseAuth mAuth;
    private String AuthenticationID;
    public EditText editTextEmail;
    public EditText editTextPassword;
    public Button buttonEnter;
    public TextView textViewRegistration;
    public LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_enter, container, false);
        linearLayout = rootView.findViewById(R.id.linearLayout);
        editTextEmail = rootView.findViewById(R.id.editTextEmail);
        editTextPassword = rootView.findViewById(R.id.editTextPassword);
        textViewRegistration = rootView.findViewById(R.id.textViewRegistration);
        buttonEnter = rootView.findViewById(R.id.buttonEnter);
        textViewRegistration.setOnClickListener(this);
        buttonEnter.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonEnter:
                if (checkField() == true) {
                    signIn(String.valueOf(editTextEmail.getText()), String.valueOf(editTextPassword.getText()));
                }
                break;
            case R.id.textViewRegistration:
                ChangeFragment listener = (ChangeFragment) getActivity();
                listener.onButtonSelected(5);
                break;
        }
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            ChangeFragment listener = (ChangeFragment) getActivity();
                            listener.onButtonSelected(2);
                            updateUI(user);
                        } else {
                            Animation sunRiseAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                            linearLayout.startAnimation(sunRiseAnimation);
                            Snackbar.make(getView(), "Ошибка: некорректный логин или пароль", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            SharedPreferences SharedPreferencesID;
            SharedPreferencesID = getActivity().getSharedPreferences("sID", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = SharedPreferencesID.edit();
            editor2.putString("sID", currentUser.getUid());
            editor2.apply();
        }
    }

    public interface ChangeFragment {
        void onButtonSelected(int fragmentIndex);
    }

    public boolean checkField() {
        boolean boolInf = false;
        if ((editTextEmail.getText().length() != 0)) {
            if ((editTextPassword.getText().length() != 0)) {
                boolInf = true;
            } else {
                Snackbar.make(getView(), "Ошибка: заполните поле \"Пароль\"", Snackbar.LENGTH_LONG).show();
            }
        } else {
            Snackbar.make(getView(), "Ошибка: заполните поле \"Email\"", Snackbar.LENGTH_LONG).show();
        }
        return boolInf;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
}















