package com.example.task3_1c;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity extends AppCompatActivity {

    private EditText inputName;
    private Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("NAME");
//            Log.d("MainActivity", "Name received: " + name);

            if (name != null) {
//                Log.d("MainActivity", "Name received: " + name);
                inputName = findViewById(R.id.inputName);
                inputName.setText(name);
            }
        }

        inputName = findViewById(R.id.inputName);
        startButton = findViewById(R.id.startButton);


        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get name
                String name = inputName.getText().toString().trim();

                //Log.d("MainActivity", "Name received: " + name);

                // intent name to QuizActivity
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra("NAME", name);
                startActivity(intent);

            }
        });
    }

}

