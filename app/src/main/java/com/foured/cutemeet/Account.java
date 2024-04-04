package com.foured.cutemeet;

import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.foured.cutemeet.algorithms.StringAlgorithms;
import com.foured.cutemeet.config.ConstStrings;
import com.foured.cutemeet.models.UserAccountData;
import com.foured.cutemeet.net.SpringSecurityClient;
import com.google.android.material.imageview.ShapeableImageView;

import org.w3c.dom.Text;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends Fragment {

    private ShapeableImageView avatarImage;
    private TextView usernameText;
    private TextView FIOText;
    private TextView bDateText;
    private TextView educationPlaceText;
    private TextView descriptionText;
    private TextView tagsText;
    private TextView tgText;

    private FrameLayout mainLayout;
    private ImageButton fillQuestionnaireButton;

    private ImageView loadingImage;
    private AnimatedVectorDrawable loadingAVD;
    SpringSecurityClient client;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Account() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Account.
     */
    // TODO: Rename and change types and number of parameters
    public static Account newInstance(String param1, String param2) {
        Account fragment = new Account();
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
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.accountPanel_messangerButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_account_to_messanger));
        view.findViewById(R.id.accountPanel_questionnairesButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_account_to_questionnaires));
        view.findViewById(R.id.accountPanel_newsButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_account_to_news));
        view.findViewById(R.id.accountPanel_eventsButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_account_to_events));

        mainLayout = view.findViewById(R.id.accountPanel_dataLayout);
        fillQuestionnaireButton = view.findViewById(R.id.accountPanel_fillQuestionnaireButton);

        avatarImage = view.findViewById(R.id.accountPanel_avatarImage);
        usernameText = view.findViewById(R.id.accountPanel_usernameText);
        FIOText = view.findViewById(R.id.accountPanel_FIOText);
        bDateText = view.findViewById(R.id.accountPanel_birthdayDateText);
        educationPlaceText = view.findViewById(R.id.accountPanel_educationPlaceText);
        descriptionText = view.findViewById(R.id.accountPanel_descriptionText);
        tagsText = view.findViewById(R.id.accountPanel_tagsText);
        tgText = view.findViewById(R.id.accountPanel_tgText);

        loadingImage = view.findViewById(R.id.accountPanel_loadingImage);
        loadingAVD = (AnimatedVectorDrawable) loadingImage.getDrawable();
        loadingAVD.stop();

        view.findViewById(R.id.accountPanel_fillQuestionnaireButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_account_to_setAccountDataPanel_1));

        client = SpringSecurityClient.createFromCookiesData(SpringSecurityClient.loadCookiesDataFromSharedPreferences(getContext()));
        UpdateAccountInfo();
    }

    private void UpdateAccountInfo(){
        loadingImage.setVisibility(View.VISIBLE);
        loadingAVD.start();

        CompletableFuture<String> future1 = client.get_async(ConstStrings.serverAddress + "/account/get_accountData");

        future1.thenAcceptAsync(result -> {
            Log.i("Account", result);
            if (!Objects.equals(result, "")) {
                UserAccountData uad = StringAlgorithms.parseJsonClass(result, UserAccountData.class);
                getActivity().runOnUiThread(() -> {
                    mainLayout.setVisibility(View.VISIBLE);
                    fillQuestionnaireButton.setVisibility(View.GONE);

                    bDateText.setText(uad.birthdayDate);
                    educationPlaceText.setText(uad.educationPlace);
                    descriptionText.setText(uad.description);
                    tagsText.setText(uad.tags);
                    tgText.setText("Телеграмм: " + uad.tgLink);
                });

                CompletableFuture<String> future2 = client.get_async(ConstStrings.serverAddress + "/account/get_username");

                future2.thenAcceptAsync(result1 -> {
                    getActivity().runOnUiThread(() -> {
                        usernameText.setText(result1);
                    });
                    CompletableFuture<String> future3 = client.get_async(ConstStrings.serverAddress + "/account/get_photo");

                    future3.thenAcceptAsync(result2 -> {
                        byte[] imageData = Base64.decode(result2, Base64.DEFAULT);
                        getActivity().runOnUiThread(() -> {
                            avatarImage.setImageBitmap(BitmapFactory.decodeByteArray(imageData, 0, imageData.length));
                            loadingAVD.stop();
                            loadingImage.setVisibility(View.GONE);
                        });
                    });
                });


            } else {
                getActivity().runOnUiThread(() -> {
                    mainLayout.setVisibility(View.GONE);
                    fillQuestionnaireButton.setVisibility(View.VISIBLE);

                    loadingAVD.stop();
                    loadingImage.setVisibility(View.GONE);
                });
            }
        });
    }
}