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

import com.example.egor.smokenet.Adapters.DialogListAdapter;
import com.example.egor.smokenet.Adapters.MessageListAdapter;
import com.example.egor.smokenet.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 */
public class DialogFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG = DialogFragment.class.getSimpleName();
    private static final String CLIENT_LOGIN = "param1";
    private static final String CLIENT_EMAIL = "param2";
    private RecyclerView mRecyclerViewDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



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
            mParam1 = getArguments().getString(CLIENT_LOGIN);
            mParam2 = getArguments().getString(CLIENT_EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
        mRecyclerViewDialog = v.findViewById(R.id.dialogMessage);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewDialog.setLayoutManager(linearLayoutManager);
        MessageListAdapter messageListAdapter = new MessageListAdapter(getContext());
        mRecyclerViewDialog.setAdapter(messageListAdapter);


        return v;
    }









    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
class SocketThreadClient extends Thread
{
    @Override
    public void run() {
        try {
            InetAddress inetAddress = InetAddress.getByName("192.168.1.12");
            Socket socket = new Socket(inetAddress, 8189);
            InputStream socketInputStream = socket.getInputStream();
            OutputStream socketOutputStream = socket.getOutputStream();

            DataInputStream socketDataInputStream = new DataInputStream(socketInputStream);
            DataOutputStream socketDataOutputStream = new DataOutputStream(socketOutputStream);
            String str = "prikol";

            while(!this.isInterrupted())
            {

                socketDataOutputStream.writeUTF(str); // отсылаем введенную строку текста серверу.
                socketDataOutputStream.flush(); // заставляем поток закончить передачу данных.
                str = socketDataInputStream.readUTF(); // ждем пока сервер отошлет строку текста.
                Log.d("DialogFragment", str);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
