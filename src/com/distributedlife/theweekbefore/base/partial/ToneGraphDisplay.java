package com.distributedlife.theweekbefore.base.partial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import com.distributedlife.language.ipa.WordGuide;
import com.distributedlife.theweekbefore.base.R;

public class ToneGraphDisplay implements SurfaceHolder.Callback {
    private WordGuide wordGuide;
    private Context owner;

    public ToneGraphDisplay(WordGuide wordGuide, Context owner) {
        this.wordGuide = wordGuide;
        this.owner = owner;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        tryDrawing(surfaceHolder);
    }

    public void tryDrawing(SurfaceHolder surfaceHolder) {
        Canvas canvas = surfaceHolder.lockCanvas();

        if (canvas != null) {
            drawMyStuff(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void drawMyStuff(Canvas canvas) {
        float markerSize = 10.0f;

        drawMarkers(canvas, markerSize);

        Paint paint = new Paint();
        paint.setColor(owner.getResources().getColor(R.color.key));
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

    public void drawMarkers(Canvas canvas, float markerSize) {
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

    public void drawMarker(Canvas canvas, float markerSize, float top) {
        Paint paint = new Paint();
        paint.setStrokeWidth(4.0f);
        paint.setColor(owner.getResources().getColor(R.color.marker));

        float leftEdge = 0.0f;
        float rightEdge = canvas.getWidth();
        float rightHandSizeMarkerStart = rightEdge - markerSize;

        canvas.drawLine(leftEdge, top, markerSize, top, paint);
        canvas.drawLine(rightEdge, top, rightHandSizeMarkerStart, top, paint);
    }

    public float calculateY(WordGuide.Tones tone, int height) {
        switch (tone) {
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