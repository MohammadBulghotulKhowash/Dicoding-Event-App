package com.dicoding.latihan.dicodingeventapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.dicodingeventapp.data.response.ListEventsItem
import com.dicoding.latihan.dicodingeventapp.databinding.FragmentHomeBinding
import com.dicoding.latihan.dicodingeventapp.ui.adapter.CarouselEventAdapter
import com.dicoding.latihan.dicodingeventapp.ui.adapter.FinishedListItemAdapter
import com.dicoding.latihan.dicodingeventapp.ui.adapter.SearchResultAdapter
import com.dicoding.latihan.dicodingeventapp.ui.detail.DetailEventActivity

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        setupSearch()

        homeViewModel.eventCarousel.observe(viewLifecycleOwner) { loadCarousel ->
            setCarousel(loadCarousel)
        }
        homeViewModel.eventFinishedList.observe(viewLifecycleOwner) { loadFinishedList ->
            setFinishListItem(loadFinishedList)
        }
        homeViewModel.isLoadingUpcoming.observe(viewLifecycleOwner) { isLoadUpcoming ->
            isLoadingUpcoming(isLoadUpcoming)
        }
        homeViewModel.isLoadingFinished.observe(viewLifecycleOwner) { isLoadFinished ->
            isLoadingFinished(isLoadFinished)
        }
        homeViewModel.searchResult.observe(viewLifecycleOwner) { result ->
            handleSearchResult(result)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val searchVisibility = binding.searchResultList.visibility

            if (searchVisibility == View.VISIBLE) {
                binding.searchBar.clearText()
                toggleViews(isHome = true)
            } else {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }

    private fun setupRecyclerViews() {
        // Layout Manager Carousel RecyclerView
        val layoutManagerCarousel = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCarouselEvent.layoutManager = layoutManagerCarousel
        binding.rvCarouselEvent.addItemDecoration(DividerItemDecoration(requireContext(), layoutManagerCarousel.orientation))

        // Layout Manager Finished List RecyclerView
        val layoutManagerListItem = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvFinishedList.layoutManager = layoutManagerListItem
        binding.rvFinishedList.addItemDecoration(DividerItemDecoration(requireContext(), layoutManagerListItem.orientation))

        // Layout Manager Search Result RecyclerView
        val searchAdapter = SearchResultAdapter {
            val intent = Intent(requireContext(), DetailEventActivity::class.java)
            intent.putExtra(DetailEventActivity.EXTRA_ID, it.toString())
            startActivity(intent)
        }
        binding.searchResultList.adapter = searchAdapter
        val layoutManagerSearchResult = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchResultList.layoutManager = layoutManagerSearchResult
        binding.searchResultList.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManagerSearchResult.orientation)
        )
    }

    private fun setupSearch() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { v, _, _ ->
                val query = v.text.toString()
                homeViewModel.getSearchResult(query)
                searchBar.setText(query)
                searchView.hide()
                false
            }
        }
    }

    private fun handleSearchResult(searchResult: List<ListEventsItem>) {
        val searchAdapter = binding.searchResultList.adapter as SearchResultAdapter
        if (searchResult.isEmpty()) {
            toggleViews(isHome = false)
            binding.tvNoResult.visibility = View.VISIBLE
            searchAdapter.submitList(emptyList())
        } else {
            toggleViews(isHome = false)
            binding.tvNoResult.visibility = View.GONE
            searchAdapter.submitList(searchResult)
        }
    }

    private fun setCarousel(carouselEvent: List<ListEventsItem>) {
        val adapter = CarouselEventAdapter {
            val intent = Intent(requireContext(), DetailEventActivity::class.java)
            intent.putExtra(DetailEventActivity.EXTRA_ID, it.toString())
            startActivity(intent)
        }
        adapter.submitList(carouselEvent)
        binding.rvCarouselEvent.adapter = adapter
    }

    private fun setFinishListItem(listItem: List<ListEventsItem>) {
        val adapter = FinishedListItemAdapter {
            val intent = Intent(requireContext(), DetailEventActivity::class.java)
            intent.putExtra(DetailEventActivity.EXTRA_ID, it.toString())
            startActivity(intent)
        }
        adapter.submitList(listItem)
        binding.rvFinishedList.adapter = adapter
    }

    private fun isLoadingUpcoming(isLoading: Boolean) {
        binding.progressBarUpcoming.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun isLoadingFinished(isLoading: Boolean) {
        binding.progressBarFinished.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    private fun toggleViews(isHome: Boolean) {
        val homeVisibility = if (isHome) View.VISIBLE else View.GONE
        val searchVisibility = if (isHome) View.GONE else View.VISIBLE

        with(binding) {
            tvHeadingUpcoming.visibility = homeVisibility
            rvCarouselEvent.visibility = homeVisibility
            tvHeadingFinished.visibility = homeVisibility
            rvFinishedList.visibility = homeVisibility
            searchResultList.visibility = searchVisibility
            tvNoResult.visibility = if (isHome) View.GONE else tvNoResult.visibility
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
