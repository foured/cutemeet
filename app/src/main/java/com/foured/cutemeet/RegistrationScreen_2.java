package com.foured.cutemeet;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.foured.cutemeet.algorithms.RegistrationFieldsChecker;
import com.foured.cutemeet.config.ConstStrings;
import com.foured.cutemeet.models.UserData;
import com.foured.cutemeet.net.SpringSecurityClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationScreen_2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationScreen_2 extends Fragment {

    private UserData uad;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistrationScreen_2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationScreen_2.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationScreen_2 newInstance(String param1, String param2) {
        RegistrationScreen_2 fragment = new RegistrationScreen_2();
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
        return inflater.inflate(R.layout.fragment_registration_screen_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((EditText) view.findViewById(R.id.registrationPanel_2_phoneNumberEditText)).getText().clear();
        ((EditText) view.findViewById(R.id.registrationPanel_2_emailEditText)).getText().clear();

        view.findViewById(R.id.registrationPanel_2_backButton)
                .setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_registrationScreen_2_to_registrationScreen_1));

        TextView logText = view.findViewById(R.id.registrationPanel_2_logText);
        logText.setText("");

        ImageView loadingImage = view.findViewById(R.id.registrationPanel_2_loadingImage);
        loadingImage.setVisibility(View.GONE);
        AnimatedVectorDrawable loadingAVD = (AnimatedVectorDrawable)loadingImage.getDrawable();

        view.findViewById(R.id.registrationPanel_2_nextButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText pnET = view.findViewById(R.id.registrationPanel_2_phoneNumberEditText);
                        EditText eET = view.findViewById(R.id.registrationPanel_2_emailEditText);

                        if(RegistrationFieldsChecker.isPhoneNumber(pnET.getText()) && RegistrationFieldsChecker.isEmailAddress(eET.getText())){
                            logText.setText("");
                            loadingImage.setVisibility(View.VISIBLE);
                            loadingAVD.start();

                            Bundle bundle = new Bundle();
                            uad.phoneNumber = String.valueOf(pnET.getText());
                            uad.email = String.valueOf(eET.getText());
                            bundle.putSerializable("user_account_data", uad);

                            String url1 = ConstStrings.serverAddress + "/operations/check_checkEmailAndPhoneNumber";
                            List<SpringSecurityClient.Pair> params1 = new ArrayList<>();
                            params1.add(new SpringSecurityClient.Pair("email", uad.email));
                            params1.add(new SpringSecurityClient.Pair("phoneNumber", uad.phoneNumber));
                            CompletableFuture<String> future1 = SpringSecurityClient.get_nc_async(url1, params1);

                            future1.thenAcceptAsync(result1 -> {
                                if(result1.equals("")){
                                    String url2 = ConstStrings.serverAddress + "/operations/send_mail";
                                    List<SpringSecurityClient.Pair> params2 = new ArrayList<>();
                                    params2.add(new SpringSecurityClient.Pair("recipient", uad.email));
                                    CompletableFuture<String> future2 = SpringSecurityClient.get_nc_async(url2, params2);

                                    getActivity().runOnUiThread(() -> {
                                        loadingAVD.stop();
                                        loadingImage.setVisibility(View.GONE);
                                    });

                                    Navigation.findNavController(view).navigate(R.id.action_registrationScreen_2_to_registrationScreen_3, bundle);
                                }
                                else{
                                    getActivity().runOnUiThread(() -> {
                                        loadingAVD.stop();
                                        loadingImage.setVisibility(View.GONE);
                                        logText.setText(result1);
                                    });
                                }
                            });

//                            response.thenAcceptAsync(res -> {
//                                getActivity().runOnUiThread(() -> {
//                                    loadingAVD.stop();
//                                    loadingImage.setVisibility(View.GONE);
//                                });
//                                Navigation.findNavController(view).navigate(R.id.action_registrationScreen_2_to_registrationScreen_3, bundle);
//                            }).exceptionally(e -> {
//                                Log.e("Reg panel 3","Error during the asynchronous operation: " + e.getMessage());
//                                return null;
//                            });
                        }
                        else{
                            Toast.makeText(view.getContext(), ConstStrings.wrongRegistrationLine, Toast.LENGTH_LONG).show();
                        }
                    }
                });

        ((EditText) view.findViewById(R.id.registrationPanel_2_phoneNumberEditText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(RegistrationFieldsChecker.isPhoneNumber(s)){
                    ((ImageView) view.findViewById(R.id.registrationPanel_2_phoneNumberEditText_correctImage)).setImageResource(R.drawable.correctlineimage);
                }
                else{
                    ((ImageView) view.findViewById(R.id.registrationPanel_2_phoneNumberEditText_correctImage)).setImageResource(R.drawable.wronglineimage);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ((EditText) view.findViewById(R.id.registrationPanel_2_emailEditText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(RegistrationFieldsChecker.isEmailAddress(s)){
                    ((ImageView) view.findViewById(R.id.registrationPanel_2_emailEditText_correctImage)).setImageResource(R.drawable.correctlineimage);
                }
                else{
                    ((ImageView) view.findViewById(R.id.registrationPanel_2_emailEditText_correctImage)).setImageResource(R.drawable.wronglineimage);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}