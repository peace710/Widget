package com.app.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.app.R;
import com.app.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private Switch switch1;
    private Switch switch2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switch1 = (Switch)findViewById(R.id.switch1);
        switch2 = (Switch)findViewById(R.id.switch2);
        switch2.setSwitch(true);
        switch2.setSwitchOnColor(Color.parseColor("#48FF4081"));

        switch1.setOnSwitchListener(new Switch.OnSwitchListener() {
            @Override
            public void onSwitch(boolean isSwitch) {
                Toast.makeText(MainActivity.this,"==>1:" + isSwitch,Toast.LENGTH_LONG).show();
            }
        });

        switch2.setOnSwitchListener(new Switch.OnSwitchListener() {
            @Override
            public void onSwitch(boolean isSwitch) {
                Toast.makeText(MainActivity.this,"==>2 :" + isSwitch,Toast.LENGTH_LONG).show();
            }
        });
    }
}
