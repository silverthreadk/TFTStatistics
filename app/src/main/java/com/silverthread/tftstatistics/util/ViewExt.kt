package com.silverthread.tftstatistics.util

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.silverthread.tftstatistics.R
import com.silverthread.tftstatistics.ui.common.DividerItemDecoration
import com.silverthread.tftstatistics.ui.summoner.SummonerViewModel

fun View.margin(left: Float? = null, top: Float? = null, right: Float? = null, bottom: Float? = null) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.run { leftMargin = dpToPx(this) }
        top?.run { topMargin = dpToPx(this) }
        right?.run { rightMargin = dpToPx(this) }
        bottom?.run { bottomMargin = dpToPx(this) }
    }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

fun RecyclerView.setupItemDecoration(context: Context){
    val heightInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
    addItemDecoration(DividerItemDecoration(ContextCompat.getColor(context, R.color.rarity_0), heightInPixels))
}

fun SwipeRefreshLayout.refresh(summonerViewModel: SummonerViewModel) {
    setOnRefreshListener {
        summonerViewModel.loadSummonerByPuuid()
        isRefreshing = false
    }
}