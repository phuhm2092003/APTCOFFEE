package fpt.edu.aptcoffee.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import java.util.Objects;
import fpt.edu.aptcoffee.MainActivity;
import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.dao.NguoiDungDAO;
import fpt.edu.aptcoffee.utils.MyToast;

public class SignInActivity extends AppCompatActivity {
    public static final int SIGN_IN_SUCCESSFUL = 0;
    public static final int SIGN_IN_FAILE = 1;
    public static final int SIGN_IN_ERORR = -1;
    public static final String SIGN_IN_STATUS = "SIGN_IN_STATUS";
    public static final String ACTION_SIGN_IN = "ACTION_SIGN_IN";
    public static final String KEY_USER = "maNguoiDung";
    TextInputLayout tilUserName, tilPassword;
    Button btnSignIn;
    TextView tvRegister;
    NguoiDungDAO nguoiDungDAO;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();
        initIntentFilter();

        nguoiDungDAO = new NguoiDungDAO(SignInActivity.this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInSystem();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }

    private void initView() {
        tilUserName = findViewById(R.id.tilUserName);
        tilPassword = findViewById(R.id.tilPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvRegister = findViewById(R.id.tvRegister);
    }

    private void initIntentFilter() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SIGN_IN);
    }

    @NonNull
    private String getText(TextInputLayout tilUserName) {
        return Objects.requireNonNull(tilUserName.getEditText()).getText().toString().trim();
    }

    private void signInSystem() {
        String username = getText(tilUserName);
        String password = getText(tilPassword);
        int signInStatus = SIGN_IN_ERORR;
        if (!username.isEmpty() && !password.isEmpty()) {
            if (nguoiDungDAO.checkLogin(username, password)) {
                signInStatus = SIGN_IN_SUCCESSFUL;
                openMainActivity(username);
            } else {
                signInStatus = SIGN_IN_FAILE;
            }
        }
        // Send BroadcastReceiver
        sendMyBroadcast(signInStatus);

    }

    private void sendMyBroadcast(int signInStatus) {
        Intent intent = new Intent();
        intent.setAction(ACTION_SIGN_IN);
        intent.putExtra(SIGN_IN_STATUS, signInStatus);
        sendBroadcast(intent);
    }

    private void openMainActivity(String username) {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        intent.putExtra(KEY_USER, username);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
    }

    private final BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int signInStatus = intent.getIntExtra(SIGN_IN_STATUS, SIGN_IN_ERORR);
            switch (signInStatus) {
                case SIGN_IN_SUCCESSFUL:
                    MyToast.successful(SignInActivity.this, "Đăng nhập thành công");
                    break;
                case SIGN_IN_FAILE:
                    MyToast.error(SignInActivity.this, "Đăng nhập thất bại");
                    break;
                case SIGN_IN_ERORR:
                    MyToast.error(SignInActivity.this, "Không để trống mật khẩu hoặc tên đăng nhập");
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myBroadcastReceiver);
    }
}