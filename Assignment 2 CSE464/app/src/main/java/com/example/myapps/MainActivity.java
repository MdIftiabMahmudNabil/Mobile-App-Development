package com.example.myapps;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
TextView tv;
int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.tv_count);
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            //Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            //return insets;
            //
        //});
    }

    public void ToastButtonPressed(View view) {
        a=0;
        tv.setText(String.valueOf(a));
        Toast.makeText(this, "Hello Toast!", Toast.LENGTH_SHORT).show();
    }

    public void countButtonPressed(View view) {
        a++;
        tv.setText(String.valueOf(a));

    }
}