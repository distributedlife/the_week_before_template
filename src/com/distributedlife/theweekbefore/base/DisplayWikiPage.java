package com.distributedlife.theweekbefore.base;

import android.app.Activity;
import android.os.Bundle;
import us.feras.mdv.MarkdownView;

public class DisplayWikiPage extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MarkdownView markdownView = new MarkdownView(this);
        setContentView(markdownView);

        String filename = getIntent().getStringExtra("filename");
        markdownView.loadMarkdownUrl(String.format("file:///android_asset/pages/%s", filename));
    }
}
