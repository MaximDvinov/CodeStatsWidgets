package code.stats.widget.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Int.formatter(): String =
    DecimalFormat("#,###", DecimalFormatSymbols(Locale.getDefault()))
        .format(this)