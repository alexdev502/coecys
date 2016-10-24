package edu.fiusac.coecys.day_ditail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.fiusac.coecys.R;
import edu.fiusac.coecys.model.ActivityInfo;
import edu.fiusac.coecys.model.interfaces.OnItemClickListener;
import edu.fiusac.coecys.utils.ResourceIcon;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ActivityViewHolder> {
    private List<ActivityInfo> items;
    private OnItemClickListener onItemClickListener;
    protected Context context;

    public ActivitiesAdapter(Context context, List<ActivityInfo> items, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_activity, viewGroup, false);
        return new ActivityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder viewHolder, int i) {
        ActivityInfo activityInfo = items.get(i);
        viewHolder.bind(activityInfo, this.onItemClickListener, this.context);
    }


    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        protected CircleImageView civActivityImage;
        protected TextView tvTitle;
        protected TextView tvSpeaker;
        protected TextView tvDescription;

        public ActivityViewHolder(View view) {
            super(view);
            civActivityImage = (CircleImageView) view.findViewById(R.id.activity_image);
            tvTitle = (TextView) view.findViewById(R.id.tvCardActivityTitle);
            tvSpeaker = (TextView) view.findViewById(R.id.tvCardActivitySpeaker);
            tvDescription = (TextView) view.findViewById(R.id.tvCardActivityDescription);
        }

        public void bind(final ActivityInfo activityInfo, final OnItemClickListener onItemClickListener, Context context) {
            this.loadingImage(activityInfo.getIcon());
            tvTitle.setText(activityInfo.getTitle());
            tvSpeaker.setText(activityInfo.getSpeaker());
            tvDescription.setText(activityInfo.getStartTime() + " - " + activityInfo.getEndTime());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) onItemClickListener.onItemClick(activityInfo);
                }
            });
        }

        private void loadingImage(int icon) {
            Glide.with(civActivityImage.getContext())
                    .load(ResourceIcon.getResource(icon))
                    .centerCrop()
                    .into(civActivityImage);
        }
    }
}