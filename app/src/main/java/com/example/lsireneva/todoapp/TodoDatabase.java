package com.example.lsireneva.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Liubov Sireneva
 */
public class TodoDatabase {

    private SQLiteDatabase sqlTodoDB;
    private TodoDatabaseHelper dbHelper;
    private Context context;

    private static final String TABLE_TODOTASK = "todoTaskTable";
    private static final String DATABASE_NAME = "todoDatabase";
    private static final int DATABASE_VERSION = 4;


    // TodoTaskTable Columns
    private static final String KEY_TASK_ID = "id";
    private static final String KEY_TASKNAME_TEXT = "taskName";
    private static final String KEY_TASKPRIORITYLEVEL = "taskPriorityLevel";
    private static final String KEY_TASKDUEDATE_LONG = "taskDueDate";
    private static final String KEY_TASKNOTES_TEXT = "taskNotes";
    private static final String KEY_TASKSTATUS_TEXT = "taskStatus";

    public TodoDatabase(Context context) {
        this.context = context;
    }

    public void connectTodoDB() {
        dbHelper = new TodoDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqlTodoDB=dbHelper.getWritableDatabase();
    }

    public void closeTodoDB() {
        dbHelper.close();
    }

    // Insert task into the database
    public void writeTasks(MainActivity.ToDoTask toDoTask) {
        Log.d("TODOAPP", "writeTasks = " + toDoTask.taskName);
        Log.d("TODOAPP", "writeTasks = " + toDoTask.taskDueDate);
        Log.d("TODOAPP", "writeTasks = " + toDoTask.taskNotes);
        Log.d("TODOAPP", "writeTasks = " + toDoTask.taskPriority);
        Log.d("TODOAPP", "writeTasks = " + toDoTask.taskStatus);
        // Create and/or open the database for writing
        //sqlTodoDB=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        sqlTodoDB.beginTransaction();
        try {
            values.put(KEY_TASKNAME_TEXT, toDoTask.taskName);
            values.put(KEY_TASKDUEDATE_LONG, toDoTask.taskDueDate);
            values.put(KEY_TASKNOTES_TEXT, toDoTask.taskNotes);
            values.put(KEY_TASKPRIORITYLEVEL, toDoTask.taskPriority);
            values.put(KEY_TASKSTATUS_TEXT, toDoTask.taskStatus);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            sqlTodoDB.insert(TABLE_TODOTASK, null, values);
            sqlTodoDB.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("TODOAPP", "Error while trying to add task to database");
        } finally {
            sqlTodoDB.endTransaction();
        }
    }

    public void updateTask(MainActivity.ToDoTask toDoTask) {
        Log.d("TODOAPP", "updateTask");
        Log.d("TODOAPP", "updateTass ID=" + toDoTask.taskID);
        Log.d("TODOAPP", "updateTask Name=" + toDoTask.taskName);
        //sqlTodoDB=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        sqlTodoDB.beginTransaction();
        try {
            values.put(KEY_TASKNAME_TEXT, toDoTask.taskName);
            values.put(KEY_TASKDUEDATE_LONG, toDoTask.taskDueDate);
            values.put(KEY_TASKNOTES_TEXT, toDoTask.taskNotes);
            values.put(KEY_TASKPRIORITYLEVEL, toDoTask.taskPriority);
            values.put(KEY_TASKSTATUS_TEXT, toDoTask.taskStatus);
            // Which row to update, based on the ID
            String selection = KEY_TASK_ID + " LIKE ?";
            String[] selectionArgs = {String.valueOf(toDoTask.taskID)};


            int count = sqlTodoDB.update(
                    TABLE_TODOTASK,
                    values,
                    selection,
                    selectionArgs);
            sqlTodoDB.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("TODOAPP", "Error while trying to update task to database");
        } finally {
            sqlTodoDB.endTransaction();
        }
    }

