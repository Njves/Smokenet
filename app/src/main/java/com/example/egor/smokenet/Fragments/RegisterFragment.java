package com.example.egor.smokenet.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.egor.smokenet.Models.Controller;
import com.example.egor.smokenet.R;


public class RegisterFragment extends Fragment {
    private Button mButtonRegisterSubmit;
    private EditText mEditTextRegisterLogin;
    private EditText mEditTextRegisterPassword;

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
         mEditTextRegisterLogin = v.findViewById(R.id.editTextLogin);
         mEditTextRegisterPassword = v.findViewById(R.id.editTextPassword);
         mButtonRegisterSubmit = v.findViewById(R.id.buttonSubmitRegister);

         mButtonRegisterSubmit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });


        return v;
    }

}
