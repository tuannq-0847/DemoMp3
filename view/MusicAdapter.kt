package com.example.samplethread.view

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samplethread.R
import com.example.samplethread.model.Music
import kotlinx.android.synthetic.main.item_song.view.*

class MusicAdapter(private val context: Context, private val listSong: ArrayList<Music>) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
    private var selectionPos = 0
    private var isClicked = false
    private val layoutInflater = LayoutInflater.from(context)
    private var onItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.item_song, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listSong.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val music = listSong[position]
        holder.txtNameArtist.text = music.songArtist
        holder.txtNameSong.text = music.songName
        holder.layoutSong.setOnClickListener {
            onItemClickListener?.onItemClicked(music, position)
            selectionPos = position
            isClicked = true
            notifyDataSetChanged()
        }
        if (selectionPos == position && isClicked) {
            holder.txtNameArtist.setTextColor(Color.parseColor("#FF0000"))
            holder.txtNameSong.setTextColor(Color.parseColor("#FF0000"))
        } else {
            holder.txtNameArtist.setTextColor(Color.parseColor("#000000"))
            holder.txtNameSong.setTextColor(Color.parseColor("#000000"))
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNameSong = itemView.txtNameSong
        val txtNameArtist = itemView.txtArtist
        val layoutSong = itemView.layoutSong

    }

    interface OnItemClickListener {
        fun onItemClicked(music: Music, position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}