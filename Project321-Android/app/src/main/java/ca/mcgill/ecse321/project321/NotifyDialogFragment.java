package ca.mcgill.ecse321.project321;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

/**
 * Class that allows to create notification pop ups with a specific message
 */
public class NotifyDialogFragment extends DialogFragment {

    private String notifyMsg = "";

    public NotifyDialogFragment(String notifyMsg) {
        this.notifyMsg = notifyMsg;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(this.notifyMsg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
