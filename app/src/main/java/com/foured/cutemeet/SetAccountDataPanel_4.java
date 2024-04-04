package com.foured.cutemeet;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.foured.cutemeet.config.ConstStrings;
import com.foured.cutemeet.models.UserAccountData;
import com.foured.cutemeet.net.SpringSecurityClient;

import java.util.concurrent.CompletableFuture;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetAccountDataPanel_4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetAccountDataPanel_4 extends Fragment {
    private UserAccountData uad;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SetAccountDataPanel_4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetAccountDataPanel_4.
     */
    // TODO: Rename and change types and number of parameters
    public static SetAccountDataPanel_4 newInstance(String param1, String param2) {
        SetAccountDataPanel_4 fragment = new SetAccountDataPanel_4();
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
        return inflater.inflate(R.layout.fragment_set_account_data_panel_4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView loadingImage = view.findViewById(R.id.setAccountDataPanel_4_loadingImage);
        AnimatedVectorDrawable loadingAVD = (AnimatedVectorDrawable) loadingImage.getDrawable();
        ((EditText) view.findViewById(R.id.setAccountDataPanel_4_tagsEditText)).getText().clear();

        SpringSecurityClient client = SpringSecurityClient.createFromCookiesData(SpringSecurityClient.loadCookiesDataFromSharedPreferences(getContext()));

        view.findViewById(R.id.setAccountDataPanel_4_sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingImage.setVisibility(View.VISIBLE);
                loadingAVD.start();

                uad.tags = String.valueOf(((EditText) view.findViewById(R.id.setAccountDataPanel_4_tagsEditText)).getText());
                Bundle bundle = new Bundle();
                bundle.putSerializable("user_account_data_2", uad);
                String jsonData = uad.toJsonString();
                Log.e("!!!!!", jsonData);
                CompletableFuture<String> future = client.post_async(ConstStrings.serverAddress + "/account/set_accountData", jsonData);
                future.thenAcceptAsync(result -> {
                    Log.i("SetAccountDataPanel 4", result);

                    getActivity().runOnUiThread(()-> {
                        loadingAVD.stop();
                        loadingImage.setVisibility(View.GONE);
                        Navigation.findNavController(view).navigate(R.id.action_setAccountDataPanel_4_to_account);
                    });
                });
            }
        });

        view.findViewById(R.id.setAccountDataPanel_4_backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user_account_data_2", uad);
                Navigation.findNavController(view).navigate(R.id.action_setAccountDataPanel_4_to_setAccountDataPanel_3, bundle);
            }
        });
    }
}