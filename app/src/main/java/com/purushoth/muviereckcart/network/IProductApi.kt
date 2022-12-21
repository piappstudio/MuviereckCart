package com.purushoth.muviereckcart.network

import com.purushoth.muviereckcart.data.ProductInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IProductApi {
    @GET("products")
    suspend fun getProducts(@Query("skip") skip:Int, @Query("limit") limit:Int=10):Response<ProductInfo?>
}