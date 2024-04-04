package com.foured.cutemeet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.foured.cutemeet.models.UserAccountData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetAccountDataPanel_3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetAccountDataPanel_3 extends Fragment {
    UserAccountData uad;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SetAccountDataPanel_3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetAccountDataPanel_3.
     */
    // TODO: Rename and change types and number of parameters
    public static SetAccountDataPanel_3 newInstance(String param1, String param2) {
        SetAccountDataPanel_3 fragment = new SetAccountDataPanel_3();
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
        return inflater.inflate(R.layout.fragment_set_account_data_panel_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((EditText) view.findViewById(R.id.setAccountDataPanel_3_descriptionEditText)).getText().clear();
        view.findViewById(R.id.setAccountDataPanel_3_backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user_account_data_2", uad);
                Navigation.findNavController(view).navigate(R.id.action_setAccountDataPanel_3_to_setAccountDataPanel_2, bundle);
            }
        });

        view.findViewById(R.id.setAccountDataPanel_3_nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uad.description = String.valueOf(((EditText) view.findViewById(R.id.setAccountDataPanel_3_descriptionEditText)).getText());
                Bundle bundle = new Bundle();
                bundle.putSerializable("user_account_data_2", uad);
                Navigation.findNavController(view).navigate(R.id.action_setAccountDataPanel_3_to_setAccountDataPanel_4, bundle);
            }
        });
    }
}