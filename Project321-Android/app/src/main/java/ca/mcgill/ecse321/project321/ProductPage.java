package ca.mcgill.ecse321.project321;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Product> products = new ArrayList<Product>();
    private boolean hasCart = false;
    ProductAdapter adapter;

    public ProductPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductPage.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductPage newInstance(String param1, String param2) {
        ProductPage fragment = new ProductPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getCart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        products = new ArrayList<Product>();
        View view = inflater.inflate(R.layout.product_page, container, false);

        Product emptyProduct = new Product("no products available", "N/A", "N/A", "N/A");
        products.add(emptyProduct);

        ListView listView = (ListView)view.findViewById(R.id.productList);
        adapter = new ProductAdapter(getActivity(), R.layout.custom_listview, products);
        listView.setAdapter(adapter);

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
                    addProduct(products.get(i));
                }
            }
        });
        getProducts();

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
                        products.add(new Product(response.getJSONObject(i).getString("productName") ,
                                response.getJSONObject(i).getString("isAvailableOnline"),
                                response.getJSONObject(i).getString("priceType"),
                                response.getJSONObject(i).getString("price") + "$"));

                    } catch (Exception e) {
                        Log.d("products", "222222222 ");

                    }
                }
                if (products.get(0).getProductName().equals("no products available")) {
                    products.remove(0);
                }
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
                if (errorResponse.toString().contains("\"status\":500")) {
                    Toast.makeText(getActivity(), "" + p.getProductName() + " is out of stock", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Network problem with the server", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}