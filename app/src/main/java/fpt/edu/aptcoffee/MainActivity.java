package fpt.edu.aptcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import fpt.edu.aptcoffee.database.CoffeeDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        CoffeeDB coffeeDB = new CoffeeDB(this);
        SQLiteDatabase sqLiteDatabase = coffeeDB.getWritableDatabase();
        sqLiteDatabase.close();
    }
}