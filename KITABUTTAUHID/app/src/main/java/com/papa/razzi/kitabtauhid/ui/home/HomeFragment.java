package com.papa.razzi.kitabtauhid.ui.home;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.papa.razzi.kitabtauhid.DataModel;
import com.papa.razzi.kitabtauhid.DataModelAdapter;
import com.papa.razzi.kitabtauhid.MainActivity;
import com.papa.razzi.kitabtauhid.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<DataModel> musics = new ArrayList<DataModel>();
        musics.add(new DataModel("KITABUL TAUHID 1",R.raw.k1));
        musics.add(new DataModel("KITABUL TAUHID 2",R.raw.k2));
        musics.add(new DataModel("KITABUL TAUHID 3",R.raw.k3));
        musics.add(new DataModel("KITABUL TAUHID 4",R.raw.k4));
        musics.add(new DataModel("KITABUL TAUHID 5",R.raw.k5));
        musics.add(new DataModel("KITABUL TAUHID 6",R.raw.k6));
        musics.add(new DataModel("KITABUL TAUHID 7",R.raw.k7));
        musics.add(new DataModel("KITABUL TAUHID 8",R.raw.k8));
        musics.add(new DataModel("KITABUL TAUHID 9",R.raw.k9));
        musics.add(new DataModel("KITABUL TAUHID 10",R.raw.k10));
        musics.add(new DataModel("KITABUL TAUHID 11",R.raw.k11));
        musics.add(new DataModel("KITABUL TAUHID 12",R.raw.k12));
        musics.add(new DataModel("KITABUL TAUHID 13",R.raw.k13));
        musics.add(new DataModel("KITABUL TAUHID 14",R.raw.k14));
        musics.add(new DataModel("KITABUL TAUHID 15",R.raw.k15));
        DataModelAdapter adapter = new DataModelAdapter(getActivity(),musics);
        ListView listView = (ListView) root.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                DataModel current = musics.get(position);
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getActivity(), current.getMusicResourceId());
                    mMediaPlayer.start();


                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }

            }
        });
        return root;


    }
    @Override
    public void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}





