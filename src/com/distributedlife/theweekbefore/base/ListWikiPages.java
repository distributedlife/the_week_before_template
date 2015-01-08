package com.distributedlife.theweekbefore.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.distributedlife.theweekbefore.base.listadapters.PagesAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListWikiPages extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_things);

        List<String> files = new ArrayList<String>();
        try {
            Collections.addAll(files, getAssets().list("pages"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ((ListView) findViewById(R.id.place_to_put_list)).setAdapter(new PagesAdapter(this, files, this));
    }
}
