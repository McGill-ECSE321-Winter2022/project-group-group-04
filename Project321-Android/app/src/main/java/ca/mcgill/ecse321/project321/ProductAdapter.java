package ca.mcgill.ecse321.project321;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

//This method to create a custom adapter is learned from https://www.youtube.com/watch?v=E6vE8fqQPTE&t=181s
public class ProductAdapter extends ArrayAdapter<Product> {
    private Context mContext;
    int mResource;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get product Info
        String productName = getItem(position).getProductName();
        //String isAvailableOnline = getItem(position).getIsAvailableOnline();
        String priceType = getItem(position).getPriceType();
        String price = getItem(position).getPrice();

        //Construct the product
       // Product product = new Product(productName, isAvailableOnline, priceType, price);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);  // Recycle view can be used if the list get really large

        // get TextViews
        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvPriceType = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.textView3);

        // Pair each text view with the fields
        tvName.setText(productName);
        tvPriceType.setText(priceType);
        tvPrice.setText(price);

        return convertView;
    }

}
