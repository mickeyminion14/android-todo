package com.example.sarthak.todo;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sarthak on 11/2/18.
 */

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.CustomViewHolder> {
    private home context;
    private ArrayList<Information> informationList;
    private LayoutInflater layoutInflater;
    private final SparseBooleanArray array = new SparseBooleanArray();

    public InformationAdapter(home context, ArrayList<Information> informationList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.informationList = informationList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_layout, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.content.setText(informationList.get(position).content);
        holder.date.setText(informationList.get(position).date);
        holder.time.setText(informationList.get(position).time);
        holder.checkBox.setTag(position);
    }

    @Override
    public int getItemCount() {
        return informationList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView content;
        private TextView date;
        private TextView time;

        private ImageView checkBox;

        public CustomViewHolder(final View itemView) {
            super(itemView);

            this.content = itemView.findViewById(R.id.content);
            this.date = itemView.findViewById(R.id.date);
            this.time = itemView.findViewById(R.id.time);
            this.checkBox = itemView.findViewById(R.id.checkbox);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    new AlertDialog.Builder(context).setTitle("WARNING").setIcon(R.drawable.warning_icon).setMessage("Are you sure you have completed this task ??").setPositiveButton(

                            "yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    int position = (int) view.getTag();
                                    Log.d("App", "pos : " + position);
                                    array.put(getAdapterPosition(), true);
                                    context.deletethis(position);
                                }
                            }
                    ).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();

                }
            });
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}