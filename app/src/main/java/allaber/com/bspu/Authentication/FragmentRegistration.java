package allaber.com.bspu.Authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import allaber.com.bspu.R;

public class FragmentRegistration extends Fragment implements View.OnClickListener {

    private final String TAG = "FragmentRegistration";
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String AuthenticationID;
    public EditText editTextName;
    public EditText editTextLastname;
    public EditText editTextEmail;
    public EditText editTextPassword;
    public Button buttonRegistration;

    public Spinner spinnerRole;
    public Spinner spinnerСourse;
    public Spinner spinnerFaculty;
    public Spinner spinnerSpecialty;

    public ImageView imageViewBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);
        editTextName = rootView.findViewById(R.id.editTextName);
        editTextLastname = rootView.findViewById(R.id.editTextLastname);
        editTextEmail = rootView.findViewById(R.id.editTextEmail);
        editTextPassword = rootView.findViewById(R.id.editTextPassword);
        buttonRegistration = rootView.findViewById(R.id.textViewRegistration);
        buttonRegistration.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        imageViewBack = rootView.findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(this);

        spinnerRole = rootView.findViewById(R.id.spinnerRole);
        spinnerСourse = rootView.findViewById(R.id.spinnerСourse);
        spinnerFaculty = rootView.findViewById(R.id.spinnerFaculty);
        spinnerSpecialty = rootView.findViewById(R.id.spinnerSpecialty);


        ArrayAdapter<?> adapterRole = ArrayAdapter.createFromResource(getContext(), R.array.listRole, android.R.layout.simple_spinner_item);
        adapterRole.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapterRole);

        ArrayAdapter<?> adapterCourse = ArrayAdapter.createFromResource(getContext(), R.array.listCourse, android.R.layout.simple_spinner_item);
        adapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerСourse.setAdapter(adapterCourse);

        ArrayAdapter<?> adapterFaculty = ArrayAdapter.createFromResource(getContext(), R.array.listFaculty, android.R.layout.simple_spinner_item);
        adapterFaculty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFaculty.setAdapter(adapterFaculty);

        ArrayAdapter<?> adapterSpecialty = ArrayAdapter.createFromResource(getContext(), R.array.listSpecialty, android.R.layout.simple_spinner_item);
        adapterSpecialty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialty.setAdapter(adapterSpecialty);


        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                String string = spinnerRole.getSelectedItem().toString();
                if (string.equals("Преподаватель")) {
                    spinnerRole.setBackgroundResource(R.drawable.style_edit_bot);
                    spinnerСourse.setVisibility(View.GONE);
                    spinnerFaculty.setVisibility(View.GONE);
                    spinnerSpecialty.setVisibility(View.GONE);
                }

                if (string.equals("Студент")) {
                    spinnerRole.setBackgroundResource(R.drawable.style_edit);
                    spinnerСourse.setVisibility(View.VISIBLE);
                    spinnerFaculty.setVisibility(View.VISIBLE);
                    spinnerSpecialty.setVisibility(View.VISIBLE);
                }


            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewRegistration:
                if (checkField() && checkSpinner()) {
                    createAccount(String.valueOf(editTextEmail.getText()), String.valueOf(editTextPassword.getText()));
                }
                break;
            case R.id.imageViewBack:
                ChangeFragment listener = (ChangeFragment) getActivity();
                listener.onButtonSelected(6);
                break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            AuthenticationID = user.getUid();
                            writeNewUser();
                        } else {
                            Snackbar.make(getView(), "Ошибка: недопустимый формат полей", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    }
                });
    }

    private void writeNewUser() {
        FirebaseClassUser user = new FirebaseClassUser();
        user.email = String.valueOf(editTextEmail.getText());
        user.password = String.valueOf(editTextPassword.getText());
        user.lastname = String.valueOf(editTextLastname.getText());
        user.name = String.valueOf(editTextName.getText());
        user.role = spinnerRole.getSelectedItem().toString();

        if (spinnerRole.getSelectedItem().toString().equals("Преподаватель")) {
            user.course = "-";
            user.faculty = "-";
            user.specialty = "-";
        } else {
            user.course = spinnerСourse.getSelectedItem().toString();
            user.faculty = spinnerFaculty.getSelectedItem().toString();
            user.specialty = spinnerSpecialty.getSelectedItem().toString();
        }
        myRef.child("users/" + AuthenticationID).setValue(user);
        ChangeFragment listener = (ChangeFragment) getActivity();
        listener.onButtonSelected(2);


        SharedPreferences SharedPreferencesID;
        SharedPreferencesID = getActivity().getSharedPreferences("sID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = SharedPreferencesID.edit();
        editor2.putString("sID", AuthenticationID);
        editor2.putString("specialty", user.specialty);
        editor2.putString("listRole", user.role);
        editor2.apply();
    }

    public interface ChangeFragment {
        void onButtonSelected(int fragmentIndex);
    }

    public boolean checkField() {
        boolean boolInf = false;
        if ((editTextName.getText().length() != 0)) {
            if ((editTextLastname.getText().length() != 0)) {
                if ((editTextEmail.getText().length() != 0)) {
                    if ((editTextPassword.getText().length() != 0)) {
                        boolInf = true;
                    } else {
                        Snackbar.make(getView(), "Ошибка: заполните поле \"Пароль\"", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                } else {
                    Snackbar.make(getView(), "Ошибка: заполните поле \"Email\"", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            } else {
                Snackbar.make(getView(), "Ошибка: заполниет поле \"Фамилия\"", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        } else {
            Snackbar.make(getView(), "Ошибка: заполниет поле \"Имя\"", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
        return boolInf;
    }


    public boolean checkSpinner() {
        boolean boolInf = false;
        String stringRole = spinnerRole.getSelectedItem().toString();
        String stringСourse = spinnerСourse.getSelectedItem().toString();
        String stringFaculty = spinnerFaculty.getSelectedItem().toString();
        String stringSpecialty = spinnerSpecialty.getSelectedItem().toString();

        if (stringRole.equals("Преподаватель")) {
            boolInf = true;
        } else {
            if (!stringRole.equals("Тип пользователя")) {
                if (!stringСourse.equals("Курс")) {
                    if (!stringFaculty.equals("Факультет")) {
                        if (!stringSpecialty.equals("Специальность")) {
                            boolInf = true;
                        } else {
                            Snackbar.make(getView(), "Ошибка: выбирите \"Специальность\"", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    } else {
                        Snackbar.make(getView(), "Ошибка: выбирите \"Факультет\"", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                } else {
                    Snackbar.make(getView(), "Ошибка: выбирите \"Курс\"", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            } else {
                Snackbar.make(getView(), "Ошибка: выбирите \"Тип пользователя\"", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }

        return boolInf;
    }
}
