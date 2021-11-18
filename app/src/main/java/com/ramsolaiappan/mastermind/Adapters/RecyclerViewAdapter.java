package com.ramsolaiappan.mastermind.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.ramsolaiappan.mastermind.Classes.ColorCode;
import com.ramsolaiappan.mastermind.GlobalVariables;
import com.ramsolaiappan.mastermind.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private int lastPosition=-1;
    private ArrayList<ColorCode> colorCodeList = new ArrayList<>();
    private Context context;
    private int resource;
    private OnColorClickListener onColorClickListener;

    public RecyclerViewAdapter(Context context, int resource,ArrayList<ColorCode> colorCodeList) {
        this.context = context;
        this.resource = resource;
        this.colorCodeList = colorCodeList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView idTV;
        private CircleImageView ac1,ac2,ac3,ac4,hc1,hc2,hc3,hc4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTV = itemView.findViewById(R.id.roundIdTV);
            ac1 = itemView.findViewById(R.id.acolor1);
            ac2 = itemView.findViewById(R.id.acolor2);
            ac3 = itemView.findViewById(R.id.acolor3);
            ac4 = itemView.findViewById(R.id.acolor4);
            hc1 = itemView.findViewById(R.id.hcolor1);
            hc2 = itemView.findViewById(R.id.hcolor2);
            hc3 = itemView.findViewById(R.id.hcolor3);
            hc4 = itemView.findViewById(R.id.hcolor4);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(this.resource,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ColorCode currentItem = colorCodeList.get(position);
        holder.idTV.setText(String.valueOf(currentItem.getId()));
        holder.ac1.setImageResource(GlobalVariables.colorCodes[currentItem.getColorCode()[0]]);
        holder.ac2.setImageResource(GlobalVariables.colorCodes[currentItem.getColorCode()[1]]);
        holder.ac3.setImageResource(GlobalVariables.colorCodes[currentItem.getColorCode()[2]]);
        holder.ac4.setImageResource(GlobalVariables.colorCodes[currentItem.getColorCode()[3]]);
        holder.hc1.setImageResource(GlobalVariables.hintCodes[currentItem.getHintCode()[0]]);
        holder.hc2.setImageResource(GlobalVariables.hintCodes[currentItem.getHintCode()[1]]);
        holder.hc3.setImageResource(GlobalVariables.hintCodes[currentItem.getHintCode()[2]]);
        holder.hc4.setImageResource(GlobalVariables.hintCodes[currentItem.getHintCode()[3]]);

        if(position == (colorCodeList.size()-1))
        {
            int k  = position;
            if(onColorClickListener != null)
            {
                holder.ac1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onColorClickListener.OnColorClick(k,0,colorCodeList.get(k).getColorCode()[0]);
                    }
                });
                holder.ac2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onColorClickListener.OnColorClick(k,1,colorCodeList.get(k).getColorCode()[1]);
                    }
                });
                holder.ac3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onColorClickListener.OnColorClick(k,2,colorCodeList.get(k).getColorCode()[2]);
                    }
                });
                holder.ac4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onColorClickListener.OnColorClick(k,3,colorCodeList.get(k).getColorCode()[3]);
                    }
                });
            }
        }
        else
        {
            holder.ac1.setOnClickListener(null);
            holder.ac2.setOnClickListener(null);
            holder.ac3.setOnClickListener(null);
            holder.ac4.setOnClickListener(null);
        }

        setAnimation(holder.itemView,position);
    }

    public void setAnimation(View view, int pos)
    {
        if(pos > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.recycler_item_animation);
            view.startAnimation(animation);
            lastPosition = pos;
        }
    }

    @Override
    public int getItemCount() {
        return colorCodeList.size();
    }

    public interface OnColorClickListener
    {
        void OnColorClick(int position, int colorIndex, int colorOfView);
    }

    public void setOnColorClickListener(OnColorClickListener listener)
    {
        this.onColorClickListener = listener;
    }

}
