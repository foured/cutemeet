package com.foured.cutemeet;

import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TextView;

import com.foured.cutemeet.adapters.EventsAdapter;
import com.foured.cutemeet.adapters.QuestionnairesAdapter;
import com.foured.cutemeet.algorithms.RegistrationFieldsChecker;
import com.foured.cutemeet.algorithms.StringAlgorithms;
import com.foured.cutemeet.config.ConstStrings;
import com.foured.cutemeet.models.EventData;
import com.foured.cutemeet.models.QuestionnaireData;
import com.foured.cutemeet.net.SpringSecurityClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Questionnaires#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Questionnaires extends Fragment {
    private class FindPanelManager{
        boolean isFindPanelActive = false;

        ImageButton currentButton;
        FrameLayout currentPanel;

        ImageButton findButton;
        FrameLayout findPanel;

        ImageButton byTagsButton;
        ImageButton byNameButton;
        ImageButton byEducationPlaceButton;
        ImageButton byAgeButton;
        ImageButton byTgLinkButton;

        FrameLayout byTagsPanel;
        FrameLayout byNamePanel;
        FrameLayout byEducationPlacePanel;
        FrameLayout byAgePanel;
        FrameLayout byTgLinkPanel;

        ImageButton byTagsBySimilarButton;
        ImageButton byTagsByCustomButton;
        FrameLayout byTagsBySimilarPanel;
        FrameLayout byTagsByCustomPanel;

        ImageButton byAgeBySimilarButton;
        ImageButton byAgeByRangeButton;
        FrameLayout byAgeBySimilarPanel;
        FrameLayout byAgeByRangePanel;

        ImageButton byTagsBySimilarBackButton;
        ImageButton byTagsByCustomBackButton;
        ImageButton byNameBackButton;
        ImageButton byEducationPlaceBackButton;
        ImageButton byAgeBySimilarBackButton;
        ImageButton byAgeByRangeBackButton;
        ImageButton byTgLinkBackButton;

        ImageButton byTagsBySimilarFindButton;
        ImageButton byTagsByCustomFindButton;
        ImageButton byNameFindButton;
        ImageButton byEducationPlaceFindButton;
        ImageButton byAgeBySimilarFindButton;
        ImageButton byAgeByRangeFindButton;
        ImageButton byTgLinkFindButton;

        EditText tagsEditText;
        EditText nameEditText;
        EditText tgLinkEditText;
        EditText ageRangeEditText;

        public FindPanelManager(View view){
            getBaseElements(view);
            bindClicks();
            bindFindClicks();
        }

        public void getBaseElements(View view){
            findButton = view.findViewById(R.id.questionnairesPanel_findButton);
            findPanel = view.findViewById(R.id.questionnairesPanel_findPanel);

            byTagsButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByTagsButton);
            byNameButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByNameButton);
            byEducationPlaceButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByEducationPlaceButton);
            byAgeButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByAgeButton);
            byTgLinkButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByTgLinkButton);

            byTagsPanel = view.findViewById(R.id.questionnairesPanel_findPanel_findByTagsPanel);
            byNamePanel = view.findViewById(R.id.questionnairesPanel_findPanel_findByNamePanel);
            byEducationPlacePanel = view.findViewById(R.id.questionnairesPanel_findPanel_findByEducationPlacePanel);
            byAgePanel = view.findViewById(R.id.questionnairesPanel_findPanel_findByAgePanel);
            byTgLinkPanel = view.findViewById(R.id.questionnairesPanel_findPanel_findByTgLinkPanel);

            currentButton = byTagsButton;
            currentPanel = byTagsPanel;

            byTagsBySimilarButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByTagsPanel_bySimilarButton);
            byTagsByCustomButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByTagsPanel_byCustomButton);
            byTagsBySimilarPanel = view.findViewById(R.id.questionnairesPanel_findPanel_findByTagsPanel_bySimilar);
            byTagsByCustomPanel = view.findViewById(R.id.questionnairesPanel_findPanel_findByTagsPanel_byCustom);

            byAgeBySimilarButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByAgePanel_bySimilarButton);
            byAgeByRangeButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByAgePanel_byRangeButton);
            byAgeBySimilarPanel = view.findViewById(R.id.questionnairesPanel_findPanel_findByAgePanel_bySimilar);
            byAgeByRangePanel = view.findViewById(R.id.questionnairesPanel_findPanel_findByAgePanel_byRange);

            byTagsBySimilarBackButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByTagsPanel_bySimilar_backButton);
            byTagsByCustomBackButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByTagsPanel_byCustom_backButton);
            byNameBackButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByNamePanel_backButton);
            byEducationPlaceBackButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByEducationPlacePanel_backButton);
            byAgeBySimilarBackButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByAgePanel_bySimilar_backButton);
            byAgeByRangeBackButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByAgePanel_byRange_backButton);
            byTgLinkBackButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByTgLinkPanel_backButton);

            byTagsBySimilarFindButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByTagsPanel_bySimilar_findButton);
            byTagsByCustomFindButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByTagsPanel_byCustom_findButton);
            byNameFindButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByNamePanel_findButton);
            byEducationPlaceFindButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByEducationPlacePanel_findButton);
            byAgeBySimilarFindButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByAgePanel_bySimilar_findButton);
            byAgeByRangeFindButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByAgePanel_byRange_findButton);
            byTgLinkFindButton = view.findViewById(R.id.questionnairesPanel_findPanel_findByTgLinkPanel_findButton);

            tagsEditText = view.findViewById(R.id.questionnairesPanel_findPanel_findByTagsPanel_byCustom_tagsEditText);
            nameEditText = view.findViewById(R.id.questionnairesPanel_findPanel_findByNamePanel_nameEditText);
            tgLinkEditText = view.findViewById(R.id.questionnairesPanel_findPanel_findByTgLinkPanel_tgEditText);
            ageRangeEditText = view.findViewById(R.id.questionnairesPanel_findPanel_findByAgePanel_byRange_rangeEditText);

            tagsEditText.getText().clear();
            nameEditText.getText().clear();
            tgLinkEditText.getText().clear();
            ageRangeEditText.getText().clear();
        }
        public void bindClicks(){
            findButton.setOnClickListener(v -> {
                if(isFindPanelActive) {
                    findPanel.setVisibility(View.GONE);
                    closeMainMenu();
                }
                else{
                    findPanel.setVisibility(View.VISIBLE);
                    isFindPanelActive = true;
                }
            });

            byTagsButton.setOnClickListener(v -> changeMainMenu(byTagsButton));
            byNameButton.setOnClickListener(v -> changeMainMenu(byNameButton));
            byEducationPlaceButton.setOnClickListener(v -> changeMainMenu(byEducationPlaceButton));
            byAgeButton.setOnClickListener(v -> changeMainMenu(byAgeButton));
            byTgLinkButton.setOnClickListener(v -> changeMainMenu(byTgLinkButton));

            byTagsBySimilarButton.setOnClickListener(v -> {
                byTagsBySimilarButton.setImageResource(R.drawable.findpanel_q_findbytags_bysimularbutton_active);
                byTagsBySimilarPanel.setVisibility(View.VISIBLE);
                byTagsByCustomButton.setImageResource(R.drawable.findpanel_q_findbytags_bycustombutton_notactive);
                byTagsByCustomPanel.setVisibility(View.GONE);
            });
            byTagsByCustomButton.setOnClickListener(v -> {
                byTagsByCustomButton.setImageResource(R.drawable.findpanel_q_findbytags_bycustombutton_active);
                byTagsByCustomPanel.setVisibility(View.VISIBLE);
                byTagsBySimilarButton.setImageResource(R.drawable.findpanel_q_findbytags_bysimularbutton_notactive);
                byTagsBySimilarPanel.setVisibility(View.GONE);
            });
            byAgeBySimilarButton.setOnClickListener(v -> {
                byAgeBySimilarButton.setImageResource(R.drawable.findpanel_q_findbyage_bysimularbutton_active);
                byAgeBySimilarPanel.setVisibility(View.VISIBLE);
                byAgeByRangeButton.setImageResource(R.drawable.findpanel_q_findbyage_byrangebutton_notactive);
                byAgeByRangePanel.setVisibility(View.GONE);
            });
            byAgeByRangeButton.setOnClickListener(v -> {
                byAgeByRangeButton.setImageResource(R.drawable.findpanel_q_findbyage_byrangebutton_active);
                byAgeByRangePanel.setVisibility(View.VISIBLE);
                byAgeBySimilarButton.setImageResource(R.drawable.findpanel_q_findbyage_bysimularbutton_notactive);
                byAgeBySimilarPanel.setVisibility(View.GONE);
            });

            byTagsBySimilarBackButton.setOnClickListener(v -> closeMainMenu());
            byTagsByCustomBackButton.setOnClickListener(v -> closeMainMenu());
            byNameBackButton.setOnClickListener(v -> closeMainMenu());
            byEducationPlaceBackButton.setOnClickListener(v -> closeMainMenu());
            byAgeBySimilarBackButton.setOnClickListener(v -> closeMainMenu());
            byAgeByRangeBackButton.setOnClickListener(v -> closeMainMenu());
            byTgLinkBackButton.setOnClickListener(v -> closeMainMenu());
        }
        public void bindFindClicks(){
            byTagsBySimilarFindButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   logText.setText("Используются фильтры");
                    justCloseMainMenu();
                   loadQuestionnairesBy_TagsSimilar();
                }
            });
            byTagsByCustomFindButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logText.setText("Используются фильтры");
                    justCloseMainMenu();
                    loadQuestionnairesBy_TagsCustom(String.valueOf(tagsEditText.getText()));
                }
            });
            byNameFindButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logText.setText("Используются фильтры");
                    justCloseMainMenu();
                    loadQuestionnairesBy_Username(String.valueOf(nameEditText.getText()));
                }
            });
            byEducationPlaceFindButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logText.setText("Используются фильтры");
                    justCloseMainMenu();
                    loadQuestionnairesBy_EducationPlace();
                }
            });
            byAgeBySimilarFindButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logText.setText("Используются фильтры");
                    justCloseMainMenu();
                    loadQuestionnairesBy_SimilarAge();
                }
            });
            byAgeByRangeFindButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(RegistrationFieldsChecker.isNumber(String.valueOf(ageRangeEditText.getText()))) {
                        logText.setText("Используются фильтры");
                        justCloseMainMenu();
                        loadQuestionnairesBy_RangedAge(Integer.parseInt(String.valueOf(ageRangeEditText.getText())));
                    }
                    else{
                        logText.setText("Допустимы только цифры");
                    }
                }
            });
            byTgLinkFindButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logText.setText("Используются фильтры");
                    justCloseMainMenu();
                    loadQuestionnairesBy_TgLink(String.valueOf(tgLinkEditText.getText()));
                }
            });
        }

        public void changeMainMenu(ImageButton button){
            if(button == currentButton) return;

            if(currentButton == byTagsButton) currentButton.setImageResource(R.drawable.findpanel_findbytags_notactive);
            else if(currentButton == byNameButton) currentButton.setImageResource(R.drawable.findpanel_q_findbynamebutton_notactive);
            else if(currentButton == byEducationPlaceButton) currentButton.setImageResource(R.drawable.findpanel_q_findbyplacebutton_notactive);
            else if(currentButton == byAgeButton) currentButton.setImageResource(R.drawable.findpanel_q_findbyagebutton_notactive);
            else if(currentButton == byTgLinkButton) currentButton.setImageResource(R.drawable.findpanel_q_findbytgbutton_notactive);

            currentPanel.setVisibility(View.GONE);

            if(button == byTagsButton) {
                button.setImageResource(R.drawable.findpanel_findbytags_active);
                currentPanel = byTagsPanel;
            }
            else if(button == byNameButton){
                button.setImageResource(R.drawable.findpanel_q_findbynamebutton_active);
                currentPanel = byNamePanel;
            }
            else if(button == byEducationPlaceButton){
                button.setImageResource(R.drawable.findpanel_q_findbyplacebutton_active);
                currentPanel = byEducationPlacePanel;
            }
            else if(button == byAgeButton){
                button.setImageResource(R.drawable.findpanel_q_findbyagebutton_active);
                currentPanel = byAgePanel;
            }
            else if(button == byTgLinkButton){
                button.setImageResource(R.drawable.findpanel_q_findbytgbutton_active);
                currentPanel = byTgLinkPanel;
            }

            currentPanel.setVisibility(View.VISIBLE);
            currentButton = button;
        }

        public void closeMainMenu(){
            findPanel.setVisibility(View.GONE);
            isFindPanelActive = false;
            logText.setText("");
            loadQuestionnairesBy_SimilarAge();
        }

        public void justCloseMainMenu(){
            findPanel.setVisibility(View.GONE);
            isFindPanelActive = false;
        }
    }

    SpringSecurityClient client;

    FindPanelManager findPanelManager;

    ImageView loadingImage;
    AnimatedVectorDrawable loadingAVD;
    TextView logText;

    private RecyclerView questionnairesList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Questionnaires() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Questionnaires.
     */
    // TODO: Rename and change types and number of parameters
    public static Questionnaires newInstance(String param1, String param2) {
        Questionnaires fragment = new Questionnaires();
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
        return inflater.inflate(R.layout.fragment_questionnaires, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.questionnairesPanel_messabgerButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_questionnaires_to_messanger));
        view.findViewById(R.id.questionnairesPanel_eventsButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_questionnaires_to_events));
        view.findViewById(R.id.questionnairesPanel_newsButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_questionnaires_to_news));
        view.findViewById(R.id.questionnairesPanel_accountButton).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_questionnaires_to_account));

        loadingImage = view.findViewById(R.id.questionnairesPanel_loadingImage);
        loadingAVD = (AnimatedVectorDrawable) loadingImage.getDrawable();

        logText = view.findViewById(R.id.questionnairesPanel_logTextView);
        logText.setText("");

        questionnairesList = view.findViewById(R.id.questionnairesPanel_questionnairePrev_recyclerView);

        findPanelManager = new FindPanelManager(view);
        client = SpringSecurityClient.createFromCookiesData(SpringSecurityClient.loadCookiesDataFromSharedPreferences(getContext()));
        loadQuestionnairesBy_SimilarAge();
    }

    public void loadQuestionnairesBy_SimilarAge(){
        loadingImage.setVisibility(View.VISIBLE);
        loadingAVD.start();

        CompletableFuture<String> future = client.get_async(ConstStrings.serverAddress + "/questionnaires/find_byAge");
        acceptQuestionnairesFuture(future);
    }
    public void loadQuestionnairesBy_RangedAge(int range){
        loadingImage.setVisibility(View.VISIBLE);
        loadingAVD.start();

        List<SpringSecurityClient.Pair> params = new ArrayList<>();
        params.add(new SpringSecurityClient.Pair("range", String.valueOf(range)));
        CompletableFuture<String> future = client.get_async(ConstStrings.serverAddress + "/questionnaires/find_byAgeInRange", params);

        acceptQuestionnairesFuture(future);
    }
    public void loadQuestionnairesBy_EducationPlace(){
        loadingImage.setVisibility(View.VISIBLE);
        loadingAVD.start();

        CompletableFuture<String> future = client.get_async(ConstStrings.serverAddress + "/questionnaires/find_byEducationPlace");
        acceptQuestionnairesFuture(future);
    }
    public void loadQuestionnairesBy_TagsSimilar(){
        loadingImage.setVisibility(View.VISIBLE);
        loadingAVD.start();

        CompletableFuture<String> future = client.get_async(ConstStrings.serverAddress + "/questionnaires/find_byTagsJaccardSimilarity");
        acceptQuestionnairesFuture(future);
    }
    public void loadQuestionnairesBy_TagsCustom(String tags){
        loadingImage.setVisibility(View.VISIBLE);
        loadingAVD.start();

        List<SpringSecurityClient.Pair> params = new ArrayList<>();
        params.add(new SpringSecurityClient.Pair("tagsLine", String.valueOf(tags)));
        CompletableFuture<String> future = client.get_async(ConstStrings.serverAddress + "/questionnaires/find_byTags", params);
        acceptQuestionnairesFuture(future);
    }
    public void loadQuestionnairesBy_TgLink(String tgLink){
        loadingImage.setVisibility(View.VISIBLE);
        loadingAVD.start();

        List<SpringSecurityClient.Pair> params = new ArrayList<>();
        params.add(new SpringSecurityClient.Pair("tgLink", String.valueOf(tgLink)));
        CompletableFuture<String> future = client.get_async(ConstStrings.serverAddress + "/questionnaires/find_byTgLink", params);
        acceptQuestionnairesFuture(future);
    }
    public void loadQuestionnairesBy_Username(String username){
        loadingImage.setVisibility(View.VISIBLE);
        loadingAVD.start();

        List<SpringSecurityClient.Pair> params = new ArrayList<>();
        params.add(new SpringSecurityClient.Pair("username", String.valueOf(username)));
        CompletableFuture<String> future = client.get_async(ConstStrings.serverAddress + "/questionnaires/find_byUsername", params);
        acceptQuestionnairesFuture(future);
    }


    private void acceptQuestionnairesFuture(CompletableFuture<String> future){
        future.thenAcceptAsync(result -> {
            Log.i("Questionnaires", result);
            if(!result.isEmpty()) {
                List<QuestionnaireData> dataList = StringAlgorithms.parseJsonArray(result, QuestionnaireData.class);

                getActivity().runOnUiThread(() -> {
                    updateQuestionnairesList(dataList);
                    loadingAVD.stop();
                    loadingImage.setVisibility(View.GONE);
                });
            }
            else{
                getActivity().runOnUiThread(() -> {
                    List<QuestionnaireData> data = new ArrayList<>();
                    updateQuestionnairesList(data);
                    loadingAVD.stop();
                    loadingImage.setVisibility(View.GONE);
                });
            }
        });
    }

    private void updateQuestionnairesList(List<QuestionnaireData> list){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        questionnairesList.setLayoutManager(layoutManager);
        questionnairesList.setHasFixedSize(true);

        QuestionnairesAdapter questionnairesAdapter = new QuestionnairesAdapter(list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(questionnairesList.getContext(),
                layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(new ColorDrawable(Color.TRANSPARENT));
        questionnairesList.addItemDecoration(dividerItemDecoration);
        questionnairesList.setAdapter(questionnairesAdapter);
    }
}