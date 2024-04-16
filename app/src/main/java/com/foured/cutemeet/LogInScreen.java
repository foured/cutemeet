package com.foured.cutemeet;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.foured.cutemeet.config.ConstStrings;
import com.foured.cutemeet.net.AuthenticationException;
import com.foured.cutemeet.net.SpringSecurityClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogInScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogInScreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LogInScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogInScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static LogInScreen newInstance(String param1, String param2) {
        LogInScreen fragment = new LogInScreen();
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
        return inflater.inflate(R.layout.fragment_log_in_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText unET = view.findViewById(R.id.logInPanel_userDataEditText);
        unET.getText().clear();
        EditText pET = view.findViewById(R.id.logInPanel_passwordEditText);
        pET.getText().clear();

        TextView logText = view.findViewById(R.id.logInPanel_logText);
        logText.setText("");

        view.findViewById(R.id.logInPanel_forgotPasswordButton)
                .setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_logInScreen_to_passwordRecoveryScreen_1));
        view.findViewById(R.id.logInPanel_registerButton)
                .setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_logInScreen_to_registrationScreen_1));

        ImageButton loginButton = view.findViewById(R.id.logInPanel_loginButton);

        ImageView loadingImage = view.findViewById(R.id.logInPanel_loadingImage);
        loadingImage.setVisibility(View.GONE);
        AnimatedVectorDrawable loadingAVD = (AnimatedVectorDrawable) loadingImage.getDrawable();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logText.setText("");
                loadingImage.setVisibility(View.VISIBLE);
                loadingAVD.start();

                String username = String.valueOf(unET.getText());
                String password = String.valueOf(pET.getText());

                List<SpringSecurityClient.Pair> params = new ArrayList<>();
                params.add(new SpringSecurityClient.Pair("username", username));
                params.add(new SpringSecurityClient.Pair("password", password));
                String url = ConstStrings.serverAddress + "/operations/check_password";
                CompletableFuture<String> result1 = SpringSecurityClient.get_nc_async(url, params);

                result1.thenAcceptAsync(res -> {
                    Log.i("LOGIN", res);
                    String url2 = ConstStrings.serverAddress + "/login";
                    try {
                        SpringSecurityClient client = SpringSecurityClient.login_ns(url2, username, password);
                        client.saveCookiesToSharedPreferences(getContext());

                        SharedPreferences sharedPreferences = getContext().getSharedPreferences(ConstStrings.sharedPreferencesUserDataPath, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(ConstStrings.sharedPreferences_usernameKey, username);
                        editor.putString(ConstStrings.sharedPreferences_passwordKey, password);
                        editor.apply();

                        getActivity().runOnUiThread(() -> {
                            loadingAVD.stop();
                            loadingImage.setVisibility(View.GONE);
                        });

                        Navigation.findNavController(view).navigate(R.id.action_logInScreen_to_account);
                    }
                    catch (AuthenticationException ae){
                        Log.w("LOGIN", "Login error: \n" + ae);

                        getActivity().runOnUiThread(() -> {
                            logText.setText("Неправильный логин или пароль.");
                            loadingAVD.stop();
                            loadingImage.setVisibility(View.GONE);
                        });

                    }catch (IOException ioe){
                        Log.w("LOGIN", "HTTP error: \n" + ioe);

                        getActivity().runOnUiThread(() -> {
                            loadingAVD.stop();
                            loadingImage.setVisibility(View.GONE);
                        });
                    }
                });
            }
        });
    }
}