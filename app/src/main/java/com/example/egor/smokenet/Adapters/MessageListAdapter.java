package com.example.egor.smokenet.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.egor.smokenet.POJO.Message;
import com.example.egor.smokenet.R;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>
{
    public static final String TAG = MessageListAdapter.class.getSimpleName();
    List<Message> messageList;
    List<Message> messageListReverse;
    private Context context;
    ArrayList<Message> list = new ArrayList<>();


    public MessageListAdapter(Context context, List<Message> messageList, List<Message> reverse) {
        this.context = context;
        this.messageList = messageList;
        this.messageListReverse = reverse;

    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item_message;
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layoutIdForListItem,viewGroup, false);
        MessageViewHolder messageViewHolder = new MessageViewHolder(v);
        return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {

        //Вставить данные в переменные
        messageViewHolder.putMessage(i);
        messageViewHolder.putMessageReciver(i);


    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder
    {

        TextView textViewUserSender;
        TextView textViewUserReciver;
        TextView textViewTextMessageSender;
        TextView textViewTextMessageReciver;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTextMessageSender = itemView.findViewById(R.id.text_sender);
            textViewTextMessageReciver = itemView.findViewById(R.id.text_reciver);
            textViewUserSender = itemView.findViewById(R.id.login_sender);
            textViewUserReciver = itemView.findViewById(R.id.login_reciver);

        }
        public void putMessage(int i)
        {
            Message msg = messageList.get(i);
            textViewUserSender.setText(msg.getUserSender());
            textViewTextMessageSender.setText(msg.getText());
        }
        public void putMessageReciver(int i)
        {
            Message msg = messageListReverse.get(i);
            textViewTextMessageReciver.setText(msg.getUserSender());
            textViewTextMessageReciver.setText(msg.getText());
        }

    }

}

