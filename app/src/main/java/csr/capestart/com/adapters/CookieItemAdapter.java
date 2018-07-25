package csr.capestart.com.adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import csr.capestart.com.R;
import csr.capestart.com.data.ApiEndpoints;
import csr.capestart.com.data.models.CookieItem;

public class CookieItemAdapter extends RecyclerView.Adapter<CookieItemAdapter.MyViewHolder> {

    private List<CookieItem> cookieItems = Collections.emptyList();

    public CookieItemAdapter() {

    }

    public void refresh(List<CookieItem> cookieItems) {
        this.cookieItems = cookieItems;
        notifyDataSetChanged();
    }

    public void addAllAndRefresh(List<CookieItem> cookieItems) {
        this.cookieItems.addAll(cookieItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cookie, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CookieItem cookieItem = cookieItems.get(position);
        holder.itemName.setText(cookieItem.getItemName());
        holder.barcode.setText(cookieItem.getBarcode());
        holder.expiredDate.setText(cookieItem.getExpiredDate());
        holder.category.setText(cookieItem.getCategory());
        holder.price.setText(cookieItem.getPrice());
//        Uri uri = Uri.parse(ApiEndpoints.IMAGE_BASE_PATH + "/cookie_item_2.jpg");
//        holder.imageView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return cookieItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView itemName;
        private TextView barcode;
        private TextView price;
        private TextView category;
        private TextView expiredDate;
        private ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.item_name);
            barcode = view.findViewById(R.id.barcode);
            price = view.findViewById(R.id.price);
            expiredDate = view.findViewById(R.id.expired_date);
            category = view.findViewById(R.id.category);
            //imageView = view.findViewById(R.id.cookie_img);
        }
    }
}
