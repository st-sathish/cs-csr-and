package csr.capestart.com.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import csr.capestart.com.R;
import csr.capestart.com.data.models.ExpiredItem;

public class ExpiredItemAdapter extends RecyclerView.Adapter<ExpiredItemAdapter.MyViewHolder> {

    private List<ExpiredItem> expiredItems = Collections.emptyList();

    public ExpiredItemAdapter() {

    }

    public void refresh(List<ExpiredItem> expiredItems) {
        this.expiredItems = expiredItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpiredItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cookie, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpiredItemAdapter.MyViewHolder holder, int position) {
        ExpiredItem expiredItem = this.expiredItems.get(position);
        holder.itemName.setText(expiredItem.getItemName());
        holder.barcode.setText(expiredItem.getBarcode());
        holder.category.setText(expiredItem.getCategory());
        holder.price.setText(expiredItem.getPurchasePrice());
    }

    @Override
    public int getItemCount() {
        return this.expiredItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName;
        private TextView barcode;
        private TextView price;
        private TextView category;
        private TextView expiredDate;

        public MyViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.item_name);
            barcode = view.findViewById(R.id.barcode);
            price = view.findViewById(R.id.price);
            expiredDate = view.findViewById(R.id.expired_date);
            category = view.findViewById(R.id.category);
        }
    }
}