    public void deleteTask(MainActivity.ToDoTask toDoTask) {
        Log.d("TODOAPP", "deleteTask");
        Log.d("TODOAPP", "deleteTask ID=" + toDoTask.taskID);
        Log.d("TODOAPP", "deleteTask Name=" + toDoTask.taskName);
        //sqlTodoDB=dbHelper.getWritableDatabase();

        sqlTodoDB.beginTransaction();
        try {
            // Which row to delete, based on the ID
            String selection = KEY_TASK_ID + " LIKE ?";
            String[] selectionArgs = {String.valueOf(toDoTask.taskID)};

            sqlTodoDB.delete(TABLE_TODOTASK, selection, selectionArgs);
            sqlTodoDB.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("TODOAPP", "Error while trying to delete task from database");
        } finally {
            sqlTodoDB.endTransaction();
        }
    }


    public ArrayList<MainActivity.ToDoTask> getAllTasks() {

        Log.d("TODOAPP", "getAllTasks()");
        ArrayList<MainActivity.ToDoTask> tasks = new ArrayList<>();

        //sqlTodoDB=dbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                KEY_TASK_ID,
                KEY_TASKNAME_TEXT,
                KEY_TASKDUEDATE_LONG,
                KEY_TASKNOTES_TEXT,
                KEY_TASKPRIORITYLEVEL,
                KEY_TASKSTATUS_TEXT
        };

        String orderBy = "taskPriorityLevel";
        String query = "SELECT * " + "from " + TABLE_TODOTASK + " WHERE " + KEY_TASKPRIORITYLEVEL + " IN ('High', 'Medium','Low') ORDER BY CASE " + KEY_TASKPRIORITYLEVEL +
                " WHEN 'High' THEN 1 WHEN 'Medium' THEN 2 WHEN 'Low' THEN 3 END," + KEY_TASKPRIORITYLEVEL + "; ";


        Cursor cursor = sqlTodoDB.rawQuery(query, null);


        try {
            if (cursor.moveToFirst()) {
                do {
                    MainActivity.ToDoTask newTask = new MainActivity.ToDoTask();
                    newTask.taskID = cursor.getInt(cursor.getColumnIndex(KEY_TASK_ID));
                    newTask.taskName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TASKNAME_TEXT));
                    newTask.taskDueDate = cursor.getLong(cursor.getColumnIndexOrThrow(KEY_TASKDUEDATE_LONG));
                    newTask.taskNotes = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TASKNOTES_TEXT));
                    newTask.taskPriority = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TASKPRIORITYLEVEL));
                    newTask.taskStatus = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TASKSTATUS_TEXT));
                    Log.d("TODOAPP", "newTask.taskID=" + newTask.taskID);
                    Log.d("TODOAPP", "newTask.taskName=" + newTask.taskName);
                    Log.d("TODOAPP", "newTask.taskDueDate=" + newTask.taskDueDate);
                    Log.d("TODOAPP", "newTask.taskNotes=" + newTask.taskNotes);
                    Log.d("TODOAPP", "newTask.taskPriority=" + newTask.taskPriority);
                    Log.d("TODOAPP", "newTask.taskSTATUS=" + newTask.taskStatus);
                    tasks.add(newTask);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("TODOAPP", "Error while trying to get tasks from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return tasks;
    }


    public static class TodoDatabaseHelper extends SQLiteOpenHelper {

        public TodoDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("TODOAPP", "--- onCreate database ---");
            String CREATE_TODOAPP_TABLE = "CREATE TABLE " + TABLE_TODOTASK +
                    "(" +
                    KEY_TASK_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                    KEY_TASKNAME_TEXT + " TEXT," +
                    KEY_TASKDUEDATE_LONG + " INTEGER," +
                    KEY_TASKNOTES_TEXT + " TEXT," +
                    KEY_TASKPRIORITYLEVEL + " TEXT," +
                    KEY_TASKSTATUS_TEXT + " TEXT" +
                    ")";
            db.execSQL(CREATE_TODOAPP_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("TODOAPP", "--- onUpgrade ---");
            if (oldVersion != newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOTASK);
                onCreate(db);
            }
        }


    }
}
