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
//                    userExists(email);
//                    System.out.println("Account exists? " + Login.accountExist);
//                    if(Login.accountExist) {
//                        login(email, password);
//                        System.out.println("Login success? " + MainActivity.status);
//                        if(MainActivity.status) {
//                            NavHostFragment.findNavController(Login.this)
//                                .navigate(R.id.action_login_to_signup);
//                        }
//                    }
                    loginProcess(email, password);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loginProcess(String email, String password) {
        userExists(email, password, new responseCallback() {
            @Override
            public void successResponse(String email, String password) {
                login(email, password, new responseCallback() {
                    @Override
                    public void successResponse(String email, String password) {
                        System.out.println(MainActivity.useremail + " of type " + MainActivity.usertype + " with password " + MainActivity.userpassword);
                        LoggedInDialogFragment popup = new LoggedInDialogFragment();
                        popup.show(getParentFragmentManager(), "loggedin");
                        NavHostFragment.findNavController(Login.this)
                                .navigate(R.id.action_login_to_storeinfo); // Can change this to any page!
                    }
                });
            }
        });
    }

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
                Login.accountExist = false;
                binding.loginerror.setText("Failed to reach the server to verify the email");
                binding.loginerror.setVisibility(View.VISIBLE);
            }
        });
    }

    private void login(String email, String password, responseCallback cb) {
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
                    cb.successResponse(email, password);
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

    public interface responseCallback {
        public void successResponse(String email, String password);
    }

    public static class LoggedInDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Login successful!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            return;
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
