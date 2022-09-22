package com.example.githubuserapp.Adapter

import com.example.githubuserapp.Response.PersonRespons

interface OnItemClickCallback {
    fun onItemClicked(person : PersonRespons)
}