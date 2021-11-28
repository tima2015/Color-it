package com.forward.colorit.tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;

public class MusicPlayer {

    private static final String TAG = "MusicPlayer";

    private boolean active = false;
    private int current = 0;
    private float volume = 1f;

    private final Array<Music> musics = new Array<>();
    private final MusicPlayerOnCompletionListener completionListener = new MusicPlayerOnCompletionListener();

    public void addMusic(Music music) {
        Gdx.app.debug(TAG, "addMusic() called with: music = [" + music + "]");
        musics.add(music);
        music.setOnCompletionListener(completionListener);
    }

    public Music getCurrent() {
        return musics.isEmpty() ? null : musics.get(current);
    }

    public void start() {
        Gdx.app.debug(TAG, "start() called");
        if (getCurrent() == null) return;
        active = true;
        play();
    }

    public void stop() {
        Gdx.app.debug(TAG, "stop() called");
        if (getCurrent() == null) return;
        active = false;
        getCurrent().stop();
    }

    public void next() {
        Gdx.app.debug(TAG, "next() called");
        if (getCurrent() == null) return;
        if (++current >= musics.size) current = 0;
        play();
    }

    public void prev() {
        Gdx.app.debug(TAG, "prev() called");
        if (getCurrent() == null) return;
        if (--current <= 0) current = musics.size - 1;
        play();
    }

    public void setVolume(float volume) {
        Gdx.app.debug(TAG, "setVolume() called with: volume = [" + String.format("%.10f", volume) + "]");
        if (volume < 0) volume = 0;
        else if (volume > 1) volume = 1;
        this.volume = volume;
        if (getCurrent() != null) getCurrent().setVolume(volume);
    }

    public void shuffle(){
        Gdx.app.debug(TAG, "shuffle() called");
        musics.shuffle();
    }

    private void play() {
        Gdx.app.debug(TAG, "play() called");
        getCurrent().setVolume(volume);
        getCurrent().play();
    }

    private class MusicPlayerOnCompletionListener implements Music.OnCompletionListener {

        @Override
        public void onCompletion(Music music) {
            Gdx.app.debug(TAG, "onCompletion() called with: music = [" + music + "]");
            if (!active) return;
            if (++current >= musics.size) current = 0;
            next();
        }
    }
}
