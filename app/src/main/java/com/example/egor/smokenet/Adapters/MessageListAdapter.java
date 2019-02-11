package com.example.egor.smokenet.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.egor.smokenet.R;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>
{
    public static final String TAG = MessageListAdapter.class.getSimpleName();
    List<String> messageList;
    private Context context;

    public MessageListAdapter(Context context) {
        this.context = context;

        messageList = new ArrayList<>();
        messageList.add("Привет!");
        messageList.add("Привет) как дела?");
        messageList.add("Да ничего, в школе сижу, а ты где?");
        messageList.add("Я сегодня в больницу поехал, поэтому дома остался");
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



        if(i%2==0)
        {
            messageViewHolder.textViewUserSender.setText("Егор");
            messageViewHolder.textViewUserSender.setText(messageList.get(i));
        }
        else
        {
            messageViewHolder.textViewUserReciver.setText("Никита");
            messageViewHolder.textViewUserReciver.setText(messageList.get(i));
        }
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
    }
}

