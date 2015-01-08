package com.distributedlife.theweekbefore.base.listadapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.distributedlife.language.ipa.Word;
import com.distributedlife.theweekbefore.base.clickactions.ShowVariations;
import com.distributedlife.theweekbefore.base.R;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {
    private final Context context;
    private final List<Word> words;
    private final Activity owner;

    public WordAdapter(Context context, List<Word> words, Activity owner) {
        super(context, R.id.place_to_put_list, words);
        this.context = context;
        this.words = words;
        this.owner = owner;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Word word = words.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.search_results , parent, false);

        rowView.setOnClickListener(new ShowVariations(word, owner));

        ((TextView) rowView.findViewById(R.id.word)).setText(word.getWord());
        ((TextView) rowView.findViewById(R.id.meaning)).setText(word.getMeaning());

        return rowView;
    }
}
