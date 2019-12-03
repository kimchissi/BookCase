package edu.temple.bookcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ServiceConnection;
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

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import edu.temple.audiobookplayer.AudiobookService;

public class MainActivity extends AppCompatActivity implements BookListFragment.MainactivityInterface, PlayerFragment.PlayerFragmentInterface {

    BookDetailsFragment bookDetailsFragment;
    BookListFragment bookListFragment;
    ViewPagerFragment viewPagerFragment;
    ArrayList<Book> bookShelf = new ArrayList<Book>();
    //ArrayList<Book> bookShelf;
    JSONArray bookListArray = new JSONArray();
    //JSONArray bookListArray;
    EditText searchInput;
    Button goButton;
    Fragment fragment;
    boolean isConnected = false;
    FrameLayout playerFrame;
    AudiobookService.MediaControlBinder binder;
    ServiceConnection serviceConnection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = getSupportFragmentManager().findFragmentById(R.id.frame1);
        searchInput = findViewById(R.id.editText);
        goButton = findViewById(R.id.button);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tSearch = new Thread(){
                    @Override
                    public void run(){
                        getBookSearch(searchInput.getText().toString());
                    }
                };tSearch.start();
            }
        });

        Thread t = new Thread() {
            @Override
            public void run() {
                URL bookListUrl;
                try {
                    bookListUrl = new URL(getResources().getString(R.string.jsonLink));
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

                    JSONArray bookListArray = new JSONArray(response);

                    Message message = Message.obtain();
                    message.obj = bookListArray;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            try {
                bookShelf = new ArrayList<Book>();
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
            //Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame1);
            //if(fragment instanceof ViewPagerFragment) {

            //} else if(fragment instanceof BookListFragment) {

            //} else {
            //    return false;
            //}
            bookListFragment = BookListFragment.newInstance(bookShelf);
            bookDetailsFragment = BookDetailsFragment.newInstance(new Book(0, "", "", 0, ""));
            getSupportFragmentManager().beginTransaction().remove(bookDetailsFragment);
            viewPagerFragment = ViewPagerFragment.newInstance(bookShelf);
            if (findViewById(R.id.frame2) != null){
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame1, bookListFragment).commit();
                getSupportFragmentManager().beginTransaction().remove(bookDetailsFragment).commit();
                getSupportFragmentManager().beginTransaction().add(R.id.frame2, bookDetailsFragment).commit();
            } else {
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame1, viewPagerFragment).commit();
            }
            return false;
        }
    });


    Handler searchHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            try {
                bookShelf = new ArrayList<Book>();
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
            getSupportFragmentManager().beginTransaction().remove(bookDetailsFragment);
            viewPagerFragment = ViewPagerFragment.newInstance(bookShelf);
            if (findViewById(R.id.frame2) != null){
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame1, bookListFragment).commit();
                getSupportFragmentManager().beginTransaction().remove(bookDetailsFragment);
                getSupportFragmentManager().beginTransaction().add(R.id.frame2, bookDetailsFragment).commit();
            } else {
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame1, viewPagerFragment).commit();
            }
            return false;
        }
    });


    public Message getBookSearch(String searchWord) {

        URL bookListUrlSearch;


        try {
            bookListUrlSearch = new URL(getResources().getString(R.string.jsonSearchLink) + searchWord);
            BufferedReader reader = new BufferedReader((
                    new InputStreamReader(bookListUrlSearch.openStream())));

            String response = "", tmpResponse;

            tmpResponse = reader.readLine();
            while (tmpResponse != null) {
                response = response + tmpResponse;
                tmpResponse = reader.readLine();
            }

            JSONArray bookArray = new JSONArray(response);
            Message message = Message.obtain();
            message.obj = bookArray;
            searchHandler.sendMessage(message);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void selectedBook(Book book) {
        bookDetailsFragment.setTextView(book);
    }

    public void updateSeekBar(int progress) {
        if (isConnected) {
            binder.seekTo(progress);
        }
    }

    public void pressPlayPause() {
        binder.pause();
    }

    public void pressStop() {
        binder.stop();
        playerFrame.setVisibility(View.GONE);
    }
}
