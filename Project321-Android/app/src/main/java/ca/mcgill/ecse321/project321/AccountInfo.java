package ca.mcgill.ecse321.project321;


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

Button update_button;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AccountInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
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
        TextView passworderror = (TextView) view.findViewById(R.id.passworderrorupdate);
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
                passworderror.setVisibility(View.GONE);

            }
            @Override
            public void  onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
                if(errorResponse != null){
                    accounterror.setText(errorResponse.toString());
                    accounterror.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}





