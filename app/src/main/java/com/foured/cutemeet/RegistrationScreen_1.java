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

import com.foured.cutemeet.algorithms.RegistrationFieldsChecker;
import com.foured.cutemeet.config.ConstStrings;
import com.foured.cutemeet.models.UserAccountData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationScreen_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationScreen_1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistrationScreen_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationScreen_1.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationScreen_1 newInstance(String param1, String param2) {
        RegistrationScreen_1 fragment = new RegistrationScreen_1();
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
        return inflater.inflate(R.layout.fragment_registration_screen_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((EditText) view.findViewById(R.id.registrationPanel_1_surnameEditText)).getText().clear();
        ((EditText) view.findViewById(R.id.registrationPanel_1_nameEditText)).getText().clear();
        ((EditText) view.findViewById(R.id.registrationPanel_1_middleNameEditText)).getText().clear();

        view.findViewById(R.id.registrationPanel_1_loginButton)
                .setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_registrationScreen_1_to_logInScreen));
        //Navigation.createNavigateOnClickListener(R.id.action_registrationScreen_1_to_registrationScreen_2)
        view.findViewById( R.id.registrationPanel_1_nextButton)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText snET = view.findViewById(R.id.registrationPanel_1_surnameEditText);
                        EditText nET = view.findViewById(R.id.registrationPanel_1_nameEditText);

                        if(RegistrationFieldsChecker.isTextLine(snET.getText()) && RegistrationFieldsChecker.isTextLine(nET.getText())){
                            EditText mnET = view.findViewById(R.id.registrationPanel_1_middleNameEditText);
                            Bundle bundle = new Bundle();
                            UserAccountData uad = new UserAccountData();
                            uad.surname = String.valueOf(snET.getText());
                            uad.name = String.valueOf(nET.getText());
                            uad.middleName = String.valueOf(mnET.getText());
                            bundle.putSerializable("user_account_data", uad);
                            Navigation.findNavController(view).navigate(R.id.action_registrationScreen_1_to_registrationScreen_2, bundle);
                        }
                        else{
                            Toast.makeText(view.getContext(), ConstStrings.wrongRegistrationLine, Toast.LENGTH_LONG).show();
                        }
                    }
                });

        ((EditText) view.findViewById(R.id.registrationPanel_1_surnameEditText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(RegistrationFieldsChecker.isTextLine(s)){
                    ((ImageView) view.findViewById(R.id.registrationPanel_1_surnameEditText_correctImage)).setImageResource(R.drawable.correctlineimage);
                }
                else{
                    ((ImageView) view.findViewById(R.id.registrationPanel_1_surnameEditText_correctImage)).setImageResource(R.drawable.wronglineimage);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ((EditText) view.findViewById(R.id.registrationPanel_1_nameEditText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(RegistrationFieldsChecker.isTextLine(s)){
                    ((ImageView) view.findViewById(R.id.registrationPanel_1_nameEditText_correctImage)).setImageResource(R.drawable.correctlineimage);
                }
                else{
                    ((ImageView) view.findViewById(R.id.registrationPanel_1_nameEditText_correctImage)).setImageResource(R.drawable.wronglineimage);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}