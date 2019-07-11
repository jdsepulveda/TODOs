package com.todo.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class Task : BaseObservable() {
    var taskId: Long = 0L

    @get:Bindable
    var title: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    var desc: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.desc)
        }

    @get:Bindable
    var priorityLevel: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.priorityLevel)
        }

    @get:Bindable
    var status: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.status)
        }
}