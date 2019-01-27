package com.example.egor.smokenet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DialogListAdapter extends RecyclerView.Adapter<DialogListAdapter.DialogViewHolder> {
    private int numberItems;
    protected List<String> nameList = new ArrayList<>();
    protected List<String> lastMessageList = new ArrayList<>();
    public DialogListAdapter(int numItems)
    {
        this.numberItems = numItems;
        nameList.add("Njves");
        nameList.add("Egor");
        nameList.add("Prikol");
        nameList.add("Daun");

        lastMessageList.add("Привет");
        lastMessageList.add("Как дела?");
        lastMessageList.add("Даун?");



    }
    @NonNull
    @Override
    public DialogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item_dialogs;
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layoutIdForListItem,viewGroup, false);
        DialogViewHolder dialogViewHolder = new DialogViewHolder(v);
        dialogViewHolder.textViewUserName.setText(nameList.get(1));
        return dialogViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DialogViewHolder dialogViewHolder, int i) {
        dialogViewHolder.bind(nameList.get(2), lastMessageList.get(2));
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    class DialogViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageViewUserAvatar;
        TextView textViewUserName;
        TextView textViewLastMessage;

        public DialogViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUserAvatar = itemView.findViewById(R.id.user_avatar_item);
            textViewUserName = itemView.findViewById(R.id.user_name_item);
            textViewLastMessage = itemView.findViewById(R.id.last_user_message_item);

        }
        private void bind(String name, String message)
        {
            textViewUserName.setText(name);
            textViewLastMessage.setText(message);
        }
    }
}
