package edu.temple.bookcase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;



public class ViewPagerFragment extends Fragment {
    private static final String BOOKCASE_KEY = "book case";
    private String[] bookCase;
    private ArrayList<BookDetailsFragment> detailsFragmentArrayList;
    private ViewPager viewPager;

    public ViewPagerFragment() {
        // Required empty public constructor
    }

    public static ViewPagerFragment newInstance(String[] bookCase) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putStringArray(BOOKCASE_KEY, bookCase);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            bookCase = bundle.getStringArray(BOOKCASE_KEY);
        }
        detailsFragmentArrayList = new ArrayList<>();
        for (int i = 0; i < bookCase.length; i++) {
            BookDetailsFragment bdf = new BookDetailsFragment();
            Book book = new Book(bookCase[i]);
            bdf.setTextView(book);
            detailsFragmentArrayList.add(bdf);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
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

}
