package com.nish.android.playground.activity;

import android.os.Bundle;
import com.nish.android.playground.R;
import androidx.appcompat.app.AppCompatActivity;
import dagger.android.AndroidInjection;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
    }
}
