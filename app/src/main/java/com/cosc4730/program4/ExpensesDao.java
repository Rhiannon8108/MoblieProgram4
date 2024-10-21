package com.cosc4730.program4;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpensesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Expenses> expenses);

    @Query("SELECT * FROM " + Expenses.TABLE_NAME)
    LiveData<List<Expenses>> selectAll();


    @Query("SELECT * FROM " + Expenses.TABLE_NAME + " ORDER BY " + Expenses.COLUMN_NAME + " ASC")
    LiveData<List<Expenses>> selectByName();


    @Query("SELECT * FROM " + Expenses.TABLE_NAME + " WHERE " + Expenses.COLUMN_ID + " = :id")
    LiveData<List<Expenses>> selectById(long id);

    @Query("SELECT * FROM " + Expenses.TABLE_NAME + " ORDER BY " + Expenses.COLUMN_ID + " ASC")
    LiveData<List<Expenses>> getAllExpenses();

    @Insert
    long insert(Expenses expensesData);

    @Insert
    long[] insertAll(Expenses[] expensesData);

    @Query("DELETE FROM " + Expenses.TABLE_NAME + " WHERE " + Expenses.COLUMN_ID + " = :id")
    int deleteById(long id);

    @Query(" DELETE FROM " + Expenses.TABLE_NAME)
    void deleteAllExpenses();

    @Update
    void updateExpenses(Expenses expenses);

    @Delete
    void deleteExpenses(Expenses expenses);

}