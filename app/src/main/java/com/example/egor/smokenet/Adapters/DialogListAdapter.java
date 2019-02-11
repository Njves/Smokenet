package com.example.egor.smokenet.Adapters;

import android.content.Context;
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
import com.example.egor.smokenet.POJO.Client;
import com.example.egor.smokenet.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogListAdapter extends RecyclerView.Adapter<DialogListAdapter.DialogViewHolder> {
    public static final String TAG = DialogListAdapter.class.getSimpleName();
    private int numberItems;
    private Context context;


    protected List<String> lastMessageList = new ArrayList<>();
    protected List<Client> list;

    public DialogListAdapter(int numItems, Context context, List<Client> list)
    {
        this.numberItems = numItems;
        this.context = context;
        this.list = list;
        Log.d(TAG, list.toString());

    }
    @NonNull
    @Override
    public DialogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item_dialogs;
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layoutIdForListItem,viewGroup, false);
        DialogViewHolder dialogViewHolder = new DialogViewHolder(v);

        return dialogViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DialogViewHolder dialogViewHolder, int i) {
        Client client = list.get(i);
        dialogViewHolder.textViewUserName.setText(client.getLogin());
        dialogViewHolder.textViewLastMessage.setText(client.getEmail());
    }

    public HashMap<String, String> getLogin()
    {
        SQLiteHandler sqliteHandler = new SQLiteHandler(context);
        return sqliteHandler.getUserDetails();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class DialogViewHolder extends RecyclerView.ViewHolder
    {

        public final String TAG = DialogViewHolder.class.getSimpleName();
        DialogHolderListener listener;
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

                    Toast.makeText(context, pos + " " + textViewLastMessage.getText().toString(), Toast.LENGTH_SHORT).show();
                    Client client = list.get(pos);
                    listener = (DialogHolderListener) context;

                    listener.getClient(client);

                }
            });
        }
        private void bind()
        {


        }


    }
    public interface DialogHolderListener
    {
        public void getClient(Client client);
    }
}
