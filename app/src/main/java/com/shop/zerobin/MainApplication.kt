package com.shop.zerobin

import android.app.Application
import com.shop.zerobin.data.repository.mypage.MyPageRepository
import com.shop.zerobin.data.repository.shop.ReviewRepository
import com.shop.zerobin.data.repository.shop.ShopRepository
import com.shop.zerobin.ui.home.HomeViewModel
import com.shop.zerobin.ui.home.shop.ShopDetailViewModel
import com.shop.zerobin.ui.home.shop.WriteReviewViewModel
import com.shop.zerobin.ui.mypage.MyPageViewModel
import com.shop.zerobin.ui.mypage.sign.DeleteUserViewModel
import com.shop.zerobin.ui.mypage.sign.ResetPasswordViewModel
import com.shop.zerobin.ui.mypage.sign.SignInViewModel
import com.shop.zerobin.ui.mypage.sign.SignUpViewModel
import com.shop.zerobin.ui.review.ReviewViewModel
import com.shop.zerobin.ui.splash.filter.FilterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Koin Android logger
            androidLogger()
            //inject Android context
            androidContext(this@MainApplication)
            // use modules
            modules(myViewModel, myModule)
        }
    }
}

val myViewModel = module {
    viewModel { HomeViewModel(get()) }
    viewModel { ReviewViewModel(get()) }
    viewModel { MyPageViewModel(get(), get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { ShopDetailViewModel(get(), get()) }
    viewModel { FilterViewModel(get()) }
    viewModel { WriteReviewViewModel(get(), get()) }
    viewModel { DeleteUserViewModel(get()) }
    viewModel { ResetPasswordViewModel(get()) }
}

val myModule = module {
    single { MyPageRepository(get()) }
    single { ShopRepository(get()) }
    single { ReviewRepository(get()) }
}