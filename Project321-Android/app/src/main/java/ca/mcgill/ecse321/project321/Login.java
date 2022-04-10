package ca.mcgill.ecse321.project321;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;

import org.json.JSONException;
import org.json.JSONObject;

import ca.mcgill.ecse321.project321.databinding.LoginBinding;

public class Login extends Fragment {
    private LoginBinding binding;
    private static boolean accountExist = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = LoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.loginerror.setVisibility(View.GONE);
                String email = binding.emailinput.getText().toString();
                String password = binding.passwordinput.getText().toString();
                if(email.length() == 0 || password.length() == 0) {
                    binding.loginerror.setText("Email or password cannot be empty");
                    binding.loginerror.setVisibility(View.VISIBLE);
                } else {
                    userExists(email);
                    if(accountExist) {
                        login(email, password);
                        if(MainActivity.status) {
                            NavHostFragment.findNavController(Login.this)
                                .navigate(R.id.action_login_to_signup);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void userExists(String email) {
        RequestParams rp = new RequestParams();
        rp.add("email", email);
        HttpUtils.get("userexists", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getBoolean("exists")) {
                        Login.accountExist = true;
                    } else {
                        Login.accountExist = false;
                        binding.loginerror.setText("No account linked to this email, try again with another email");
                        binding.loginerror.setVisibility(View.VISIBLE);
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Login.accountExist = false;
                binding.loginerror.setText("Failed to reach the server to verify the email");
                binding.loginerror.setVisibility(View.VISIBLE);
            }
        });
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
                binding.loginerror.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                MainActivity.status = false;
                binding.loginerror.setText("The password provided is wrong, try again");
                binding.loginerror.setVisibility(View.VISIBLE);
            }
        });
    }
}
