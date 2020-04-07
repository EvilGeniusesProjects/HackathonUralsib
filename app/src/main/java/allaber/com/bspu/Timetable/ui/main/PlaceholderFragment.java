package allaber.com.bspu.Timetable.ui.main;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import allaber.com.bspu.R;
import allaber.com.bspu.Timetable.ClassTimetable;
import allaber.com.bspu.DatabaseHelper;
import allaber.com.bspu.Timetable.MyRecyclerViewAdapterTimetable;


public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    MyRecyclerViewAdapterTimetable adapter;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    int week;
    RecyclerView recyclerView;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_activity_timetable, container, false);



        DateTime datetime = DateTime.now();
        week = datetime.getWeekOfWeekyear();
        week = week;

        mDBHelper = new DatabaseHelper(getContext());
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        recyclerView = root.findViewById(R.id.recyclerView);

        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                List<ClassTimetable> timetables = new ArrayList<>();
                Cursor cursor = mDb.rawQuery("SELECT * FROM ISIT1119", null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    ClassTimetable classTimetable = new ClassTimetable();
                    classTimetable.setDayOfTheWeek(cursor.getString(0));
                    classTimetable.setCouple(cursor.getString(1));
                    classTimetable.setCoupleTime(cursor.getString(2));
                    classTimetable.setItemName(cursor.getString(3));
                    classTimetable.setaWeek(cursor.getString(4));
                    classTimetable.setSubgroup(cursor.getString(5));
                    classTimetable.setKindOfCouple(cursor.getString(6));
                    classTimetable.setTheAudience(cursor.getString(7));
                    classTimetable.setTeacher(cursor.getString(8));
                    classTimetable.setCoupleTimeStart(cursor.getString(9));
                    classTimetable.setCoupleTimeStartEnd(cursor.getString(10));
                    if (checkForWeek(cursor.getString(4), week + "") && (Integer.valueOf(cursor.getString(0)) == Integer.valueOf(s)) && (Integer.valueOf(cursor.getString(2)) != Integer.valueOf(2))) {
                        timetables.add(classTimetable);
                    }
                    cursor.moveToNext();
                }
                cursor.close();

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new MyRecyclerViewAdapterTimetable(getContext(), timetables);
                recyclerView.setAdapter(adapter);
            }
        });
        return root;
    }

    boolean checkForWeek(String line, String week) {
        return line.contains(week);
    }
}