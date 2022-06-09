package com.rayxxv.letswatch.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rayxxv.letswatch.R
import com.rayxxv.letswatch.data.Status
import com.rayxxv.letswatch.data.local.User
import com.rayxxv.letswatch.data.pojo.Movie
import com.rayxxv.letswatch.data.pojo.ResultX
import com.rayxxv.letswatch.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

//testingCi
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnProfile.setOnClickListener{
            findNavController().navigate(R.id.action_menuFragment_to_profileFragment2)
        }
        viewModel.movies.observe(viewLifecycleOwner){ resource ->
            when (resource.status){
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d("hasil","loading")
                }
                Status.SUCCESS -> {
                    showListMovie(resource.data?.movies)
                    binding.progressBar.visibility = View.GONE
                    Log.d("hasil","sukses")
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error get Data : ${resource.message}", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("hasil","error")
                }
            }
        }
        viewModel.getAllPopularMovies()

        viewModel.series.observe(viewLifecycleOwner){ resource ->
            when (resource.status){
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d("hasil","loading")
                }
                Status.SUCCESS -> {
                    showListSeries(resource.data?.results)
                    binding.progressBar.visibility = View.GONE
                    Log.d("hasil","sukses")
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error get Data : ${resource.message}", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("hasil","error")
                }
            }
        }
        viewModel.getAllPopularSeries()
    }
    private fun updateUser(){
        binding.btnProfile.setOnClickListener {
            viewModel.getUser(binding?.tvJudul?.text.toString())
            viewModel.getDataUser.observe(viewLifecycleOwner) {
                if (it != null) {
                    val user = User(
                        it.id,
                        it.username,
                        it.email,
                        it.password,
                        it.uri
                    )
                    val navigateUpdate = HomeFragmentDirections.actionMenuFragmentToProfileFragment(user)
                    findNavController().navigate(navigateUpdate)
                }
            }
        }
    }
    private fun showListMovie(data: List<Movie>?) {
        val adapter = MoviesAdapter(object : MoviesAdapter.OnClickListener {
            override fun onClickItem(data: Movie) {
                val homeToDetail = HomeFragmentDirections.actionMenuFragmentToDetailFragment(data.id)
                findNavController().navigate(homeToDetail)
            }
        })

        adapter.submitData(data)
        binding.rvPopularMovie.adapter = adapter
    }

    private fun showListSeries(data: List<ResultX>?) {
        val adapter = SeriesAdapter(object : SeriesAdapter.OnClickListener {
            override fun onClickItem(data: ResultX) {
                val homeToDetail = HomeFragmentDirections.actionMenuFragmentToDetailSeriesFragment(data.number)
                findNavController().navigate(homeToDetail)
            }
        })

        adapter.submitData(data)
        binding.rvPopularSeries.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}