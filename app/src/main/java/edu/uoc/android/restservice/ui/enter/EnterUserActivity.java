package edu.uoc.android.restservice.ui.enter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.uoc.android.restservice.FollowersActivity;
import edu.uoc.android.restservice.R;

public class EnterUserActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUser;
    private Button btnFollowers;
    private final String mUserNameExtra = "user_name";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_user);
        initViews();
    }

    private void initViews() {
        etUser = findViewById(R.id.enter_user_edit_text);
        btnFollowers = findViewById(R.id.enter_user_button);
        btnFollowers.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnFollowers) {
            // Go to nex activity passing name introduced by user as extra data.
            Intent intent = new Intent(EnterUserActivity.this, FollowersActivity.class);
            intent.putExtra(mUserNameExtra, etUser.getText().toString());
            startActivity(intent);
        }
    }
}
