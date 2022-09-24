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
import com.example.githubusersub2.databinding.ItemRowPersonBinding

class ListPersonAdapter : RecyclerView.Adapter<ListPersonAdapter.MyViewHolder>() {

    private var listPersonResponse = ArrayList<PersonRespons>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    @SuppressLint("NotifyDataSetChanged")
    fun addDataList(items: ArrayList<PersonRespons>) {
        listPersonResponse.clear()
        listPersonResponse.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            ItemRowPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listPersonResponse[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listPersonResponse[position]) }
    }

    override fun getItemCount(): Int = listPersonResponse.size

    class MyViewHolder(var binding: ItemRowPersonBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(personRespons: PersonRespons) {
            binding.tvItemUsername.text = personRespons.login
            Glide.with(binding.root)
                .load(personRespons.avatarUrl)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_baseline_refresh_24)
                        .error(R.drawable.ic_baseline_broken_image_24)
                )
                .circleCrop()
                .into(binding.imgItemPhoto)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}




