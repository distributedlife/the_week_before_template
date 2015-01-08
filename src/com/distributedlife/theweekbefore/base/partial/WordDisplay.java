package com.distributedlife.theweekbefore.base.partial;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.SurfaceView;
import android.widget.ListView;
import android.widget.TextView;
import com.distributedlife.language.ipa.Ipa;
import com.distributedlife.language.ipa.Word;

import java.util.ArrayList;
import java.util.List;

public class WordDisplay {

    public static final String IPA_FONT = "gentium_plus_l.ttf";

    public static void displayMeaning(Activity activity, int where, Word word) {
        ((TextView) activity.findViewById(where)).setText(word.getMeaning());
    }

    public static void displayWord(Activity activity, int where, Word word) {
        ((TextView) activity.findViewById(where)).setText(word.getWord());
    }

    public static void displayIpa(Activity activity, int where, Word word) {
        Typeface font = Typeface.createFromAsset(activity.getAssets(), IPA_FONT);
        TextView ipaDisplay = (TextView) activity.findViewById(where);
        ipaDisplay.setText(word.getIpa());
        if (font != null) {
            ipaDisplay.setTypeface(font);
        }
    }

    public static void displayPronunciationGuidance(Activity activity, int where, Word word) {
        if (word.getIpa().isEmpty()) {
            return;
        }

        ((ListView) activity.findViewById(where)).setAdapter(new IpaGuidanceDisplay(
                activity,
                getIpaAsListOfSymbols(word.getIpa()),
                activity,
                Ipa.getHelpText(word.getIpa())
        ));
    }

    private static List<String> getIpaAsListOfSymbols(String ipa) {
        List<String> ipaAsList = new ArrayList<String>();
        for (int c = 0; c < ipa.length(); c++) {
            String ipaSymbol = String.valueOf(ipa.charAt(c));
            if (Ipa.displayable(ipaSymbol)) {
                ipaAsList.add(ipaSymbol);
            }
        }
        return ipaAsList;
    }

    public static void displayToneGraph(Activity activity, int where, Word word) {
        SurfaceView surfaceView = (SurfaceView) activity.findViewById(where);
        surfaceView.getHolder().addCallback(
                new ToneGraphDisplay(Ipa.getHelpText(word.getIpa()), activity)
        );
    }
}
