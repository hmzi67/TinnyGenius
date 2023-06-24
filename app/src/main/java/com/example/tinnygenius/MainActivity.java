package com.example.tinnygenius;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment();
        Toast.makeText(this, "Welcome to Tinny Genius", Toast.LENGTH_SHORT).show();
    }

    private void replaceFragment() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBarView);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,new HomeFragment());
        fragmentTransaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId())
                    {
                        case R.id.tg_home:
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, new HomeFragment());
                    }
                    return true;
                }
        );
    }

}