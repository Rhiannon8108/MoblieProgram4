package com.cosc4730.program4;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ExpensesViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<Expenses>> mObservableExpenses;
    private final AppDatabase ad;
    final private String TAG = "ViewModel";

    public ExpensesViewModel(Application application) {
        super(application);
        ad = AppDatabase.getInstance(application);
        mObservableExpenses = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableExpenses.setValue(null);
        LiveData<List<Expenses>> expenses = ad.ExpensesDao().selectAll();

        mObservableExpenses.addSource(expenses, new androidx.lifecycle.Observer<List<Expenses>>() {
            @Override
            public void onChanged(@Nullable List<Expenses> expensesEntities) {
                mObservableExpenses.setValue(expensesEntities);
            }
        });
    }
    public void addAllData( Expenses expenses) {
        Thread myThread = new Thread() {  //database additions can't be on the main thread.
            public void run() {
                Log.d(TAG, "Inserting data");
                ad.ExpensesDao().insert(expenses);
                // After insertion, get the updated list of expenses and update LiveData
                LiveData<List<Expenses>> updatedExpenses = ad.ExpensesDao().selectAll();
                mObservableExpenses.postValue(updatedExpenses.getValue());
            }
        };
        myThread.start();
    }

    public void updateAllData( Expenses expenses){
        Thread myThread = new Thread() {  //database additions can't be on the main thread.
            public void run() {
                Log.d(TAG, "Inserting data");
                ad.ExpensesDao().updateExpenses(expenses);
                // After insertion, get the updated list of expenses and update LiveData
                LiveData<List<Expenses>> updatedExpenses = ad.ExpensesDao().selectAll();
                mObservableExpenses.postValue(updatedExpenses.getValue());
            }
        };
        myThread.start();
    }

    public void deleteExpenses( Expenses expenses) {
        Thread myThread = new Thread() {  //database additions can't be on the main thread.
            public void run() {
                Log.d(TAG, "deleting data");
                ad.ExpensesDao().deleteExpenses(expenses);
                // After insertion, get the updated list of expenses and update LiveData
                LiveData<List<Expenses>> updatedExpenses = ad.ExpensesDao().selectAll();
                mObservableExpenses.postValue(updatedExpenses.getValue());
            }
        };
        myThread.start();
    }



    public LiveData<List<Expenses>> getData() {
        return mObservableExpenses;
    }

    public LiveData<List<Expenses>> getReadAllData() {
        return ad.ExpensesDao().getAllExpenses();
    }

}