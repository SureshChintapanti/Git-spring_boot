
package com.example.song.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import com.example.song.repository.SongJpaRepository;
import com.example.song.repository.SongRepository;
import com.example.song.model.Song;

@Service
public class SongJpaService implements SongRepository {

    @Autowired
    private SongJpaRepository songJpaRepository;

    @Override
    public void deleteSong(int songId) {
        try {
            songJpaRepository.deleteById(songId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Song updateSong(int songId, Song song) {
        try {
            Song existingSong = songJpaRepository.findById(songId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            if (song.getSongName() != null) {
                existingSong.setSongName(song.getSongName());
            }
            if (song.getLyricist() != null) {
                existingSong.setLyricist(song.getLyricist());
            }
            songJpaRepository.save(existingSong);
            return existingSong;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Song addSong(Song song) {
        try {
            songJpaRepository.save(song);
            return song;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Song getSongById(int songId) {
        try {
            return songJpaRepository.findById(songId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ArrayList<Song> getSongs() {
        List<Song> songList = songJpaRepository.findAll();
        return new ArrayList<>(songList);
    }

}
