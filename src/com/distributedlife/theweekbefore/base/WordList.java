package com.distributedlife.theweekbefore.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.distributedlife.language.ipa.Ipa;
import com.distributedlife.language.ipa.Word;
import com.distributedlife.theweekbefore.base.abstractions.StaticDictionaryFilenameStore;
import com.distributedlife.theweekbefore.base.listadapters.WordAdapter;

import java.util.ArrayList;
import java.util.List;

public class WordList extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_things);

        String categoryToDisplay = getIntent().getStringExtra("category");

        List<Word> dictionary = Ipa.loadDictionary(StaticDictionaryFilenameStore.getFilename());
        List<Word> filtered = filterWordsByCategory(dictionary, categoryToDisplay);

        ((ListView) findViewById(R.id.place_to_put_list)).setAdapter(new WordAdapter(this, filtered, this));
    }

    private List<Word> filterWordsByCategory(List<Word> words, String categoryToDisplay) {
        List<Word> filtered = new ArrayList<Word>();
        for (Word word : words) {
            if (word.getCategory().equals(categoryToDisplay)) {
                filtered.add(word);
            }
        }
        return filtered;
    }
}
