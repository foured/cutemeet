package com.foured.cutemeet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.foured.cutemeet.adapters.EventsAdapter;
import com.foured.cutemeet.config.ConstStrings;
import com.foured.cutemeet.models.EventData;
import com.foured.cutemeet.net.HTTP;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Events#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Events extends Fragment {

    private RecyclerView eventsList;
    private EventsAdapter eventsAdapter;

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

        eventsList = view.findViewById(R.id.eventsPanel_eventsPrev_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        eventsList.setLayoutManager(layoutManager);
        eventsList.setHasFixedSize(true);

        HTTP.getHttpResponseAsync(ConstStrings.serverAddress + "/activities/all", new HTTP.HttpResponseListener() {
            @Override
            public void onHttpResponse(String result) {
                List<EventData> eventList = parseJsonArray(result, EventData.class);
                System.out.println(result);
                eventsAdapter = new EventsAdapter((ArrayList<EventData>) eventList);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(eventsList.getContext(),
                        layoutManager.getOrientation());
                eventsList.addItemDecoration(dividerItemDecoration);

                eventsList.setAdapter(eventsAdapter);
            }
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