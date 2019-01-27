package com.example.egor.smokenet;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.egor.smokenet.Database.SQLiteHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogListAdapter extends RecyclerView.Adapter<DialogListAdapter.DialogViewHolder> {
    public static final String TAG = DialogListAdapter.class.getSimpleName();
    private int numberItems;
    private Context context;
    protected List<String> nameList = new ArrayList<>();
    protected List<String> lastMessageList = new ArrayList<>();
    public DialogListAdapter(int numItems,Context context)
    {
        this.numberItems = numItems;
        this.context = context;

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
        String login = randomString(nameList);
        String message = randomString(lastMessageList);
        HashMap<String,String> data = getLogin();
        dialogViewHolder.bind(data.get("login"), message);
    }
    public String randomString(List list)
    {
        String login = "";
        int randInt = (int)(Math.random()*(list.size())+0);
        try
        {
            login = (String)list.get(randInt);
        }catch(Exception e)
        {
            Log.d(TAG,e.toString() + " Произошла ошибка");
        }

        return login;
    }
    public HashMap<String, String> getLogin()
    {
        SQLiteHandler sqliteHandler = new SQLiteHandler(context);
        return sqliteHandler.getUserDetails();



    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    class DialogViewHolder extends RecyclerView.ViewHolder
    {
        public final String TAG = DialogViewHolder.class.getSimpleName();
        ImageView imageViewUserAvatar;
        TextView textViewUserName;
        TextView textViewLastMessage;

        public DialogViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUserAvatar = itemView.findViewById(R.id.user_avatar_item);
            textViewUserName = itemView.findViewById(R.id.user_name_item);
            textViewLastMessage = itemView.findViewById(R.id.last_user_message_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                }
            });
        }
        private void bind(String name, String message)
        {
            textViewUserName.setText(name);
            textViewLastMessage.setText(message);
        }

    }

}