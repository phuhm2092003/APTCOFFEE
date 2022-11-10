package fpt.edu.aptcoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;

import fpt.edu.aptcoffee.dao.BanDAO;
import fpt.edu.aptcoffee.dao.NguoiDungDAO;
import fpt.edu.aptcoffee.database.CoffeeDB;
import fpt.edu.aptcoffee.model.Ban;
import fpt.edu.aptcoffee.model.NguoiDung;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}