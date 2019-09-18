package com.segroup9.uta_stud.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.segroup9.uta_stud.EventsActivity;
import com.squareup.picasso.Picasso;

import com.segroup9.uta_stud.Helper.EventInformation;
import com.segroup9.uta_stud.R;

import java.util.List;

/**
 * Created by kevinyanogo on 11/17/18.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.CourseViewHolder> {

    private Context mCtx;
    private List<EventInformation> eventsList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private AdapterView.OnItemLongClickListener mLongClickListener;
    private AdapterView adapterView;


    public EventAdapter(Context mCtx, List<EventInformation> eventsList) {
        this.mCtx = mCtx;
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public EventAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.adapter_event_row, parent, false);
        return new EventAdapter.CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.CourseViewHolder holder, int position) {
        EventInformation event = eventsList.get(position);
        Picasso.get()
                .load(event.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.eventImage);
        holder.eventTitle.setText(event.eventTitle);
        holder.eventDate.setText(event.eventDate);
        holder.eventStartingTime.setText(event.startTime);
        holder.eventEndTime.setText(event.endTime);
        holder.eventDetails.setText(event.eventDetails);

    }

    public EventInformation getItem(int position){
        return eventsList.get(position);
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }


    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView eventImage;
        TextView eventTitle;
        TextView eventDate;
        TextView eventStartingTime;
        TextView eventEndTime;
        TextView eventDetails;



         public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            eventImage = (ImageView) itemView.findViewById(R.id.event_image);
            eventTitle = (TextView) itemView.findViewById(R.id.event_title);
            eventDate = (TextView) itemView.findViewById(R.id.eventDate);
            eventStartingTime = (TextView) itemView.findViewById(R.id.eventStartingTime);
            eventEndTime = (TextView) itemView.findViewById(R.id.eventEndTime);
            eventDetails = (TextView) itemView.findViewById(R.id.event_details);
             itemView.setOnClickListener(this);
             itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view){
            if(mLongClickListener != null){
                mLongClickListener.onItemLongClick(adapterView, view, getAdapterPosition(), getItemId());
            }
            return true;
        }
    }

    public void setLongClickListener(AdapterView.OnItemLongClickListener itemLongClickListener){
        this.mLongClickListener = itemLongClickListener;
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
