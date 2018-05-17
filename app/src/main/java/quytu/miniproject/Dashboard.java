package quytu.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    Button btnShowMap, btnAddLocation, btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnShowMap = findViewById(R.id.btn_show_maps);
        btnAddLocation = findViewById(R.id.btn_add_location);
        btnSignOut = findViewById(R.id.btn_log_out);


        btnShowMap.setOnClickListener(this);
        btnAddLocation.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_show_maps) {
            startActivity(new Intent(Dashboard.this, MapsActivity.class));
            finish();
        }
        else if(v.getId() == R.id.btn_add_location) {
            startActivity(new Intent(Dashboard.this, AddLocation.class));
            finish();
        }
        else if(v.getId() == R.id.btn_log_out) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Dashboard.this, MainActivity.class));
            finish();
        }
    }
}
