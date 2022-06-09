package com.rayxxv.letswatch.ui.detail

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.rayxxv.letswatch.R
import com.rayxxv.letswatch.data.Status
import com.rayxxv.letswatch.data.local.Favorite
import com.rayxxv.letswatch.databinding.FragmentDetailMovieBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieFragment : Fragment() {
    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
    private val viewModel: DetailMovieViewModel by viewModel()
    private val args: DetailMovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = args.movieId
        val name = "default name"
        val image = "default image"
        val progressDialog = ProgressDialog(requireContext())

        viewModel.getAllDetailMovies(movieId)
        viewModel.checkFavorite(movieId)

        viewModel.detailMovie.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    progressDialog.setMessage("Loading...")
                    progressDialog.show()
                }
                Status.SUCCESS -> {
                    Glide.with(binding.ivBackdrop)
                        .load(IMAGE_BASE + resource.data?.backdropPath)
                        .error(R.drawable.ic_broken)
                        .into(binding.ivBackdrop)

                    Glide.with(binding.ivPoster)
                        .load(IMAGE_BASE + resource.data?.posterPath)
                        .error(R.drawable.ic_broken)
                        .into(binding.ivPoster)

                    binding.apply {
                        tvJudul.text = resource.data?.originalTitle
                        tvTitle.text = resource.data?.originalTitle
                        tvVoteCount.text = resource.data?.voteCount.toString()
                        tvOverview.text = resource.data?.overview
                        resource.data?.voteAverage.let {
                            if (it != null) {
                                rbRating.rating = (it / 2).toFloat()
                            }
                        }
                        if (resource.data?.releaseDate != null && resource.data.releaseDate.isNotBlank()) {
                            tvReleaseDate.text = resource.data.releaseDate
                        } else {
                            tvReleaseDate.visibility = View.GONE
                        }
                        progressDialog.dismiss()

                    }
                }
                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        "Error get Data : ${resource.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            viewModel.dataFav.observe(viewLifecycleOwner) {
                if (it != null) {
                    if (it.id == resource.data?.id) {
                        binding.ivFav.setImageResource(R.drawable.ic_baseline_favorite_36)
                    } else {
                        binding.ivFav.setImageResource(R.drawable.ic_baseline_favorite_border_36)
                    }
                }
            }
        }
        binding.ivFav.setOnClickListener {
            addFavorite(movieId,name,image)
        }

        binding.ivBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }
    private fun addFavorite(id : Int?, name: String, image: String) {
        var checkFav = false

        viewModel.dataFav.observe(viewLifecycleOwner){
            if (it != null){
                if (it.id == id){
//                    binding.ivFav.setImageResource(R.drawable.ic_baseline_favorite_36)
                    checkFav = true
                }
            }
        }
        if (checkFav){
            binding.ivFav.setImageResource(R.drawable.ic_baseline_favorite_border_36)
            viewModel.deleteFav(Favorite(id, name, image))
        }else{
            binding.ivFav.setImageResource(R.drawable.ic_baseline_favorite_36)
            viewModel.insertFavorite(Favorite(id, name, image))
        }
        viewModel.checkFavorite(id.toString().toInt())
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}