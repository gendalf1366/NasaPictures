package ru.gendalf13666.nasapictures.ui.recycler_activity

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.gendalf13666.nasapictures.ui.recycler_activity.model.Data

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: Pair<Data, Boolean>)
}
