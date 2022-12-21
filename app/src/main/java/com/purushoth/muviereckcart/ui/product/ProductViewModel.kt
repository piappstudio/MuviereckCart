package com.purushoth.muviereckcart.ui.product

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.purushoth.muviereckcart.data.ProductsItem
import com.purushoth.muviereckcart.data.toCurrency
import com.purushoth.muviereckcart.network.ProductPageDataSource
import com.purushoth.muviereckcart.network.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(val productRepository: ProductRepository, private val productPageDataSource: ProductPageDataSource):ViewModel() {

    private val _lstCarts:MutableStateFlow<List<ProductsItem>> = MutableStateFlow(emptyList())
    val lstCarts:StateFlow<List<ProductsItem>> = _lstCarts

    fun addToCart(value: ProductsItem?) {
        value?.let {
            if (!_lstCarts.value.contains(value)) {
                val oldList =  mutableListOf<ProductsItem>()
                oldList.addAll(_lstCarts.value)
                oldList.add(value)
                _lstCarts.value  = oldList
            }
        }
    }

    fun updateCount(quantity: Int, item: ProductsItem) {
        val oldItemIndex = _lstCarts.value.indexOf(item)
        val updatedItem = item.copy(quantity = quantity+1)

        val oldArray = mutableListOf<ProductsItem>()
        oldArray.addAll(_lstCarts.value)
        oldArray[oldItemIndex] = updatedItem
        _lstCarts.update { oldArray }

    }

    fun removeItem(cartDataItem: ProductsItem) {
        val oldItemIndex = _lstCarts.value.indexOf(cartDataItem)
        val oldArray = mutableListOf<ProductsItem>()
        oldArray.addAll(_lstCarts.value)
        oldArray.removeAt(oldItemIndex)
        _lstCarts.update { oldArray }
    }

    fun getTotal(): String {
        return lstCarts.value.sumOf { it.quantity* (it.price?:0) }.toCurrency()
    }

    val productPager = Pager(PagingConfig(pageSize = 10)) { productPageDataSource }.flow.cachedIn(viewModelScope)
}