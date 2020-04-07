package allaber.com.bspu.News;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import allaber.com.bspu.DatabaseHelper;
import allaber.com.bspu.R;

public class FragmentNews extends Fragment implements MyRecyclerViewAdapterNews.ItemClickListener, View.OnClickListener {

    MyRecyclerViewAdapterNews adapter;
    ImageView imageViewMenu;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    List<ClassNews> newsList = new ArrayList<ClassNews>();
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
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
        recyclerView = rootView.findViewById(R.id.recyclerView);
        readDB();


        imageViewMenu = rootView.findViewById(R.id.imageViewMenu);
        imageViewMenu.setOnClickListener(this);
        return rootView;
    }


    public void readDB(){
        Cursor cursor = mDb.rawQuery("SELECT * FROM news", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
                ClassNews news = new ClassNews();
                news.image = cursor.getString(0);
                news.date = cursor.getString(1);
                news.name = cursor.getString(2);
                news.description = cursor.getString(3);
                newsList.add(news);
                cursor.moveToNext();
        }
        cursor.close();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewAdapterNews(getContext(), newsList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {


        ClassNews news = newsList.get(position);
        Intent intent = new Intent(getContext(), ActivityNews.class);
        intent.putExtra("image", news.image);
        intent.putExtra("description", news.description);
        startActivity(intent);
        //Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
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
