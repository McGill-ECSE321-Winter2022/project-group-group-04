package ca.mcgill.ecse321.project321;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import ca.mcgill.ecse321.project321.databinding.CartPageBinding;
import ca.mcgill.ecse321.project321.databinding.CheckoutPageBinding;
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class CheckoutPage extends Fragment {
    private CheckoutPageBinding binding;
    private Button timeslot_button;
    private Button payment_button;
    TimeslotAdapter adapter;

    private JSONArray cartItems;
    private int TotalPrice = 0;

    private String customeremail = MainActivity.getEmail();
    private String customerpassword = MainActivity.getPassword();
    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<String> alltimeslots = new ArrayList<>();
    ArrayList<Timeslot> timeslots = new ArrayList<>();

    private String paymentcode;

    public CheckoutPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = CheckoutPageBinding.inflate(inflater, container, false);
        Timeslot selectedTimeslot = new Timeslot(new Time(23,59,58),
                                            new Time(23,59,59),
                                            new Date(1999,12,31));

        timeslots = new ArrayList<Timeslot>();
        timeslots.add(selectedTimeslot);
        View view = inflater.inflate(R.layout.checkout_page, container, false);
        ListView listView = (ListView)view.findViewById(R.id.timeslotList);
        adapter = new TimeslotAdapter(getActivity(),R.layout.custom_listview,timeslots);
        listView.setAdapter(adapter);

        //Set Visibility of Items that don't need to be seen yet
        binding.paymentButton.setEnabled(false);
        binding.checkouterror.setVisibility(View.GONE);
        //Button on click setters
        payment_button = binding.paymentButton;
        payment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentcode = binding.paymentcodeinput.getText().toString();
                Log.d("paymentcode input",paymentcode);
                if(paymentcode.length() == 0) {
                    binding.checkouterror.setText("payment code cannot be empty");
                    binding.checkouterror.setVisibility(View.VISIBLE);
                }
                else {
                    pay(paymentcode);
                }
            }
        });

        timeslot_button = binding.timeslotButton;
        timeslot_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (selectedTimeslot.getDate()=="1999-12-31"){
                    binding.checkouterror.setText("You should select Timeslot");
                    binding.checkouterror.setVisibility(View.VISIBLE);

                }
                else {
                    confirm_timeslot(selectedTimeslot);
                }
            }
        });
        //When the timeslot is clicked, the timeslot will be selected.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Status",String.valueOf(MainActivity.getStatus()));
                if (timeslots.get(i).getDate().equals("1999-12-31")){
                    Toast.makeText(getActivity(), "There is no product in the system", Toast.LENGTH_SHORT).show();
                    ///
                }
            }
        });

        //Get cart if possible
        getCart();
        //get timeslot
        getTimeslot();
        //Get timeslots if possible
        return binding.getRoot();
    }

    //Attempts to retrieve cart the customer has and lists the items in cart if any to list view
    public void getCart(){
        RequestParams rp = new RequestParams();
        rp.add("customeremail", customeremail);
        rp.add("customerpassword", customerpassword);
        HttpUtils.get("cart", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    cartItems = response.getJSONArray("cartItems");

                    if(cartItems.length() >0){ //If cart is not empty then loop through all the items and add each item as a string to the items arraylist for the list view

                        for(int i = 0; i < cartItems.length(); i++){
                            String listElement,productName,productPriceType;
                            int productPrice,productQuantity;
                            productName = cartItems.getJSONObject(i).getJSONObject("product").getString("productName");
                            productPrice = cartItems.getJSONObject(i).getJSONObject("product").getInt("price");
                            productPriceType = cartItems.getJSONObject(i).getJSONObject("product").getString("priceType");
                            productQuantity = cartItems.getJSONObject(i).getInt("quantity");
                            listElement = productName + "    " + productPriceType + ": " + productPrice + "$  " + "Quantity: " + productQuantity + "                                  Item Total:  " + (productPrice*productQuantity)+ "$" ;
                            items.add(listElement);
                            TotalPrice += productPrice*productQuantity;
                        }
                    }
                    else if(cartItems.length() == 0){
                        TotalPrice = 0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                binding.cartItemsTotal.setVisibility(View.VISIBLE);
                binding.cartItemsTotal.setText("Total Cart Price:  " + TotalPrice);
                populateItemList();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(customeremail.isEmpty()&&customerpassword.isEmpty()){
                    Toast.makeText(getActivity(), "You must Log in first!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "You must create a cart first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Populates the listview with items from cart
    public void populateItemList(){
        ListView lv = binding.cartItemsReviewList;
        ArrayAdapter<String> itemListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        lv.setAdapter(itemListAdapter);
    }

    public void getTimeslot(){
        RequestParams rp = new RequestParams();
        HttpUtils.get("availabletimeslots",rp,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                try {

                    for(int i=0; i<response.length();i++){
                        String listElement, date, start, end;
                        date = response.getJSONObject(i).getString("date");
                        start = response.getJSONObject(i).getString("startTime");
                        end = response.getJSONObject(i).getString("endTime");
                        listElement = "["+date+"]"+start+"-"+end;
                        Log.d("myTag",listElement);
                        alltimeslots.add(listElement);
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
                populateTimeslotList();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                Log.d("myTag","getting timeslot failed");
            }
        });
    }
    //Populates the listview with items from cart
    public void populateTimeslotList(){
        ListView lv = binding.timeslotList;
        ArrayAdapter<String> timeslotListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, alltimeslots);
        lv.setAdapter(timeslotListAdapter);
    }

    public void confirm_timeslot(Timeslot timeslot){
        RequestParams rp = new RequestParams();
        rp.add("customeremail", customeremail);
        rp.add("customerpassword", customerpassword);
        rp.add("timeslotdate",timeslot.getDate());
        rp.add("timeslotstarttime",timeslot.getStartTime());
        rp.add("timeslotendtime",timeslot.getEndTime());
        HttpUtils.post("/carts/timeslot",rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Toast.makeText(getActivity(),"Your timeslot is confirmed",Toast.LENGTH_SHORT).show();
                binding.paymentButton.setEnabled(true);

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                Toast.makeText(getActivity(),"something went wrong, try again later",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void pay(String paymentcode){
        RequestParams rp = new RequestParams();
        rp.add("paymentcode",paymentcode);
        rp.add("customeremail", customeremail);
        rp.add("customerpassword", customerpassword);
        HttpUtils.post("/cart/pay",rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                NavHostFragment.findNavController(CheckoutPage.this)
                        .navigate(R.id.action_payment_to_history);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                Toast.makeText(getActivity(),"payment cannot be completed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}