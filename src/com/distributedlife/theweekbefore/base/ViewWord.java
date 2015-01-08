package com.distributedlife.theweekbefore.base;

import android.app.Activity;
import android.os.Bundle;
import com.distributedlife.language.ipa.Word;
import com.distributedlife.theweekbefore.base.partial.WordDisplay;

public class ViewWord extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_word);

        String wordString = "";
        if (getIntent().hasExtra("word")) {
            wordString = getIntent().getStringExtra("word");
        }

        String meaning = "";
        if (getIntent().hasExtra("meaning")) {
            meaning = getIntent().getStringExtra("meaning");
        }

        String ipa = "";
        if (getIntent().hasExtra("ipa")) {
            ipa = getIntent().getStringExtra("ipa");
        }

        Word word = new Word(wordString, meaning, ipa);

        WordDisplay.displayWord(this, R.id.word, word);
        WordDisplay.displayMeaning(this, R.id.meaning, word);
        WordDisplay.displayIpa(this, R.id.ipa, word);
        WordDisplay.displayPronunciationGuidance(this, R.id.pronunciation_list, word);
        WordDisplay.displayToneGraph(this, R.id.tones, word);
    }
}