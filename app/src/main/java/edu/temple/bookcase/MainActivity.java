package edu.temple.bookcase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements BookListFragment.MainactivityInterface {

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
        if (findViewById(R.id.frame1) != null){
            getSupportFragmentManager().beginTransaction().add(R.id.frame1, viewPagerFragment).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.frame2, bookDetailsFragment).commit();
        }
    }

    public void selectedBook(Book book) {
        bookDetailsFragment.setTextView(book);
    }
}
