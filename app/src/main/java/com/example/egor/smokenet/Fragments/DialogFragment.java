package com.example.egor.smokenet.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.egor.smokenet.Adapters.DialogListAdapter;
import com.example.egor.smokenet.Adapters.MessageListAdapter;
import com.example.egor.smokenet.Database.SQLiteHandler;
import com.example.egor.smokenet.Models.NetworkService;
import com.example.egor.smokenet.POJO.Message;
import com.example.egor.smokenet.R;
import com.example.egor.smokenet.Requests.WriteMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLSocket;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 */
public class DialogFragment extends Fragment implements Callback<Message> {
    public static final String TAG = DialogFragment.class.getSimpleName();
    private List<Message> mListMessage;
    private TextView textViewReciver;
    private ImageButton buttonSendMessage;
    private EditText editTextTextField;
    private static final String CLIENT_LOGIN = "param1";
    private static final String CLIENT_EMAIL = "param2";
    private RecyclerView mRecyclerViewDialog;

    private String clientLogin;
    private String clientEmail;



    public DialogFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DialogFragment newInstance(String param1, String param2) {
        DialogFragment fragment = new DialogFragment();
        Bundle args = new Bundle();
        args.putString(CLIENT_LOGIN, param1);
        args.putString(CLIENT_EMAIL, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            clientLogin = getArguments().getString(CLIENT_LOGIN);
            clientEmail = getArguments().getString(CLIENT_EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);

        buttonSendMessage = v.findViewById(R.id.buttonSendMessage);
        editTextTextField = v.findViewById(R.id.editTextMessageInput);
        SQLiteHandler sqliteHandler = new SQLiteHandler(getContext());
        HashMap<String, String> userDetails = sqliteHandler.getUserDetails();

        mListMessage = new ArrayList<>();

        mRecyclerViewDialog = v.findViewById(R.id.dialogMessage);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewDialog.setLayoutManager(linearLayoutManager);
        MessageListAdapter messageListAdapter = new MessageListAdapter(getContext(),mListMessage);
        mRecyclerViewDialog.setAdapter(messageListAdapter);
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(DialogFragment.this);
            }
        });

        return v;
    }

    public void sendMessage(Callback<Message> callback)
    {
        SQLiteHandler sqliteHandler = new SQLiteHandler(getContext());
        HashMap<String, String> userDetails = sqliteHandler.getUserDetails();
        String userSender = userDetails.get("login");
        Log.d(TAG , userSender);
        WriteMessage writeMessage = NetworkService.getInstance().getRetrofit().create(WriteMessage.class);
        HashMap<String, String> postMap = new HashMap<>();
        postMap.put("user_reciver", clientLogin);
        postMap.put("user_sender",userSender);
        postMap.put("text", editTextTextField.getText().toString());
        Call<Message> call = writeMessage.writeMessage(postMap);
        call.enqueue(callback);

    }


    @Override
    public void onResponse(Call<Message> call, Response<Message> response) {
        Log.d(TAG, response.body().toString());
    }

    @Override
    public void onFailure(Call<Message> call, Throwable t) {
        Log.i(TAG, "Failure - " + t.toString());
    }
}
class AsyncClient extends AsyncTask<Void, Void, Void>
{
    private String clientIp;
    private int port;
    public AsyncClient(String ip, int port) {
        clientIp = ip;
        this.port = port;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Socket socket = new Socket(clientIp,port);
            InputStream socketInputStream = socket.getInputStream();
            OutputStream socketOutputStream = socket.getOutputStream();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}


