package com.distributedlife.theweekbefore.base.listadapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.distributedlife.theweekbefore.base.R;
import com.distributedlife.theweekbefore.base.clickactions.OpenWikiPage;

import java.util.List;

public class PagesAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> files;
    private final Activity owner;

    public PagesAdapter(Context context, List<String> files, Activity owner) {
        super(context, R.id.place_to_put_list, files);
        this.context = context;
        this.files = files;
        this.owner = owner;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String page = files.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.wiki_pages_list , parent, false);

        rowView.setOnClickListener(new OpenWikiPage(page, owner));

        ((TextView) rowView.findViewById(R.id.label)).setText(prettifyPageName(page));

        return rowView;
    }

    private String prettifyPageName(String page) {
        return page.replaceAll("-", " ").replace(".md", "");
    }
}
