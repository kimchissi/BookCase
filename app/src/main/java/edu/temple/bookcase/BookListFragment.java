package edu.temple.bookcase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;


public class BookListFragment extends Fragment {

    private static final String BOOKCASE_KEY = "book case";
    private ArrayList<Book> bookShelf;
    private String[] bookCase;
    private MainactivityInterface parent;
    private ListView listView;

    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainactivityInterface) {
            parent = (MainactivityInterface) context;
        } else {
            throw new RuntimeException("Interface not implemented");
        }
    }
    public static BookListFragment newInstance(String[] bookCase) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putStringArray(BOOKCASE_KEY, bookCase);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookCase = getArguments().getStringArray(BOOKCASE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BookAdapter bookAdapter = new BookAdapter(getActivity(), bookCase);
        View v = inflater.inflate(R.layout.fragment_book_list, container, false);
        listView = v.findViewById(R.id.bookCaseListView);
        listView.setAdapter(bookAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                parent.selectedBook((Book) adapterView.getAdapter().getItem(i));
            }
        });
        return v;
    }

    interface MainactivityInterface {
        void selectedBook(Book book);
    }


}
