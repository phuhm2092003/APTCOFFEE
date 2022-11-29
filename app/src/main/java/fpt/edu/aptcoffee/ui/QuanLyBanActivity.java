package fpt.edu.aptcoffee.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.adapter.BanAdapter;
import fpt.edu.aptcoffee.dao.BanDAO;
import fpt.edu.aptcoffee.utils.MyToast;

public class QuanLyBanActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewBan;
    BanDAO banDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_ban);
        initToolBar();
        banDAO = new BanDAO(this);
        loadData();

    }

    private void loadData() {
        recyclerViewBan = findViewById(R.id.recyclerViewBan);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
        recyclerViewBan.setLayoutManager(linearLayoutManager);
        BanAdapter banAdapter = new BanAdapter(banDAO.getAll());
        recyclerViewBan.setAdapter(banAdapter);
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.toolbarBan);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

        }
        return super.onOptionsItemSelected(item);
    }
}