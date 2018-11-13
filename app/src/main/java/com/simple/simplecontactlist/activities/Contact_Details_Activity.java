package com.simple.simplecontactlist.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.simple.simplecontactlist.R;

import static com.simple.simplecontactlist.activities.MainActivity.contactList;

public class Contact_Details_Activity extends AppCompatActivity {

    private Toolbar toolbar_contact;
    private int selectedFramePosition;

    private TextView user_name_txt,user_name_phno,user_name_address,user_name_website,user_name_company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__details_);

        selectedFramePosition = getIntent().getExtras().getInt("selectedFramePosition");

        toolbar_contact = (Toolbar) findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar_contact);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(""+contactList.get(selectedFramePosition).getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_contact.setTitleTextColor(Color.WHITE);
        toolbar_contact.getOverflowIcon()
                .setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        toolbar_contact.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Contact_Details_Activity.this.finish();
            }
        });

        user_name_txt=findViewById(R.id.user_name_txt);
        user_name_txt.setText(""+contactList.get(selectedFramePosition).getUsername());

        user_name_phno=findViewById(R.id.user_name_phno);
        user_name_phno.setText(""+contactList.get(selectedFramePosition).getPhone());


        user_name_address=findViewById(R.id.user_name_address);
        user_name_address.setText(""+(contactList.get(selectedFramePosition).getAddress().getSuite())+", "
                +(contactList.get(selectedFramePosition).getAddress().getStreet())+
        ", \n"+(contactList.get(selectedFramePosition).getAddress().getCity())+", "
                +(contactList.get(selectedFramePosition).getAddress().getZipcode()));

        user_name_website=findViewById(R.id.user_name_website);
        user_name_website.setText(""+contactList.get(selectedFramePosition).getWebsite());

        user_name_company=findViewById(R.id.user_name_company);
        user_name_company.setText(""+(contactList.get(selectedFramePosition).getCompany().getName())+", \n"
                +(contactList.get(selectedFramePosition).getCompany().getCatchPhrase())+
                ", \n"+(contactList.get(selectedFramePosition).getCompany().getBs()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {

            contactList.remove(selectedFramePosition);
           Contact_Details_Activity.this.finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
