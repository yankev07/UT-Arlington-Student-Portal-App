package com.segroup9.uta_stud.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.segroup9.uta_stud.R;
import com.segroup9.uta_stud.Utils.CourseItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinyanogo on 10/19/18.
 */

public class CourseGridAdapter extends RecyclerView.Adapter<CourseGridAdapter.ViewHolder> {

    private List<CourseItem> items = new ArrayList<>();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, CourseItem obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CourseGridAdapter(Context context, List<CourseItem> items) {
        this.items = items;
        ctx = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public ImageView image;
        public LinearLayout lyt_parent;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.grid_txt);
            image = (ImageView) v.findViewById(R.id.grid_img);
            lyt_parent = (LinearLayout) v.findViewById(R.id.lyt_parent);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_courses_row, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CourseItem c = items.get(position);
        holder.name.setText(c.getName());
        holder.image.setImageResource(c.getImage());
        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view, c, position);
            }
        });
    }

    public CourseItem getItem(int position){
        return items.get(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }
}
