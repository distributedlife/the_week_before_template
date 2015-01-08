package com.distributedlife.theweekbefore.base;

import android.widget.TextView;
import com.distributedlife.theweekbefore.base.clickactions.PassReview;
import com.distributedlife.theweekbefore.base.R;

public class NewCard extends Revealed {
    @Override
    public void setup() {
        setContentView(R.layout.newcard);

        findViewById(R.id.ok).setOnClickListener(
                new PassReview(this, intervals, schedule, scheduler.getCurrentReview())
        );
        ((TextView) findViewById(R.id.header)).setText(R.string.new_item);
    }
}