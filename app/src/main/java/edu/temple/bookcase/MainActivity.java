package edu.temple.bookcase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookListFragment.MainactivityInterface {

    BookDetailsFragment bookDetailsFragment;
    BookListFragment bookListFragment;
    ViewPagerFragment viewPagerFragment;
    Book[] bookTitles;
    ArrayList<Book> bookShelf;

    //Book book = new Book(1, "The Book", "The Authur", 2019, "url.url");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookTitles = getResources().getStringArray(R.array.bookCase);
        bookDetailsFragment = BookDetailsFragment.newInstance(bookTitles);
        bookListFragment = BookListFragment.newInstance(bookTitles);
        viewPagerFragment = ViewPagerFragment.newInstance(bookTitles);
        if (findViewById(R.id.frame2) != null){
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame1, bookListFragment).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.frame2, bookDetailsFragment).commit();
        } else {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame1, viewPagerFragment).commit();
        }
    }

    public void selectedBook(Book book) {
        bookDetailsFragment.setTextView(book);
    }
}
