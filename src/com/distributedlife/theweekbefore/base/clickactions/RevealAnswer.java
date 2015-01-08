package com.distributedlife.theweekbefore.base.clickactions;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.distributedlife.theweekbefore.base.Revealed;

public class RevealAnswer implements View.OnClickListener {
    private Activity owner;

    public RevealAnswer(Activity owner) {
        this.owner = owner;
    }

    @Override
    public void onClick(View view) {
        owner.startActivity(new Intent(view.getContext(), Revealed.class));
        owner.finish();
    }
}