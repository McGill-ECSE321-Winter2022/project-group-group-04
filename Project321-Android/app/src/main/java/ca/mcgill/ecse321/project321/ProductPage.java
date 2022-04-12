package ca.mcgill.ecse321.project321;


import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ProductPage extends Fragment {

    ArrayList<Product> products = new ArrayList<Product>();
    private boolean hasCart = false;
    ProductAdapter adapter;

    public ProductPage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCart();  // check if customer have a cart or not
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        products = new ArrayList<Product>();
        View view = inflater.inflate(R.layout.product_page, container, false);

        Product emptyProduct = new Product("no products available", "N/A", "N/A", "N/A");
        products.add(emptyProduct);   //This empty product will be displaced when there is no product in the system

        ListView listView = (ListView)view.findViewById(R.id.productList);
        // The adapter is a custom adapter made to display product name, price type and price
        adapter = new ProductAdapter(getActivity(), R.layout.custom_listview, products);
        listView.setAdapter(adapter);

        /**
         * When the a list that contains the product info is clicked, the product will be added to the cart
         * Warning will be given as Toast if the there is no product in the system, user did not login or user has no cart
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("hasCart", String.valueOf(hasCart));
                Log.d("Status", String.valueOf(MainActivity.getStatus()));
                if (products.get(i).getProductName().equals("no products available")) {
                    Toast.makeText(getActivity(), "There is no product in the system", Toast.LENGTH_SHORT).show();
                } else if (!MainActivity.getStatus()){
                    Toast.makeText(getActivity(), "You need to login first", Toast.LENGTH_SHORT).show();
                } else if (!hasCart) {
                    Toast.makeText(getActivity(), "You need to create a cart first", Toast.LENGTH_SHORT).show();
                } else {
                    addProduct(products.get(i));  // add the product to the cart
                }
            }
        });

        getProducts();// Get all product in the system to display

        /**
         * The below block create a info button for the customer to show what customer can do in this page
         */
        FloatingActionButton button = (FloatingActionButton)view.findViewById(R.id.imageButton2);
        FloatingActionButton cancelButton = (FloatingActionButton)view.findViewById(R.id.imageButton1);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView)view.findViewById(R.id.popup_text);
                tv.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView)view.findViewById(R.id.popup_text);
                tv.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    public void getProducts() {
        RequestParams rp = new RequestParams();
        rp.add("customerPage", "true");
        Log.d("products", "66666666666 ");
        HttpUtils.get("/products", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for( int i = 0; i < response.length(); i++){
                    try {
                        Log.d("products", "111111111 ");
                        // Add the product to the list
                        products.add(new Product(response.getJSONObject(i).getString("productName") ,
                                response.getJSONObject(i).getString("isAvailableOnline"),
                                response.getJSONObject(i).getString("priceType"),
                                response.getJSONObject(i).getString("price") + "$"));
                    } catch (Exception e) {
                        Log.d("products", "222222222 ");
                    }
                }
                // If system have products, we do not need the emptyProduct
                if (products.size()>1 && products.get(0).getProductName().equals("no products available")) {
                    products.remove(0);
                }
                // Notify the adapter the content of the product list has now changed
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    System.out.println(errorResponse.get("message").toString());
                    Log.d("products", "333333333 ");
                } catch (JSONException e) {
                    Log.d("products", "444444444 ");
                }
            }
        });
    }

    /**
     * If a customer has cart, the boolean hasCart will be true
     */
    public void getCart(){
        hasCart = false;
        RequestParams rp = new RequestParams();
        rp.add("customeremail", MainActivity.getEmail());
        rp.add("customerpassword", MainActivity.getPassword());
        HttpUtils.get("cart", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("cart", "Has cart");
                hasCart = true;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                hasCart = false;
            }
        });
    }

    /**
     * Add one selected product to the customer's cart
     * @param p a product
     */
    public void addProduct(Product p) {
        Log.d("EnterAddProduct", "Success");
        RequestParams rp = new RequestParams();
        rp.add("useremail", MainActivity.getEmail());
        rp.add("userpassword", MainActivity.getPassword());
        rp.add("productname", p.getProductName());
        rp.add("quantity", "1");

        HttpUtils.post("cart/item", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("addproduct", "Success");
                Toast.makeText(getActivity(), "You added " + p.getProductName() + " To the cart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse.toString().contains("\"status\":500")) { // If error 500 is return, this means no more target product available
                    Toast.makeText(getActivity(), "" + p.getProductName() + " is out of stock", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Network problem with the server", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}