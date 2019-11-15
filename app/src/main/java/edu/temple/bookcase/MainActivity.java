package edu.temple.bookcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookListFragment.MainactivityInterface {

    BookDetailsFragment bookDetailsFragment;
    BookListFragment bookListFragment;
    ViewPagerFragment viewPagerFragment;
    ArrayList<Book> bookShelf;

    JSONArray bookListArray = new JSONArray();

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            try {
                bookListArray = new JSONArray(msg.obj.toString());
                for (int i = 0; i < bookListArray.length(); i++) {
                    Book book = new Book();
                    JSONObject jsonObject = bookListArray.getJSONObject(i);
                    book.setId(jsonObject.getInt("book_id"));
                    book.setTitle(jsonObject.getString("title"));
                    book.setAuthor(jsonObject.getString("author"));
                    book.setPublished(jsonObject.getInt("published"));
                    book.setCoverURL(jsonObject.getString("cover_url"));
                    bookShelf.add(book);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            bookListFragment = BookListFragment.newInstance(bookShelf);
            bookDetailsFragment = BookDetailsFragment.newInstance(new Book(0, "", "", 0, ""));
            viewPagerFragment = ViewPagerFragment.newInstance(bookShelf);
            if (findViewById(R.id.frame2) != null){
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame1, bookListFragment).commit();
                getSupportFragmentManager().beginTransaction().add(R.id.frame2, bookDetailsFragment).commit();
            } else {
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame1, viewPagerFragment).commit();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Thread t = new Thread() {
            @Override
            public void run() {
                URL bookListUrl;
                try {
                    bookListUrl = new URL("https://kamorris.com/lab/audlib/booksearch.php");
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(
                                    bookListUrl.openStream()));
                    String response = "";
                    String tempResponse;
                    tempResponse = reader.readLine();
                    while (tempResponse != null) {
                        response = response + tempResponse;
                        tempResponse = reader.readLine();
                    }

                    bookListArray = new JSONArray(response);

                    Message message = Message.obtain();
                    message.obj = response;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();




        //bookTitles = getResources().getStringArray(R.array.bookCase);

    }



    public void selectedBook(Book book) {
        bookDetailsFragment.setTextView(book);
    }
}
