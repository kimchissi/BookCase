package edu.temple.bookcase;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.temple.audiobookplayer.AudiobookService;

public class PlayerFragment extends Fragment {
    Context parent;
    int progress = 0;
    SeekBar seekBar;
    TextView textView;
    Button playPauseButton;
    Button stopButton;
    boolean isPaused = false;
    boolean isStopped = false;

    public PlayerFragment() {
        //Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof PlayerFragmentInterface) {
            parent = context;
        } else {
            throw new RuntimeException("PlayerFragment Interface not implemented");
        }
    }

    Handler progressHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(@NonNull Message msg) {
            AudiobookService.BookProgress bookProgress = (AudiobookService.BookProgress) msg.obj;
            if (!isPaused) {
                progress = bookProgress.getProgress();
            }
            seekBar.setProgress(progress);
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        seekBar = view.findViewById(R.id.seekBar);
        textView = view.findViewById(R.id.titleTextView);
        playPauseButton = view.findViewById(R.id.playPauseButton);
        stopButton = view.findViewById(R.id.stopButton);
        seekBar.setProgress(progress);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isPaused = true;
                ((PlayerFragmentInterface) parent).pressPlayPause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((PlayerFragmentInterface) parent).updateSeekBar(seekBar.getProgress());
                ((PlayerFragmentInterface) parent).pressPlayPause();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PlayerFragmentInterface) parent).pressStop();
                isStopped = true;
            }
        });

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PlayerFragmentInterface) parent).pressPlayPause();
                if (!isPaused) {
                    playPauseButton.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                    isPaused = true;
                } else if (isPaused) {
                    playPauseButton.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    isPaused = false;
                }
            }
        });
        return view;
    }

    public void updatePlayer(String title) {
        textView.setText("Now Playing " + title);
    }


    interface PlayerFragmentInterface{
        void updateSeekBar(int progress);
        void pressPlayPause();
        void pressStop();
    }
}
