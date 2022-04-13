package ca.mcgill.ecse321.project321;

import android.os.Bundle;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.mcgill.ecse321.project321.databinding.CheckoutPageBinding;
import cz.msebera.android.httpclient.entity.mime.Header;


public class CheckoutPage extends Fragment{
    private CheckoutPageBinding binding;
    private Button timeslot_button;
    private Button payment_button;
    private JSONArray cartItems;
    private JSONArray timeslots;
    private String cartType;

    private String customeremail = MainActivity.getEmail();
    private String customerpassword = MainActivity.getPassword();
    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<String> datetimes = new ArrayList<>();

    public CheckoutPage(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        binding = CheckoutPageBinding.inflate(inflater, container, false);
        binding.paymentButton.setEnabled(false);
        binding.checkouterror.setVisibility(View.GONE);

        //button on click setter
        payment_button = binding.paymentButton;
        payment_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                makePayment();
            }
        });

        getTimeSLot();
        //spinner population and on selection action
        Spinner spinner = binding.timeslotSpinner;
        ArrayAdapter<CharSequence> adapter
                = ArrayAdapter.createFromResource(this.getActivity(), R.array.cart_type_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //Called when item is selected, use position of item to find it from list of items

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getCart();

        return binding.getRoot();
    }

    public void getCart(){
        TextView error = binding.checkouterror;
        RequestParams rp = new RequestParams();
        rp.add("customeremail",customeremail);
        rp.add("customerpassword",customerpassword);
        HttpUtils.get("cart",rp,new JsonHttpResponseHandler(){
            //@Override
            public void OnSuccess(int statusCode, Header[] headers, JSONObject response) throws JSONException {
                error.setVisibility(View.GONE);
                try {
                    cartItems = response.getJSONArray("cartItems");
                    cartType = response.getJSONObject("shoppingType").getString("cartType");
                    int productTotalPrice=0;
                    int paymentPrice=0;
                    if(cartItems.length()>0){
                        for(int i=0; i< cartItems.length(); i++){
                            String listElement, productName;
                            int productPrice, productQuantity, subtotal;
                            productName = cartItems.getJSONObject(i).getJSONObject("product").getString("productName");
                            productPrice = cartItems.getJSONObject(i).getJSONObject("price").getInt("productPrice");
                            productQuantity = cartItems.getJSONObject(i).getJSONObject("quantity").getInt("productQuantity");
                            subtotal = productPrice * productQuantity;
                            productTotalPrice += subtotal;
                            listElement = productName+" subtotal :"+subtotal+"($"+productPrice+"*"+productQuantity+"qty)";
                            items.add(listElement);
                            Log.d("MyTag","products:"+cartItems.get(i).toString());
                        }
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
                populateItemList(binding.cartItemsList,items);
                Log.d("myTag","test : CartItems:"+cartItems);
            }
            //@Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                error.setVisibility(View.VISIBLE);
                error.setText(errorResponse.toString());
                Log.d("myTag","test: fail getting cart");
            }
        });
    }

    public void getTimeSLot(){
        TextView error = binding.checkouterror;
        RequestParams rp = new RequestParams();
        HttpUtils.get("availabletimeslots",rp,new JsonHttpResponseHandler(){
            //@Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                error.setVisibility(View.GONE);
                binding.timeslotSpinner.setVisibility(View.VISIBLE);
                try{
                    timeslots = response.getJSONArray("timeslots");
                    if (timeslots.length()>0){
                        for(int i=0; i<timeslots.length(); i++){
                            String listElement, start, end, date;
                            int max;
                            start = timeslots.getJSONObject(i).getJSONObject("startTime").getString("start");
                            end = timeslots.getJSONObject(i).getJSONObject("endTime").getString("end");
                            date = timeslots.getJSONObject(i).getJSONObject("date").getString("date");
                            max = timeslots.getJSONObject(i).getJSONObject("maxOrderPerSlot").getInt("max");
                            listElement = date+" "+start+" ~ "+end;
                            datetimes.add(listElement);
                        }
                    }
                } catch(JSONException e){
                    e.printStackTrace();;
                }
                populateItemList(binding.timeslotList,datetimes);
                Log.d("myTag","test : CartItems:"+cartItems);
            }
            //@Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                error.setVisibility(View.VISIBLE);
                error.setText(errorResponse.toString());
                Log.d("myTag","test : fail getting timeslots");
            }
        });
    }

    public void makePayment(){
        TextView error = binding.checkouterror;
    }

    public void populateItemList(ListView lv, ArrayList<String> arr){
        ArrayAdapter<String> itemListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arr);
        lv.setAdapter(itemListAdapter);
    }


}
