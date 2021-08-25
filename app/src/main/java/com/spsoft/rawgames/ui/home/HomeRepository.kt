package com.spsoft.rawgames.ui.home

import com.spsoft.rawgames.data.apiservice.ApiService
import com.spsoft.rawgames.utils.BaseRepository

class HomeRepository (private val api: ApiService) : BaseRepository() {
    suspend fun getListGames(page: Int, page_size: Int, key: String) = saveApiCall {
        api.getList(page, page_size, key)
    }

    suspend fun getListTags(page: Int, page_size: Int, key: String) = saveApiCall {
        api.getTags(page, page_size, key)
    }

    suspend  fun getListGamesLink(s: String)= saveApiCall {
        api.getlistLink(s)
    }
}

