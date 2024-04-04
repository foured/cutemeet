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

import com.foured.cutemeet.algorithms.RegistrationFieldsChecker;
import com.foured.cutemeet.models.UserAccountData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetAccountDataPanel_2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetAccountDataPanel_2 extends Fragment {
    private UserAccountData uad;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SetAccountDataPanel_2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetAccountDataPanel_2.
     */
    // TODO: Rename and change types and number of parameters
    public static SetAccountDataPanel_2 newInstance(String param1, String param2) {
        SetAccountDataPanel_2 fragment = new SetAccountDataPanel_2();
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

            uad = (UserAccountData) getArguments().getSerializable("user_account_data_2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_account_data_panel_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText educationPlaceET = view.findViewById(R.id.setAccountDataPanel_2_educationPlaceEditText);
        educationPlaceET.getText().clear();
        EditText birthdayDateET = view.findViewById(R.id.setAccountDataPanel_2_birthdayDateEditText);
        birthdayDateET.getText().clear();
        EditText tgIDET = view.findViewById(R.id.setAccountDataPanel_2_tgLinkEditText);
        tgIDET.getText().clear();

        educationPlaceET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() >= 3){
                    ((ImageView) view.findViewById(R.id.setAccountDataPanel_2_educationPlaceEditText_correctImage)).setImageResource(R.drawable.correctlineimage);
                }
                else{
                    ((ImageView) view.findViewById(R.id.setAccountDataPanel_2_educationPlaceEditText_correctImage)).setImageResource(R.drawable.wronglineimage);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        birthdayDateET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(RegistrationFieldsChecker.isDate(s)){
                    ((ImageView) view.findViewById(R.id.setAccountDataPanel_2_birthdayDateEditText_correctImage)).setImageResource(R.drawable.correctlineimage);
                }
                else{
                    ((ImageView) view.findViewById(R.id.setAccountDataPanel_2_birthdayDateEditText_correctImage)).setImageResource(R.drawable.wronglineimage);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tgIDET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() >= 5){
                    ((ImageView) view.findViewById(R.id.setAccountDataPanel_2_tgLinkEditText_correctImage)).setImageResource(R.drawable.correctlineimage);
                }
                else{
                    ((ImageView) view.findViewById(R.id.setAccountDataPanel_2_tgLinkEditText_correctImage)).setImageResource(R.drawable.wronglineimage);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ((ImageButton) view.findViewById(R.id.setAccountDataPanel_2_backButton))
                .setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_setAccountDataPanel_2_to_setAccountDataPanel_1));

        ((ImageButton) view.findViewById(R.id.setAccountDataPanel_2_nextButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(educationPlaceET.getText().length() >= 3 && RegistrationFieldsChecker.isDate(birthdayDateET.getText()) && tgIDET.getText().length() >= 5){
                    uad.educationPlace = String.valueOf(educationPlaceET.getText());
                    uad.birthdayDate = String.valueOf(birthdayDateET.getText());
                    uad.tgLink = String.valueOf(tgIDET.getText());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user_account_data_2", uad);

                    Navigation.findNavController(view).navigate(R.id.action_setAccountDataPanel_2_to_setAccountDataPanel_3, bundle);
                }
            }
        });
    }
}