package edu.temple.bookcase;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BookAdapter extends BaseAdapter {

    Context context;
    String[] bookCase;

    public BookAdapter(Context context, String[] bookCase) {
        this.context = context;
        this.bookCase = bookCase;
    }

    @Override
    public int getCount() {
        return this.bookCase.length;
    }

    @Override
    public Object getItem(int position) {
        Book book = new Book(bookCase[position]);
        return book;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView instanceof TextView) {
            textView = (TextView) convertView;
        } else {
            textView = new TextView(context);
        }
        textView.setText(bookCase[position]);
        textView.setTextSize(40);
        textView.setTextColor(Color.GRAY);
        return textView;
    }

}
