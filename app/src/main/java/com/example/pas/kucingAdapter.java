package com.example.pas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class kucingAdapter extends RecyclerView.Adapter<kucingAdapter.MyViewHolder> {
    private Context context;
    private ProgressBar file_progressbar;
    private List<kucingModel> kucinglist;
    private CodeAdapterListener listener;


public class MyViewHolder extends RecyclerView.ViewHolder{
    public TextView tvid;
    public ImageView img;
    public MyViewHolder(View view){
    super(view);
    tvid =view.findViewById(R.id.tvname);
    img = view.findViewById(R.id.kucing);
    file_progressbar = view.findViewById(R.id.progress);

    view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onCodeSelected(kucinglist.get(getAdapterPosition()));
        }
    });
    }

}
public kucingAdapter(Context context,List<kucingModel> kucinglist,CodeAdapterListener listener){
this.context = context;
this.kucinglist = kucinglist;
    this.listener = listener;

}


    @NonNull
    @Override
    public kucingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom, parent,false);
            return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull kucingAdapter.MyViewHolder holder, int position) {
final kucingModel kucing =this.kucinglist.get(position);
holder.tvid.setText(kucing.getId());
Glide.with(holder.img.getContext()).load(kucing.getImage()).apply(new RequestOptions()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return this.kucinglist.size();
    }
    public interface CodeAdapterListener {
        void onCodeSelected(kucingModel code);
    }
}
