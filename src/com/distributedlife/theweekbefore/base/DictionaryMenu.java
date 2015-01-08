package com.distributedlife.theweekbefore.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DictionaryMenu extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary_menu);

        findViewById(R.id.manners).setOnClickListener(new StartWordList(this, "manners"));
        findViewById(R.id.salutations).setOnClickListener(new StartWordList(this, "salutations"));
        findViewById(R.id.general).setOnClickListener(new StartWordList(this, "general"));
        findViewById(R.id.food).setOnClickListener(new StartWordList(this, "food"));
        findViewById(R.id.places_and_things).setOnClickListener(new StartWordList(this, "things"));
        findViewById(R.id.numbers).setOnClickListener(new StartWordList(this, "numbers"));
        findViewById(R.id.dates_and_times).setOnClickListener(new StartWordList(this, "time"));
        findViewById(R.id.search).setOnClickListener(new OpenSearch(this));
    }

    private class OpenSearch implements View.OnClickListener {
        private Context owner;

        public OpenSearch(Activity owner) {
            this.owner = owner;
        }

        @Override
        public void onClick(View view) {
            startActivity(new Intent(owner, FindWords.class));
        }
    }

    private class StartWordList implements View.OnClickListener {
        private Context owner;
        private String category;

        public StartWordList(Activity owner, String category) {
            this.owner = owner;
            this.category = category;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(owner, WordList.class);
            intent.putExtra("category", category);
            startActivity(intent);
        }
    }
}
