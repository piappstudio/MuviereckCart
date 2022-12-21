package com.purushoth.muviereckcart.data

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import com.purushoth.muviereckcart.ui.product.Dimens

fun Modifier.piShadow(elevation: Dp = Dimens.space): Modifier = composed {
    shadow(elevation = elevation, shape = RoundedCornerShape(elevation), clip = true)
}

fun Int.toCurrency():String {
    return "₹ $this"
}