package com.foured.cutemeet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
 * Use the {@link RegistrationScreen_4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationScreen_4 extends Fragment {

    private UserAccountData uad;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistrationScreen_4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationScreen_4.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationScreen_4 newInstance(String param1, String param2) {
        RegistrationScreen_4 fragment = new RegistrationScreen_4();
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
            uad = (UserAccountData) getArguments().getSerializable("user_account_data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_screen_4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText unET = (EditText) view.findViewById(R.id.registrationPanel_4_usernameEditText);
        EditText pET = (EditText) view.findViewById(R.id.registrationPanel_4_passwordEditText);
        EditText pcET = (EditText) view.findViewById(R.id.registrationPanel_4_passwordConfirmEditText);

        unET.getText().clear();
        pET.getText().clear();
        pcET.getText().clear();
        unET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(RegistrationFieldsChecker.isUsername(s)){
                    ((ImageView) view.findViewById(R.id.registrationPanel_4_usernameEditText_correctImage)).setImageResource(R.drawable.correctlineimage);
                }
                else{
                    ((ImageView) view.findViewById(R.id.registrationPanel_4_usernameEditText_correctImage)).setImageResource(R.drawable.wronglineimage);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(RegistrationFieldsChecker.isPassword(s)){
                    ((ImageView) view.findViewById(R.id.registrationPanel_4_passwordEditText_correctImage)).setImageResource(R.drawable.correctlineimage);
                }
                else{
                    ((ImageView) view.findViewById(R.id.registrationPanel_4_passwordEditText_correctImage)).setImageResource(R.drawable.wronglineimage);
                }

                if(String.valueOf(pcET.getText()).equals(String.valueOf(pET.getText()))){
                    ((ImageView) view.findViewById(R.id.registrationPanel_4_passwordConfirmEditText_correctImage)).setImageResource(R.drawable.correctlineimage);
                }
                else{
                    ((ImageView) view.findViewById(R.id.registrationPanel_4_passwordConfirmEditText_correctImage)).setImageResource(R.drawable.wronglineimage);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pcET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(pcET.getText()).equals(String.valueOf(pET.getText()))){
                    ((ImageView) view.findViewById(R.id.registrationPanel_4_passwordConfirmEditText_correctImage)).setImageResource(R.drawable.correctlineimage);
                }
                else{
                    ((ImageView) view.findViewById(R.id.registrationPanel_4_passwordConfirmEditText_correctImage)).setImageResource(R.drawable.wronglineimage);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        view.findViewById(R.id.registrationPanel_4_compliteRegistrationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RegistrationFieldsChecker.isUsername(unET.getText()) && RegistrationFieldsChecker.isPassword(pET.getText())
                 && String.valueOf(pcET.getText()).equals(String.valueOf(pET.getText()))){
                    uad.password = String.valueOf(pET.getText());
                    uad.userName = String.valueOf(unET.getText());

                    System.out.println(uad.name);
                    System.out.println(uad.surname);
                    System.out.println(uad.middleName);
                    System.out.println(uad.phoneNumber);
                    System.out.println(uad.email);
                    System.out.println(uad.password);
                }
                else{
                    Toast.makeText(view.getContext(), ConstStrings.wrongRegistrationLine, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}