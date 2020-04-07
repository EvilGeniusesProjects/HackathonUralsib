package allaber.com.bspu.Calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import allaber.com.bspu.R;

public class FragmentCalculator extends Fragment implements View.OnClickListener {

    ImageView imageViewMenu;

    TextView textViewCalculatorScreen;
    TextView textViewAverageMark;
    TextView textViewThrowOff;

    Button button2;
    Button button3;
    Button button4;
    Button button5;

    double gradePointAverage;
    String stringCalculatorScreen = "";
    double points = 0;
    int count = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calculator, container, false);

        imageViewMenu = rootView.findViewById(R.id.imageViewMenu);

        textViewCalculatorScreen = rootView.findViewById(R.id.textViewCalculatorScreen);
        textViewAverageMark = rootView.findViewById(R.id.textViewAverageMark);
        textViewThrowOff = rootView.findViewById(R.id.textViewThrowOff);

        button2 = rootView.findViewById(R.id.button2);
        button3 = rootView.findViewById(R.id.button3);
        button4 = rootView.findViewById(R.id.button4);
        button5 = rootView.findViewById(R.id.button5);
        imageViewMenu.setOnClickListener(this);
        textViewThrowOff.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                counting(2);
                break;
            case R.id.button3:
                counting(3);
                break;
            case R.id.button4:
                counting(4);
                break;
            case R.id.button5:
                counting(5);
                break;
            case R.id.imageViewMenu:
                goFragment(1);
                break;
            case R.id.textViewThrowOff:
                points = 0;
                count = 0;
                gradePointAverage = 0;
                stringCalculatorScreen = "";
                textViewCalculatorScreen.setText("");
                textViewAverageMark.setText("Cредний балл");
                break;
        }

    }


    public void counting(int index){

        points = points + index;
        stringCalculatorScreen = stringCalculatorScreen + " + " + index;

        if(count == 0){
            stringCalculatorScreen = "" + index;
        }

        count++;


        textViewCalculatorScreen.setText("(" + stringCalculatorScreen + ") / " + count);
        gradePointAverage = points / count;
        textViewAverageMark.setText("" + Math.round(gradePointAverage * 100.0) / 100.0);
    }

    public void goFragment(int index) {
        ChangeFragment listener = (ChangeFragment) getActivity();
        listener.onButtonSelected(index);
    }


    public interface ChangeFragment {
        void onButtonSelected(int fragmentIndex);
    }
}


















