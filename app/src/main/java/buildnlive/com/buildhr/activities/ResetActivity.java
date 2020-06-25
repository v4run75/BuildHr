package buildnlive.com.buildhr.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import java.util.HashMap;

import buildnlive.com.buildhr.App;
import buildnlive.com.buildhr.Interfaces;
import buildnlive.com.buildhr.R;
import buildnlive.com.buildhr.console;
import buildnlive.com.buildhr.utils.Config;
import buildnlive.com.buildhr.utils.UtilityofActivity;

public class ResetActivity extends AppCompatActivity {
    private App app;
    private EditText mobile;
    private Button reset;
    private ProgressBar progress;
    private TextView hider;
    private AlertDialog.Builder builder;
    private Context context;
    private UtilityofActivity utilityofActivity;
    private AppCompatActivity appCompatActivity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        utilityofActivity = new UtilityofActivity(this);
        mobile = findViewById(R.id.mobile);
        reset = findViewById(R.id.reset);
        app = (App) getApplication();
        context = this;
        builder = new AlertDialog.Builder(context);
        progress = findViewById(R.id.progress);
        hider = findViewById(R.id.hider);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("Are you sure?").setTitle("Forgot Password?");

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to Submit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String m = mobile.getText().toString();
                                if (m.length() > 3) {
                                    resetPassword(m);
                                } else {
                                    Toast.makeText(context, "Please enter valid details", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Forgot Password?");
                alert.show();


            }
        });
    }

    private void resetPassword(String m) {
        progress.setVisibility(View.VISIBLE);
        hider.setVisibility(View.VISIBLE);
        String resetPass = Config.FORGOT_PASSWORD;
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile_no", m);
        app.sendNetworkRequest(resetPass, Request.Method.POST, params, new Interfaces.NetworkInterfaceListener() {
            @Override
            public void onNetworkRequestStart() {
                utilityofActivity.showProgressDialog();
            }

            @Override
            public void onNetworkRequestError(String error) {
                utilityofActivity.dismissProgressDialog();
                Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkRequestComplete(String response) {
                console.log("Response:" + response);
                utilityofActivity.dismissProgressDialog();
                if (response.equals("0")) {
                    Toast.makeText(context, "User Does Not Exist", Toast.LENGTH_LONG).show();
                } else if (response.equals("1")) {
                    Toast.makeText(context, "New password has been sent to your email", Toast.LENGTH_LONG).show();
                    finish();
                }
                progress.setVisibility(View.GONE);
                hider.setVisibility(View.GONE);
            }
        });
    }
}
