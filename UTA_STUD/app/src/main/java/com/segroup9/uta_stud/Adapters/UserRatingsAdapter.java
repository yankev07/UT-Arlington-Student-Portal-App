package com.segroup9.uta_stud.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.segroup9.uta_stud.Helper.EventInformation;
import com.segroup9.uta_stud.Helper.RatingInformation;
import com.segroup9.uta_stud.R;

import java.util.List;

/**
 * Created by kevinyanogo on 10/21/18.
 */

public class UserRatingsAdapter extends RecyclerView.Adapter<UserRatingsAdapter.CourseViewHolder> {

    private Context mCtx;
    private List<RatingInformation> coursesList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private AdapterView.OnItemLongClickListener mLongClickListener;
    private AdapterView adapterView;

    public UserRatingsAdapter(Context mCtx, List<RatingInformation> coursesList) {
        this.mCtx = mCtx;
        this.coursesList = coursesList;
    }

    @NonNull
    @Override
    public UserRatingsAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.fragment_ratings_review, parent, false);
        return new UserRatingsAdapter.CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRatingsAdapter.CourseViewHolder holder, int position) {
        RatingInformation course = coursesList.get(position);
        if(course.overallRating == 1){
            holder.imageViewRating.setImageResource(R.drawable.star1);
            holder.imageViewEmoji.setImageResource(R.drawable.icon_sad);
        }
        else if(course.overallRating == 2){
            holder.imageViewRating.setImageResource(R.drawable.stars2);
            holder.imageViewEmoji.setImageResource(R.drawable.icon_sad);
        }
        else if(course.overallRating == 3){
            holder.imageViewRating.setImageResource(R.drawable.stars3);
            holder.imageViewEmoji.setImageResource(R.drawable.icon_neutral);
        }
        else if(course.overallRating == 4){
            holder.imageViewRating.setImageResource(R.drawable.stars4);
            holder.imageViewEmoji.setImageResource(R.drawable.icon_happy);
        }
        else{
            holder.imageViewRating.setImageResource(R.drawable.stars5);
            holder.imageViewEmoji.setImageResource(R.drawable.icon_happy);
        }
        holder.courseName.setText(course.courseName);
        holder.instructorName.setText(course.professorName);
        holder.examsRating.setText(course.courseExams);
        holder.contentRating.setText(course.courseContent);
        holder.teachingRating.setText(course.courseTeaching);
        holder.homeworksRating.setText(course.courseHomeworks);
        holder.wouldRecommend.setText(course.courseRecommendation);
        holder.personnalFeedback.setText(course.personalComments);
        holder.author.setText(course.author);

    }

    public RatingInformation getItem(int position){
        return coursesList.get(position);
    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView imageViewRating;
        ImageView imageViewEmoji;
        TextView courseName;
        TextView instructorName;
        TextView examsRating;
        TextView contentRating;
        TextView teachingRating;
        TextView homeworksRating;
        TextView wouldRecommend;
        TextView personnalFeedback;
        TextView author;


        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewRating = (ImageView) itemView.findViewById(R.id.course_rating);
            imageViewEmoji = (ImageView) itemView.findViewById(R.id.emoji);
            courseName = (TextView) itemView.findViewById(R.id.course_name);
            instructorName = (TextView) itemView.findViewById(R.id.instructor_name);
            examsRating = (TextView) itemView.findViewById(R.id.examsRating);
            contentRating = (TextView) itemView.findViewById(R.id.contentRating);
            teachingRating = (TextView) itemView.findViewById(R.id.teachingRating);
            homeworksRating = (TextView) itemView.findViewById(R.id.homeworksRating);
            wouldRecommend = (TextView) itemView.findViewById(R.id.wouldRecommend);
            personnalFeedback = (TextView) itemView.findViewById(R.id.personal_feedback);
            author = (TextView) itemView.findViewById(R.id.author);

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
