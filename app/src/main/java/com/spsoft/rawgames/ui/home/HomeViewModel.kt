package com.spsoft.rawgames.ui.home



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spsoft.rawgames.data.models.game.Game
import com.spsoft.rawgames.data.models.tags.Tags
import com.spsoft.rawgames.utils.NetworkResponse
import kotlinx.coroutines.launch

class HomeViewModel (
    private val homeRepository: HomeRepository
        ): ViewModel() {

    private lateinit var listener: MainListener


    fun getListGames(page: Int, page_size: Int, key: String) = viewModelScope.launch {
        when (val response = homeRepository.getListGames(page, page_size, key)) {
            is NetworkResponse.Success -> {
                listener.success(response.data)
            }
            is NetworkResponse.Failure -> {
                listener.error(response.message)
            }
        }
    }

    fun getListTags(page: Int, page_size: Int, key: String) = viewModelScope.launch {
        when (val response = homeRepository.getListTags(page, page_size, key)) {
            is NetworkResponse.Success -> {
                listener.successTags(response.data)
            }
            is NetworkResponse.Failure -> {
                listener.error(response.message)
            }
        }
    }



    fun setListener(listener: MainListener){this.listener=listener }



    interface MainListener {
        fun success(data: Game)
        fun error(message: String)
        fun successTags(data: Tags)


    }


}