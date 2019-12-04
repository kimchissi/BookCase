package edu.temple.bookcase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Library implements Parcelable {
    private ArrayList<Book> bookList;

    public Library() {
        bookList = new ArrayList<Book>();
    }

    protected Library(Parcel in) {
        bookList = in.createTypedArrayList(Book.CREATOR);
    }

    public static final Creator<Library> CREATOR = new Creator<Library>() {
        @Override
        public Library createFromParcel(Parcel in) {
            return new Library(in);
        }

        @Override
        public Library[] newArray(int size) {
            return new Library[size];
        }
    };

    public Book getBookAt(int position) {
        return bookList.get(position);
    }

    public Book getBookWithId(int id) {
        Book book = null;
        for(Book nextBook : bookList) {
            if(nextBook.getId() == id) {
                return nextBook;
            }
        }
        return null;
    }

    public void addBook(Book book) {
        bookList.clear();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(bookList);
    }
}
