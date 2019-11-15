package edu.temple.bookcase;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class BookDetailsFragment extends Fragment {
    private static final String BOOK_KEY = "bookKey";
    private Book book = new Book();
    private TextView idTextView;
    private TextView titleTextView;
    private TextView authorTextView;
    private TextView publishedTextView;
    private TextView coverURLTextView;

    public BookDetailsFragment() {
        // Required empty public constructor
    }

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(BOOK_KEY, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(BOOK_KEY);
            //book = new Book(getArguments().getInt(BOOKCASE_KEY), getArguments().getString(BOOKCASE_KEY),
            //        getArguments().getString(BOOKCASE_KEY), getArguments().getInt(BOOKCASE_KEY),
            //        getArguments().getString(BOOKCASE_KEY));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_details, container, false);
        idTextView = v.findViewById(R.id.bookIdTextView);
        titleTextView = v.findViewById(R.id.bookTitleTextView);
        authorTextView = v.findViewById(R.id.bookAuthorTextView);
        publishedTextView = v.findViewById(R.id.bookPublishedTextView);
        coverURLTextView = v.findViewById(R.id.bookCoverURLTextView);

        idTextView.setText(book.getId());
        titleTextView.setText(book.getTitle());
        authorTextView.setText(book.getAuthor());
        publishedTextView.setText(book.getPublished());
        coverURLTextView.setText(book.getCoverURL());
        return v;
    }



    public void setTextView(Book book) {
        idTextView.setText(book.getId());
        titleTextView.setText(book.getTitle());
        authorTextView.setText(book.getAuthor());
        publishedTextView.setText(book.getPublished());
        coverURLTextView.setText(book.getCoverURL());
    }
}
