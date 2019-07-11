package com.todo.utils

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.todo.database.DBTask

@BindingAdapter("priorityLevel")
fun TextView.setPriorityLevel(item: DBTask?) {
    item?.let {
        if (item.priorityLevel) {
            setBackgroundColor(Color.RED)
        } else {
            setBackgroundColor(Color.LTGRAY)
        }
    }
}