package com.distributedlife.theweekbefore.base;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.distributedlife.language.ipa.Ipa;
import com.distributedlife.language.ipa.Word;
import com.distributedlife.language.search.WordMatcher;
import com.distributedlife.theweekbefore.base.abstractions.StaticDictionaryFilenameStore;
import com.distributedlife.theweekbefore.base.listadapters.WordAdapter;

import java.util.List;

public class FindWords extends Activity {
    private List<Word> dictionary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_words);

        dictionary = Ipa.loadDictionary(StaticDictionaryFilenameStore.getFilename());

        setupSearchControl(this);

        ((ListView) findViewById(R.id.place_to_put_list)).setAdapter(new WordAdapter(this, dictionary, this));
    }

    private void setupSearchControl(final Activity parent) {
        EditText search = (EditText) findViewById(R.id.search);
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                ((ListView) findViewById(R.id.place_to_put_list)).setAdapter(
                        new WordAdapter(parent, filterWords(String.valueOf(((TextView) view).getText())), parent)
                );

                return false;
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ((ListView) findViewById(R.id.place_to_put_list)).setAdapter(
                        new WordAdapter(parent, filterWords(String.valueOf(charSequence)), parent)
                );
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private List<Word> filterWords(String text) {
        return WordMatcher.search(text, dictionary);
    }
}
