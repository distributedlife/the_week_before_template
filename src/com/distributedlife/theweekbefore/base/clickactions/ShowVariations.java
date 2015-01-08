package com.distributedlife.theweekbefore.base.clickactions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.distributedlife.language.ipa.Word;
import com.distributedlife.theweekbefore.base.ViewWord;

public class ShowVariations implements View.OnClickListener {
    private final Word word;
    private final Context owner;

    public ShowVariations(Word word, Activity owner) {
        this.word = word;
        this.owner = owner;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(owner, ViewWord.class);
        intent.putExtra("word", word.getWord());
        intent.putExtra("meaning", word.getMeaning());
        intent.putExtra("ipa", word.getIpa());

        owner.startActivity(intent);
    }
}