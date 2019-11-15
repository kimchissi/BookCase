package edu.temple.bookcase;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends BaseAdapter {

    Context context;
    ArrayList<Book> bookShelf;

    public BookAdapter(Context context, ArrayList<Book> bookShelf) {
        this.context = context;
        this.bookShelf = bookShelf;
    }

    @Override
    public int getCount() {
        return this.bookShelf.size();
    }

    @Override
    public Object getItem(int position) {
        Book book = new Book(bookShelf.get(position).getId(),
                bookShelf.get(position).getTitle(),
                bookShelf.get(position).getAuthor(),
                bookShelf.get(position).getPublished(),
                bookShelf.get(position).getCoverURL());
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
        textView.setText(bookShelf.get(position).getTitle());
        textView.setTextSize(40);
        textView.setTextColor(Color.GRAY);
        return textView;
    }

}
