package com.foured.cutemeet;

import android.graphics.drawable.AnimatedVectorDrawable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.foured.cutemeet.algorithms.RegistrationFieldsChecker;
import com.foured.cutemeet.config.ConstStrings;
import com.foured.cutemeet.models.UserData;
import com.foured.cutemeet.net.SpringSecurityClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationScreen_4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationScreen_4 extends Fragment {

    private UserData uad;

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
            uad = (UserData) getArguments().getSerializable("user_account_data");
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

        TextView logText = view.findViewById(R.id.registrationPanel_4_logText);
        logText.setText("");

        ImageView loadingImage = view.findViewById(R.id.registrationPanel_4_loadingImage);
        loadingImage.setVisibility(View.GONE);
        AnimatedVectorDrawable loadingAVD = (AnimatedVectorDrawable) loadingImage.getDrawable();

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
                    logText.setText("");
                    loadingImage.setVisibility(View.VISIBLE);
                    loadingAVD.start();

                    uad.password = String.valueOf(pET.getText());
                    uad.userName = String.valueOf(unET.getText());
                    String jsonUser = uad.toJsonString();

                    String url1 = ConstStrings.serverAddress + "/operations/check_checkUsername";
                    List<SpringSecurityClient.Pair> params = new ArrayList<>();
                    params.add(new SpringSecurityClient.Pair("username", uad.userName));
                    CompletableFuture<String> future1 = SpringSecurityClient.get_nc_async(url1, params);

                    future1.thenAcceptAsync(result -> {
                        if(result.equals("")){
                            String url2 = ConstStrings.serverAddress + "/operations/new_user";
                            CompletableFuture<String> future2 = SpringSecurityClient.post_nc_async(url2, jsonUser);

                            future2.thenAccept(res -> {
                                getActivity().runOnUiThread(() -> {
                                    loadingAVD.stop();
                                    loadingImage.setVisibility(View.GONE);
                                });
                                Navigation.findNavController(view).navigate(R.id.action_registrationScreen_4_to_logInScreen);
                            });
                        }
                        else{
                            getActivity().runOnUiThread(() -> {
                                loadingAVD.stop();
                                loadingImage.setVisibility(View.GONE);
                                logText.setText(result);
                            });
                        }
                    });
                }
                else{
                    getActivity().runOnUiThread(() -> {
                        loadingAVD.stop();
                        loadingImage.setVisibility(View.GONE);
                    });
                    Toast.makeText(view.getContext(), ConstStrings.wrongRegistrationLine, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}