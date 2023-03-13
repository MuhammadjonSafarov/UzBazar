package uz.xia.bazar.ui.home.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CategoryItemDecoration(
    private val spaceEdge: Int, private val space: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (position % 4 == 0) {
            outRect.left = spaceEdge
            outRect.top = space / 2
            outRect.right = space / 2
            outRect.bottom = space / 2
        } else if ((position - 1) % 4 == 0) {
            outRect.left = space / 2
            outRect.top = space / 2
            outRect.right = space / 2
            outRect.bottom = space / 2
        } else if ((position - 2) % 4 == 0) {
            outRect.left = space / 2
            outRect.top = space / 2
            outRect.right = space / 2
            outRect.bottom = space / 2
        } else {
            outRect.left = space / 2
            outRect.top = space / 2
            outRect.right = spaceEdge
            outRect.bottom = space / 2
        }
    }
}