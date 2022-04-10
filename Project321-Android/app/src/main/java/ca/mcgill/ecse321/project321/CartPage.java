package ca.mcgill.ecse321.project321;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import ca.mcgill.ecse321.project321.databinding.CartPageBinding;
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class CartPage extends Fragment {
    private CartPageBinding binding;
    Button create_button;
    Button delete_button;
    Button get_button;
    JSONArray cartItems;
    private String cartType;

    //Test variables
    public String customeremail = "customer@email.com";
    public String customerpassword = "password";
    public ArrayList<String> items = new ArrayList<>();
    //


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
        binding.carterror.setVisibility(View.GONE);
        binding.cartItemsList.setVisibility(View.GONE);
        //Button on click setters
        create_button = binding.createCartButton;
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCart();
            }
        });
        delete_button = binding.deleteCartButton;
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCart();
            }
        });
        get_button = binding.getCartButton;
        get_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCart();
            }
        });
        //Spinner population and on select actions
        Spinner spinner = binding.cartTypeSpinner;
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
        //Get cart
        getCart();
        return binding.getRoot();
    }
    //Create new cart
    public void createCart() {
        TextView error = binding.carterror;
        RequestParams rp = new RequestParams();
        rp.add("type", cartType);
        rp.add("customeremail", customeremail);
        rp.add("customerpassword", customerpassword);
        HttpUtils.post("carts", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("myTag", "test: Success");
                error.setVisibility(View.GONE);
                binding.createCartButton.setVisibility(View.GONE);
                binding.cartTypeSpinner.setVisibility(View.GONE);
                binding.cartType.setVisibility(View.GONE);
                getCart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                error.setVisibility(View.VISIBLE);
                error.setText(errorResponse.toString());
                Log.d("myTag", "test: Fail");
            }
        });
    }
    //Method for deleting existing Cart
    public void deleteCart(){
        TextView error = binding.carterror;
        RequestParams rp = new RequestParams();
        rp.add("customeremail", customeremail);
        rp.add("customerpassword", customerpassword);
        HttpUtils.post("carts/delete", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                error.setVisibility(View.GONE);
                binding.createCartButton.setVisibility(View.VISIBLE);
                binding.cartTypeSpinner.setVisibility(View.VISIBLE);
                binding.cartType.setVisibility(View.VISIBLE);
                Log.d("myTag", "test: Success");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                error.setVisibility(View.VISIBLE);
                error.setText(errorResponse.toString());
                Log.d("myTag", "test: Fail");
                binding.createCartButton.setVisibility(View.VISIBLE);
                binding.cartTypeSpinner.setVisibility(View.VISIBLE);
                binding.cartType.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable){
                error.setVisibility(View.VISIBLE);
                error.setText(errorResponse.toString());
                Log.d("myTag", "test: Fail");
                binding.createCartButton.setVisibility(View.VISIBLE);
                binding.cartTypeSpinner.setVisibility(View.VISIBLE);
                binding.cartType.setVisibility(View.VISIBLE);
            }
        });
    }
    //Gets cart if one already exists
    public void getCart(){
        TextView error = binding.carterror;
        RequestParams rp = new RequestParams();
        rp.add("customeremail", customeremail);
        rp.add("customerpassword", customerpassword);
        HttpUtils.get("cart", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                error.setVisibility(View.GONE);
                binding.createCartButton.setVisibility(View.GONE);
                binding.cartTypeSpinner.setVisibility(View.GONE);
                binding.cartType.setVisibility(View.GONE);
                binding.cartItemsList.setVisibility(View.VISIBLE);
                try {
                    cartItems = response.getJSONArray("cartItems");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                populateItemList();
                Log.d("myTag", "test: Success");
                Log.d("myTag", "test: CartItems: " + cartItems);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                error.setVisibility(View.VISIBLE);
                error.setText(errorResponse.toString());
                Log.d("myTag", "test: Fail");
            }
        });
    }

    public void populateItemList(){
        items.add("CartItem1");
        items.add("CartItem2");
        items.add("CartItem3");
        ListView lv = binding.cartItemsList;
        ArrayAdapter<String> itemListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        lv.setAdapter(itemListAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}