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
    private Context context;
    ArrayList<String> list = new ArrayList<>();


    public MessageListAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
        list.add("Привет");
        list.add("Привет!");
        list.add("Как дела?");
        list.add("Отлично, а у тебя?");
        list.add("Классно, скоро пойду на автобус в магазин");
        list.add("Понятно, а что брать собираешься?");
        list.add("Одежду, там сейчас скидки");
        list.add("Скидки? Может и мне тогда съездить, ты не против?");
        list.add("Не против, супер)");
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


        for (int j = 0; j < i; j++) {
            if(i%2==0) {
                messageViewHolder.textViewUserSender.setText("Njves");
                messageViewHolder.textViewTextMessageSender.setText(list.get(i));
            }
            else
            {
                messageViewHolder.textViewUserReciver.setText("Egor");
                messageViewHolder.textViewTextMessageReciver.setText(list.get(i));
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
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

