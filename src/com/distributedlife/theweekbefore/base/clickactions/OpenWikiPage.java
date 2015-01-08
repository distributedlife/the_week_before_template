package com.distributedlife.theweekbefore.base.clickactions;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.distributedlife.theweekbefore.base.DisplayWikiPage;

public class OpenWikiPage implements View.OnClickListener {
    private final String filename;
    private final Activity owner;

    public OpenWikiPage(String filename, Activity owner) {
        this.filename = filename;
        this.owner = owner;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(owner, DisplayWikiPage.class);
        intent.putExtra("filename", filename);
        owner.startActivity(intent);
    }
}