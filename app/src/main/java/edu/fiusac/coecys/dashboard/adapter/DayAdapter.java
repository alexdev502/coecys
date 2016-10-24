package edu.fiusac.coecys.dashboard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.fiusac.coecys.R;
import edu.fiusac.coecys.model.DayInfo;
import edu.fiusac.coecys.model.interfaces.OnItemClickListener;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
    private List<DayInfo> items;
    private OnItemClickListener onItemClickListener;

    public DayAdapter(List<DayInfo> items, OnItemClickListener onItemClickListener) {
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup viewGroup, int typeView) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_day_dashboard, viewGroup, false);
        return new DayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DayViewHolder viewHolder, int i) {
        DayInfo dayInfo = items.get(i);
        viewHolder.bind(dayInfo, this.onItemClickListener);
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvMonthName;
        protected TextView tvDayNum;
        protected TextView tvYear;
        protected TextView tvTitle;
        protected TextView tvDescription;

        public DayViewHolder(View view) {
            super(view);
            tvMonthName = (TextView) view.findViewById(R.id.tvCardDashMonthName);
            tvDayNum = (TextView) view.findViewById(R.id.tvCardDashDayNum);
            tvYear = (TextView) view.findViewById(R.id.tvCardDashYear);
            tvTitle = (TextView) view.findViewById(R.id.tvCardDashTitle);
            tvDescription = (TextView) view.findViewById(R.id.tvCardDashDescription);
        }

        public void bind(final DayInfo dayInfo, final OnItemClickListener listener) {
            tvMonthName.setText(dayInfo.getNameMonth());
            tvDayNum.setText(dayInfo.getNumDay());
            tvYear.setText(dayInfo.getYear());
            tvTitle.setText(dayInfo.getTitle());
            tvDescription.setText(dayInfo.getTimeStart() + " - " + dayInfo.getTimeEnd());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(dayInfo);
                }
            });
        }
    }
}