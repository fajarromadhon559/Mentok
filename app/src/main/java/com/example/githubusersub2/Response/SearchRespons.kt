package com.example.githubuserapp.Response

import com.google.gson.annotations.SerializedName

data class SearchRespons (
        @SerializedName("totalCount")
        val totalCount : Int,

        @SerializedName("incompleteResult")
        val incompleteResult :Boolean,

        val items : ArrayList<PersonRespons>?
        )