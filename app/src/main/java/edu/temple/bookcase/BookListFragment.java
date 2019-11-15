package edu.temple.bookcase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;


public class BookListFragment extends Fragment {

    private static final String BOOKSHELF_KEY = "bookShelf";
    private ArrayList<Book> bookShelf;
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
    public static BookListFragment newInstance(ArrayList<Book> bookShelf) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(BOOKSHELF_KEY, bookShelf);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookShelf = getArguments().getParcelableArrayList(BOOKSHELF_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BookAdapter bookAdapter = new BookAdapter(getActivity(), bookShelf);
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
