package com.distributedlife.theweekbefore.base.abstractions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.distributedlife.language.ipa.Ipa;
import com.distributedlife.language.ipa.Word;
import com.distributedlife.pushflashbang.db.AndroidSchedule;
import com.distributedlife.pushflashbang.engine.api.Intervals;
import com.distributedlife.pushflashbang.engine.api.Scheduler;
import com.distributedlife.pushflashbang.engine.catalogue.Catalogue;
import com.distributedlife.pushflashbang.engine.db.TheSchedule;
import com.distributedlife.theweekbefore.base.ReviewWord;

public class PushFlashBangActivity extends Activity {
    protected Intervals intervals = new Intervals();
    protected Scheduler<Word> scheduler;
    protected TheSchedule<Word> schedule;
    protected Catalogue<Word> thingsToLearn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thingsToLearn = new Catalogue<Word>(Ipa.loadDictionary(StaticDictionaryFilenameStore.getFilename()));
        schedule = new AndroidSchedule<Word>(this, thingsToLearn);
        scheduler = new Scheduler<Word>(schedule, thingsToLearn);
    }

    protected void returnToReview() {
        show(this, ReviewWord.class);
        this.finish();
    }

    protected void show(Context context, Class klass) {
        Intent intent = new Intent(context, klass);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ((AndroidSchedule) schedule).close();
    }
}