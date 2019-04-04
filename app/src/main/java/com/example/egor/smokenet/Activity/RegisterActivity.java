package com.example.egor.smokenet.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.egor.smokenet.Database.SQLiteHandler;

import com.example.egor.smokenet.Models.ErrorTranslator;
import com.example.egor.smokenet.Models.NetworkService;
import com.example.egor.smokenet.Models.ProgressDialogShower;
import com.example.egor.smokenet.Models.SessionManager;
import com.example.egor.smokenet.POJO.ServerInformation;
import com.example.egor.smokenet.R;
import com.example.egor.smokenet.Requests.RegisterUser;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements Dialog.OnShowListener{
    private Button mButtonRegisterSubmit;
    private EditText mEditTextRegisterEmail;
    private EditText mEditTextRegisterLogin;
    private EditText mEditTextRegisterPassword;
    private EditText mEditTextRegisterRePassword;
    private Context context;
    private SQLiteHandler db;
    private SessionManager session;
    private ProgressDialogShower mProgressDialogShower;
    // Окно согласия политики конфиденциальности
    private Dialog agreementDialog;
    public static final String TAG = RegisterActivity.class.getSimpleName();
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = getApplicationContext();
        session = new SessionManager(context);
        mProgressDialogShower = new ProgressDialogShower(this);
        mEditTextRegisterLogin = findViewById(R.id.editTextLogin);
        mEditTextRegisterEmail = findViewById(R.id.editTextEmail);
        mEditTextRegisterPassword = findViewById(R.id.editTextPassword);
        mButtonRegisterSubmit = findViewById(R.id.buttonSubmitRegister);
        mEditTextRegisterRePassword = findViewById(R.id.ediTextRePassword);
        if(savedInstanceState!=null) {
            mEditTextRegisterEmail.setText(savedInstanceState.getString("email"));
            mEditTextRegisterLogin.setText(savedInstanceState.getString("login"));

        }



        db = new SQLiteHandler(getApplicationContext());

        mButtonRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialogShower.showProgressDialog();
                if(mEditTextRegisterPassword.getText().toString().length()>=6 && mEditTextRegisterRePassword.getText().toString().length()>=6) {
                    if (mEditTextRegisterRePassword.getText().toString().equals(mEditTextRegisterPassword.getText().toString())) {
                        registerUser();
                    }
                    else
                    {
                        mProgressDialogShower.hideProgressDialog();
                        Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    mProgressDialogShower.hideProgressDialog();
                    Toast.makeText(context, "Пароль содержит меньше 6 символов", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void showAgreementDialog()
    {
        agreementDialog = new Dialog(RegisterActivity.this);
        agreementDialog.setContentView(R.layout.dialog_agreement_privacy_policy);
        agreementDialog.setOnShowListener(this);

        agreementDialog.show();

    }
   @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "Override bundle in Register");
        outState.putString("login", mEditTextRegisterLogin.getText().toString());
        outState.putString("email", mEditTextRegisterEmail.getText().toString());
    }

    public void registerUser() {

        if (!mEditTextRegisterLogin.getText().toString().isEmpty() && !mEditTextRegisterEmail.getText().toString().isEmpty() && !mEditTextRegisterPassword.getText().toString().isEmpty()) {

            if (mEditTextRegisterPassword.getText().toString().equals(mEditTextRegisterRePassword.getText().toString())) {
                RegisterUser mcu = NetworkService.getInstance().getRetrofit().create(RegisterUser.class);
                HashMap<String, String> requestMap = new HashMap<>();
                requestMap.put("login", mEditTextRegisterLogin.getText().toString().trim());
                requestMap.put("password", mEditTextRegisterPassword.getText().toString().trim());
                requestMap.put("email", mEditTextRegisterEmail.getText().toString().trim());

                Call<ServerInformation> createUserOnServer = mcu.createUser(requestMap);
                createUserOnServer.enqueue(new Callback<ServerInformation>() {
                    @Override
                    public void onResponse(Call<ServerInformation> call, Response<ServerInformation> response) {
                        mProgressDialogShower.hideProgressDialog();
                        int error = response.body().getError();

                        if (error <= 0) {
                            //Вызов диалогового окна о соглашение политики безопасности


                            String login = response.body().getUser().getLogin();
                            String email = response.body().getUser().getEmail();
                            String uid = response.body().getUid();
                            String date = response.body().getUser().getCreatedAt();
                            db.addUser(login, email, uid, date);
                            HashMap<String, String> map = db.getUserDetails();
                            Log.d(TAG, map.toString());
                            Toast.makeText(context, "Зарегистрирован", Toast.LENGTH_SHORT).show();
                            session.setLogin(true);
                            intent = new Intent(RegisterActivity.this, MenuActivity.class);
                            startActivity(intent);
                        } else {
                            String error_msg = response.body().getErrorMsg();
                            Log.d(TAG, error_msg);
                            Log.d(TAG, ErrorTranslator.translator(error_msg));
                            Toast.makeText(context, ErrorTranslator.translator(error_msg), Toast.LENGTH_SHORT).show();
                        }

                    }


                    @Override
                    public void onFailure(Call<ServerInformation> call, Throwable t) {
                        Log.d(TAG, t.toString());
                    }
                });
            }
            else
            {
                Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            }
            } else {
                Toast.makeText(context, "Заполните все данные", Toast.LENGTH_SHORT).show();
            }
        }


    @Override
    public void onShow(DialogInterface dialog) {
        Log.i(TAG, "Открыт диалог политики конфиденциальности");
    }
}
