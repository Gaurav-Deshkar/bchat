package com.example.android.bluetoothchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.common.activities.SampleActivityBase;

public class MainActivity extends SampleActivityBase {

    public static final String TAG = "MainActivity";

    // Whether the Log Fragment is currently shown
    EditText userNameEditText;
    Button continueButton;
    View fragmentView;
    View userView;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        fragmentView = findViewById(R.id.sample_content_fragment);
        userView = findViewById(R.id.user_view);

        if (hasUser()) {
            showChatFragment();
            return;
        }
        userNameEditText = (EditText) findViewById(R.id.user_name);
        continueButton = (Button) findViewById(R.id.btn_continue);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
                showChatFragment();
            }
        });
    }

    private void saveUser() {
        String userName = userNameEditText.getText().toString();
        if (userName != null || !userName.trim().isEmpty()) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.saved_user_name), userName);
            editor.commit();
        }

    }


    private void showChatFragment() {

        if (hasUser()) {
            userView.setVisibility(View.GONE);
            fragmentView.setVisibility(View.VISIBLE);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothChatFragment fragment = new BluetoothChatFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }

    @NonNull
    private boolean hasUser() {
        String userName = sharedPref.getString(getString(R.string.saved_user_name), "");

        return !userName.isEmpty();
    }

}
