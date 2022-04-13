package ca.mcgill.ecse321.project321;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ca.mcgill.ecse321.project321.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String error = null;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private static String useremail = "";
    private static String userpassword = "";
    private static String usertype = "";
    private static boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main); // replace "nav_host_fragment" with the id of your navHostFragment in activity layout
        //noinspection SimplifiableIfStatement
        if (id == R.id.store_info) {
            navController.navigate(R.id.StoreInfo);
        } else if(id == R.id.order_history) {
            navController.navigate(R.id.OrderHistory);
        } else if(id == R.id.login) {
            navController.navigate(R.id.Login);
        }else if (id == R.id.cart_page) {
            navController.navigate(R.id.CartPage);
        } else if (id == R.id.product_page){
            navController.navigate(R.id.ProductPage);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    private void testButton(){
    error = "";
    }

    public static void setEmail(String email) {
        useremail = email;
    }
    public static void setPassword(String password) {
        userpassword = password;
    }

    public static void setType(String type) {
        usertype = type;
    }

    public static void setStatus(boolean aStatus) {
        status = aStatus;
    }

    public static String getEmail() {
        return useremail;
    }
    public static String getPassword() {
        return userpassword;
    }

    public static String getType() {
        return usertype;
    }

    public static boolean getStatus() {
        return status;
    }
}