package com.example.salesbinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {

    private ArrayList<ItemModel> itemList;
    private  ArrayList<ItemModel> itemListFiltered;

    // constructor
    public RecyclerViewAdapter(ArrayList<ItemModel> itemList){
        this.itemList = itemList;
        this.itemListFiltered = itemList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            holder.itemName.setText(itemList.get(position).getNAME());
            holder.itemCategory.setText(itemList.get(position).getCATEGORY());
            holder.itemQty.setText(String.valueOf(itemList.get(position).getQty()));
            holder.itemPrice.setText(String.valueOf(itemList.get(position).getPrice()));
            holder.itemExpireDate.setText(itemList.get(position).getEXPIRY_DATE());

          /**

          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                // TODO

              }
          });

           */

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if(constraint == null | constraint.length() == 0 ){

                    filterResults.count = itemListFiltered.size();
                    filterResults.values = itemListFiltered;

                }else{

                   String searchChar = constraint.toString().toLowerCase();

                   ArrayList<ItemModel> resultList = new ArrayList<>();

                   for( ItemModel item : itemListFiltered ){
                       if(item.getNAME().toLowerCase().contains(searchChar)){
                           resultList.add(item);
                       }
                   }
                    filterResults.count = resultList.size();
                    filterResults.values = resultList;
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                   itemList = (ArrayList<ItemModel>) results.values;
                   notifyDataSetChanged();
            }
        };
        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemCategory, itemName, itemPrice, itemQty, itemExpireDate;

        public MyViewHolder(@NonNull View view) {
            super(view);

            itemCategory = view.findViewById(R.id.txt_view_list_items_category);
            itemName = view.findViewById(R.id.txt_view_list_items_name);
            itemPrice = view.findViewById(R.id.txt_view_list_items_price);
            itemQty = view.findViewById(R.id.txt_view_list_items_qty);
            itemExpireDate = view.findViewById(R.id.txt_view_list_items_expire_date);


        }
    }
}
