package com.example.lsireneva.todoapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lsireneva.todoapp.activities.MainActivity;
import com.example.lsireneva.todoapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CustomTodoTaskAdapter extends ArrayAdapter<MainActivity.ToDoTask> {
    public CustomTodoTaskAdapter(Context context, ArrayList<MainActivity.ToDoTask> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todotask, parent, false);
        }

        MainActivity.ToDoTask task = getItem(position);

        TextView taskName = (TextView) convertView.findViewById(R.id.taskNameCustom);
        TextView taskNotes = (TextView) convertView.findViewById(R.id.taskNotesCustom);
        TextView taskDueDate = (TextView) convertView.findViewById(R.id.taskDueDateCustom);
        TextView taskPriority = (TextView) convertView.findViewById(R.id.taskPriorityCustom);

        taskName.setText(task.taskName);
        taskNotes.setText(task.taskNotes);
        final Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(task.taskDueDate);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd yyyy");
        Date d = new Date(c.get(c.YEAR)-1900, c.get(c.MONTH), c.get(c.DAY_OF_MONTH));
        String strDate = dateFormatter.format(d);

        taskDueDate.setText(strDate);
        taskDueDate.setTypeface(null, Typeface.ITALIC);
        taskPriority.setText(task.taskPriority);
        String taskPriorityValue = taskPriority.getText().toString();

        if (taskPriorityValue.equals("High")){
            taskPriority.setTextColor(convertView.getResources().getColor(R.color.colorPriorityHigh));}
        else if (taskPriorityValue.equals("Medium")){
            taskPriority.setTextColor(convertView.getResources().getColor(R.color.colorPriorityMedium));}
        else {
            taskPriorityValue.equals("Low");
            taskPriority.setTextColor(convertView.getResources().getColor(R.color.colorPriorityLow)); }

        return convertView;
    }
}
