package allaber.com.bspu.Сhat;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import allaber.com.bspu.Authentication.FirebaseClassUser;
import allaber.com.bspu.R;

public class FragmentChat extends Fragment implements View.OnClickListener {


    private static final String TAG = "ActivityChat";

    RecyclerView recyclerView;
    EditText editTextMessage;
    DatabaseReference myRef;
    DatabaseReference myRefGeneral;
    boolean message = false;
    FirebaseListAdapter<ClassChatMessage> adapter;
    String nameAndLastname = "name";
    FloatingActionButton floatingActionButton;
    ImageView imageViewBack;
    MediaPlayer note;

    String title;
    TextView textViewTitle;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_chat, container, false);

        floatingActionButton = rootView.findViewById(R.id.floatingActionButton);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        editTextMessage = rootView.findViewById(R.id.editTextMessage);
        floatingActionButton.setOnClickListener(this);
        imageViewBack = rootView.findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(this);
        note = MediaPlayer.create(getContext(), R.raw.note);

        SharedPreferences SharedPreferencesID;
        SharedPreferencesID = getActivity().getSharedPreferences("sID", Context.MODE_PRIVATE);
        title = SharedPreferencesID.getString("specialty", String.valueOf(Context.MODE_PRIVATE));
        textViewTitle = rootView.findViewById(R.id.textView2);
        textViewTitle.setText(title);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String ans = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myRef = database.getReference("users/" + ans);
        myRefGeneral = database.getReference("custom_message/" + title);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseClassUser value = dataSnapshot.getValue(FirebaseClassUser.class);
                nameAndLastname = value.name + " " + value.lastname;
                displayChatMessages();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        });

        return container;
    }


    public void displayChatMessages() {
        ListView listOfMessages = rootView.findViewById(R.id.listView);
        Query query = myRefGeneral;
        FirebaseListOptions<ClassChatMessage> options = new FirebaseListOptions.Builder<ClassChatMessage>().setQuery(query, ClassChatMessage.class).setLayout(R.layout.custom_message).build();
        adapter = new FirebaseListAdapter<ClassChatMessage>(options) {

            @Override
            protected void populateView(View v, ClassChatMessage model, int position) {
                LinearLayout frame = v.findViewById(R.id.frame);
                RelativeLayout relative = v.findViewById(R.id.relative);

                if (model.getMessageUser().equals(nameAndLastname)) {
                    frame.setBackgroundResource(R.drawable.message_my);
                    relative.setGravity(Gravity.RIGHT);
                } else {
                    frame.setBackgroundResource(R.drawable.message_their);
                    relative.setGravity(Gravity.LEFT);
                }

                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("HH:mm dd/MM/yy", model.getMessageTime()));

            }
        };

        listOfMessages.setAdapter(adapter);
        adapter.startListening();

        myRefGeneral.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (message) {
                    long mills = 300L;
                    Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(mills);
                    note.start();
                }
                message = true;
            }


            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
