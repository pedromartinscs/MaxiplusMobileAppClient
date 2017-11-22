package br.com.nancode.maxiplusmobileappclient;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.nancode.maxiplusmobileappclient.CircleImageView.CircleImageView;

/**
 * Created by martins on 25/10/2017.
 */

public class ChatAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ChatAdapter(Context context, String[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.message_item, parent, false);
        TextView message = (TextView) rowView.findViewById(R.id.messageTextView);
        TextView sender = (TextView) rowView.findViewById(R.id.senderTextView);
        ImageView leftImage = (ImageView) rowView.findViewById(R.id.leftMessagePic);
        ImageView rightImage = (ImageView) rowView.findViewById(R.id.rightMessagePic);
        LinearLayout messageLine = (LinearLayout) rowView.findViewById(R.id.messageLine);
        message.setText(values[position].replace("&", ""));
        //verifica origem da mensagem através do símbolo &
        String s = values[position];
        if (s.startsWith("&")) {
            leftImage.setImageResource(R.mipmap.ic_launcher);
            rightImage.setVisibility(View.GONE);
            leftImage.setVisibility(View.VISIBLE);
            sender.setText("Equipe Maxiplus");
            messageLine.setGravity(Gravity.LEFT);
        } else {
            CircleImageView CIV = (CircleImageView) rowView.findViewById(R.id.CircleImageViewUserPhoto);
            rightImage.setImageBitmap(((BitmapDrawable)CIV.getDrawable()).getBitmap());
            leftImage.setVisibility(View.GONE);
            rightImage.setVisibility(View.VISIBLE);
            sender.setText("Eu");
            messageLine.setGravity(Gravity.RIGHT);
        }

        return rowView;
    }
}
