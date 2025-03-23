package com.example.assignment2cse464;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    EditText taskInput;
    Button addBtn, updateBtn, refreshBtn;
    ListView listView;

    DatabaseHelper db;
    Map<Integer, String> taskMap;
    ArrayList<String> taskList;
    ArrayAdapter<String> adapter;

    int selectedTaskId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskInput = findViewById(R.id.taskInput);
        addBtn = findViewById(R.id.addBtn);
        updateBtn = findViewById(R.id.updateBtn);
        refreshBtn = findViewById(R.id.refreshBtn);
        listView = findViewById(R.id.listView);

        db = new DatabaseHelper(this);
        refreshList();

        addBtn.setOnClickListener(v -> {
            String task = taskInput.getText().toString().trim();
            if (!task.isEmpty()) {
                db.addTask(task);
                refreshList();
                taskInput.setText("");
            } else {
                taskInput.setError("Enter a task");
            }
        });

        updateBtn.setOnClickListener(v -> {
            String task = taskInput.getText().toString().trim();
            if (selectedTaskId != -1 && !task.isEmpty()) {
                db.updateTask(selectedTaskId, task);
                selectedTaskId = -1;
                taskInput.setText("");
                refreshList();
            } else {
                Toast.makeText(this, "Select a task to update", Toast.LENGTH_SHORT).show();
            }
        });

        refreshBtn.setOnClickListener(v -> refreshList());

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTask = taskList.get(position);
            selectedTaskId = getKeyFromValue(taskMap, selectedTask);
            taskInput.setText(selectedTask);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            String selectedTask = taskList.get(position);
            int idToDelete = getKeyFromValue(taskMap, selectedTask);
            db.deleteTask(idToDelete);
            Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
            refreshList();
            return true;
        });
    }

    private void refreshList() {
        taskMap = db.getAllTasks();
        taskList = new ArrayList<>(taskMap.values());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        listView.setAdapter(adapter);
    }

    private int getKeyFromValue(Map<Integer, String> map, String value) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return -1;
    }
}
