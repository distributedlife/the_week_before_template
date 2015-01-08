package com.distributedlife.theweekbefore.base;

import android.os.Bundle;
import android.widget.TextView;
import com.distributedlife.theweekbefore.base.abstractions.PushFlashBangActivity;
import com.distributedlife.theweekbefore.base.clickactions.AndWeAreDone;
import com.distributedlife.theweekbefore.base.R;
import org.joda.time.DateTime;
import org.joda.time.Period;

public class Done extends PushFlashBangActivity {
    protected static final int SECONDS_IN_MINUTE = 60;
    protected static final int SECONDS_IN_HOUR = 3600;
    protected static final int SECONDS_IN_DAY = 86400;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.done);

        setupButtonEvents();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (schedule.getTimeOfNextReview().isBeforeNow()) {
            returnToReview();
        } else {
            updateDoneMessage();
        }
    }

    private void setupButtonEvents() {
        findViewById(R.id.reveal).setOnClickListener(new AndWeAreDone(this));
    }

    private void updateDoneMessage() {
        ((TextView) this.findViewById(R.id.done_notice)).setText(getUpdatedDoneMessage());
    }

    private String getUpdatedDoneMessage() {
        return this.getString(R.string.done_notice)
                .replace("TIME", getTimeFromNowHumanised(schedule.getTimeOfNextReview()));
    }

    private String getTimeFromNowHumanised(DateTime timeOfNextReview) {
        Period fromNow = new Period(DateTime.now(), timeOfNextReview);

        Integer seconds = fromNow.getSeconds() +
                (fromNow.getMinutes() * SECONDS_IN_MINUTE) +
                (fromNow.getHours() * SECONDS_IN_HOUR) +
                (fromNow.getDays() * SECONDS_IN_DAY);

        String timeMessage;
        if (seconds < SECONDS_IN_MINUTE) {
            timeMessage = "less than a minute";
        } else if (seconds < SECONDS_IN_MINUTE * 2) {
            timeMessage = "about one minute";
        } else if (seconds >= SECONDS_IN_MINUTE && seconds < SECONDS_IN_HOUR) {
            timeMessage = String.format("about %d minutes", seconds / SECONDS_IN_MINUTE);
        } else if (seconds >= SECONDS_IN_HOUR && seconds < (SECONDS_IN_HOUR * 1.5)) {
            timeMessage = "about an hour";
        } else if (seconds >= (SECONDS_IN_HOUR * 1.5) && seconds < SECONDS_IN_DAY) {
            timeMessage = String.format("about %d hours", seconds / SECONDS_IN_HOUR);
        } else if (seconds >= SECONDS_IN_DAY && seconds < (SECONDS_IN_DAY * 1.5)) {
            timeMessage = "about a day";
        } else {
            timeMessage = String.format("in %d days", seconds / SECONDS_IN_DAY);
        }
        return timeMessage;
    }
}