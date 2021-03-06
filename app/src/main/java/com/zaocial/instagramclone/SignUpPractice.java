package com.zaocial.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUpPractice extends AppCompatActivity implements View.OnClickListener {
    private Button saveData, getAllKickBoxer, btnNextActivity;
    private EditText name, punchPower, punchSpeed, kickPower, kickSpeed;
    private TextView txtGetData;
    private String allKickBoxer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_practice);

        saveData = findViewById(R.id.btnSave);
        name = findViewById(R.id.edt_Name);
        punchPower = findViewById(R.id.edt_punch_power);
        punchSpeed = findViewById(R.id.edt_punch_speed);
        kickPower = findViewById(R.id.edt_kick_power);
        kickSpeed = findViewById(R.id.edt_kick_Speed);
        txtGetData = findViewById(R.id.txtGetData);
        getAllKickBoxer = findViewById(R.id.btnAllData);
        btnNextActivity = findViewById(R.id.btnNextActivity);

        saveData.setOnClickListener(SignUpPractice.this);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("stKwEIKOEM", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object != null && e == null) {
                            txtGetData.setText(object.get("name") + " - " + "Punch Power: " + object.get("punch_power"));
                        }
                    }
                });
            }
        });

        getAllKickBoxer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allKickBoxer = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
                //queryAll.whereGreaterThan("punch_power",500);
                queryAll.whereGreaterThanOrEqualTo("punch_speed", 1000);
                queryAll.setLimit(1);

                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            if (objects.size() > 0) {
                                for (ParseObject kickBoxer : objects) {
                                    allKickBoxer = allKickBoxer + kickBoxer.get("name") + "\n";
                                }

                                FancyToast.makeText(SignUpPractice.this,
                                        allKickBoxer, FancyToast.LENGTH_LONG,
                                        FancyToast.SUCCESS,true).show();
                            } else {
                                FancyToast.makeText(SignUpPractice.this, e.getMessage(), FancyToast.LENGTH_LONG,
                                        FancyToast.ERROR, true).show();
                            }
                        }
                    }
                });
            }
        });

        btnNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPractice.this, SignUpLogin.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        try {
            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", name.getText().toString());
            kickBoxer.put("punch_power", Integer.parseInt(punchPower.getText().toString()));
            kickBoxer.put("punch_speed", Integer.parseInt(punchSpeed.getText().toString()));
            kickBoxer.put("kick_power", Integer.parseInt(kickPower.getText().toString()));
            kickBoxer.put("kick_speed", Integer.parseInt(kickSpeed.getText().toString()));

            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(SignUpPractice.this,
                                kickBoxer.get("name") + "is save to the server", FancyToast.LENGTH_LONG,
                                FancyToast.SUCCESS, true).show();

                    } else {
                        FancyToast.makeText(SignUpPractice.this, e.getMessage(), FancyToast.LENGTH_LONG,
                                FancyToast.ERROR, true).show();
                    }
                }
            });
        } catch (Exception e) {
            FancyToast.makeText(SignUpPractice.this, e.getMessage(), FancyToast.LENGTH_LONG,
                    FancyToast.ERROR, true).show();
        }
    }
}
