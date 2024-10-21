package com.cosc4730.program4;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

@Entity(tableName = Expenses.TABLE_NAME)

public class Expenses implements Parcelable{

    public static final String TABLE_NAME = "expenses";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_NOTE = "note";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    @ColumnInfo(name = COLUMN_NAME)
    public String name;

    @ColumnInfo(name = COLUMN_CATEGORY)
    public String category;

    @ColumnInfo(name = COLUMN_DATE)
    public String date;

    @ColumnInfo(name = COLUMN_AMOUNT)
    public Float amount;

    @ColumnInfo(name = COLUMN_NOTE)
    public String note;

    public long getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public String getCategory(){
        return category;
    }
    public String getDate(){
        return date;
    }

    public float getAmount(){

        return amount;
    }
    public String getNote(){
        return note;
    }

    public void setAmount(float amount){
        this.amount = amount;
    }

    @Ignore
    public Expenses(){

    }

    public Expenses( long id, String name,String category,String date, Float amount, String note) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.note = note;

    }

    protected Expenses(Parcel in) {
        id = in.readLong();
        name = in.readString();
        category = in.readString();
        date = in.readString();
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readFloat();
        }
        note = in.readString();
    }

    public static final Creator<Expenses> CREATOR = new Creator<Expenses>() {
        @Override
        public Expenses createFromParcel(Parcel in) {
            return new Expenses(in);
        }

        @Override
        public Expenses[] newArray(int size) {
            return new Expenses[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(date);
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(amount);
        }
        dest.writeString(note);
    }

    public static Expenses fromContentValues(ContentValues values) {
        final Expenses expensesData = new Expenses();
        if (values.containsKey(COLUMN_ID)) {
            expensesData.id = values.getAsLong(COLUMN_ID);
        }
        if (values.containsKey(COLUMN_NAME)) {
            expensesData.name = values.getAsString(COLUMN_NAME);
        }
        if (values.containsKey(COLUMN_CATEGORY)) {
            expensesData.category = values.getAsString(COLUMN_CATEGORY);
        }
        if (values.containsKey(COLUMN_DATE)) {
            expensesData.date = values.getAsString(COLUMN_DATE);
        }
        if (values.containsKey(COLUMN_AMOUNT)) {
            expensesData.amount = values.getAsFloat(COLUMN_AMOUNT);
        }
        if (values.containsKey(COLUMN_NOTE)) {
            expensesData.note = values.getAsString(COLUMN_NOTE);
        }

        return expensesData;
    }

}
