package com.spsoft.rawgames.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spsoft.rawgames.data.models.game.Game
import com.spsoft.rawgames.data.models.game.Result
import com.spsoft.rawgames.data.models.tags.Tags
import com.spsoft.rawgames.databinding.FragmentHomeBinding
import com.spsoft.rawgames.ui.home.adapter.GameListAdapter
import com.spsoft.rawgames.utils.showToast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(), KodeinAware, HomeViewModel.MainListener {

    private val API_KEY = "ebe7f17643cd4c5e92176cc953c88700"
    override val kodein: Kodein by closestKodein()
    private lateinit var adapter: GameListAdapter
    private val viewModelFactory: HomeViewModelFactory by instance()
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var isLoading: Boolean = false
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private val listGames = mutableListOf<Result?>()
    private var game: Game? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        homeViewModel.setListener(this)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.recyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = GameListAdapter()
        binding.recyclerview.adapter = adapter
        load(1)

        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val mLayoutManager = (binding.recyclerview.layoutManager as LinearLayoutManager)
                totalItemCount = mLayoutManager.itemCount
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount-1 == lastVisibleItem) {
                    isLoading = true
                    loadMore(Integer.parseInt(game?.next!!.split("page=")[1].first().toString()))
                }
            }
        })
        binding.swiperefresh.setOnRefreshListener {
            load(1)
        }
        return binding.root
    }
    private fun load(page: Int) {
        if (page == 1)
            listGames.clear()
        homeViewModel.getListGames(page, 4, API_KEY)
        Log.e(
            "Hello", "page : $page"
        )
    }
    private fun loadMore(page: Int) {
        listGames.add(null)
        adapter.submitList(listGames)
        homeViewModel.getListGames(page, 4, API_KEY)
    }
    override fun success(data: Game) {
        if (listGames.isNotEmpty() && listGames.last() == null) {
            listGames.removeAt(listGames.size - 1)
        }

        game = data
        listGames.addAll(data.results)
        binding.recyclerview.setHasFixedSize(true)
        adapter.submitList(listGames)
        adapter.notifyDataSetChanged()
        binding.progressBar.visibility = View.GONE
        binding.swiperefresh.isRefreshing = false
        isLoading = false


    }

    override fun error(message: String) {
        context?.showToast(message)
        isLoading = false
        binding.progressBar.visibility = View.GONE
        binding.swiperefresh.isRefreshing = false

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun successTags(data: Tags) {

    }
}