package com.repositorydemo.core.cart.di

import android.arch.persistence.room.Room
import android.content.Context
import com.repositorydemo.core.base.adapter.BaseRecyclerAdapter
import com.repositorydemo.core.cart.ui.CartActivity
import com.repositorydemo.core.cart.datamanager.CartBluePrint
import com.repositorydemo.core.cart.datamanager.CartLocalHandler
import com.repositorydemo.core.cart.datamanager.CartRemoteHandler
import com.repositorydemo.core.cart.datamanager.CartRepository
import com.repositorydemo.core.cart.viewmodel.CartViewModel
import com.repositorydemo.core.database.AppDb
import com.repositorydemo.core.di.CoreComponent
import com.repositorydemo.core.network.ApiService
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@CartScope
@Component(dependencies = [CoreComponent::class], modules = [CartModule::class])
interface CartComponent {
    fun inject(cartViewModel: CartViewModel)
    fun inject(activity: CartActivity)
}

@Module
class CartModule {

    @CartScope
    @Provides
    fun getAdapter() = BaseRecyclerAdapter()

    @CartScope
    @Provides
    fun getCartRepository(local: CartBluePrint.Local, remote: CartBluePrint.Remote) = CartRepository(local, remote)

    @CartScope
    @Provides
    fun getCartLocal(appDb: AppDb): CartBluePrint.Local = CartLocalHandler(appDb)

    @CartScope
    @Provides
    fun getCartRemote(service: ApiService): CartBluePrint.Remote = CartRemoteHandler(service)

    /* Base provides dependencies */
    @CartScope
    @Provides
    fun appDB(context: Context): AppDb = Room.databaseBuilder(context, AppDb::class.java, "app.db").build()

    @CartScope
    @Provides
    fun getPostService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}
