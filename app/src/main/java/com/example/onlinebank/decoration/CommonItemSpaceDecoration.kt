package com.example.onlinebank.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
///деокоратор, для создание пустоты между элементами recycleView
class CommonItemSpaceDecoration (space: Int):
    RecyclerView.ItemDecoration() {
    private var mSpace = space
    private var mVerticalOrientation = true
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = SizeUtils.dp2px(view.getContext(), mSpace)
        if (mVerticalOrientation) {
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.set(
                    0,
                    SizeUtils.dp2px(view.getContext(), mSpace),
                    0,
                    SizeUtils.dp2px(view.getContext(), mSpace)
                )
            } else {
                outRect.set(0, 0, 0, SizeUtils.dp2px(view.getContext(), mSpace))
            }
        } else {
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.set(SizeUtils.dp2px(view.getContext(), mSpace), 0, 0, 0)
            } else {
                outRect.set(
                    SizeUtils.dp2px(view.getContext(), mSpace),
                    0,
                    SizeUtils.dp2px(view.getContext(), mSpace),
                    0
                )
            }
        }
    }

    object SizeUtils {
        fun dp2px(context: Context, dpValue: Int): Int {
            val scale: Float = context.getResources().getDisplayMetrics().density
            return (dpValue * scale + 0.5f).toInt()
        }
    }
}