package ca.mcgill.ecse321.project321;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartPage extends Fragment {
    private CartPageBinding binding;
    private Button create_button;
    private Button delete_button;
    private Button clear_button;
    private Button checkout_button;
    private JSONArray cartItems;
    private String cartType;
    private int TotalPrice = 0;

    private String customeremail = MainActivity.getEmail();
    private String customerpassword = MainActivity.getPassword();
    private ArrayList<String> items = new ArrayList<>();


    public CartPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = CartPageBinding.inflate(inflater, container, false);
        //Set Visibility of Items that don't need to be seen yet
        binding.cartItemsList.setVisibility(View.GONE);
        binding.cartItemsTitle.setVisibility(View.GONE);
        binding.deleteCartButton.setVisibility(View.GONE);
        binding.clearCartButton.setVisibility(View.GONE);
        binding.checkoutButton.setEnabled(false);
        binding.cartTotalPrice.setVisibility(View.GONE);
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
                clearDeleteCart();
            }
        });
        clear_button = binding.clearCartButton;
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearCart();
            }
        });
        checkout_button = binding.checkoutButton;
        checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkout();
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
                }
                if(id == 1){
                    cartType = "Pickup";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                //Called when no item is selected
                //In this case at least one item is always selected
            }
        });
        //Get cart if possible
        getCart();
        return binding.getRoot();
    }
    //Create new cart using the cart type from spinner and customer info from being logged in
    public void createCart() {
        RequestParams rp = new RequestParams();
        rp.add("type", cartType);
        rp.add("customeremail", customeremail);
        rp.add("customerpassword", customerpassword);
        HttpUtils.post("carts", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                binding.createCartButton.setVisibility(View.GONE);
                binding.cartTypeSpinner.setVisibility(View.GONE);
                binding.cartType.setVisibility(View.GONE);
                getCart(); //retrieves the cart after it is created
            }
            //On failure it creates a toast that lets the user know why it failed. Either server issue or the user is not logged in
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(customeremail.isEmpty()&&customerpassword.isEmpty()){
                    Toast.makeText(getActivity(), "You must Log in first!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Uhh oh something went wrong, try again later!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Method for deleting existing Cart and resets visibility of components back to cart creation
    public void deleteCart(){
        RequestParams rp = new RequestParams();
        rp.add("customeremail", customeremail);
        rp.add("customerpassword", customerpassword);
        HttpUtils.post("carts/delete", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                binding.createCartButton.setVisibility(View.VISIBLE);
                binding.cartTypeSpinner.setVisibility(View.VISIBLE);
                binding.cartType.setVisibility(View.VISIBLE);
                binding.cartItemsList.setVisibility(View.GONE);
                binding.cartItemsTitle.setVisibility(View.GONE);
                binding.deleteCartButton.setVisibility(View.GONE);
                binding.clearCartButton.setVisibility(View.GONE);
                binding.checkoutButton.setEnabled(false);
                binding.cartTotalPrice.setVisibility(View.GONE);
                TotalPrice = 0;
                items.clear();
                Toast.makeText(getActivity(), "Cart was deleted!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                binding.createCartButton.setVisibility(View.VISIBLE);
                binding.cartTypeSpinner.setVisibility(View.VISIBLE);
                binding.cartType.setVisibility(View.VISIBLE);
                binding.cartItemsList.setVisibility(View.GONE);
                binding.cartItemsTitle.setVisibility(View.GONE);
                binding.deleteCartButton.setVisibility(View.GONE);
                binding.clearCartButton.setVisibility(View.GONE);
                binding.checkoutButton.setEnabled(false);
                binding.cartTotalPrice.setVisibility(View.GONE);
                TotalPrice = 0;
                items.clear();
                Toast.makeText(getActivity(), "Cart was deleted!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable){
                binding.createCartButton.setVisibility(View.VISIBLE);
                binding.cartTypeSpinner.setVisibility(View.VISIBLE);
                binding.cartType.setVisibility(View.VISIBLE);
                binding.cartItemsList.setVisibility(View.GONE);
                binding.cartItemsTitle.setVisibility(View.GONE);
                binding.deleteCartButton.setVisibility(View.GONE);
                binding.clearCartButton.setVisibility(View.GONE);
                binding.cartTotalPrice.setVisibility(View.GONE);
                binding.checkoutButton.setEnabled(false);
                TotalPrice = 0;
                items.clear();
                Toast.makeText(getActivity(), "Cart was deleted!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //Attempts to retrieve cart the customer has and lists the items in cart if any to list view
    public void getCart(){
        RequestParams rp = new RequestParams();
        rp.add("customeremail", customeremail);
        rp.add("customerpassword", customerpassword);
        HttpUtils.get("cart", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                binding.createCartButton.setVisibility(View.GONE);
                binding.cartTypeSpinner.setVisibility(View.GONE);
                binding.cartType.setVisibility(View.GONE);
                binding.deleteCartButton.setVisibility(View.VISIBLE);
                binding.cartItemsList.setVisibility(View.VISIBLE);
                binding.cartItemsTitle.setVisibility(View.VISIBLE);
                binding.clearCartButton.setVisibility(View.VISIBLE);
                try {
                    cartItems = response.getJSONArray("cartItems");

                    if(cartItems.length() >0){ //If cart is not empty then loop through all the items and add each item as a string to the items arraylist for the list view

                        binding.checkoutButton.setEnabled(true);
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
                binding.cartTotalPrice.setVisibility(View.VISIBLE);
                binding.cartTotalPrice.setText("Total Cart Price:  " + TotalPrice);
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
    //clearCart and re-getCart to update listview
    public void clearCart(){
        RequestParams rp = new RequestParams();
        rp.add("customeremail", customeremail);
        rp.add("customerpassword", customerpassword);
        HttpUtils.post("carts/clear", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                items.clear();
                getCart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                items.clear();
                getCart();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                items.clear();
                getCart();
            }
        });
    }
    //needed a second clear for just deleting cart because the cart must be clear to delete
    public void clearDeleteCart(){
        RequestParams rp = new RequestParams();
        rp.add("customeremail", customeremail);
        rp.add("customerpassword", customerpassword);
        HttpUtils.post("carts/clear", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                items.clear();
                deleteCart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                items.clear();
                deleteCart();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                items.clear();
                deleteCart();
            }
        });
    }

    public void checkout(){
        if(cartItems.length() >0){
            items.clear();
            NavHostFragment.findNavController(CartPage.this)
                    .navigate(R.id.action_cart_to_checkout);
        }
    }
    //Populates the listview with items from cart
    public void populateItemList(){
        ListView lv = binding.cartItemsList;
        ArrayAdapter<String> itemListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        lv.setAdapter(itemListAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}