package com.purushoth.muviereckcart.data

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import com.purushoth.muviereckcart.ui.product.Dimens

fun Modifier.piShadow(elevation: Dp = Dimens.space): Modifier = composed {
    shadow(elevation = elevation, shape = RoundedCornerShape(elevation), clip = true)
}

@Composable
fun PiProgressIndicator(isDialogIndicator:Boolean = true, modifier: Modifier= Modifier) {
    if (isDialogIndicator) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            PiProgressBar(modifier = modifier)
        }
    } else {
        PiProgressBar(modifier = modifier)
    }
}
@Composable
private fun PiProgressBar(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize()) {

        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))


    }
}


fun Int.toCurrency():String {
    return "₹ $this"
}