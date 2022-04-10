package ca.mcgill.ecse321.project321;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import ca.mcgill.ecse321.project321.databinding.CartPageBinding;
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CartPage extends Fragment {
    private CartPageBinding binding;
    Button button;
    private String cartType;
    public String customeremail = "customer@email.com";
    public String customerpassword = "customerPwd";


    public CartPage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = CartPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCart();
            }
        });
        Spinner spinner = view.findViewById(R.id.cartTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.cart_type_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)
            {
                //Called when item is selected, use position of item to find it from list of items
                if(id == 0){
                    cartType = "Delivery";
                    Log.d("myTag", "test: Selected " + cartType);
                }
                if(id == 1){
                    cartType = "Pickup";
                    Log.d("myTag", "test: Selected " + cartType);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                //Called when no item is selected
            }
        });

        return binding.getRoot();
    }

    public void createCart() {
        View view = binding.getRoot();
        TextView error = view.findViewById(R.id.carterror);
        RequestParams rp = new RequestParams();
        rp.add("type", cartType);
        rp.add("customeremail", customeremail);
        rp.add("customerpassword", customerpassword);
        Log.d("myTag", "rp: " + rp.toString());
        HttpUtils.post("/carts",rp,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //TO-DO
                Log.d("myTag", "test: Success");
                //after success
//                String st = null;
//                st = response.toString();
//                tv.setText(st);
//                Log.d("myTag", "test: Clicked" + st);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                error.setText(errorResponse.toString());
                Log.d("myTag", "test: Fail");
            }
        });
    }

    public void deleteCart(){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}