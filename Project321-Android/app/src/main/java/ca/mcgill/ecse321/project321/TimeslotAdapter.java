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
public class TimeslotAdapter extends ArrayAdapter<Timeslot> {
    private Context mContext;
    int mResource;

    public TimeslotAdapter(@NonNull Context context, int resource, @NonNull List<Timeslot> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get product Info
        String date = getItem(position).getDate();
        //String isAvailableOnline = getItem(position).getIsAvailableOnline();
        String start = getItem(position).getStartTime();
        String end = getItem(position).getEndTime();

        //Construct the product
        // Product product = new Product(productName, isAvailableOnline, priceType, price);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);  // Recycle view can be used if the list get really large

        // get TextViewsx
        TextView tvDate = (TextView) convertView.findViewById(R.id.text1);
        TextView tvStartTime = (TextView) convertView.findViewById(R.id.text2);
        TextView tvEndTime = (TextView) convertView.findViewById(R.id.text3);

        // Pair each text view with the fields
        tvDate.setText(date);
        tvStartTime.setText(start);
        tvEndTime.setText(end);

        return convertView;
    }

}
