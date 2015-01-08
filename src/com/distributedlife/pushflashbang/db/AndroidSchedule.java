package com.distributedlife.pushflashbang.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.distributedlife.pushflashbang.engine.api.Review;
import com.distributedlife.pushflashbang.engine.catalogue.Catalogue;
import com.distributedlife.pushflashbang.engine.db.TheSchedule;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AndroidSchedule<T> extends SQLiteOpenHelper implements TheSchedule<T> {
    private static final String NAME = "schedule";
    private static final String ID = "id" ;
    private static final String WHAT = "what" ;
    private static final String DUE = "due" ;
    private static final String INTERVAL = "interval" ;
    private static final String FIRST = "first";
    private static final String[] ALL_COLUMNS = new String[] {ID, WHAT, DUE, INTERVAL, FIRST};
    private static final int VERSION = 1;
    public static final String PATTERN = "YYYY-MM-DD HH:mm:ss.SSSZ";
    private Catalogue<T> catalogue;

    public AndroidSchedule(Context context, Catalogue<T> catalogue) {
        super(context, NAME, null, VERSION);
        this.catalogue = catalogue;
    }

    @Override
    public Review<T> getNext() {
        List<Review<T>> reviews = getAll();
        Collections.sort(reviews, new ReviewComparator());

        for(Review<T> review : reviews) {
            if (review.getDue().isBeforeNow()) {
                return review;
            }
        }

        return null;
    }

    @Override
    public boolean isEmpty() {
        return getAll().isEmpty();
    }

    @Override
    public void add(Review<T> review) {
        getWritableDatabase().execSQL(insert(
                String.valueOf(review.getItem().hashCode()),
                review.getDue(),
                review.getInterval())
        );
    }

    @Override
    public void update(Review<T> review) {
        DateTimeParser parser = DateTimeFormat.forPattern(PATTERN).getParser();
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(parser).toFormatter();

        String sql = String.format("UPDATE %s SET what = '%s', due = '%s', interval = %d, first = 0 WHERE id = %d;",
                NAME,
                review.getItem().hashCode(),
                review.getDue().toString(formatter),
                review.getInterval(),
                review.getId()
        );
        getWritableDatabase().execSQL(sql);
    }

    @Override
    public boolean alreadyIncludes(T item) {
        for(Review review : getAll()) {
            if (item.equals(review.getItem())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public DateTime getTimeOfNextReview() {
        List<Review<T>> reviews = getAll();
        Collections.sort(reviews, new ReviewComparator());

        return reviews.get(0).getDue();
    }

    private List<Review<T>> getAll() {
        List<Review<T>> schedule = new ArrayList<Review<T>>();

        Cursor cursor = getReadableDatabase().query(NAME, ALL_COLUMNS, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            schedule.add(mapToReview(cursor));

            cursor.moveToNext();
        }

        cursor.close();
        return schedule;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(create());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {}

    private static String create() {
        String[] columns = new String[] {
                "id INTEGER PRIMARY KEY AUTOINCREMENT, what TEXT NOT NULL",
                "due TEXT NOT NULL",
                "interval INTEGER NOT NULL, first INTEGER NOT NULL"
        };

        return "CREATE TABLE" + " " + NAME + " " + "(" + StringUtils.join(columns, ", ") + ")" + ";";
    }

    private String insert(String what, DateTime due, Integer interval) {
        DateTimeParser parser = DateTimeFormat.forPattern(PATTERN).getParser();
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(parser).toFormatter();

        String[] columns = new String[] {"what", "due", "interval", "first"};

        return String.format("INSERT INTO %s (%s) VALUES ('%s', '%s', %d, 1);", NAME, StringUtils.join(columns, ", "), what, due.toString(formatter), interval);
    }

    private Review<T> mapToReview(Cursor cursor) {
        DateTimeParser parser = DateTimeFormat.forPattern(PATTERN).getParser();
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(parser).toFormatter();

        return new Review<T>(
                cursor.getInt(0),
                catalogue.find(cursor.getInt(1)),
                DateTime.parse(cursor.getString(2), formatter),
                cursor.getInt(3),
                cursor.getInt(4)
        );
    }

    private class ReviewComparator implements Comparator<Review<T>> {
        @Override
        public int compare(Review<T> review, Review<T> review2) {
            return review.getDue().compareTo(review2.getDue());
        }
    }
}