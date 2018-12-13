package com.example.kevin.lovecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public EditText firstField;
    public EditText secondField;
    public Button calculateButton;
    public TextView resultView;

    public static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        firstField = findViewById(R.id.firstField);
        secondField = findViewById(R.id.secondField);
        resultView = findViewById(R.id.resultField);

        calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall();
            }
        });
    }

    public void makeCall() {
        String firstName = firstField.getText().toString();
        String secondName = secondField.getText().toString();

        if (firstName.toLowerCase().equals("geoff") || secondName.toLowerCase().equals("geoff")) {
            String output = "You are: 100000% compatible";
            resultView.setText(output);

        } else {
            System.out.println("api call");
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    "https://love-calculator.p.mashape.com/getPercentage?fname=" + firstName + "&sname=" + secondName,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String result = response.getString("percentage");
                                String output = result + "% compatible";
                                resultView.setText(output);
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.toString());
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("X-Mashape-Key", "rgSuty2yxFmsh0VQoPdmnn71aPl6p1RehrkjsnKvDTaGjziv40");
                    params.put("Accept", "application/json");
                    return params;
                }
            };

            requestQueue.add(jsonObjectRequest);
        }
    }
}
