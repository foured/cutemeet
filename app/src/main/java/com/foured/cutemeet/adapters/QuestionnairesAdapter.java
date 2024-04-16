package com.foured.cutemeet.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.foured.cutemeet.Questionnaires;
import com.foured.cutemeet.R;
import com.foured.cutemeet.config.BundleBuffer;
import com.foured.cutemeet.models.QuestionnaireData;
import com.foured.cutemeet.models.UserData;

import java.util.List;

public class QuestionnairesAdapter extends RecyclerView.Adapter<QuestionnairesAdapter.QuestionnaireViewHolder> {
    private List<QuestionnaireData> questionnaireDataList;

    public QuestionnairesAdapter(List<QuestionnaireData> newQuestionnaireData) { questionnaireDataList = newQuestionnaireData; }

    @NonNull
    @Override
    public QuestionnaireViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.questionnaire_preview;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, parent, false);
        QuestionnairesAdapter.QuestionnaireViewHolder questionnaireViewHolder = new QuestionnairesAdapter.QuestionnaireViewHolder(view);
        return questionnaireViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionnaireViewHolder holder, int position) {
        QuestionnaireData qd = questionnaireDataList.get(position);

        holder.nameTextView.setText(qd.FIO);
        holder.educationPlaceTextView.setText(qd.educationPlace);
        holder.bgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                BundleBuffer bundleBuffer = new BundleBuffer();
                bundleBuffer.from = BundleBuffer.From.QuestionnairePanel;
                bundle.putSerializable("data", bundleBuffer);
                bundle.putString("username", qd.username);
                Navigation.findNavController(v).navigate(R.id.action_questionnaires_to_userProfilePanel, bundle);
            }
        });
    }

    @Override
    public int getItemCount() { return questionnaireDataList.size(); }

    class QuestionnaireViewHolder extends RecyclerView.ViewHolder {

        ImageButton bgButton;
        TextView nameTextView;
        TextView educationPlaceTextView;

        public QuestionnaireViewHolder(@NonNull View itemView) {
            super(itemView);
            bgButton = itemView.findViewById(R.id.questionnairePrev_bgButton);
            nameTextView = itemView.findViewById(R.id.questionnairePrev_nameText);
            educationPlaceTextView = itemView.findViewById(R.id.questionnairePrev_educationPlaceText);
        }
    }
}
