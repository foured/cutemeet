package com.foured.cutemeet;

import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foured.cutemeet.adapters.EventsAdapter;
import com.foured.cutemeet.config.ConstStrings;
import com.foured.cutemeet.models.EventData;
import com.foured.cutemeet.net.HTTP;
import com.foured.cutemeet.net.SpringSecurityClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Events#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Events extends Fragment {

    private enum ActiveFindPanel{
        TAGS,
        USERNAME,
        PLACE
    }

    private ImageView loadingImage;
    private AnimatedVectorDrawable loadingAVD;
    private TextView logText;

    private RecyclerView eventsList;
    private EventsAdapter eventsAdapter;

    private SpringSecurityClient client;

    ActiveFindPanel currentActiveFindPanel = ActiveFindPanel.TAGS;
    private boolean isFindPanelActive = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Events() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Events.
     */
    // TODO: Rename and change types and number of parameters
    public static Events newInstance(String param1, String param2) {
        Events fragment = new Events();
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
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton createEventButton = view.findViewById(R.id.eventsPanel_newEventButton);

        view.findViewById(R.id.eventsPanel_messangerButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_events_to_messanger));
        view.findViewById(R.id.eventsPanel_questionnairesButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_events_to_questionnaires));
        view.findViewById(R.id.eventsPanel_newsButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_events_to_news));

        createEventButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_events_to_createEventPanel_1));

        loadingImage = view.findViewById(R.id.eventsPanel_loadingImage);
        loadingAVD = (AnimatedVectorDrawable) loadingImage.getDrawable();
        loadingAVD.start();

        logText = view.findViewById(R.id.eventsPanel_logTextView);
        logText.setText("");

        // find panel
        FrameLayout findPanal = view.findViewById(R.id.eventsPanel_findPanel);
        FrameLayout findByTags = view.findViewById(R.id.eventsPanel_findPanel_byTagsPanel);
        FrameLayout findByUsername = view.findViewById(R.id.eventsPanel_findPanel_byUsernamePanel);

        EditText tagsEditText = view.findViewById(R.id.eventsPanel_findPanel_byTagsPanel_tagsEditText);
        EditText usernameEditText = view.findViewById(R.id.eventsPanel_findPanel_byUsernamePanel_usernameEditText);

        tagsEditText.getText().clear();
        usernameEditText.getText().clear();

        // find panel buttons
        ImageButton findButton = view.findViewById(R.id.eventsPanel_findButton);

        ImageButton find_byTagsButton = view.findViewById(R.id.eventsPanel_findPanel_findByTagsButton);
        ImageButton find_byUsernameButton = view.findViewById(R.id.eventsPanel_findPanel_findByUsernameButton);
        ImageButton find_byPlaceButton = view.findViewById(R.id.eventsPanel_findPanel_findByPlaceButton);

        ImageButton byTagsPanel_backButton = view.findViewById(R.id.eventsPanel_findPanel_byTagsPanel_backButton);
        ImageButton byTagsPanel_findButton = view.findViewById(R.id.eventsPanel_findPanel_byTagsPanel_findButton);

        ImageButton byUsernamePanel_backButton = view.findViewById(R.id.eventsPanel_findPanel_byUsernamePanel_backButton);
        ImageButton byUsernamePanel_findButton = view.findViewById(R.id.eventsPanel_findPanel_byUsernamePanel_findButton);

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFindPanelActive = !isFindPanelActive;
                setVisible(findPanal, isFindPanelActive);
                if(!isFindPanelActive)
                    loadAllEvents(view);
            }
        });
        find_byTagsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentActiveFindPanel == ActiveFindPanel.USERNAME){
                    swipeVisibility(findByTags);
                    swipeVisibility(findByUsername);
                    currentActiveFindPanel = ActiveFindPanel.TAGS;

                    find_byUsernameButton.setImageResource(R.drawable.findpanel_findbyusername_notactive);
                    find_byTagsButton.setImageResource(R.drawable.findpanel_findbytags_active);
                }
            }
        });
        find_byUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentActiveFindPanel == ActiveFindPanel.TAGS){
                    swipeVisibility(findByTags);
                    swipeVisibility(findByUsername);
                    currentActiveFindPanel = ActiveFindPanel.USERNAME;

                    find_byUsernameButton.setImageResource(R.drawable.findpanel_findbyusername_active);
                    find_byTagsButton.setImageResource(R.drawable.findpanel_findbytags_notactive);
                }
            }
        });
        byTagsPanel_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFindPanelActive = !isFindPanelActive;
                setVisible(findPanal, isFindPanelActive);
                loadAllEvents(view);
            }
        });
        byUsernamePanel_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFindPanelActive = !isFindPanelActive;
                setVisible(findPanal, isFindPanelActive);
                loadAllEvents(view);
            }
        });
        byTagsPanel_findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logText.setText("");
                isFindPanelActive = false;
                setVisible(findPanal, false);
                loadingImage.setVisibility(View.VISIBLE);
                loadingAVD.start();

                eventsList = view.findViewById(R.id.eventsPanel_eventsPrev_recyclerView);
                LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
                eventsList.setLayoutManager(layoutManager);
                eventsList.setHasFixedSize(true);

                String url = ConstStrings.serverAddress + "/activities/find_byTags";
                List<SpringSecurityClient.Pair> params = new ArrayList<>();
                params.add(new SpringSecurityClient.Pair("tagsLine", String.valueOf(tagsEditText.getText())));
                CompletableFuture<String> future = client.get_async(url, params);

                future.thenAcceptAsync(result -> {
                    List<EventData> eventList = parseJsonArray(result, EventData.class);
                    Log.i("Events", result);

                    getActivity().runOnUiThread(() -> {
                        eventsAdapter = new EventsAdapter((ArrayList<EventData>) eventList);
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(eventsList.getContext(),
                                layoutManager.getOrientation());
                        dividerItemDecoration.setDrawable(new ColorDrawable(Color.TRANSPARENT));
                        eventsList.addItemDecoration(dividerItemDecoration);

                        eventsList.setAdapter(eventsAdapter);

                        logText.setText("Используются фильтры");
                        loadingAVD.stop();
                        loadingImage.setVisibility(View.GONE);
                    });
                });
            }
        });
        byUsernamePanel_findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logText.setText("");
                isFindPanelActive = false;
                setVisible(findPanal, false);
                loadingImage.setVisibility(View.VISIBLE);
                loadingAVD.start();

                eventsList = view.findViewById(R.id.eventsPanel_eventsPrev_recyclerView);
                LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
                eventsList.setLayoutManager(layoutManager);
                eventsList.setHasFixedSize(true);

                String url = ConstStrings.serverAddress + "/activities/find_byUsername";
                List<SpringSecurityClient.Pair> params = new ArrayList<>();
                params.add(new SpringSecurityClient.Pair("username", String.valueOf(usernameEditText.getText())));
                CompletableFuture<String> future = client.get_async(url, params);

                future.thenAcceptAsync(result -> {
                    List<EventData> eventList = parseJsonArray(result, EventData.class);
                    Log.i("Events", result);

                    getActivity().runOnUiThread(() -> {
                        eventsAdapter = new EventsAdapter((ArrayList<EventData>) eventList);
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(eventsList.getContext(),
                                layoutManager.getOrientation());
                        dividerItemDecoration.setDrawable(new ColorDrawable(Color.TRANSPARENT));
                        eventsList.addItemDecoration(dividerItemDecoration);

                        eventsList.setAdapter(eventsAdapter);

                        logText.setText("Используются фильтры");
                        loadingAVD.stop();
                        loadingImage.setVisibility(View.GONE);
                    });
                });
            }
        });

        client = SpringSecurityClient.createFromCookiesData(SpringSecurityClient.loadCookiesDataFromSharedPreferences(getContext()));
        loadAllEvents(view);
    }

    private static void swipeVisibility(View view){
        if(view.getVisibility() == View.VISIBLE){
            view.setVisibility(View.GONE);
        }
        else{
            view.setVisibility(View.VISIBLE);
        }
    }

    private static void setVisible(View view, Boolean value){
        if(value){
            view.setVisibility(View.VISIBLE);
        }
        else{
            view.setVisibility(View.GONE);
        }
    }

    private void loadAllEvents(View view){
        logText.setText("");
        loadingImage.setVisibility(View.VISIBLE);
        loadingAVD.start();

        eventsList = view.findViewById(R.id.eventsPanel_eventsPrev_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        eventsList.setLayoutManager(layoutManager);
        eventsList.setHasFixedSize(true);


        CompletableFuture<String> action = client.get_async(ConstStrings.serverAddress + "/activities/all");
        action.thenAcceptAsync(result -> {
            List<EventData> eventList = parseJsonArray(result, EventData.class);
            Log.i("Events", result);

            getActivity().runOnUiThread(() -> {
                eventsAdapter = new EventsAdapter((ArrayList<EventData>) eventList);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(eventsList.getContext(),
                        layoutManager.getOrientation());
                dividerItemDecoration.setDrawable(new ColorDrawable(Color.TRANSPARENT));
                eventsList.addItemDecoration(dividerItemDecoration);

                eventsList.setAdapter(eventsAdapter);

                loadingAVD.stop();
                loadingImage.setVisibility(View.GONE);
            });
        });
    }

    private static <T> List<T> parseJsonArray(String jsonArrayString, Class<T> classOfT) {
        List<T> resultList = new ArrayList<>();

        Gson gson = new Gson();
        JsonArray jsonArray = JsonParser.parseString(jsonArrayString).getAsJsonArray();

        for (JsonElement jsonElement : jsonArray) {
            T object = gson.fromJson(jsonElement, classOfT);
            resultList.add(object);
        }

        return resultList;
    }
}