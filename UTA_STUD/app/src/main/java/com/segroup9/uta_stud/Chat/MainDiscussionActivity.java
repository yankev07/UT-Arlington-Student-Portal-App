package com.segroup9.uta_stud.Chat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.segroup9.uta_stud.Adapters.CourseDiscussionAdapter;
import com.segroup9.uta_stud.Helper.ContactInformation;
import com.segroup9.uta_stud.LocationsActivity;
import com.segroup9.uta_stud.R;
import com.segroup9.uta_stud.database.DatabaseSQL;

import java.util.List;

/**
 * Created by kevinyanogo on 10/5/18.
 */

public class MainDiscussionActivity extends AppCompatActivity implements CourseDiscussionAdapter.ItemClickListener, View.OnClickListener {

    private CourseDiscussionAdapter adapter;
    public RecyclerView recyclerView;
    private AppCompatEditText searchbar;
    private AppCompatButton searchButton;
    private ContactInformation user;
    private List<ContactInformation> contactsList;
    DatabaseReference databaseReference;
    private DatabaseSQL myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_discussion_main);

        myDB = new DatabaseSQL(MainDiscussionActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.discussion_recyclerView);
        searchbar = (AppCompatEditText) findViewById(R.id.search_view);
        searchButton = (AppCompatButton) findViewById(R.id.button_search);
        searchButton.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        recyclerView.addItemDecoration(new DividerItemDecoration(MainDiscussionActivity.this, DividerItemDecoration.VERTICAL));

        contactsList = myDB.fetchContactsList();
        adapter = new CourseDiscussionAdapter(this, contactsList);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        adapter.setLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainDiscussionActivity.this, adapter.getItemEmail(i), Toast.LENGTH_LONG).show();
                showDeleteDialog(adapter.getItemEmail(i), i);
                return true;
            }
        });

    }

    ValueEventListener valueEventListener1 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    user = snapshot.getValue(ContactInformation.class);

                    showDialog();
                }
                adapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(MainDiscussionActivity.this, "User not Found...", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    @Override
    public void onItemClick(View view, int position){
        //Toast.makeText(MainDiscussionActivity.this, adapter.getItem(position).trim(), Toast.LENGTH_LONG).show();
        myDB.saveReceiverInfo(adapter.getItemUid(position).trim(), adapter.getItemEmail(position).trim(), "0000");
        Intent intent = new Intent(MainDiscussionActivity.this, ChatActivity.class);
        intent.putExtra("receiverName", adapter.getItemFirstName(position) + " " + adapter.getItemLastName(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position){
        //Toast.makeText(getActivity(), "Long Click", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResume(){
        super.onResume();
        //getListName();
        //Toast.makeText(ListActivity.this, listname, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.button_search:
                if(searchbar.getText().toString().isEmpty()){
                    Toast.makeText(MainDiscussionActivity.this, "Enter a NetID", Toast.LENGTH_LONG).show();
                }
                else{
                    Query query1 = FirebaseDatabase.getInstance().getReference("users").orderByChild("netID").equalTo(searchbar.getText().toString().trim());
                    query1.addListenerForSingleValueEvent(valueEventListener1);
                }
                break;
        }
    }


    public void showDialog(){
        final Dialog alertDialog = new Dialog(MainDiscussionActivity.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_search_result);
        alertDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        alertDialog.setTitle("My Custom Dialog");

        Button Cancel = (Button)alertDialog.findViewById(R.id.button_cancel);
        Button Save = (Button)alertDialog.findViewById(R.id.button_add);
        TextView name = (TextView)alertDialog.findViewById(R.id.user_name);
        TextView email = (TextView)alertDialog.findViewById(R.id.user_email);

        Cancel.setEnabled(true);
        Save.setEnabled(true);
        name.setText(user.firstName + " " + user.lastName);
        email.setText(user.email);

        Cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();
            }
        });

        Save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!user.email.equals(myDB.getEmail())){
                    myDB.saveNewContact(user.uid, user.firstName, user.lastName, user.email, user.course1, user.course2, user.course3);
                    contactsList.add(user);
                    adapter.notifyDataSetChanged();
                    alertDialog.dismiss();
                }

                else{
                    Toast.makeText(MainDiscussionActivity.this, "You can't add yourself...", Toast.LENGTH_LONG).show();
                }
            }
        });

        alertDialog.show();
    }

    public void showDeleteDialog(final String eventName, final int position){

        final Dialog alertDialog = new Dialog(this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_delete_contact);
        alertDialog.setTitle("My Custom Dialog");

        TextView eventTitle = (TextView)alertDialog.findViewById(R.id.courseName);
        Button Cancel = (Button)alertDialog.findViewById(R.id.button_cancel);
        Button Yes = (Button)alertDialog.findViewById(R.id.button_delete);

        eventTitle.setText(eventName);
        Cancel.setEnabled(true);
        Yes.setEnabled(true);

        Cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();
            }
        });

        Yes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myDB.deleteContact(eventName);
                contactsList.remove(position);
                adapter.notifyItemRemoved(position);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
