package com.foured.cutemeet;

import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.foured.cutemeet.config.BundleBuffer;
import com.foured.cutemeet.config.ConstStrings;
import com.foured.cutemeet.models.UserAccountData;
import com.foured.cutemeet.net.SpringSecurityClient;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfilePanel#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfilePanel extends Fragment {

    private ShapeableImageView avatarImage;
    private TextView usernameText;
    private TextView FIOText;
    private TextView bDateText;
    private TextView educationPlaceText;
    private TextView descriptionText;
    private TextView tagsText;
    private TextView tgText;

    BundleBuffer bundleBuffer;
    String username;

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

    public UserProfilePanel() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfilePanel.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfilePanel newInstance(String param1, String param2) {
        UserProfilePanel fragment = new UserProfilePanel();
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

            bundleBuffer = (BundleBuffer) getArguments().getSerializable("data");
            username = getArguments().getString("username");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile_panel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avatarImage = view.findViewById(R.id.userProfilePanel_avatarImage);
        usernameText = view.findViewById(R.id.userProfilePanel_usernameText);
        FIOText = view.findViewById(R.id.userProfilePanel_FIOText);
        bDateText = view.findViewById(R.id.userProfilePanel_birthdayDateText);
        educationPlaceText = view.findViewById(R.id.userProfilePanel_educationPlaceText);
        descriptionText = view.findViewById(R.id.userProfilePanel_descriptionText);
        tagsText = view.findViewById(R.id.userProfilePanel_tagsText);
        tgText = view.findViewById(R.id.userProfilePanel_tgText);

        loadingImage = view.findViewById(R.id.userProfilePanel_loadingImage);
        loadingAVD = (AnimatedVectorDrawable) loadingImage.getDrawable();
        loadingAVD.stop();

        ((ImageButton) view.findViewById(R.id.userProfilePanel_backButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", bundleBuffer);
                if(bundleBuffer.from == BundleBuffer.From.ViewEvent){
                    Navigation.findNavController(view).navigate(R.id.action_userProfilePanel_to_eventViewPanel_1, bundle);
                }
            }
        });

        client = SpringSecurityClient.createFromCookiesData(SpringSecurityClient.loadCookiesDataFromSharedPreferences(getContext()));
        UpdateAccountInfo();
    }

    private void UpdateAccountInfo(){
        loadingImage.setVisibility(View.VISIBLE);
        loadingAVD.start();

        List<SpringSecurityClient.Pair> args = new ArrayList<>();
        args.add(new SpringSecurityClient.Pair("username", username));
        CompletableFuture<String> future1 = client.get_async(ConstStrings.serverAddress + "/operations/get_userAccountData", args);

        future1.thenAcceptAsync(result -> {
            Log.i("UserProfile", result);
            if (!Objects.equals(result, "")) {
                UserAccountData uad = StringAlgorithms.parseJsonClass(result, UserAccountData.class);
                getActivity().runOnUiThread(() -> {
                    usernameText.setText(username);
                    bDateText.setText(uad.birthdayDate);
                    educationPlaceText.setText(uad.educationPlace);
                    descriptionText.setText(uad.description);
                    tagsText.setText(uad.tags);
                    tgText.setText("Телеграмм: " + uad.tgLink);
                });

                CompletableFuture<String> future2 = client.get_async(ConstStrings.serverAddress + "/operations/get_userPhoto", args);

                future2.thenAcceptAsync(result2 -> {
                    byte[] imageData = Base64.decode(result2, Base64.DEFAULT);
                    getActivity().runOnUiThread(() -> {
                        avatarImage.setImageBitmap(BitmapFactory.decodeByteArray(imageData, 0, imageData.length));
                        loadingAVD.stop();
                        loadingImage.setVisibility(View.GONE);
                    });
                });

            } else {
                getActivity().runOnUiThread(() -> {
                    loadingAVD.stop();
                    loadingImage.setVisibility(View.GONE);
                });
            }
        });
    }
}

