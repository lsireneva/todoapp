package com.example.lsireneva.todoapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Liubov Sireneva
 */

public class AddItemFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    Spinner spinnerTaskPriority, spinnerTaskStatus;
    ArrayAdapter<CharSequence> adapterTaskPriority, adapterTaskStatus;
    TextView taskName,taskNotes;
    DatePicker taskDueDate;

    private int taskYear, taskMonth, taskDay;

    private OnFragmentInteractionListener mListener;

    public AddItemFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_item,  container, false);
        setupFragmentView ();
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        return view;
    }

    public void setupFragmentView () {
        taskName = (TextView) view.findViewById(R.id.taskNameInput);
        taskName.setText("");
        taskNotes = (TextView) view.findViewById(R.id.taskNotesInput);
        taskNotes.setText("");

        taskDueDate = (DatePicker) view.findViewById(R.id.dueDatePicker);

        // get current data
        final Calendar calendar = Calendar.getInstance();

        taskDueDate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                taskMonth=month+1;
                taskDay=dayOfMonth;
                taskYear=year;
            }
        });

        spinnerTaskPriority = (Spinner) view.findViewById(R.id.taskPriorityLevelSpinner);
        adapterTaskPriority = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.taskPriorityLevelSpinner, android.R.layout.simple_spinner_item);
        adapterTaskPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskPriority.setAdapter(adapterTaskPriority);

        spinnerTaskPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.taskPriorityLevelSpinner);
                String selected=choose[selectedItemPosition];
                Log.d("TODOAPP","taskPriorityLevelSpinner="+selected);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerTaskStatus = (Spinner) view.findViewById(R.id.taskStatusSpinner);
        adapterTaskStatus = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.taskStatusSpinner, android.R.layout.simple_spinner_item);
        adapterTaskStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskStatus.setAdapter(adapterTaskStatus);

        spinnerTaskStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.taskStatusSpinner);
                String selected=choose[selectedItemPosition];
                Log.d("TODOAPP","taskStatusSpinner="+selected);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }
}
