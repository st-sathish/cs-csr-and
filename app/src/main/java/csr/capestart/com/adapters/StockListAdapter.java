package csr.capestart.com.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import csr.capestart.com.R;
import csr.capestart.com.data.models.Cookie;

public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.MyViewHolder> {

    private List<Cookie> cookies = new ArrayList<>();

    private Context context;

    public StockListAdapter(Context context) {
        this.context = context;
    }

    public void refresh(List<Cookie> cookies) {
        this.cookies = cookies;
        notifyDataSetChanged();
    }

    public void addAllAndRefresh(List<Cookie> cookies) {
        this.cookies.addAll(cookies);
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
        Cookie cookie = cookies.get(position);
        holder.itemName.setText(cookie.getItemName());
        holder.barcode.setText(cookie.getBarcode());
        holder.expiredDate.setText(cookie.getExpiredDate());
        holder.category.setText(cookie.getCategory());
        String pPrice = this.context.getString(R.string.Rs)+" "+cookie.getPurchasePrice();
        holder.price.setText(pPrice);
//        Uri uri = Uri.parse(ApiEndpoints.IMAGE_BASE_PATH + "/cookie_item_2.jpg");
//        holder.imageView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return cookies.size();
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
