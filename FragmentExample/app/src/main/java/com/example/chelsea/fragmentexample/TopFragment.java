package com.example.chelsea.fragmentexample;

/**
 * Created by Chelsea on 11/2/16.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

public class TopFragment extends Fragment {

    private static EditText topTextInput;
    private static EditText bottomTextInput;

    TopFragmentListener activityCommander;

    public interface TopFragmentListener{
        void CreateMeme(String top, String bottom);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCommander=(TopFragmentListener)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_fragment,container,false);

        topTextInput=(EditText)view.findViewById(R.id.topTextInput);
        bottomTextInput=(EditText)view.findViewById(R.id.bottomTextInput);
        final Button button=(Button)view.findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        buttonClicked(view);

                    }

                }
        );
        return view;
    }

    public void buttonClicked(View view){
        activityCommander.CreateMeme(topTextInput.getText().toString(),bottomTextInput.getText().toString());

    }
}
