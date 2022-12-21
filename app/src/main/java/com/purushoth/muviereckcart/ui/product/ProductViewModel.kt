package com.purushoth.muviereckcart.ui.product

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.tool.xml.XMLWorkerHelper
import com.purushoth.muviereckcart.BuildConfig
import com.purushoth.muviereckcart.R
import com.purushoth.muviereckcart.data.ProductsItem
import com.purushoth.muviereckcart.data.Resource
import com.purushoth.muviereckcart.data.toCurrency
import com.purushoth.muviereckcart.network.ProductPageDataSource
import com.purushoth.muviereckcart.network.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    val productRepository: ProductRepository,
    private val productPageDataSource: ProductPageDataSource
) : ViewModel() {

    private val _lstCarts: MutableStateFlow<List<ProductsItem>> = MutableStateFlow(emptyList())
    val lstCarts: StateFlow<List<ProductsItem>> = _lstCarts

    fun addToCart(value: ProductsItem?) {
        value?.let {
            if (!_lstCarts.value.contains(value)) {
                val oldList = mutableListOf<ProductsItem>()
                oldList.addAll(_lstCarts.value)
                oldList.add(value)
                _lstCarts.value = oldList
            }
        }
    }

    fun updateCount(quantity: Int, item: ProductsItem) {
        val oldItemIndex = _lstCarts.value.indexOf(item)
        val updatedItem = item.copy(quantity = quantity + 1)

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

    private val _appStatus = MutableStateFlow(Resource.Status.NONE)
    val appStatus: StateFlow<Resource.Status> = _appStatus
    fun generatePdf(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _appStatus.update { Resource.Status.LOADING }
            val rowBuffer = StringBuffer()
            lstCarts.value.forEach {
                rowBuffer.append("<tr>")
                rowBuffer.append("<td>${it.title}</td>")
                rowBuffer.append("<td>${it.price}</td>")
                rowBuffer.append("<td>${it.quantity}</td>")
                rowBuffer.append("<td>${it.quantity * (it.price ?: 0)}</td>")
                rowBuffer.append("</tr>")
            }

            // Generate html
            val htmlTableString =
                context.getString(R.string.html_pdf).replace("%s", rowBuffer.toString())
            val total =
                lstCarts.value.sumOf { prod -> prod.quantity * (prod.price ?: 0) }.toCurrency()
            val finalHtmlString = htmlTableString.replace("%d", total)

            val pdfFile = File(context.cacheDir, "Invoice.pdf")

            val document = com.itextpdf.text.Document()

            val inStream = finalHtmlString.byteInputStream(Charsets.UTF_8)

            val pdf = PdfWriter.getInstance(document, pdfFile.outputStream())
            document.open()
            XMLWorkerHelper.getInstance().parseXHtml(pdf, document, inStream)
            document.close()
            inStream.close()
            _appStatus.update { Resource.Status.SUCCESS }

            val uri = FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID + ".provider",
                pdfFile
            )
            val pdfIntent = Intent(Intent.ACTION_VIEW)
            pdfIntent.setDataAndType(uri, "application/pdf")
            pdfIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.startActivity(pdfIntent)

        }

    }

    val productPager =
        Pager(PagingConfig(pageSize = 10)) { productPageDataSource }.flow.cachedIn(viewModelScope)
}