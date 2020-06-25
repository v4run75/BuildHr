package buildnlive.com.buildhr.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

//import buildnlive.com.buildlive.Agenda.Agenda;
import buildnlive.com.buildhr.App;
import buildnlive.com.buildhr.Interfaces;
import buildnlive.com.buildhr.R;
import buildnlive.com.buildhr.activities.AddItem;
import buildnlive.com.buildhr.activities.Approval;
import buildnlive.com.buildhr.console;
import buildnlive.com.buildhr.elements.Project;
import buildnlive.com.buildhr.elements.ProjectMember;
import buildnlive.com.buildhr.utils.Config;
import buildnlive.com.buildhr.utils.PrefernceFile;
import buildnlive.com.buildhr.utils.UtilityofActivity;
import io.realm.Realm;
import io.realm.RealmResults;

import static buildnlive.com.buildhr.utils.Config.PREF_NAME;

public class HomeFragment extends Fragment implements View.OnClickListener {
    //    private TextView title;
    private LinearLayout approval,manageInventory;
    private SharedPreferences pref;
    private Spinner projects;
    private static App app;
    private TextView badge;
    private Context context;
    private UtilityofActivity utilityofActivity;
    private AppCompatActivity appCompatActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.appCompatActivity = (AppCompatActivity) activity;
    }

    public static HomeFragment newInstance(App a) {
        app = a;
        return new HomeFragment();
    }

    private ArrayList<String> userProjects = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        utilityofActivity = new UtilityofActivity(appCompatActivity);

        pref = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        manageInventory = view.findViewById(R.id.manage_inventory);
        approval = view.findViewById(R.id.approve);

        badge = getActivity().findViewById(R.id.badge_notification);

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Project> projects = realm.where(Project.class).findAll();
        for (Project p : projects) {
            userProjects.add(p.getName());
        }
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, userProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.projects.setAdapter(adapter);
        this.projects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                App.projectId = projects.get(position).getId();
                App.belongsTo = App.projectId + App.userId;
                App.projectName = projects.get(position).getName();
                syncProject();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        manageInventory.setOnClickListener(this);
        approval.setOnClickListener(this);

        ArrayList<String> permissionList = PrefernceFile.Companion.getInstance(context).getArrayList("Perm");

        for (String permission : permissionList) {
            switch (permission) {
                case "Inventory": {
                    manageInventory.setVisibility(View.VISIBLE);
                    break;
                }
                case "Indent Item": {
                    manageInventory.setVisibility(View.VISIBLE);
                    break;
                }
                case "Approvals": {
                    approval.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }

    }

    private void syncProject() {
        String url = Config.REQ_SYNC_PROJECT;
        url = url.replace("[0]", App.userId).replace("[1]", App.projectId);
        app.sendNetworkRequest(url, 0, null, new Interfaces.NetworkInterfaceListener() {
            @Override
            public void onNetworkRequestStart() {

            }

            @Override
            public void onNetworkRequestError(String error) {

            }

            @Override
            public void onNetworkRequestComplete(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    final JSONArray array = obj.getJSONArray("project_members");
                    Realm realm = Realm.getDefaultInstance();
                    for (int i = 0; i < array.length(); i++) {
                        final ProjectMember member = new ProjectMember().parseFromJSON(array.getJSONObject(i));
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(member);
                            }
                        });
                    }
                    realm.close();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.manage_inventory:
                startActivity(new Intent(getContext(), AddItem.class));
                break;
            case R.id.approve:
                startActivity(new Intent(getContext(), Approval.class));
                break;
        }
    }

    private void sendRequest() throws JSONException {
        App app = ((App) getActivity().getApplication());
        HashMap<String, String> params = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("project_id", App.projectId).put("user_id", App.userId);
        params.put("notification_count", jsonObject.toString());
        console.log("Res:" + params);
        app.sendNetworkRequest(Config.GET_NOTIFICATIONS_COUNT, 1, params, new Interfaces.NetworkInterfaceListener() {
            @Override
            public void onNetworkRequestStart() {

            }

            @Override
            public void onNetworkRequestError(String error) {

            }

            @Override
            public void onNetworkRequestComplete(String response) {
                console.log(response);
                if (response.equals("0")) {
                    badge.setVisibility(View.GONE);
                } else {
                    badge.setVisibility(View.VISIBLE);
                    badge.setText(response);
                }
            }
        });
    }
}