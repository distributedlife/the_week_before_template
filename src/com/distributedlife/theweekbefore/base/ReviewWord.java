package com.distributedlife.theweekbefore.base;

import android.os.Bundle;
import android.widget.TextView;
import com.distributedlife.language.ipa.Word;
import com.distributedlife.pushflashbang.engine.api.Intervals;
import com.distributedlife.pushflashbang.engine.api.Review;
import com.distributedlife.theweekbefore.base.abstractions.PushFlashBangActivity;
import com.distributedlife.theweekbefore.base.clickactions.RevealAnswer;

public class ReviewWord extends PushFlashBangActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);

        findViewById(R.id.reveal).setOnClickListener(new RevealAnswer(this));

        route();
    }

    @Override
    public void onResume() {
        super.onResume();

        route();
    }

    private void route() {
        Review<Word> review = scheduler.getCurrentReview();

        if (review == null) {
            show(this, Done.class);
            finish();
        } else if (review.isFirst()) {
            show(this, NewCard.class);
            finish();
        } else {
            updateToLatestReview(review);
        }
    }

    private void updateToLatestReview(Review<Word> review) {
        ((TextView) findViewById(R.id.what)).setText(getDisplayText(review));
        ((TextView) findViewById(R.id.hint)).setText(getHintText(review));
    }

    private String getHintText(Review<Word> review) {
        if (Intervals.isReview(review.getInterval())) {
            return getString(R.string.review_hint);
        } else {
            return getString(R.string.translate_hint);
        }
    }

    private String getDisplayText(Review<Word> review) {
        if (Intervals.isReview(review.getInterval())) {
            return review.getItem().getWord();
        } else {
            return review.getItem().getMeaning();
        }
    }
}
