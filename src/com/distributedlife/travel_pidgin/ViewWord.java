package com.distributedlife.travel_pidgin;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.distributedlife.language.ipa.CharacterGuide;
import com.distributedlife.language.ipa.Ipa;
import com.distributedlife.language.ipa.WordGuide;

public class ViewWord extends Activity implements SurfaceHolder.Callback {

    private WordGuide wordGuide;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_word);

        String word = getIntent().getStringExtra("word");
        String meaning = getIntent().getStringExtra("meaning");
        String whereSpoken = getIntent().getStringExtra("variation");
        String ipa = getIntent().getStringExtra("ipa");

        ((TextView) findViewById(R.id.word)).setText(word);
        ((TextView) findViewById(R.id.meaning)).setText(meaning);
        ((TextView) findViewById(R.id.where)).setText(String.format("(%s)", whereSpoken));

        Typeface font = Typeface.createFromAsset(getAssets(), "gentium_plus_l.ttf");
        TextView ipaDisplay = (TextView) findViewById(R.id.ipa);
        ipaDisplay.setText(ipa);
        ipaDisplay.setTypeface(font);

        wordGuide = Ipa.getHelpText(ipa);
        displayPronunciationGuide(wordGuide);

        displayToneGraph();
    }

    private void displayToneGraph() {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.tones);
        surfaceView.getHolder().addCallback(this);
    }

    private void displayPronunciationGuide(WordGuide wordGuide) {
        LinearLayout guidance = (LinearLayout) findViewById(R.id.guidance);
        for (CharacterGuide characterGuide : wordGuide.getCharacterGuides()) {
            String guide = characterGuide.getWord();

            String beforeKey;
            String key;
            String afterKey ;

            int start = guide.indexOf("(");
            int end = guide.indexOf(")");

            beforeKey = guide.substring(0, start);
            key = guide.substring(start + 1, end);
            afterKey = guide.substring(end + 1, guide.length());

            int wordSpacing = 10;
            int offsetBefore = wordSpacing;
            int offsetAfter = 0;

            if (!beforeKey.isEmpty()) {
                TextView before = new TextView(this);
                before.setTextSize(36f);
                before.setPadding(offsetBefore, 0, 0, 0);
                before.setText(beforeKey);
                before.setSingleLine(false);
                guidance.addView(before);

                offsetBefore = 0;
            }

            TextView middle = new TextView(this);
            middle.setTextSize(36f);
            if (afterKey.isEmpty()) {
                offsetAfter = wordSpacing;
            }
            middle.setPadding(offsetBefore, 0, offsetAfter, 0);
            middle.setText(key);
            middle.setTextColor(getResources().getColor(R.color.key));
            middle.setSingleLine(false);
            guidance.addView(middle);

            if (!afterKey.isEmpty()) {
                TextView after = new TextView(this);
                after.setTextSize(36f);
                after.setPadding(0, 0, offsetAfter, 0);
                after.setText(afterKey);
                after.setSingleLine(false);

                guidance.addView(after);
            }
        }

        ((TextView) findViewById(R.id.guide_collapsed)).setText(wordGuide.getPhoneticCompressed());
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        tryDrawing(surfaceHolder);
    }

    private void tryDrawing(SurfaceHolder surfaceHolder) {
        Canvas canvas = surfaceHolder.lockCanvas();

        if (canvas != null) {
            drawMyStuff(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawMyStuff(Canvas canvas) {
        float markerSize = 10.0f;

        drawMarkers(canvas, markerSize);

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.key));
        paint.setStrokeWidth(4.0f);


        int numberOfBreaks = 0;
        for (int i = 0; i < wordGuide.getTones().size(); i++) {
            if (wordGuide.getTone(i).equals(WordGuide.Tones.Break)) {
                numberOfBreaks++;
            }
        }

        int numberOfTones = wordGuide.getTones().size() - 1;
        float segmentLength = canvas.getWidth() / (numberOfTones - (numberOfBreaks * 2));
        float offsetX = 0;
        for (int i = 0; i < numberOfTones; i++) {
            if (wordGuide.getTone(i).equals(WordGuide.Tones.Break)) {
                continue;
            }
            if (wordGuide.getTone(i + 1).equals(WordGuide.Tones.Break)) {
                continue;
            }

            float startX = offsetX + markerSize;
            float stopX = startX + segmentLength - (markerSize * 2.0f);

            offsetX += segmentLength;

            WordGuide.Tones startTone = wordGuide.getTone(i);
            WordGuide.Tones stopTone = wordGuide.getTone(i + 1);

            float startY = calculateY(startTone, canvas.getHeight());
            float stopY = calculateY(stopTone, canvas.getHeight());

            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }
    }

    private void drawMarkers(Canvas canvas, float markerSize) {
        float bottom = canvas.getHeight() - 1;

        float halfHeight = bottom / 2.0f;
        float quarterHeight = bottom / 4.0f;
        float top = 1.0f;

        drawMarker(canvas, markerSize, top);
        drawMarker(canvas, markerSize, quarterHeight);
        drawMarker(canvas, markerSize, halfHeight);
        drawMarker(canvas, markerSize, bottom - quarterHeight);
        drawMarker(canvas, markerSize, bottom);
    }

    private void drawMarker(Canvas canvas, float markerSize, float top) {
        Paint paint = new Paint();
        paint.setStrokeWidth(4.0f);
        paint.setColor(getResources().getColor(R.color.marker));

        float leftEdge = 0.0f;
        float rightEdge = canvas.getWidth();
        float rightHandSizeMarkerStart = rightEdge - markerSize;

        canvas.drawLine(leftEdge, top, markerSize, top, paint);
        canvas.drawLine(rightEdge, top, rightHandSizeMarkerStart, top, paint);
    }

    private float calculateY(WordGuide.Tones tone, int height) {
        switch(tone) {
            case High:
                return 0;
            case MidHigh:
                return height * 0.25f;
            case Mid:
                return height * 0.5f;
            case LowMid:
                return height * 0.75f;
            case Low:
                return height;
            default:
                return 0;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        tryDrawing(surfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }
}
