package com.example.samplethread.presenter

import android.content.Context
import android.provider.MediaStore
import com.example.samplethread.model.Music

class MusicManager {
    var mListSong: ArrayList<Music> = ArrayList()

    companion object {
        const val TAG = "MusicManager"
    }

    fun getSongs(context: Context): ArrayList<Music> {
        val contentResolver = context.contentResolver
        val musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver.query(musicUri, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            val columnName = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val columnArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val columnUri = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            while (cursor.moveToNext()) {
                val nameMusic = cursor.getString(columnName)
                val nameArtist = cursor.getString(columnArtist)
                val uriSong = cursor.getString(columnUri)
                mListSong.add(Music(nameMusic, nameArtist, uriSong))
            }
            cursor.close()
        }
        return mListSong
    }
}