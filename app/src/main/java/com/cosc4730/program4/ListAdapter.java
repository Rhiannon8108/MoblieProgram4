package com.cosc4730.program4;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<Expenses> expensesList;
    private ExpensesViewModel mExpensesViewModel;

    public ListAdapter(ExpensesViewModel viewModel){
        this.expensesList = new ArrayList<>();
        this.mExpensesViewModel = viewModel ;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewCategory;
        TextView textViewDate;
        TextView textViewAmount;
        TextView textViewNote;
        ImageButton deleteButton;
        public MyViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewNote = itemView.findViewById(R.id.textViewNote);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
        public void bind(Expenses expense) {
            if (expense != null && textViewName != null) {
                textViewName.setText(String.valueOf(expense.getName()));
            }
            if (expense != null && textViewCategory != null) {
                textViewCategory.setText(String.valueOf(expense.getCategory()));
            }
            if (expense != null && textViewDate != null) {
                textViewDate.setText(String.valueOf(expense.getDate()));
            }
            if (expense != null && textViewAmount != null) {
                textViewAmount.setText(String.valueOf(expense.getAmount()));
            }
            if (expense != null && textViewNote != null) {
                textViewNote.setText(String.valueOf(expense.getNote()));
            } else {
                Log.e("MyViewHolder", "Expense or TextView is null");
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (holder != null && position >= 0 && position < expensesList.size()) {
            Expenses currentItem = expensesList.get(position);
            holder.bind(currentItem);
            holder.itemView.findViewById(R.id.rowLayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListFragmentDirections.ActionListFragmentToUpdateFragment action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem);
                    NavController navController = Navigation.findNavController(holder.itemView);
                    navController.navigate(action);
                }
            });
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExpensesViewModel.deleteExpenses(currentItem); // Delete item from ViewModel
                    notifyItemRemoved(position); // Notify adapter about item removal
                    Toast.makeText(v.getContext(), "Expense deleted", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.e("ListAdapter", "Holder is null or position out of bounds");
        }

    }

    public void setData(List<Expenses> expenses) {
        this.expensesList = expenses;
        notifyDataSetChanged();
    }

        @Override
    public int getItemCount() {
        System.out.println(expensesList.size());
        return expensesList.size();

    }
}
