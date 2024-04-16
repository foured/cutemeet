package com.foured.cutemeet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Messanger#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Messanger extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Messanger() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Messanger.
     */
    // TODO: Rename and change types and number of parameters
    public static Messanger newInstance(String param1, String param2) {
        Messanger fragment = new Messanger();
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
        return inflater.inflate(R.layout.fragment_messanger, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.messangerPanel_questionnairesButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_messanger_to_questionnaires));
        view.findViewById(R.id.messangerPanel_eventsButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_messanger_to_events));
        view.findViewById(R.id.messangerPanel_newsButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_messanger_to_news));
        view.findViewById(R.id.messangerPanel_accountButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_messanger_to_account));

        //ImageButton b = view.findViewById(R.id.messangerPanel_messabgerButton);
        //LinearLayout bottomPanel = view.findViewById(R.id.messangerPanel_bottomPanel_linearLayout);
        //ImageView hilighter = view.findViewById(R.id.messangerPanel_hilighter);
        //int[] location = new int[2];
        //b.getLocationInWindow(location);
        //hilighter.setX(location[0]);
        //hilighter.setY(location[1]);
    }
}