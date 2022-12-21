package com.purushoth.muviereckcart.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.purushoth.muviereckcart.network.ProductPageDataSource
import com.purushoth.muviereckcart.network.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(val productRepository: ProductRepository, private val productPageDataSource: ProductPageDataSource):ViewModel() {
    val productPager = Pager(PagingConfig(pageSize = 10)) { productPageDataSource }.flow.cachedIn(viewModelScope)
}