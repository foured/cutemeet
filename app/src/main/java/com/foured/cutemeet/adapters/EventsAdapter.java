package com.foured.cutemeet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foured.cutemeet.R;
import com.foured.cutemeet.models.EventData;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {
    private List<EventData> eventsDataList;

    public EventsAdapter(ArrayList<EventData> newEventsData){
        eventsDataList = newEventsData;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layutID = R.layout.event_preview;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layutID, parent, false);
        EventViewHolder eventViewHolder = new EventViewHolder(view);
        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.bg.setImageResource(R.drawable.eventspanel_eventprew);
        String description = eventsDataList.get(position).description;
        String output = description;
        int end = 115;
        if(description.length() > end){
            output = description.substring(0, end) + "....";
        }
        holder.descriptionText.setText(output);
    }

    @Override
    public int getItemCount() {
        return eventsDataList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder{

        ImageView bg;
        ImageButton moreInfoButton;
        TextView descriptionText;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            bg = itemView.findViewById(R.id.eventPrev_bg);
            moreInfoButton = itemView.findViewById(R.id.eventPrev_moreInfoButton);
            descriptionText = itemView.findViewById(R.id.eventPrev_descriptionText);
        }
    }
}
