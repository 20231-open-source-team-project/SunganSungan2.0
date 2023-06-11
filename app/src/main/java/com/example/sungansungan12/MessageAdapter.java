package com.example.sungansungan12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.sungansungan12.R;
import java.util.List;
//체팅에사용

public class MessageAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> messages;

    public MessageAdapter(Context context, List<String> messages) {
        super(context, 0, messages);
        this.context = context;
        this.messages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item_message, parent, false);
        }

        String message = messages.get(position);

        TextView messageTextView = itemView.findViewById(R.id.messageTextView);
        messageTextView.setText(message);

        return itemView;
    }
}