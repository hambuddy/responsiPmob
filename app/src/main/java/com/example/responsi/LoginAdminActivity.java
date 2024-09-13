package com.example.responsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginAdminActivity extends AppCompatActivity {

    EditText TxEmail, TxPassword;
    Button BtnLogin;
    DBHelper1 dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        TxEmail = (EditText)findViewById(R.id.txEmailAdmin);
        TxPassword = (EditText)findViewById(R.id.txPasswordAdmin);
        BtnLogin = (Button)findViewById(R.id.btnLoginAdmin);

        dbHelper = new DBHelper1(this);

        TextView tvCreateAccount = (TextView)findViewById(R.id.tvCreateAccountAdmin);

        tvCreateAccount.setText(fromHtml("Belum punya akun?. " +
                "</font><font color='#3b5998'>Buat disini!</font>"));
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAdminActivity.this, RegisterAdminActivity.class));
            }
        });

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = TxEmail.getText().toString().trim();
                String password = TxPassword.getText().toString().trim();

                Boolean res = dbHelper.checkUser(email,password);
                Boolean resEmail = dbHelper.checkemail(email);
                Boolean resPasswd = dbHelper.checkPasswd(password);
                if(res == true){
//                    Toast.makeText(LoginActivity.this, "Login Berhasil!", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(LoginActivity.this, ListGoodsActivity.class));

                    Intent login = new Intent(LoginAdminActivity.this, ListGoodsActivity.class);
                    Toast.makeText(LoginAdminActivity.this, "Login Berhasil!", Toast.LENGTH_SHORT).show();
                    startActivity(login);
                }else {
                    if (TxEmail.getText().toString().isEmpty()) {
                        TxEmail.setError("Email Tidak Boleh Kosong!");
                    }else if(resEmail == false){
                        TxEmail.setError("Harap Masukkan Email Yang Valid!");
                    }
                    if (TxPassword.getText().toString().isEmpty()) {
                        TxPassword.setError("Password Tidak Boleh Kosong!");
                    } else if (TxPassword.length() < 6) {
                        TxPassword.setError("Password Harus 6 Karakter atau Lebih!");
                    } else if(resPasswd == false){
                        TxPassword.setError("Password Salah");
                    } else {
                        Toast.makeText(LoginAdminActivity.this, "Login gagal, Silahkan coba kembali!", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    public static Spanned fromHtml(String html){
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        }else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}