package com.foured.cutemeet;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.foured.cutemeet.config.ConstStrings;
import com.foured.cutemeet.models.UserAccountData;
import com.foured.cutemeet.net.SpringSecurityClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationScreen_3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationScreen_3 extends Fragment {
    private UserAccountData uad;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistrationScreen_3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationScreen_3.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationScreen_3 newInstance(String param1, String param2) {
        RegistrationScreen_3 fragment = new RegistrationScreen_3();
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
        return inflater.inflate(R.layout.fragment_registration_screen_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText cET = ((EditText) view.findViewById(R.id.registrationPanel_3_codeEditText));
        cET.getText().clear();

        TextView logText = view.findViewById(R.id.registrationPanel_3_logText);
        logText.setText("");

        ImageView loadingImage = view.findViewById(R.id.registrationPanel_3_loadingImage);
        loadingImage.setVisibility(View.GONE);
        AnimatedVectorDrawable loadingAVD = (AnimatedVectorDrawable) loadingImage.getDrawable();

        view.findViewById(R.id.registrationPanel_3_backButton)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user_account_data", uad);
                        Navigation.findNavController(view).navigate(R.id.action_registrationScreen_3_to_registrationScreen_2);
                    }
                });

        view.findViewById(R.id.registrationPanel_3_nextButton)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logText.setText("");
                        loadingImage.setVisibility(View.VISIBLE);
                        loadingAVD.start();

                        List<SpringSecurityClient.Pair> params = new ArrayList<>();
                        params.add(new SpringSecurityClient.Pair("email", uad.email));
                        params.add(new SpringSecurityClient.Pair("code", cET.getText().toString()));
                        String url = ConstStrings.serverAddress + "/operations/check_code";
                        CompletableFuture<String> result = SpringSecurityClient.get_nc_async(url, params);
                        result.thenAccept(res -> {
                            boolean r = Boolean.parseBoolean(res);

                            if(r){
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user_account_data", uad);

                                getActivity().runOnUiThread(() -> {
                                    loadingAVD.stop();
                                    loadingImage.setVisibility(View.GONE);
                                });

                                Navigation.findNavController(view).navigate(R.id.action_registrationScreen_3_to_registrationScreen_4, bundle);
                            }
                            else{
                                getActivity().runOnUiThread(() -> {
                                    loadingAVD.stop();
                                    loadingImage.setVisibility(View.GONE);

                                    logText.setText(ConstStrings.wrongCode);
                                });
                            }
                        });
                    }
                });
    }
}