package com.segroup9.uta_stud.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.segroup9.uta_stud.Helper.ContactInformation;
import com.segroup9.uta_stud.R;

import java.util.List;

/**
 * Created by kevinyanogo on 10/5/18.
 */

public class CourseDiscussionAdapter extends RecyclerView.Adapter<CourseDiscussionAdapter.ViewHolder> {

    private List<ContactInformation> contactsList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private AdapterView.OnItemLongClickListener mLongClickListener;
    private AdapterView adapterView;


    // data is passed into the constructor
    public CourseDiscussionAdapter(Context context, List<ContactInformation> contactsList) {
        this.mInflater = LayoutInflater.from(context);
        this.contactsList = contactsList;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_discussion_course_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContactInformation contact = contactsList.get(position);
        holder.firstName.setText(contact.firstName);
        holder.lastName.setText(contact.lastName);
        holder.email.setText(contact.email);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return contactsList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView firstName;
        TextView lastName;
        TextView email;

        ViewHolder(View itemView) {
            super(itemView);
            firstName = (TextView) itemView.findViewById(R.id.firstName);
            lastName = (TextView) itemView.findViewById(R.id.lastName);
            email = (TextView) itemView.findViewById(R.id.email);
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

    // convenience method for getting data at click position
    public String getItemEmail(int id) {
        return contactsList.get(id).email;
    }

    public String getItemUid(int id) {
        return contactsList.get(id).uid;
    }

    public String getItemFirstName(int id) {
        return contactsList.get(id).firstName;
    }

    public String getItemLastName(int id) {
        return contactsList.get(id).lastName;
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
