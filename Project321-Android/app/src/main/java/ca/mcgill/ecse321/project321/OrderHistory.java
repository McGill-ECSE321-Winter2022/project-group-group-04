package ca.mcgill.ecse321.project321;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import ca.mcgill.ecse321.project321.databinding.CartPageBinding;
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ca.mcgill.ecse321.project321.databinding.OrderHistoryBinding;




public class OrderHistory extends Fragment {
    private OrderHistoryBinding binding;
  //  private JSONArray orderCarts;
    private JSONArray cartItems;

    public String customeremail = MainActivity.getEmail();
    public String customerpassword = MainActivity.getPassword();
    public ArrayList<String> orders = new ArrayList<>();

    public OrderHistory(){
        //required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("first", "here1");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //creating the binding
        binding = OrderHistoryBinding.inflate(inflater, container, false);
        //visibility settings
        binding.ordererror.setVisibility(View.GONE);
        binding.orderListItems.setVisibility(View.GONE);

        //ListView listView = binding.orderListItems;
       // ArrayAdapter<String> itemListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, orders);
       // listView.setAdapter(itemListAdapter);

        getHistoryCart();
        return binding.getRoot();
    }

//Helper method to get the Order History
    public void getHistoryCart(){
        TextView error = binding.ordererror;
        RequestParams rp = new RequestParams();

        rp.add("customeremail", customeremail);
        rp.add("customerpassword", customerpassword);

        Log.d("orders", "here123");

        HttpUtils.get("cart/history", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
               error.setVisibility(View.GONE);
               binding.orderListItems.setVisibility(View.VISIBLE);
                try {
                    if (response.length() > 0) {
                        for (int y = 0; y < response.length(); y++) {
                            cartItems = response.getJSONObject(y).getJSONArray("cartItems");
                            //Fetching all the data from an order
                            String listElement, orderType, startingHour, endingHour, date;
                            orderType = response.getJSONObject(y).getString("shoppingType");
                            startingHour = response.getJSONObject(y).getJSONObject("timeSlot").getString("startTime");
                            endingHour = response.getJSONObject(y).getJSONObject("timeSlot").getString("endTime");
                            date = response.getJSONObject(y).getJSONObject("timeSlot").getString("date");
                            listElement = y+ ". " + orderType  + " from " + startingHour + " to " + endingHour + " the " + date + " of ";
                            if (cartItems.length() > 0) {
                                for (int i = 0; i < cartItems.length(); i++) {
                                    String cartElement, productName;
                                    int productQuantity;
                                    productName = cartItems.getJSONObject(i).getJSONObject("product").getString("productName");
                                    productQuantity = cartItems.getJSONObject(i).getInt("quantity");
                                    cartElement = productQuantity +" "+productName;
                                    if (i < cartItems.length() - 1){
                                        listElement = listElement+ cartElement +", ";
                                    }
                                    else {
                                        listElement = listElement+ cartElement;
                                    }
                                }
                            }
                            orders.add(listElement);
                            Log.d("MyTag", "Order: " + response.get(0).toString());
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                populateOrderList();
                Log.d("myTag", "test: Success");
                Log.d("myTag", "test: OrderElements: " + response);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                error.setVisibility(View.VISIBLE);
                error.setText(errorResponse.toString());
                Log.d("myTag", "test: Fail");
            }
        });
    }

    //Populates the listview with orders from cart
    public void populateOrderList(){
        ListView listView = binding.orderListItems;
        ArrayAdapter<String> itemListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, orders);
        listView.setAdapter(itemListAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
