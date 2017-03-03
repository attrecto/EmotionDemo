package com.android.attrecto.emotiondemo.ui;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.attrecto.emotiondemo.R;
import com.android.attrecto.emotiondemo.object.EmojiAdapter;

/**
 * Created by Somogyi Bence on 2017.03.03..
 */

public class EmojiInfoDialog extends BaseCustomDialog {

    RecyclerView mList;


    public EmojiInfoDialog(Context context) {

        super(context);
    }

    @Override
    protected int getLayoutResId() {

        return R.layout.dialog_emoji_info;
    }

    @Override
    protected void acquireReferences() {

        mList = (RecyclerView) findViewById(R.id.app_info_list);

    }

    @Override
    protected void setListeners() {

        rootView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                dismiss();
            }
        });

        mList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                dismiss();
            }
        });
    }

    @Override
    protected void onAfterAll() {

        mList.setLayoutManager(new LinearLayoutManager(getContext()));

        mList.setAdapter(new EmojiAdapter());

    }
}
