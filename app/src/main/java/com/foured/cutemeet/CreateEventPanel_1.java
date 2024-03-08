package com.foured.cutemeet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.foured.cutemeet.config.ConstStrings;
import com.foured.cutemeet.models.EventData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateEventPanel_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventPanel_1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateEventPanel_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment createEventPanel_1.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventPanel_1 newInstance(String param1, String param2) {
        CreateEventPanel_1 fragment = new CreateEventPanel_1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_event_panel_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton nextButton = view.findViewById(R.id.createEventsPanel_1_nextButton);
        ImageButton backButton = view.findViewById(R.id.createEventsPanel_1_backButton);

        EditText enET = view.findViewById(R.id.createEventsPanel_1_eventNameEditText);
        EditText edET = view.findViewById(R.id.createEventsPanel_1_eventDateEditText);
        EditText elET = view.findViewById(R.id.createEventsPanel_1_eventLocationEditText);

        enET.getText().clear();
        edET.getText().clear();
        elET.getText().clear();

        enET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() >= 1){
                    ((ImageView) view.findViewById(R.id.createEventsPanel_1_eventNameEditText_correctImage)).setImageResource(R.drawable.correctlineimage);
                }
                else{
                    ((ImageView) view.findViewById(R.id.createEventsPanel_1_eventNameEditText_correctImage)).setImageResource(R.drawable.wronglineimage);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() >= 1){
                    ((ImageView) view.findViewById(R.id.createEventsPanel_1_eventDateEditText_correctImage)).setImageResource(R.drawable.correctlineimage);
                }
                else{
                    ((ImageView) view.findViewById(R.id.createEventsPanel_1_eventDateEditText_correctImage)).setImageResource(R.drawable.wronglineimage);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        elET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() >= 1){
                    ((ImageView) view.findViewById(R.id.createEventsPanel_1_eventLocationEditText_correctImage)).setImageResource(R.drawable.correctlineimage);
                }
                else{
                    ((ImageView) view.findViewById(R.id.createEventsPanel_1_eventLocationEditText_correctImage)).setImageResource(R.drawable.wronglineimage);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(enET.getText().length() >= 1
                && edET.getText().length() >= 1
                && elET.getText().length() >= 1)
                {
                    EventData eventData = new EventData();
                    eventData.name = String.valueOf(enET.getText());
                    eventData.date = String.valueOf(edET.getText());
                    eventData.location = String.valueOf(elET.getText());

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("event_data", eventData);
                    Navigation.findNavController(view).navigate(R.id.action_createEventPanel_1_to_createEventPanel_2, bundle);
                }
                else{
                    Toast.makeText(view.getContext(), ConstStrings.wrongRegistrationLine, Toast.LENGTH_LONG).show();
                }
            }
        });
        backButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_createEventPanel_1_to_events));
    }
}