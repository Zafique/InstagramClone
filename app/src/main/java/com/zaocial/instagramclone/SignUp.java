package com.zaocial.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import okhttp3.internal.Internal;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private Button saveData;
    private EditText name, punchPower, punchSpeed, kickPower, kickSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        saveData = findViewById(R.id.btnSave);
        name = findViewById(R.id.edt_Name);
        punchPower = findViewById(R.id.edt_punch_power);
        punchSpeed = findViewById(R.id.edt_punch_speed);
        kickPower = findViewById(R.id.edt_kick_power);
        kickSpeed = findViewById(R.id.edt_kick_Speed);

        saveData.setOnClickListener(SignUp.this);

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
                        FancyToast.makeText(SignUp.this,
                                kickBoxer.get("name") + "is save to the server", FancyToast.LENGTH_LONG,
                                FancyToast.SUCCESS, true).show();

                    } else {
                        FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG,
                                FancyToast.ERROR, true).show();
                    }
                }
            });
        } catch (Exception e) {
            FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG,
                    FancyToast.ERROR, true).show();
        }
    }
}
