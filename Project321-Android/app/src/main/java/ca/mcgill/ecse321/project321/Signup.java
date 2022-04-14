package ca.mcgill.ecse321.project321;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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
        binding.signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.signuperror.setVisibility(View.GONE);
                String email = binding.emailinputsignup.getText().toString();
                String name = binding.firstnameinputsignup.getText().toString() + binding.lastnameinputsignup.getText().toString();
                String phone = binding.phoneinputsignup.getText().toString();
                String password = binding.passwordinputsignup.getText().toString();
                String unit = binding.unitinputsignup.getText().toString();
                String street = binding.streetinputsignup.getText().toString();
                String town = binding.towninputsignup.getText().toString();
                String postalcode = binding.postalcodeinputsignup.getText().toString();
                if(email.length() == 0 || name.length() == 0 || phone.length() == 0 || password.length() <= 6 ||
                    unit.length() == 0 || street.length() == 0 || town.length() == 0 || postalcode.length() == 0) {
                    binding.signuperror.setText("One or more field is empty! Fill all fields before signing up");
                    binding.signuperror.setVisibility(View.VISIBLE);
                } else {
                    signupProcess(email, name, phone, password, unit, street, town, postalcode);
                }
            }
        });
        binding.passwordinputsignup.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(binding.passwordinputsignup.getText().toString().length() <= 6) {
                    binding.passworderror.setVisibility(View.VISIBLE);
                } else {
                    binding.passworderror.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Method that defines the process of signing in. The process starts by verifying that the email
     * provided is not already linked to an account. If the email is free, it will call the backend
     * controller to create a new account with the provided information. After the account is
     * created, the user is notified by a popup dialog and the app is rerouted to the login page
     * @param email Email of the customer trying to create an account
     * @param name Name of the customer trying to create an account
     * @param phone Phone number of the customer trying to create an account
     * @param password Password of the customer trying to create an account
     * @param unit Unit of the residence of the customer trying to create an account
     * @param street Street of the residence of the customer trying to create an account
     * @param town Town of the residence of the customer trying to create an account
     * @param postalcode Postal code of the residence of the customer trying to create an account
     */
    private void signupProcess(String email, String name, String phone, String password, String unit, String street, String town, String postalcode) {
        userExists(email, name, phone, password, unit, street, town, postalcode, new Signup.responseCallback() {
            // Define the callback method that will create the account if the email is free
            @Override
            public void successResponse(String email, String name, String phone, String password, String unit, String street, String town, String postalcode) {
                signup(email, name, phone, password, unit, street, town, postalcode, new Signup.responseCallback() {
                    // Define the callback method that will notify the user of successful sign up and reroute the app
                    @Override
                    public void successResponse(String email, String name, String phone, String password, String unit, String street, String town, String postalcode) {
                        NotifyDialogFragment popup = new NotifyDialogFragment("Sign up successful!");
                        popup.show(getParentFragmentManager(), "signedup");
                        NavHostFragment.findNavController(Signup.this)
                                .navigate(R.id.action_signup_to_login);
                    }
                });
            }
        });
    }

    /**
     * Method used to verify if an account with the specified email already exists and to proceed to creating a customer account
     * if the email is free
     * @param email Email of the customer trying to create an account
     * @param name Name of the customer trying to create an account
     * @param phone Phone number of the customer trying to create an account
     * @param password Password of the customer trying to create an account
     * @param unit Unit of the residence of the customer trying to create an account
     * @param street Street of the residence of the customer trying to create an account
     * @param town Town of the residence of the customer trying to create an account
     * @param postalcode Postal code of the residence of the customer trying to create an account
     * @param cb Callback method used to handle the creation of the account if the email is free to use
     */
    private void userExists(String email, String name, String phone, String password, String unit, String street, String town, String postalcode, Signup.responseCallback cb) {
        RequestParams rp = new RequestParams();
        rp.add("email", email);
        HttpUtils.get("userexists", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (!response.getBoolean("exists")) {
                        cb.successResponse(email, name, phone, password, unit, street, town, postalcode);
                    } else {
                        binding.signuperror.setText("An account is already linked to this email, try again with another email");
                        binding.signuperror.setVisibility(View.VISIBLE);
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                binding.signuperror.setText("Failed to reach the server to verify the email");
                binding.signuperror.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Method used to create a new account with the specified information.
     * @param email Email of the customer trying to create an account
     * @param name Name of the customer trying to create an account
     * @param phone Phone number of the customer trying to create an account
     * @param password Password of the customer trying to create an account
     * @param unit Unit of the residence of the customer trying to create an account
     * @param street Street of the residence of the customer trying to create an account
     * @param town Town of the residence of the customer trying to create an account
     * @param postalcode Postal code of the residence of the customer trying to create an account
     * @param cb Callback function that will allow to notice the user that the sign up was
     *           successful and will reroute the app to the login page
     */
    private void signup(String email, String name, String phone, String password, String unit, String street, String town, String postalcode, Signup.responseCallback cb) {
        RequestParams rp = new RequestParams();
        rp.add("email", email);
        rp.add("password", password);
        rp.add("name", name);
        rp.add("phone", phone);
        rp.add("town", town);
        rp.add("street", street);
        rp.add("postalcode", postalcode);
        rp.add("unit", unit);
        HttpUtils.post("customers", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                cb.successResponse(email, name, phone, password, unit, street, town, postalcode);
                binding.signuperror.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                binding.signuperror.setText("Failed to create an account due to server errors");
                binding.signuperror.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Interface that declares the callback method for successful response in the sign up process
     */
    public interface responseCallback {
        public void successResponse(String email, String name, String phone, String password, String unit, String street, String town, String postalcode);
    }
}
