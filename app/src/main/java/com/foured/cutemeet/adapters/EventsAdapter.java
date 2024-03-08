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
        holder.eventName.setText(eventsDataList.get(position).name);
        holder.senderName.setText(eventsDataList.get(position).senderName);
        holder.eventDate.setText(eventsDataList.get(position).date);
    }

    @Override
    public int getItemCount() {
        return eventsDataList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder{

        ImageView bg;
        ImageButton moreInfoButton;
        TextView eventName;
        TextView senderName;
        TextView eventDate;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            bg = itemView.findViewById(R.id.eventPrev_bg);
            moreInfoButton = itemView.findViewById(R.id.eventPrev_moreInfoButton);
            eventName = itemView.findViewById(R.id.eventPrev_eventNameText);
            senderName = itemView.findViewById(R.id.eventPrev_senderNameText);
            eventDate = itemView.findViewById(R.id.eventPrev_eventLocationText);
        }
    }
}
