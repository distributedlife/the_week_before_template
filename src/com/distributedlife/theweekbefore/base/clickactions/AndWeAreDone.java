package com.distributedlife.theweekbefore.base.clickactions;

import android.app.Activity;
import android.view.View;

public class AndWeAreDone implements View.OnClickListener {
    private Activity activity;

    public AndWeAreDone(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        activity.finish();
    }
}
