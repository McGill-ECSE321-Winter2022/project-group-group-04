package ca.mcgill.ecse321.project321;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.loopj.android.http.AsyncHttpResponseHandler;
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
                    loginProcess(email, password);
                }
            }
        });

        binding.signupredirectclickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Login.this)
                        .navigate(R.id.action_login_to_signup);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Method that defines the log in process. The process starts by verifying that the email
     * specified is linked with an active account before attempting to log in. If the log in is
     * successful, the user is notified and the app is rerouted to the product page
     * @param email Email of the customer trying to log in
     * @param password Password of the customer trying to log in
     */
    private void loginProcess(String email, String password) {
        userExists(email, password, new responseCallback() {
            @Override
            public void successResponse(String email, String password) {
                login(email, password, new responseCallback() {
                    @Override
                    public void successResponse(String email, String password) {
                        NotifyDialogFragment popup = new NotifyDialogFragment("Log in successful!");
                        popup.show(getParentFragmentManager(), "loggedin");
                        NavHostFragment.findNavController(Login.this)
                                .navigate(R.id.action_login_to_productpage); // Can change this to any page!
                    }
                });
            }
        });
    }

    /**
     * Method used to verify if the email specified is linked with an existing account before
     * attempting to log in with the specfied email and password.
     * @param email Email of the customer trying to log in
     * @param password Password of the customer trying to log in
     * @param cb Callback method that will attempt to log in if the email specified is indeed
     *           linked to an active account
     */
    private void userExists(String email, String password, responseCallback cb) {
        RequestParams rp = new RequestParams();
        rp.add("email", email);
        HttpUtils.get("userexists", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getBoolean("exists")) {
                        cb.successResponse(email, password);
                    } else {
                        binding.loginerror.setText("No account linked to this email, try again with another email");
                        binding.loginerror.setVisibility(View.VISIBLE);
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                binding.loginerror.setText("Failed to reach the server to verify the email");
                binding.loginerror.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Method used to log in the user by verifying that the user gives the proper password related
     * to the specified email. The user information is saved in the MainActivity class for later use
     * @param email Email of the customer trying to log in
     * @param password Password of the customer trying to log in
     * @param cb Callback method that will allow to notify the user on successful log in and reroute
     *           the app to the product page
     */
    private void login(String email, String password, responseCallback cb) {
        RequestParams rp = new RequestParams();
        rp.add("email", email);
        rp.add("password", password);
        HttpUtils.get("login", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    MainActivity.setEmail(response.getString("email"));
                    MainActivity.setPassword(response.getString("password"));
                    MainActivity.setType(response.getString("type"));
                    MainActivity.setStatus(true);
                    System.out.println("Email " + MainActivity.getEmail() + " of type " + MainActivity.getType() + " with password " + MainActivity.getPassword());
                    cb.successResponse(email, password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                binding.loginerror.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                MainActivity.setStatus(false);
                binding.loginerror.setText("The password provided is wrong, try again");
                binding.loginerror.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Interface that declares the callback method for successful response in the sign in process
     */
    public interface responseCallback {
        public void successResponse(String email, String password);
    }


}
