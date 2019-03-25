package com.example.egor.smokenet.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.egor.smokenet.Activity.MenuActivity;
import com.example.egor.smokenet.Adapters.DialogListAdapter;
import com.example.egor.smokenet.Models.ProgressDialogShower;
import com.example.egor.smokenet.POJO.Client;
import com.example.egor.smokenet.Models.NetworkService;
import com.example.egor.smokenet.R;
import com.example.egor.smokenet.Requests.DialogsUser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DialogsFragment extends Fragment {
    RecyclerView recyclerViewDialogsView;
    Context context;
    DialogListAdapter dialogAdapter;
    List<Client> clientList;
    ProgressDialogShower showProgressDialog;
    public static final String TAG = DialogsFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnGetClientDialogFragment mListener;

    public DialogsFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DialogsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogsFragment newInstance(String param1, String param2) {
        DialogsFragment fragment = new DialogsFragment();
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
        View v = inflater.inflate(R.layout.fragment_dialogs, container, false);

        showProgressDialog = new ProgressDialogShower(context);
        recyclerViewDialogsView = v.findViewById(R.id.list_dialogs);
        showProgressDialog.showProgressDialog();
        clientList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerViewDialogsView.setLayoutManager(linearLayoutManager);
        dialogAdapter = new DialogListAdapter(10, getContext(),clientList);
        recyclerViewDialogsView.setAdapter(dialogAdapter);
        DialogsUser dialogsUser = NetworkService.getInstance().getRetrofit().create(DialogsUser.class);

        Call<List<Client>> call = dialogsUser.getUsersLogin();
        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                showProgressDialog.hideProgressDialog();
                clientList.addAll(response.body() != null ? response.body() : null);

                recyclerViewDialogsView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {

            }
        });



        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    public interface OnGetClientDialogFragment {
        // TODO: Update argument type and name
        Client setClient(Client client);
    }
}

