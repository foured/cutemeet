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
import android.widget.ImageView;
import android.widget.Toast;

import com.foured.cutemeet.algorithms.RegistrationFieldsChecker;
import com.foured.cutemeet.config.ConstStrings;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordRecoveryScreen_2_2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PasswordRecoveryScreen_2_2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PasswordRecoveryScreen_2_2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PasswordRecoveryScreen_2_2.
     */
    // TODO: Rename and change types and number of parameters
    public static PasswordRecoveryScreen_2_2 newInstance(String param1, String param2) {
        PasswordRecoveryScreen_2_2 fragment = new PasswordRecoveryScreen_2_2();
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
        return inflater.inflate(R.layout.fragment_password_recovery_screen_2_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((EditText) view.findViewById(R.id.passwordRecoveryPanel_2_2_enterEmailEditText)).getText().clear();
        view.findViewById(R.id.passwordRecoveryPanel_2_2_backButton)
                .setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_passwordRecoveryScreen_2_2_to_passwordRecoveryScreen_1));
        //R.id.action_passwordRecoveryScreen_2_2_to_passwordRecoveryScreen_3
        view.findViewById(R.id.passwordRecoveryPanel_2_2_nextButton)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText eET = view.findViewById(R.id.passwordRecoveryPanel_2_2_enterEmailEditText);

                        if(RegistrationFieldsChecker.isEmailAddress(eET.getText())){
                            Navigation.findNavController(view).navigate(R.id.action_passwordRecoveryScreen_2_2_to_passwordRecoveryScreen_3);
                        }
                        else {
                            Toast.makeText(view.getContext(), ConstStrings.wrongRegistrationLine, Toast.LENGTH_LONG).show();
                        }
                    }
                });

        ((EditText) view.findViewById(R.id.passwordRecoveryPanel_2_2_enterEmailEditText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(RegistrationFieldsChecker.isEmailAddress(s)){
                    ((ImageView) view.findViewById(R.id.passwordRecoveryPanel_2_2_emailEditText_correctImage)).setImageResource(R.drawable.correctlineimage);
                }
                else{
                    ((ImageView) view.findViewById(R.id.passwordRecoveryPanel_2_2_emailEditText_correctImage)).setImageResource(R.drawable.wronglineimage);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}