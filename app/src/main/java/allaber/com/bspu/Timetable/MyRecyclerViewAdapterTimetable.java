package allaber.com.bspu.Timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import allaber.com.bspu.R;

public class MyRecyclerViewAdapterTimetable extends RecyclerView.Adapter<MyRecyclerViewAdapterTimetable.ViewHolder> {

    private List<ClassTimetable> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public MyRecyclerViewAdapterTimetable(Context context, List<ClassTimetable> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recycler_timetable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassTimetable timetable = mData.get(position);
        holder.textViewItemName.setText(timetable.getItemName());
        holder.textViewSubgroup.setText(timetable.getSubgroup());
        holder.textViewKindOfCouple.setText(timetable.getKindOfCouple());
        holder.textViewTheAudience.setText(timetable.getTheAudience());
        holder.textViewTeacher.setText(timetable.getTeacher());
        holder.textViewCoupleTimeStart.setText(timetable.getCoupleTimeStart());
        holder.textViewCoupleTimeStartEnd.setText(timetable.getCoupleTimeStartEnd());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewItemName;
        TextView textViewSubgroup;
        TextView textViewKindOfCouple;
        TextView textViewTheAudience;
        TextView textViewTeacher;
        TextView textViewCoupleTimeStart;
        TextView textViewCoupleTimeStartEnd;

        ViewHolder(View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewSubgroup = itemView.findViewById(R.id.textViewSubgroup);
            textViewKindOfCouple = itemView.findViewById(R.id.textViewKindOfCouple);
            textViewTheAudience = itemView.findViewById(R.id.textViewTheAudience);
            textViewTeacher = itemView.findViewById(R.id.textViewTeacher);
            textViewCoupleTimeStart = itemView.findViewById(R.id.textViewCoupleTimeStart);
            textViewCoupleTimeStartEnd = itemView.findViewById(R.id.textViewCoupleTimeStartEnd);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    ClassTimetable getItem(int id) {
        return mData.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}