package com.example.githubusersub2.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusersub2.Database.FavoriteEntity
import com.example.githubusersub2.Detail.DetailActivity
import com.example.githubusersub2.databinding.ActivityFavPersonBinding
import com.example.githubusersub2.databinding.ItemRowFollowBinding
import com.example.githubusersub2.databinding.ItemRowPersonBinding

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    private val personFavorite = ArrayList<FavoriteEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemRowPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(personFavorite[position])
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(personFavorite[position])
        }
    }

    override fun getItemCount() = personFavorite.size

    class MyViewHolder (private val binding: ItemRowPersonBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(favEntity : FavoriteEntity){
            with(binding){
                tvItemName.text = favEntity.login
                Glide.with(root)
                    .load(favEntity.avatar_url)
                    .circleCrop()
                    .into(binding.imgItemPhoto)
                root.setOnClickListener{
                    val i = Intent(itemView.context, DetailActivity ::class.java)
                    i.putExtra(DetailActivity.EXTRA_PERSON,favEntity)
                    itemView.context.startActivity(i)
                }
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setListFavorite(items: List<FavoriteEntity>) {
        personFavorite.clear()
        personFavorite.addAll(items)
        notifyDataSetChanged()
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(favEntity: FavoriteEntity)
    }
}