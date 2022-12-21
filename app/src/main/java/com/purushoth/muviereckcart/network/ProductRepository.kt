package com.purushoth.muviereckcart.network

import com.purushoth.muviereckcart.data.ProductInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val iProductApi: IProductApi){

    suspend fun fetchProducts(skip:Int):ProductInfo? {
        return withContext(Dispatchers.IO) {
            val response = iProductApi.getProducts(skip = skip)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

}