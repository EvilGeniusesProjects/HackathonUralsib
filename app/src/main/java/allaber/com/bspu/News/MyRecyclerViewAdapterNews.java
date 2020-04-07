package allaber.com.bspu.News;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

import allaber.com.bspu.R;

public class MyRecyclerViewAdapterNews extends RecyclerView.Adapter<MyRecyclerViewAdapterNews.ViewHolder> {

    private List<ClassNews> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    MyRecyclerViewAdapterNews(Context context, List<ClassNews> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recyclerview_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassNews animal = mData.get(position);
        holder.myTextView.setText(animal.name);
        holder.textDate.setText(animal.date);


        InputStream ims = getClass().getResourceAsStream("/res/drawable/" + animal.image + ".png");

        //Uri imageURI = Uri.parse("android.resource://allaber.com.bspu/drawable/" + animal.image);
        //Picasso.get().load(imageURI).into(holder.imageView);

        holder.imageView.setImageDrawable(Drawable.createFromStream(ims, null));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        TextView textDate;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.textNameNews);
            textDate = itemView.findViewById(R.id.textDate);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    ClassNews getItem(int id) {
        return mData.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}