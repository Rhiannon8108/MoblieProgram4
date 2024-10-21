package com.cosc4730.program4;

import static androidx.databinding.adapters.TextViewBindingAdapter.setText;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


public class UpdateFragment extends Fragment {
    View view;
    private ExpensesViewModel mExpensesViewModel;
    EditText editTextUpdateCategory;
    EditText editTextUpdateName;
    EditText editTextUpdateDate;
    EditText editTextUpdateAmount;
    EditText editTextUpdateNote;

    private @NonNull UpdateFragmentArgs args;

    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        if (getArguments() != null) {
            args = UpdateFragmentArgs.fromBundle(getArguments());
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_update, container, false);
        // Initialize the EditText views
        editTextUpdateName = view.findViewById(R.id.editTextUpdateName);
        editTextUpdateDate = view.findViewById(R.id.editTextUpdateDate);
        editTextUpdateCategory = view.findViewById(R.id.editTextUpdateCategory);
        editTextUpdateAmount = view.findViewById(R.id.editTextUpdateAmount);
        editTextUpdateNote = view.findViewById(R.id.editTextUpdateNote);

        // Set the existing data to the EditText views
        editTextUpdateName.setText(args.getCurrentExpenses().getName());
        editTextUpdateDate.setText(args.getCurrentExpenses().getDate());
        editTextUpdateCategory.setText(args.getCurrentExpenses().getCategory());
        editTextUpdateAmount.setText(String.valueOf(args.getCurrentExpenses().getAmount()));
        editTextUpdateNote.setText(args.getCurrentExpenses().getNote());

        mExpensesViewModel = new ViewModelProvider(this).get(ExpensesViewModel.class);

        view.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDataToDB();
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

   private void updateDataToDB(){
       String category = editTextUpdateCategory.getText().toString();
       String date = editTextUpdateDate.getText().toString();
       String textAmount = editTextUpdateAmount.getText().toString();
       float amount = Float.parseFloat(textAmount);
       String name = editTextUpdateName.getText().toString();
       String note = editTextUpdateNote.getText().toString();

           if (inputCheck(name, category, date, textAmount)){
           Expenses updateExpenses = new Expenses(args.getCurrentExpenses().getId(),
                   name, category, date, amount, note);
           mExpensesViewModel.updateAllData(updateExpenses);
           System.out.println(updateExpenses);
           Toast.makeText(requireContext(),"Data updated", Toast.LENGTH_SHORT).show();
           NavController navController = Navigation.findNavController(view);
           navController.navigate(R.id.action_updateFragment_to_listFragment);

       }
       else{
           Toast.makeText(requireContext(),"Please fill out required fields", Toast.LENGTH_LONG).show();


       }
   }

   private void deleteData(){
        Expenses deleteExpenses = args.getCurrentExpenses();
        mExpensesViewModel.deleteExpenses(deleteExpenses);
        Toast.makeText(requireContext(), "Data deleted", Toast.LENGTH_SHORT).show();
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_updateFragment_to_listFragment);
   }

    private boolean inputCheck(String name, String category, String date, String textAmount) {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(category)
                && TextUtils.isEmpty(date) && TextUtils.isEmpty(textAmount));
    }


}
