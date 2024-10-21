package com.cosc4730.program4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class AddFragment extends Fragment {
    View view;
    private ExpensesViewModel mExpensesViewModel;
    EditText editTextName;
    EditText editTextCategory;
    EditText editTextDate;
    EditText editTextAmount;
    EditText getEditTextNote;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add, container, false);

        mExpensesViewModel = new ViewModelProvider(this).get(ExpensesViewModel.class);

        editTextName = view.findViewById(R.id.editTextName);
        editTextCategory = view.findViewById(R.id.editTextCategory);
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextAmount = view.findViewById(R.id.editTextAmount);
        getEditTextNote = view.findViewById(R.id.editTextNote);

        view.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDataToDB();
            }
        });
        view.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });

        return view;
    }

    public void insertDataToDB(){
        String name = editTextName.getText().toString();
        String category = editTextCategory.getText().toString();
        String date = editTextDate.getText().toString();
        String textAmount = editTextAmount.getText().toString();
        float amount = Float.parseFloat(textAmount);
        String note = getEditTextNote.getText().toString();

        if (inputCheck(name, category, date, textAmount)){
            Expenses expenses = new Expenses(0, name, category, date, amount, note);
            mExpensesViewModel.addAllData(expenses);
            System.out.println(expenses);
            Toast.makeText(requireContext(),"Data added", Toast.LENGTH_SHORT).show();
            NavController navController = Navigation.findNavController(view); // Use the local view variable here
            navController.navigate(R.id.action_addFragment_to_listFragment);

         }
        else{
            Toast.makeText(requireContext(),"Please fill out required fields", Toast.LENGTH_LONG).show();


        }

    }
    private void deleteData(){
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_addFragment_to_listFragment);
    }

    private boolean inputCheck(String name, String category, String date, String textAmount) {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(category)
                && TextUtils.isEmpty(date) && TextUtils.isEmpty(textAmount));
    }

}