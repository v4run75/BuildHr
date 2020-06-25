package buildnlive.com.buildhr.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import buildnlive.com.buildhr.App;
import buildnlive.com.buildhr.Interfaces;
import buildnlive.com.buildhr.R;
import buildnlive.com.buildhr.adapters.NotificationsAdapter;
import buildnlive.com.buildhr.console;
import buildnlive.com.buildhr.elements.Notification;
import buildnlive.com.buildhr.utils.Config;
import buildnlive.com.buildhr.utils.UtilityofActivity;
import io.realm.Realm;

public class NotificationActivity extends AppCompatActivity {
    private App app;
    private Realm realm;
    private ProgressBar progressBar;
    private ArrayList<Notification> notificationList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageButton back;
    NotificationsAdapter adapter;
    AlertDialog.Builder builder;
    private Context context;
    private UtilityofActivity utilityofActivity;
    private AppCompatActivity appCompatActivity=this;


    public NotificationsAdapter.OnItemClickListener listener = new NotificationsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(final Notification notification, final int pos, final View view) {

            if (view.getId() == R.id.image) {
                Intent intent = new Intent(NotificationActivity.this, BillImageView.class);
                Bundle bundle = new Bundle();
                bundle.putString("Link", notification.getImage());
                intent.putExtras(bundle);
                startActivity(intent);
            } else if (view.getId() == R.id.review) {

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_dialog_review_notification, null);
                androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.PinDialog);
                final androidx.appcompat.app.AlertDialog alertDialog = dialogBuilder.setCancelable(false).setView(dialogView).create();
                alertDialog.show();
                final EditText alert_message = dialogView.findViewById(R.id.alert_message);
                final Button close = dialogView.findViewById(R.id.negative);
                final Button approve = dialogView.findViewById(R.id.positive);


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                approve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            sendRequest(notification.getId(), alert_message.getText().toString(), "Revision");
                            notificationList.remove(pos);
                            adapter.notifyItemRemoved(pos);
                            adapter.notifyItemRangeChanged(pos, notificationList.size());
                            alertDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                builder.setMessage("Do you want to Submit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                switch (view.getId()) {
                                    case R.id.receive:
                                        try {
                                            sendRequest(notification.getId(), "", "Received");
                                            notificationList.remove(pos);
                                            adapter.notifyItemRemoved(pos);
                                            adapter.notifyItemRangeChanged(pos, notificationList.size());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case R.id.not_receive:
                                        try {
                                            sendRequest(notification.getId(), "", "Not Received");
                                            notificationList.remove(pos);
                                            adapter.notifyItemRemoved(pos);
                                            adapter.notifyItemRangeChanged(pos, notificationList.size());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case R.id.approve:
                                        try {
                                            sendRequest(notification.getId(), "", "Approved");
                                            notificationList.remove(pos);
                                            adapter.notifyItemRemoved(pos);
                                            adapter.notifyItemRangeChanged(pos, notificationList.size());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case R.id.reject:
                                        try {
                                            sendRequest(notification.getId(), "", "Rejected");
                                            notificationList.remove(pos);
                                            adapter.notifyItemRemoved(pos);
                                            adapter.notifyItemRangeChanged(pos, notificationList.size());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case R.id.issued:
                                        try {
                                            sendRequest(notification.getId(), "", "Issued");
                                            notificationList.remove(pos);
                                            adapter.notifyItemRemoved(pos);
                                            adapter.notifyItemRangeChanged(pos, notificationList.size());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case R.id.read_notification:
                                        try {
                                            sendRequest(notification.getId(), "", "Read");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        notificationList.remove(pos);
                                        adapter.notifyItemRemoved(pos);
                                        adapter.notifyItemRangeChanged(pos, notificationList.size());
                                        break;
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
                alert.setTitle("Submit");
                alert.show();

            }


        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        context = this;
        utilityofActivity=new UtilityofActivity(appCompatActivity);
        utilityofActivity.configureToolbar(appCompatActivity);

        TextView toolbar_title=findViewById(R.id.toolbar_title);
        TextView toolbar_subtitle=findViewById(R.id.toolbar_subtitle);
        toolbar_subtitle.setText(App.projectName);
        toolbar_title.setText("Notifications");

        app = (App) getApplication();
        progressBar = findViewById(R.id.progress);
        realm = Realm.getDefaultInstance();
        refresh();
        builder = new AlertDialog.Builder(this);
        recyclerView = (RecyclerView) findViewById(R.id.notifications);


//        final String adapter=new ArrayAdapter<String>(this,mobileArray);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void refresh() {
        String requestUrl = Config.SEND_NOTIFICATIONS;
        notificationList.clear();
        requestUrl = requestUrl.replace("[0]", App.userId);
        requestUrl = requestUrl.replace("[1]", App.projectId);
        console.log(requestUrl);
        app.sendNetworkRequest(requestUrl, Request.Method.GET, null, new Interfaces.NetworkInterfaceListener() {
            @Override
            public void onNetworkRequestStart() {
                progressBar.setVisibility(View.VISIBLE);
                utilityofActivity.showProgressDialog();
            }

            @Override
            public void onNetworkRequestError(String error) {
                progressBar.setVisibility(View.GONE);
                utilityofActivity.dismissProgressDialog();
                console.error("Network request failed with error :" + error);
                Toast.makeText(getApplicationContext(), "Check Network, Something went wrong", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNetworkRequestComplete(String response) {
                console.log(response);
                console.log(response);
                utilityofActivity.dismissProgressDialog();
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        final JSONObject obj = array.getJSONObject(i);
                        notificationList.add(new Notification().parseFromJSON(obj));
                    }
                    adapter = new NotificationsAdapter(getApplicationContext(), notificationList, listener);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.hasFixedSize();
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {

                }
            }
        });
    }


    private void sendRequest(String id, String comments, String answer) throws JSONException {
        App app = ((App) getApplication());
        HashMap<String, String> params = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        if (answer.equals("Revision")) {
            jsonObject.put("id", id).put("response", answer).put("user_id", App.userId).put("response_comments", comments);
        } else {
            jsonObject.put("id", id).put("response", answer).put("user_id", App.userId).put("response_comments", "");
        }
        params.put("notification", jsonObject.toString());
        console.log("Res:" + params);
        app.sendNetworkRequest(Config.GET_NOTIFICATIONS, 1, params, new Interfaces.NetworkInterfaceListener() {
            @Override
            public void onNetworkRequestStart() {
                utilityofActivity.showProgressDialog();

            }

            @Override
            public void onNetworkRequestError(String error) {
                utilityofActivity.dismissProgressDialog();
            }

            @Override
            public void onNetworkRequestComplete(String response) {
                console.log(response);
                utilityofActivity.dismissProgressDialog();
                if (response.equals("1")) {
                    Toast.makeText(getApplicationContext(), "Request Generated", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}
