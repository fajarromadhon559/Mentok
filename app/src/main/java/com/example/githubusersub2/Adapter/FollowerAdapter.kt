package com.example.githubusersub2.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapp.Adapter.OnItemClickCallback
import com.example.githubuserapp.Response.PersonRespons
import com.example.githubusersub2.R
import com.example.githubusersub2.databinding.ItemRowFollowBinding

class FollowerAdapter : RecyclerView.Adapter<FollowerAdapter.MyViewHolder>(){
    private var listPersonResponse = ArrayList<PersonRespons>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    @SuppressLint("NotifyDataSetChanged")
    fun addDataList(items: List<PersonRespons>){
        listPersonResponse.clear()
        listPersonResponse.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemRowFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listPersonResponse[position])
        holder.itemView.setOnClickListener{ onItemClickCallback.onItemClicked(listPersonResponse[position])}
    }

    override fun getItemCount() = listPersonResponse.size

    class MyViewHolder(private var binding: ItemRowFollowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(personRespons: PersonRespons){
            binding.tvItemNameFollws.text = personRespons.login
            Glide.with(binding.root)
                .load(personRespons.avatarUrl)
                .apply (
                    RequestOptions.placeholderOf(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_baseline_broken_image_24)
                )
                .circleCrop()
                .into(binding.imgItemPhotoFollows)
        }
    }

}