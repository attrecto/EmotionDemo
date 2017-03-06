package com.android.attrecto.emotiondemo.object;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.attrecto.emotiondemo.R;
import com.android.attrecto.emotiondemo.ui.widget.RobotoLightTextView;


/**
 * Created by Somogyi Bence on 2017.03.03..
 */

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.EmojiViewHolder> {

    private int[] emojiImages;
    private String[] emojiNames;
    private View.OnClickListener mOnClickListener;

    private int count;

    public EmojiAdapter(View.OnClickListener l) {

        emojiImages = EmojiHelper.getAllEmojiRes();
        emojiNames = EmojiHelper.getAllEmojiNames();

        count = emojiImages.length;

        mOnClickListener = l;

    }

    @Override
    public EmojiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_emoji, null);

        EmojiViewHolder evh = new EmojiViewHolder(itemView, mOnClickListener);

        return evh;
    }

    @Override
    public void onBindViewHolder(EmojiViewHolder holder, int position) {

        holder.emojiImage.setImageResource(emojiImages[position]);

        holder.emojiName.setText(emojiNames[position]);

    }

    @Override
    public int getItemCount() {

        return count;
    }

    public static class EmojiViewHolder extends RecyclerView.ViewHolder {

        ImageView emojiImage;
        RobotoLightTextView emojiName;


        public EmojiViewHolder(View itemView, View.OnClickListener l) {

            super(itemView);

            emojiImage = (ImageView) itemView.findViewById(R.id.emoji_img);
            emojiName = (RobotoLightTextView) itemView.findViewById(R.id.emoji_name);

            itemView.setOnClickListener(l);
        }

    }

}
