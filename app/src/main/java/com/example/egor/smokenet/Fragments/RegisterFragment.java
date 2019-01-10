package com.example.egor.smokenet.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.egor.smokenet.Models.ErrorTranslator;
import com.example.egor.smokenet.Models.NetworkService;
import com.example.egor.smokenet.Models.SessionManager;
import com.example.egor.smokenet.Database.SQLiteHandler;
import com.example.egor.smokenet.POJO.ServerInformation;
import com.example.egor.smokenet.R;
import com.example.egor.smokenet.Requests.MessengerCreateUser;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {
    private Button mButtonRegisterSubmit;
    private EditText mEditTextRegisterEmail;
    private EditText mEditTextRegisterLogin;
    private EditText mEditTextRegisterPassword;
    private Context context;
    private SQLiteHandler db;
    private SessionManager session;
    public static final String TAG = RegisterFragment.class.getSimpleName();



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public RegisterFragment() {
        // Required empty public constructor

    }


    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View v = inflater.inflate(R.layout.fragment_register, container, false);
         session = new SessionManager(context);
         mEditTextRegisterLogin = v.findViewById(R.id.editTextLogin);
         mEditTextRegisterEmail = v.findViewById(R.id.editTextEmail);
         mEditTextRegisterPassword = v.findViewById(R.id.editTextPassword);
         mButtonRegisterSubmit = v.findViewById(R.id.buttonSubmitRegister);
         db = new SQLiteHandler(getContext());
         mButtonRegisterSubmit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 MessengerCreateUser mcu = NetworkService.getInstance().getRetrofit().create(MessengerCreateUser.class);
                 final HashMap<String,String> requestMap = new HashMap<>();
                 requestMap.put("login", mEditTextRegisterLogin.getText().toString().trim());
                 requestMap.put("password", mEditTextRegisterPassword.getText().toString().trim());
                 requestMap.put("email", mEditTextRegisterEmail.getText().toString().trim());

                 Call<ServerInformation> createUserOnServer = mcu.createUser(requestMap);
                 createUserOnServer.enqueue(new Callback<ServerInformation>() {
                     @Override
                     public void onResponse(Call<ServerInformation> call, Response<ServerInformation> response) {                                                  
                         int error = response.body().getError();
                         if(error<=0)
                         {
                            String login = response.body().getUser().getLogin();
                            String email = response.body().getUser().getEmail();
                            String uid = response.body().getUid();
                            String date = response.body().getUser().getCreatedAt();
                            db.addUser(login ,uid, email,date);
                            HashMap<String, String> map = db.getUserDetails();
                            Log.d(TAG, map.toString());
                            Toast.makeText(context, "Зарегистрирован", Toast.LENGTH_SHORT).show();
                            session.setLogin(true);
                         }
                         else
                         {
                             String error_msg = response.body().getErrorMsg();
                             Log.d(TAG,error_msg);
                             Log.d(TAG,ErrorTranslator.translator(error_msg));
                         }

                     }

                     @Override
                     public void onFailure(Call<ServerInformation> call, Throwable t) {
                        Log.d(TAG, t.toString());
                     }
                 });

             }
         });
        return v;
    }

}
