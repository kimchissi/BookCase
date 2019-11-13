package edu.temple.bookcase;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class BookDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String BOOKCASE_KEY = "bookCase";
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
        args.putParcelable(BOOKCASE_KEY, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(BOOKCASE_KEY);
            //book = new Book(getArguments().getInt(BOOKCASE_KEY), getArguments().getString(BOOKCASE_KEY),
            //        getArguments().getString(BOOKCASE_KEY), getArguments().getInt(BOOKCASE_KEY),
            //        getArguments().getString(BOOKCASE_KEY));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
