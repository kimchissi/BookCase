package edu.temple.bookcase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;



public class ViewPagerFragment extends Fragment {
    private static final String BOOKSHELF_KEY = "bookShelf";
    private ArrayList<Book> bookShelf;
    private ArrayList<BookDetailsFragment> detailsFragmentArrayList;
    private ViewPager viewPager;

    public ViewPagerFragment() {
        // Required empty public constructor
    }

    public static ViewPagerFragment newInstance(ArrayList<Book> bookShelf) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(BOOKSHELF_KEY, bookShelf);
        fragment.setArguments(args);
        Log.d("something unique", " " + bookShelf.toString());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            bookShelf = bundle.getParcelableArrayList(BOOKSHELF_KEY);
        }
        detailsFragmentArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        for (int i = 0; i < bookShelf.size(); i++) {
            BookDetailsFragment bdf = BookDetailsFragment.newInstance(bookShelf.get(i));
            detailsFragmentArrayList.add(bdf);
        }
        viewPager = view.findViewById(R.id.bookViewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager()));
        return view;
    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return detailsFragmentArrayList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return detailsFragmentArrayList.get(position);
        }
    }

    public ArrayList<Book> getBookList() {
        return bookShelf;
    }

}
