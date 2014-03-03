package com.distributedlife.travel_pidgin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.distributedlife.language.ipa.Ipa;
import com.distributedlife.language.ipa.Word;
import com.distributedlife.language.search.WordMatcher;

import java.util.List;

public class FindWords extends Activity {

    private List<Word> language = Ipa.Cantonese;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Activity parent = this;

        EditText search = (EditText) findViewById(R.id.search);
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                TextView textView = (TextView) view;

                ListView results = (ListView) findViewById(R.id.results);
                results.setAdapter(new WordAdapter(parent, filterWords(String.valueOf(textView.getText())), parent));

                return false;
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ListView results = (ListView) findViewById(R.id.results);
                results.setAdapter(new WordAdapter(parent, filterWords(String.valueOf(charSequence)), parent));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        ListView results = (ListView) findViewById(R.id.results);
        results.setAdapter(new WordAdapter(parent, language, parent));
    }

    private List<Word> filterWords(String text) {
        return WordMatcher.search(text, language);
    }

    private class ShowVariations implements View.OnClickListener {
        private final Word word;
        private final Context owner;

        public ShowVariations(Word word, Activity owner) {
            this.word = word;
            this.owner = owner;
        }

        @Override
        public void onClick(View view) {
            if (word.getPronunciationVariations().size() == 1) {
                Intent intent = new Intent(owner, ViewWord.class);
                intent.putExtra("word", word.getWord());
                intent.putExtra("meaning", word.getMeanings().get(0));
                intent.putExtra("variation", word.getPronunciationVariations().get(0).getWhere());
                intent.putExtra("ipa", word.getPronunciationVariations().get(0).getIpa());

                startActivity(intent);
            } else {
                Intent intent = new Intent(owner, SelectVariation.class);
                intent.putExtra("word", word.getWord());

                startActivity(intent);
            }

        }
    }

    private class WordAdapter extends ArrayAdapter<Word> {
        private final Context context;
        private final List<Word> words;
        private final Activity owner;

        public WordAdapter(Context context, List<Word> words, Activity owner) {
            super(context, R.id.results, words);
            this.context = context;
            this.words = words;
            this.owner = owner;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Word word = words.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.search_results, parent, false);

            rowView.setOnClickListener(new ShowVariations(word, owner));

            TextView spotForWord = (TextView) rowView.findViewById(R.id.word);
            spotForWord.setText(word.getWord());

            TextView spotForMeaning = (TextView) rowView.findViewById(R.id.meaning);
            spotForMeaning.setText(word.getMeanings().get(0));

            return rowView;
        }
    }
}
