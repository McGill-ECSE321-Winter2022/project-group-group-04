package ca.mcgill.ecse321.project321;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import ca.mcgill.ecse321.project321.databinding.OrderHistoryBinding;
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderHistory extends Fragment {
    private OrderHistoryBinding binding;
    JSONArray orderCarts;
    JSONArray cartItems;

    //Test variables
    public String customeremail = "customer@email.com";
    public String customerpassword = "customerPwd";
    public ArrayList<String> orders = new ArrayList<>();

    public OrderHistory(){
        //required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = OrderHistoryBinding.inflate(inflater, container, false);
        getHistoryCart();
        return binding.getRoot();
    }



//Helper method to get the Order History
    public void getHistoryCart(){
        RequestParams rp = new RequestParams();
        rp.add("customeremail", customeremail);
        rp.add("customerpassword", customerpassword);
        HttpUtils.get("cart/history", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
              //  error.setVisibility(View.GONE);
               binding.orderListItems.setVisibility(View.VISIBLE);
                try {
                    orderCarts = response.getJSONArray("carts");
                    if (orderCarts.length() > 0) {
                        for (int y = 0; y < orderCarts.length(); y++) {
                            cartItems = orderCarts.getJSONObject(y).getJSONArray("cartItems");
                            String listElement, orderType, startingHour, endingHour, date;
                            orderType = orderCarts.getJSONObject(y).getString("type");
                            startingHour = orderCarts.getJSONObject(y).getJSONObject("timeSlot").getString("startTime");
                            endingHour = orderCarts.getJSONObject(y).getJSONObject("timeSlot").getString("endTime");
                            date = orderCarts.getJSONObject(y).getJSONObject("timeSlot").getString("date");
                            listElement = orderType + " from " + startingHour + " to " + endingHour + " at " + date + " of ";
                            if (cartItems.length() > 0) {
                                for (int i = 0; i < cartItems.length(); i++) {
                                    String cartElement, productName;
                                    int productQuantity;
                                    productName = cartItems.getJSONObject(i).getJSONObject("product").getString("productName");
                                    productQuantity = cartItems.getJSONObject(i).getInt("quantity");
                                    cartElement = productQuantity +" "+productName;
                                    if (i < cartItems.length()-1){
                                        cartElement = cartElement +", ";
                                    }
                                    listElement = listElement.concat(cartElement);
                                    orders.add(cartElement);
                                    Log.d("MyTag", "Products: " + cartItems.get(0).toString());
                                }
                            }
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                populateItemList();
            }
        });
    }

    //Populates the listview with orders from cart
    public void populateItemList(){
        ListView lv = binding.orderListItems;
        ArrayAdapter<String> itemListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, orders);
        lv.setAdapter(itemListAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
