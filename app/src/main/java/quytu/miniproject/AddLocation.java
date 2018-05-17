package quytu.miniproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class AddLocation extends AppCompatActivity implements View.OnClickListener {

    EditText mLat, mLng;
    Button addBtn;
    String Lat, Lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        mLat = findViewById(R.id.input_lat);
        mLng = findViewById(R.id.input_lng);
        addBtn = findViewById(R.id.add_btn);

        addBtn.setOnClickListener(this);
        Lat = mLat.getText().toString();
        Lng = mLng.getText().toString();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_btn) {
            postRequest(Lat, Lng);
            Toast toast = Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void postRequest(final String Lat, final String Lng) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-34-218-246-230.us-west-2.compute.amazonaws.com:13097/add";
        StringRequest SendRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("Latitude", Lat);
                MyData.put("Longitude", Lng);
                return MyData;
            }
        };
        queue.add(SendRequest);
    }
}