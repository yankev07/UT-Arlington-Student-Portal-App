package com.segroup9.uta_stud.Chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.segroup9.uta_stud.R;

import org.w3c.dom.Text;

/**
 * Created by kevinyanogo on 10/10/18.
 */

public class ChatActivity extends AppCompatActivity {

    public TextView receiverName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        receiverName = (TextView) findViewById(R.id.chatName);
        receiverName.setText(getReceiverName());
        init();
    }

    private void init() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_content_chat, new ChatFragment());
        fragmentTransaction.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    public String getReceiverName(){
        Intent intent = getIntent();
        String receiverName = intent.getStringExtra("receiverName");
        return receiverName;
    }
}
