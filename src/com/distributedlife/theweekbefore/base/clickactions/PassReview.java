package com.distributedlife.theweekbefore.base.clickactions;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.distributedlife.language.ipa.Word;
import com.distributedlife.pushflashbang.engine.api.Intervals;
import com.distributedlife.pushflashbang.engine.api.Review;
import com.distributedlife.pushflashbang.engine.db.TheSchedule;
import com.distributedlife.pushflashbang.engine.review_behaviours.SuccessfulReview;
import com.distributedlife.theweekbefore.base.ReviewWord;

public class PassReview implements View.OnClickListener {
    private Activity activity;
    private Intervals intervals;
    private TheSchedule<Word> schedule;
    private Review<Word> currentReview;

    public PassReview(Activity activity, Intervals intervals, TheSchedule<Word> schedule, Review<Word> currentReview) {
        this.activity = activity;
        this.intervals = intervals;
        this.schedule = schedule;
        this.currentReview = currentReview;
    }

    @Override
    public void onClick(View view) {
        new SuccessfulReview<Word>(intervals, schedule, currentReview);
        activity.startActivity(new Intent(activity, ReviewWord.class));
        activity.finish();
    }
}