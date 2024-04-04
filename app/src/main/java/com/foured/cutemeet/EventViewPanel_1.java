package com.foured.cutemeet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.foured.cutemeet.config.BundleBuffer;
import com.foured.cutemeet.models.EventData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventViewPanel_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventViewPanel_1 extends Fragment {
    private EventData eventData;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventViewPanel_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventViewPanel_1.
     */
    // TODO: Rename and change types and number of parameters
    public static EventViewPanel_1 newInstance(String param1, String param2) {
        EventViewPanel_1 fragment = new EventViewPanel_1();
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

            eventData = ((BundleBuffer) getArguments().getSerializable("data")).eventData;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_view_panel_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView) view.findViewById(R.id.eventViewPanel_1_eventNameText)).setText(eventData.name);
        ((TextView) view.findViewById(R.id.eventViewPanel_1_eventDateText)).setText(eventData.date);
        ((TextView) view.findViewById(R.id.eventViewPanel_1_eventPlaceText)).setText(eventData.location);
        Button accountButton = view.findViewById(R.id.eventViewPanel_1_creatorAccountButton);
        accountButton.setText(eventData.username);

        ((ImageButton) view.findViewById(R.id.eventViewPanel_1_backButton)).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_eventViewPanel_1_to_events));

        ((ImageButton) view.findViewById(R.id.eventViewPanel_1_tagsButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BundleBuffer bb = new BundleBuffer();
                bb.eventData = eventData;
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", bb);
                Navigation.findNavController(view).navigate(R.id.action_eventViewPanel_1_to_eventViewPanel_2, bundle);
            }
        });

        ((ImageButton) view.findViewById(R.id.eventViewPanel_1_descriptionButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BundleBuffer bb = new BundleBuffer();
                bb.eventData = eventData;
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", bb);
                Navigation.findNavController(view).navigate(R.id.action_eventViewPanel_1_to_eventViewPanel_3, bundle);
            }
        });

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BundleBuffer bb = new BundleBuffer();
                bb.eventData = eventData;
                bb.from = BundleBuffer.From.ViewEvent;
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", bb);
                bundle.putString("username", eventData.username);
                Navigation.findNavController(view).navigate(R.id.action_eventViewPanel_1_to_userProfilePanel, bundle);
            }
        });
    }
}