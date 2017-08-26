package com.example.lsireneva.todoapp.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lsireneva.todoapp.R;
import com.example.lsireneva.todoapp.adapters.CustomTodoTaskAdapter;
import com.example.lsireneva.todoapp.fragments.AddItemFragment;
import com.example.lsireneva.todoapp.fragments.EditItemFragment;
import com.example.lsireneva.todoapp.models.TodoDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Liubov Sireneva
 */

public class MainActivity extends AppCompatActivity{

    ListView lvItems;
    ArrayAdapter<ToDoTask> aToDoAdapter;
    EditText todoTaskName, todoTaskNotes;
    Spinner taskPriorityLevel, taskStatus;
    DatePicker taskDueDate;
    Toolbar todoToolbar;
    TextView toolbarTitle, editedTaskName, editedDueDate, editedTaskNotes, editedPriorityLevel, editedTaskStatus;

    AddItemFragment addToDoFragment;
    EditItemFragment editToDoFragment;
    TodoDatabase todoDB;

    ArrayList<ToDoTask> alltasks;
    ToDoTask selectedTask;
    boolean ADD_NEW_TASK=false;



    private MenuItem addToDoTask, saveToDoTask, closeToDoTask, editToDoTask, deleteToDoTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        todoToolbar = (Toolbar) findViewById(R.id.todoapp_toolbar);
        setSupportActionBar(todoToolbar);
        getSupportActionBar().setCustomView(R.layout.toolbar_main);

