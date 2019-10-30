package edu.temple.bookcase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    BookDetailsFragment bookDetailsFragment;
    BookListFragment bookListFragment;
    ViewPagerFragment viewPagerFragment;
    String[] bookTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookTitles = getResources().getStringArray(R.array.bookCase);
        bookDetailsFragment = new BookDetailsFragment();
        bookListFragment = BookListFragment.newInstance(bookTitles);
        viewPagerFragment = ViewPagerFragment.newInstance(bookTitles);


    }

    public void selectedBook(Book book) {
        bookDetailsFragment.setTextView(book);
    }
}
