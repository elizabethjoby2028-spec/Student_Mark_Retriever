package com.example.database;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etName, etMarks;
    Button btnAdd, btnDisplay;
    TextView tvNames;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etMarks = findViewById(R.id.etMarks);
        btnAdd = findViewById(R.id.btnAdd);
        btnDisplay = findViewById(R.id.btnDisplay);
        tvNames = findViewById(R.id.tvNames);

        dbHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String marks = etMarks.getText().toString().trim();

            if (name.isEmpty()) {
                etName.setError("Enter student name");
                return;
            }

            if (marks.isEmpty()) {
                etMarks.setError("Enter marks");
                return;
            }

            if (dbHelper.addStudent(name, marks)) {

                Toast.makeText(this, "Student added",
                        Toast.LENGTH_SHORT).show();

                etName.setText("");
                etMarks.setText("");

            } else {
                Toast.makeText(this, "Failed to add student",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnDisplay.setOnClickListener(v -> {

            String students = dbHelper.getAllStudents();
            tvNames.setText(students);

        });
    }

    @Override
    protected void onDestroy() {
        if (dbHelper != null) {
            dbHelper.close();
        }

        super.onDestroy();
    }
}