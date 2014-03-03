package com.distributedlife.travel_pidgin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.distributedlife.language.ipa.Ipa;
import com.distributedlife.language.ipa.PronunciationVariation;
import com.distributedlife.language.ipa.Word;

import java.util.ArrayList;
import java.util.List;

public class SelectVariation extends Activity {

    private List<Word> language = Ipa.Cantonese;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_variation);

        String word = getIntent().getStringExtra("word");
        List<Word> variations = getVariations(word, language);

        ListView results = (ListView) findViewById(R.id.variations);
        results.setAdapter(new VariationAdapter(this, variations, this));
    }

    private List<Word> getVariations(String word, List<Word> dictionary) {
        List<Word> variations = new ArrayList<Word>();

        for (Word wordInDictionary : dictionary) {
            if (wordInDictionary.getWord().equals(word)) {
                for (PronunciationVariation pronunciationVariation : wordInDictionary.getPronunciationVariations()) {
                    Word variation = new Word(wordInDictionary.getWord(), wordInDictionary.getMeanings());
                    variation.addPronunciationVariation(pronunciationVariation);

                    variations.add(variation);
                }
            }
        }

        return variations;
    }

    private class VariationAdapter extends ArrayAdapter<Word> {
        private final Context context;
        private final List<Word> variations;
        private final Activity owner;

        public VariationAdapter(Context context, List<Word> variations, Activity owner) {
            super(context, R.id.results, variations);
            this.context = context;
            this.variations = variations;
            this.owner = owner;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Word variation = variations.get(position);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.variation_options, parent, false);

            PronunciationVariation pronunciationVariation = variation.getPronunciationVariations().get(0);
            rowView.setOnClickListener(new ShowWord(variation, pronunciationVariation.getIpa(), pronunciationVariation.getWhere(), owner));

            TextView spotForWord = (TextView) rowView.findViewById(R.id.word);
            spotForWord.setText(variation.getWord());

            TextView spotForMeaning = (TextView) rowView.findViewById(R.id.meaning);
            spotForMeaning.setText(pronunciationVariation.getWhere());

            return rowView;
        }
    }

    private class ShowWord implements View.OnClickListener {
        private final Word word;
        private final String ipa;
        private final String where;
        private final Context owner;

        public ShowWord(Word word, String ipa, String where, Activity owner) {
            this.word = word;
            this.ipa = ipa;
            this.where = where;
            this.owner = owner;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(owner, ViewWord.class);
            intent.putExtra("word", word.getWord());
            intent.putExtra("meaning", word.getMeanings().get(0));
            intent.putExtra("variation", where);
            intent.putExtra("ipa", ipa);

            startActivity(intent);
        }
    }
}
