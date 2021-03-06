package ca.mcgill.ecse321.project321;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;

import org.json.JSONException;
import org.json.JSONObject;

import ca.mcgill.ecse321.project321.databinding.StoreInfoBinding;

public class StoreInfo extends Fragment {

    private StoreInfoBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = StoreInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView email = (TextView) view.findViewById(R.id.storeemail);
        TextView phone = (TextView) view.findViewById(R.id.storephone);
        TextView address = (TextView) view.findViewById(R.id.storeaddress);
        TextView opening = (TextView) view.findViewById(R.id.storeopening);
        TextView closing = (TextView) view.findViewById(R.id.storeclosing);
        TextView ootf = (TextView) view.findViewById(R.id.storeoutoftownfee);
        TextView error = (TextView) view.findViewById(R.id.storeinfoerror);
        HttpUtils.get("store", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String addressstr = new String();
                    email.setText(response.getString("email"));
                    phone.setText(response.getString("telephone"));
                    addressstr = response.getJSONObject("address").getString("unit") + " " +
                            response.getJSONObject("address").getString("street") + " " +
                            response.getJSONObject("address").getString("town") + " " +
                            response.getJSONObject("address").getString("postalCode");
                    address.setText(addressstr);
                    opening.setText("Opening hours : " + response.getString("openingHour"));
                    closing.setText("Closing hours : " +response.getString("closingHour"));
                    ootf.setText("Fee for delivery out of \ntown: " + response.getString("outOfTownFee") + "$");
                } catch (JSONException e) {
                    e.printStackTrace();
                    email.setText(response.toString());
                }
                error.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                error.setText("Failed to get store info from the server.");
                if(errorResponse != null) {
                    error.setText(errorResponse.toString());
                    error.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
