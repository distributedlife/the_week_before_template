package com.distributedlife.theweekbefore.base.abstractions;

import android.os.Bundle;

public abstract class ShowCardActivity extends PushFlashBangActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract void setup();
}