        // Display icon in the toolbar
        getSupportActionBar().setLogo(R.drawable.todoapp_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Get access to the custom title view
        toolbarTitle = (TextView) todoToolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("To Do list");

        addToDoFragment = new AddItemFragment();
        editToDoFragment = new EditItemFragment();
        selectedTask = new ToDoTask();

        todoDB = new TodoDatabase(this);
        todoDB.connectTodoDB();

        populateTasksList();

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TODOAPP","Item Clicked");
                toolBarDecorate("Edit task", false, false,false,true,true);
                getSupportFragmentManager().beginTransaction().add(R.id.container, editToDoFragment).addToBackStack(null).commit();
                getSupportFragmentManager().executePendingTransactions();
                lvItems.setVisibility(View.INVISIBLE);
                editedTaskName = editToDoFragment.getView().findViewById(R.id.editedTaskName);
                editedDueDate = editToDoFragment.getView().findViewById(R.id.editedDueDateTask);
                editedTaskNotes = editToDoFragment.getView().findViewById(R.id.editedTaskNotes);
                editedPriorityLevel = editToDoFragment.getView().findViewById(R.id.editedTaskPriority);
                editedTaskStatus = editToDoFragment.getView().findViewById(R.id.editedTaskStatus);

                selectedTask.taskID=(alltasks.get(i)).taskID;
                selectedTask.taskDueDate = (alltasks.get(i)).taskDueDate;
                selectedTask.taskName=(alltasks.get(i)).taskName;
                selectedTask.taskNotes=(alltasks.get(i)).taskNotes;
                selectedTask.taskPriority=(alltasks.get(i)).taskPriority;
                selectedTask.taskStatus=(alltasks.get(i)).taskStatus;

                final Calendar c = Calendar.getInstance();
                c.setTimeInMillis((alltasks.get(i)).taskDueDate);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd yyyy");
                Date d = new Date(c.get(c.YEAR)-1900, c.get(c.MONTH), c.get(c.DAY_OF_MONTH));
                String strDate = dateFormatter.format(d);


                editedTaskName.setText(selectedTask.taskName);
                editedDueDate.setText(strDate);
                editedTaskNotes.setText(selectedTask.taskNotes);
                editedPriorityLevel.setText(selectedTask.taskPriority);
                editedTaskStatus.setText(selectedTask.taskStatus);

            }
        });
    }

    private void populateTasksList() {
        Log.d("TODOAPP","populateTasksList()");
        alltasks = todoDB.getAllTasks();
        CustomTodoTaskAdapter adapter = new CustomTodoTaskAdapter(this, alltasks);
        lvItems.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        addToDoTask = menu.findItem(R.id.add_todo_task);
        saveToDoTask = menu.findItem(R.id.save_todo_task);
        closeToDoTask = menu.findItem(R.id.close_todo_task);
        editToDoTask = menu.findItem(R.id.edit_todo_task);
        deleteToDoTask = menu.findItem(R.id.delete_todo_task);

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_todo_task:
                Log.d("TODOAPP","Add task clicked");
                toolBarDecorate("Add new task",false,true,true,false,false);

                getSupportFragmentManager().beginTransaction().add(R.id.container, addToDoFragment).addToBackStack(null).commit();
                getSupportFragmentManager().executePendingTransactions();
                lvItems.setVisibility(View.INVISIBLE);
                ADD_NEW_TASK=true;
                addToDoFragment.setupFragmentView ();
                return true;

            case R.id.save_todo_task:
                Log.d("TODOAPP","Save task Clicked");
                todoTaskName = addToDoFragment.getView().findViewById(R.id.taskNameInput);
                taskDueDate = addToDoFragment.getView().findViewById(R.id.dueDatePicker);
                todoTaskNotes = addToDoFragment.getView().findViewById(R.id.taskNotesInput);
                taskPriorityLevel = addToDoFragment.getView().findViewById (R.id.taskPriorityLevelSpinner);
                taskStatus = addToDoFragment.getView().findViewById (R.id.taskStatusSpinner);


                if (todoTaskName.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please write something to do",
                            Toast.LENGTH_LONG).show();}
                else{
                    selectedTask.taskName = todoTaskName.getText().toString();
                    selectedTask.taskDueDate=taskDueDate.getCalendarView().getDate();
                    selectedTask.taskNotes=todoTaskNotes.getText().toString();
                    selectedTask.taskPriority= taskPriorityLevel.getSelectedItem().toString();
                    selectedTask.taskStatus= taskStatus.getSelectedItem().toString();

                    if (ADD_NEW_TASK==true) {
                        Log.d("TODOAPP","Task +++WRITE+++");
                        todoDB.writeTasks(selectedTask);
                    } else {
                        Log.d("TODOAPP","Task +++UPDATE+++");
                        todoDB.updateTask(selectedTask);
                    }
                    populateTasksList();
                    toolBarDecorate("To Do list", true, false,false,false,false);
                    getSupportFragmentManager().beginTransaction().remove(addToDoFragment).commit();
                    getSupportFragmentManager().executePendingTransactions();
                    lvItems.setVisibility(View.VISIBLE);
                }
                return true;
            case R.id.close_todo_task:
                todoTaskName = addToDoFragment.getView().findViewById(R.id.taskNameInput);
                toolBarDecorate("To Do list", true, false,false,false,false);
                getSupportFragmentManager().beginTransaction().remove(editToDoFragment).commit();
                getSupportFragmentManager().beginTransaction().remove(addToDoFragment).commit();
                getSupportFragmentManager().executePendingTransactions();
                lvItems.setVisibility(View.VISIBLE);
                return true;

            case R.id.edit_todo_task:
                Log.d("TODOAPP","Edit Clicked");
                ADD_NEW_TASK=false;
                toolBarDecorate("Change task",false,true,true,false,false);
                getSupportFragmentManager().beginTransaction().remove(editToDoFragment).commit();
                getSupportFragmentManager().beginTransaction().add(R.id.container, addToDoFragment).addToBackStack(null).commit();
                getSupportFragmentManager().executePendingTransactions();
                lvItems.setVisibility(View.INVISIBLE);
                todoTaskName = addToDoFragment.getView().findViewById(R.id.taskNameInput);
                taskDueDate = addToDoFragment.getView().findViewById(R.id.dueDatePicker);
                todoTaskNotes = addToDoFragment.getView().findViewById(R.id.taskNotesInput);
                taskPriorityLevel = addToDoFragment.getView().findViewById (R.id.taskPriorityLevelSpinner);
                taskStatus = addToDoFragment.getView().findViewById (R.id.taskStatusSpinner);


                final Calendar c = Calendar.getInstance();
                c.setTimeInMillis(selectedTask.taskDueDate);

                taskDueDate.init(c.get(c.YEAR), c.get(c.MONTH), c.get(c.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        int myear=month;
                        int mmonth=dayOfMonth;
                        int mday=year+1900;
                    }
                });

                String priority = selectedTask.taskPriority;
                String status =selectedTask.taskStatus;
                ArrayAdapter adapterPriority = (ArrayAdapter) taskPriorityLevel.getAdapter();
                ArrayAdapter adapterStatus = (ArrayAdapter) taskStatus.getAdapter();


                int priority_position = adapterPriority.getPosition(priority);
                int status_position = adapterStatus.getPosition(status);

                taskPriorityLevel.setSelection(priority_position);
                taskStatus.setSelection(status_position);
                todoTaskName.setText(selectedTask.taskName);
                todoTaskNotes.setText(selectedTask.taskNotes);
                return true;



            case R.id.delete_todo_task:
                Log.d("TODOAPP","Delete Clicked");
                showAlertDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void showAlertDialog() {

        FragmentManager fm = getSupportFragmentManager();
        MyAlertDialogFragment alertDialog = MyAlertDialogFragment.newInstance("Delete task");
        alertDialog.show(fm, "fragment_alert");

    }

    public void yesClicked() {
        todoDB.deleteTask(selectedTask);
        aToDoAdapter = new CustomTodoTaskAdapter(this, todoDB.getAllTasks());
        lvItems.setAdapter(aToDoAdapter);
        toolBarDecorate("To Do list",true,false,false,false,false);
        getSupportFragmentManager().beginTransaction().remove(editToDoFragment).commit();
        getSupportFragmentManager().executePendingTransactions();
        lvItems.setVisibility(View.VISIBLE);
    }

    public void toolBarDecorate (String title, boolean addTodo, boolean saveTodo, boolean closeTodo, boolean editTodo, boolean deleteTodo){
        toolbarTitle.setText(title);
        addToDoTask.setVisible(addTodo);
        saveToDoTask.setVisible(saveTodo);
        closeToDoTask.setVisible(closeTodo);
        editToDoTask.setVisible(editTodo);
        deleteToDoTask.setVisible(deleteTodo);
    }

    @Override
    public void onBackPressed() {
        Log.d("TODOAPP","onBackPressed()");
        this.populateTasksList();
        lvItems.setVisibility(View.VISIBLE);
        toolBarDecorate("To do list",true,false,false,false,false);
        getSupportFragmentManager().beginTransaction().remove(editToDoFragment).commit();
        getSupportFragmentManager().beginTransaction().remove(addToDoFragment).commit();
        getSupportFragmentManager().executePendingTransactions();
        lvItems.setVisibility(View.VISIBLE);

    }


    public static class ToDoTask {
        public int  taskID;
        public String taskName;
        public long taskDueDate;
        public String taskNotes;
        public String taskPriority;
        public String taskStatus;

    }

    public static class MyAlertDialogFragment extends DialogFragment {

        public MyAlertDialogFragment() {
            // empty constructor required for DialogFragment
        }

        public static MyAlertDialogFragment newInstance(String title) {

            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putString("title", title);
            frag.setArguments(args);
            return frag;

        }

        @Override

        public Dialog onCreateDialog(Bundle savedInstanceState) {

            String title = getArguments().getString("title");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setIcon(R.drawable.fragment_alert_icon);
            alertDialogBuilder.setMessage("Are you sure you want to delete task?");
            alertDialogBuilder.setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ((MainActivity) getActivity()).yesClicked();
                }
            });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                }

            });

            return alertDialogBuilder.create();

        }

    }

}
