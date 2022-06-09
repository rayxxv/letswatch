package com.rayxxv.letswatch.di

import com.rayxxv.letswatch.ui.home.HomeViewModel
import com.rayxxv.letswatch.ui.login.LoginViewModel
import com.rayxxv.letswatch.ui.register.RegisterViewModel
import com.rayxxv.letswatch.ui.detail.DetailMovieViewModel
import com.rayxxv.letswatch.ui.detail.DetailSeriesViewModel
import com.rayxxv.letswatch.ui.profil.ProfileFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::DetailMovieViewModel)
    viewModelOf(::DetailSeriesViewModel)
    viewModelOf(::ProfileFragmentViewModel)
}