package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    public interface OnLongClickListner {
        void onItemLongClick(int position);
    }

    List<String> items;
    OnLongClickListner longClickListner;

    public ItemsAdapter(List<String> items, OnLongClickListner longClickListner) {
        this.items = items;
        this.longClickListner = longClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        use layout inflater to infalte a view

        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
//        wrap it insaide a View Holder and return it
        return new ViewHolder(todoView);
    }

//    responsible for binding data to a particular view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        grab the item at the position
        String item = items.get(position);
//        Bind the item into the specified view holder
        holder.bind(item);
    }

//    tells the recycler view how many items are in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    //    Container to provide easy access to views that represent each row of the lsit.
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        //    update the view inside of the view holder with this data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    Notify the listner which position was long pressed
                    longClickListner.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
