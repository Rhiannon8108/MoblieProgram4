package com.cosc4730.program4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class ListFragment extends Fragment {
    View view;
    private ExpensesViewModel mExpensesViewModel;
    private ListAdapter adapter;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list, container, false);
        mExpensesViewModel = new ViewModelProvider(this).get(ExpensesViewModel.class);
        adapter = new ListAdapter(mExpensesViewModel);
        //Recyclerview
        ListAdapter adapter = new ListAdapter(mExpensesViewModel);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        //ExpensesViewModel
        mExpensesViewModel = new ViewModelProvider(this).get(ExpensesViewModel.class);
        mExpensesViewModel.getReadAllData().observe(getViewLifecycleOwner(), new Observer<List<Expenses>>() {
            @Override
            public void onChanged(List<Expenses> expenses) {
                adapter.setData(expenses);
            }
        });



        view.findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_listFragment_to_addFragment);
            }
        });

        return view;
    }
}