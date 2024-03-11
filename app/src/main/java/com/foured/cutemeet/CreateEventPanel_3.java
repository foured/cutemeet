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
import com.foured.cutemeet.models.EventData;
import com.foured.cutemeet.net.HTTP;
import com.foured.cutemeet.net.SpringSecurityClient;

import java.util.concurrent.CompletableFuture;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateEventPanel_3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventPanel_3 extends Fragment {
    private EventData eventData;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateEventPanel_3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventPanel_3.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventPanel_3 newInstance(String param1, String param2) {
        CreateEventPanel_3 fragment = new CreateEventPanel_3();
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
            eventData = (EventData) getArguments().getSerializable("event_data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_event_panel_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SpringSecurityClient client = SpringSecurityClient.createFromCookiesData(SpringSecurityClient.loadCookiesDataFromSharedPreferences(getContext()));

        ImageButton backButton = view.findViewById(R.id.createEventsPanel_3_backButton);
        ImageButton sendButton = view.findViewById(R.id.createEventsPanel_3_sendEventButton);

        EditText tET = view.findViewById(R.id.createEventsPanel_3_tagsEditText);
        tET.getText().clear();

        ImageView loadingImage = view.findViewById(R.id.createEventsPanel_3_loadingImage);
        loadingImage.setVisibility(View.GONE);
        AnimatedVectorDrawable loadingAVD = (AnimatedVectorDrawable) loadingImage.getDrawable();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("event_data", eventData);
                Navigation.findNavController(view).navigate(R.id.action_createEventPanel_3_to_createEventPanel_2);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingImage.setVisibility(View.VISIBLE);
                loadingAVD.start();

                eventData.tags = String.valueOf(tET.getText());

                String url = ConstStrings.serverAddress + "/activities/save";
                CompletableFuture<String> future = client.post_async(url, eventData.toJsonString());
                future.thenAcceptAsync(result -> {
                    Log.i("Event sender", "Response from server: " + result);

                    getActivity().runOnUiThread(()-> {
                        loadingAVD.stop();
                        loadingImage.setVisibility(View.GONE);
                        Navigation.findNavController(view).navigate(R.id.action_createEventPanel_3_to_events);
                    });
                });
            }
        });
    }
}