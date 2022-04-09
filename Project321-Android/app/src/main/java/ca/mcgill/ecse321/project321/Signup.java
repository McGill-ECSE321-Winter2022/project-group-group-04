package ca.mcgill.ecse321.project321;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

import org.json.JSONException;
import org.json.JSONObject;

import ca.mcgill.ecse321.project321.databinding.SignupBinding;

public class Signup extends Fragment{

    private SignupBinding binding;
    private static boolean accountExist = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = SignupBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        binding.signinbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void login(String email, String password) {
        RequestParams rp = new RequestParams();
        rp.add("email", email);
        rp.add("password", password);
        HttpUtils.get("login", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    MainActivity.useremail = response.getString("email");
                    MainActivity.userpassword = response.getString("password");
                    MainActivity.usertype = response.getString("type");
                    MainActivity.status = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                binding.signuperror.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                MainActivity.status = false;
                binding.signuperror.setText("The password provided is wrong, try again");
                binding.signuperror.setVisibility(View.VISIBLE);
            }
        });
    }
}
