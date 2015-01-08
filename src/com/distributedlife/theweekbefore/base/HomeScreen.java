package com.distributedlife.theweekbefore.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        findViewById(R.id.learn).setOnClickListener(new WikiPageList(this));
        findViewById(R.id.dictionary).setOnClickListener(new OpenMenu(this));
        findViewById(R.id.practice).setOnClickListener(new StartPractice(this));
    }

    private class OpenMenu implements View.OnClickListener {
        private Context owner;

        public OpenMenu(Activity owner) {
            this.owner = owner;
        }

        @Override
        public void onClick(View view) {
            startActivity(new Intent(owner, DictionaryMenu.class));
        }
    }

    private class StartPractice implements View.OnClickListener {
        private Context owner;

        public StartPractice(Activity owner) {
            this.owner = owner;
        }

        @Override
        public void onClick(View view) {
            startActivity(new Intent(owner, ReviewWord.class));
        }
    }

    private class WikiPageList implements View.OnClickListener {
        private Context owner;

        public WikiPageList(Activity owner) {
            this.owner = owner;
        }

        @Override
        public void onClick(View view) {
            startActivity(new Intent(owner, ListWikiPages.class));
        }
    }
}
