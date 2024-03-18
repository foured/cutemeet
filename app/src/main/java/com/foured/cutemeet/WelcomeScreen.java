package com.foured.cutemeet;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.foured.cutemeet.config.ConstStrings;
import com.foured.cutemeet.models.UserAccountData;
import com.foured.cutemeet.net.AuthenticationException;
import com.foured.cutemeet.net.SpringSecurityClient;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeScreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WelcomeScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WelcomeScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static WelcomeScreen newInstance(String param1, String param2) {
        WelcomeScreen fragment = new WelcomeScreen();
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
        return inflater.inflate(R.layout.fragment_welcome_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton loginButton = view.findViewById(R.id.welcomePanel_enterAccountButton);
        loginButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_welcomeScreen_to_logInScreen));
        loginButton.setEnabled(false);
        ImageButton registrationButton = view.findViewById(R.id.welcomePanel_registrationButton);
        registrationButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_welcomeScreen_to_registrationScreen_1));
        registrationButton.setEnabled(false);

        TextView logText = view.findViewById(R.id.welcomePanel_logText);
        logText.setText("");

        ImageView loadingImage = view.findViewById(R.id.welcomePanel_loadingImage);
        AnimatedVectorDrawable loadingAVD = (AnimatedVectorDrawable) loadingImage.getDrawable();

        loadingAVD.start();

        String url1 = ConstStrings.serverAddress + "/operations/ping";
        CompletableFuture<String> future = SpringSecurityClient.get_nc_async(url1);

        boolean autoAuth = false;

        future.thenAcceptAsync(result1 -> {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(ConstStrings.sharedPreferencesUserDataPath, Context.MODE_PRIVATE);
            String username = sharedPreferences.getString(ConstStrings.sharedPreferences_usernameKey, null);
            String password = sharedPreferences.getString(ConstStrings.sharedPreferences_passwordKey, null);

            if(autoAuth) {
                if (username != null && password != null) {
                    try {
                        String url2 = ConstStrings.serverAddress + "/login";
                        SpringSecurityClient client = SpringSecurityClient.login_ns(url2, username, password);
                        client.saveCookiesToSharedPreferences(getContext());

                        Log.i("Welcome screen", "Logged to account");
                        getActivity().runOnUiThread(() -> {
                            loadingAVD.stop();
                            loadingImage.setVisibility(View.GONE);
                            Navigation.findNavController(view).navigate(R.id.action_welcomeScreen_to_news);
                        });
                    } catch (Exception e) {
                        Log.w("Welcome screen", "Error: \n" + e);
                        getActivity().runOnUiThread(() -> {
                            loadingAVD.stop();
                            loadingImage.setVisibility(View.GONE);
                        });

                    }
                } else {
                    Log.i("Welcome screen", "Can`t find user data.");
                }
            }
            getActivity().runOnUiThread(() -> {
                loadingAVD.stop();
                loadingImage.setVisibility(View.GONE);
                loginButton.setEnabled(true);
                registrationButton.setEnabled(true);
            });
        }).exceptionally(e -> {
            getActivity().runOnUiThread(() -> {
                logText.setText("Сервера не отвечают");
                loadingAVD.stop();
                loadingImage.setVisibility(View.GONE);
            });
            return null;
        });
    }
}