package com.purushoth.muviereckcart.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.purushoth.muviereckcart.data.ProductsItem
import javax.inject.Inject

class ProductPageDataSource @Inject constructor(private val productRepository: ProductRepository):
    PagingSource<Int, ProductsItem>() {
    override fun getRefreshKey(state: PagingState<Int, ProductsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(10) ?: anchorPage?.nextKey?.minus(10)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductsItem> {
        return try {
            val nextPageNumber = params.key ?: 10
            val response = productRepository.fetchProducts(nextPageNumber)
            LoadResult.Page(
                data = response?.products?: emptyList(),
                prevKey = null,
                nextKey = if (response?.products?.isNotEmpty() == true) response.skip?.plus(10) else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}