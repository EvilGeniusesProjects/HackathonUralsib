package allaber.com.bspu.Sections;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

import allaber.com.bspu.News.ClassNews;
import allaber.com.bspu.R;

public class MyRecyclerViewAdapterSection extends RecyclerView.Adapter<MyRecyclerViewAdapterSection.ViewHolder> {

    private List<ClassSection> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    MyRecyclerViewAdapterSection(Context context, List<ClassSection> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recyclerview_section, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassSection animal = mData.get(position);
        holder.myTextView.setText(animal.name);


        if(animal.date.equals("null")) {
            holder.textDate.setVisibility(View.GONE);
        }else{
            holder.textDate.setVisibility(View.VISIBLE);
            holder.textDate.setText(animal.date);
        }

        if(animal.place.equals("null")) {
            holder.textPlace.setVisibility(View.GONE);
        }else{
            holder.textPlace.setVisibility(View.VISIBLE);
            holder.textPlace.setText(animal.place);
        }

        InputStream ims = getClass().getResourceAsStream("/res/drawable/" + animal.image + ".png");
        holder.imageView.setImageDrawable(Drawable.createFromStream(ims, null));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        TextView textDate;
        TextView textPlace;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.textNameNews);
            textPlace = itemView.findViewById(R.id.textPlace);
            textDate = itemView.findViewById(R.id.textDate);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    ClassSection getItem(int id) {
        return mData.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}