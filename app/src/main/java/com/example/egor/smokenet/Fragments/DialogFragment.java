package com.example.egor.smokenet.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.egor.smokenet.Adapters.MessageListAdapter;
import com.example.egor.smokenet.Database.SQLiteHandler;
import com.example.egor.smokenet.Database.SQLiteMessageHandler;
import com.example.egor.smokenet.Interfaces.MessageListener;
import com.example.egor.smokenet.Models.NetworkService;
import com.example.egor.smokenet.Models.TCPConnection;
import com.example.egor.smokenet.POJO.Message;
import com.example.egor.smokenet.R;
import com.example.egor.smokenet.Requests.WriteMessage;
import com.example.egor.smokenet.Requests.getDialogMessages;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class DialogFragment extends Fragment implements Callback<Message> {
    public static final String TAG = DialogFragment.class.getSimpleName();
    MessageListener listener;
    private List<Message> mListMessage;
    private List<Message> mListMessageRevers;
    private TextView textViewReciver;
    private ImageButton buttonSendMessage;
    private EditText editTextTextField;
    private static final String CLIENT_LOGIN = "param1";
    private static final String CLIENT_EMAIL = "param2";
    private RecyclerView mRecyclerViewDialog;
    private Context context;
    private String clientLogin;
    private String clientEmail;
    private SQLiteHandler sqliteHandler;



    public DialogFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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
        getMessageList();

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


        HashMap<String, String> userDetails = sqliteHandler.getUserDetails();
        String userSender = userDetails.get("login");

        Log.d(TAG , userSender);

        WriteMessage writeMessage = NetworkService.getInstance().getRetrofit().create(WriteMessage.class);

        HashMap<String, String> postMap = new HashMap<>();

        postMap.put("reciver", clientLogin);
        postMap.put("sender", userSender);
        postMap.put("text", editTextTextField.getText().toString());
        postMap.put("flags", String.valueOf(0));
        Call<Message> call = writeMessage.writeMessage(postMap);

        call.enqueue(callback);


    }

    public void getMessageList()
    {
         sqliteHandler= new SQLiteHandler(getContext());
        HashMap<String, String> userDetails = sqliteHandler.getUserDetails();
         String sender = userDetails.get("login");

         getDialogMessages getDialogMessages = NetworkService.getInstance().getRetrofit().create(getDialogMessages.class);
        Call<List<Message>> callMessages = getDialogMessages.messageCall("get_list",sender, clientLogin);

        callMessages.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if(response.body()!=null) {
                    mListMessage.addAll(response.body());
                    mRecyclerViewDialog.getAdapter().notifyDataSetChanged();
                    Log.d(TAG,"List - " + mListMessage);
                }
                else
                {
                    Log.d(TAG, "List is null");
                }

            }




                @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });

    }
    @Override
    public void onResponse(Call<Message> call, Response<Message> response) {
        Log.d(TAG, response.body().toString());
    }

    @Override
    public void onFailure(Call<Message> call, Throwable t) {
        Log.d(TAG, t.getMessage());
    }
}



