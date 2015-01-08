package com.distributedlife.theweekbefore.base;

import android.os.Bundle;
import com.distributedlife.language.ipa.Word;
import com.distributedlife.pushflashbang.engine.api.Review;
import com.distributedlife.theweekbefore.base.abstractions.ShowCardActivity;
import com.distributedlife.theweekbefore.base.clickactions.FailReview;
import com.distributedlife.theweekbefore.base.clickactions.PassReview;
import com.distributedlife.theweekbefore.base.partial.WordDisplay;
import com.distributedlife.theweekbefore.base.R;

public class Revealed extends ShowCardActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setup();

        Review<Word> review = scheduler.getCurrentReview();
        if (review == null) {
            finish();
        } else {
            WordDisplay.displayWord(this, R.id.what, review.getItem());
            WordDisplay.displayMeaning(this, R.id.meaning, review.getItem());
            WordDisplay.displayIpa(this, R.id.ipa, review.getItem());
            WordDisplay.displayPronunciationGuidance(this, R.id.pronunciation_list, review.getItem());
        }
    }

    @Override
    protected void setup() {
        setContentView(R.layout.revealed);

        findViewById(R.id.fail).setOnClickListener(
                new FailReview(this, intervals, schedule, scheduler.getCurrentReview())
        );
        findViewById(R.id.pass).setOnClickListener(
                new PassReview(this, intervals, schedule, scheduler.getCurrentReview())
        );
    }
}