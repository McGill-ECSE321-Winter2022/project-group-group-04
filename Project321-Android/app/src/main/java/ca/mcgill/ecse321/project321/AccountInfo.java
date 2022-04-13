package ca.mcgill.ecse321.project321;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import ca.mcgill.ecse321.project321.databinding.AccountInfoBinding;
import cz.msebera.android.httpclient.Header;

import org.json.JSONException;
import org.json.JSONObject;

import ca.mcgill.ecse321.project321.databinding.StoreInfoBinding;

public class AccountInfo extends Fragment {

private AccountInfoBinding binding;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AccountInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    /*
    * Method that initializes the view that we take on for this page. It calls the user that is currently
    * logged in, to retrieve the name, email, phone and address. Then it displays these details on the page through
    * the xml file. This method also handles trying to change an address without a complete address.
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        TextView name = (TextView) view.findViewById(R.id.currentusername);
        TextView email = (TextView) view.findViewById(R.id.useremail);
        TextView number = (TextView) view.findViewById(R.id.userphone);
        TextView unitAddress = (TextView) view.findViewById(R.id.userunit);
        TextView townAddress = (TextView) view.findViewById(R.id.usertown);
        TextView streetAddress = (TextView) view.findViewById(R.id.userstreet);
        TextView postCodeAddress = (TextView) view.findViewById(R.id.userpostcode);
        TextView accounterror = (TextView) view.findViewById(R.id.accountinfoerror);
        RequestParams rp = new RequestParams();
        rp.add("email", MainActivity.getEmail());
        HttpUtils.get("customer", rp , new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                try{
                    name.setText(response.getString("name"));
                    email.setText(MainActivity.getEmail());
                    number.setText(response.getString("phone"));
                    unitAddress.setText(response.getJSONObject("address").getString("unit"));
                    townAddress.setText(response.getJSONObject("address").getString("town"));
                    streetAddress.setText(response.getJSONObject("address").getString("street"));
                    postCodeAddress.setText(response.getJSONObject("address").getString("postalCode"));

                }catch(JSONException e){
                    e.printStackTrace();
                }
                accounterror.setVisibility(View.GONE);

            }
            @Override
            public void  onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                if(errorResponse != null){
                    accounterror.setText(errorResponse.toString());
                    accounterror.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUnit = binding.unitinputupdate.getText().toString();
                String newStreet =  binding.streetinputupdate.getText().toString();
                String newTown = binding.towninputupdate.getText().toString();
                String newPostCode = binding.postalcodeinputupdate.getText().toString();
                if(newUnit.length() == 0 || newStreet.length() == 0 || newTown.length() == 0 || newPostCode.length() == 0){
                    accounterror.setText("All fields must be filled in order to update Address");
                    accounterror.setVisibility(View.VISIBLE);
                }else{
                    updateAccountInformation(newUnit, newStreet,
                            newTown, newPostCode );
                }
            }
        });
    }

    /*
    *This is the method that is called from onViewCreated(). It calls the method in our controller
    * setCustomerAddress. It allows the user to update their address only, as other variables/fields
    * could be prone to security issues. This method then updates the view in order to confirm that the
    * changes to the address have been made. It also accesses the controller method by calling the mainactivity
    * class to get the email and password of said user. 
    *
    * @param newUnit the new unit string the customer has inputed
    * @param newStreet the new street string the customer has inputed
    * @param newTown the new town string the customer has inputed
    * @param newPostCode the new postcode string the customer has inputed
     */

    private void updateAccountInformation(String newUnit, String newStreet, String newTown, String newPostCode) {
     RequestParams rp = new RequestParams();
     rp.add("town", newTown);
     rp.add("street", newStreet);
     rp.add("postalcode", newPostCode);
     rp.add("unit", newUnit);
     rp.add("customeremail", MainActivity.getEmail());
     rp.add("customerpassword", MainActivity.getPassword());
        HttpUtils.post("customers/address", rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                binding.userunit.setText(newUnit);
                binding.userstreet.setText(newStreet);
                binding.usertown.setText(newTown);
                binding.userpostcode.setText(newPostCode);
                binding.accountinfoerror.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                binding.accountinfoerror.setText("Failed to update address due to server errors");
                binding.accountinfoerror.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}





