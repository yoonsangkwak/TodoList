package site.yoonsang.todolist

import java.util.*

data class Todo(
    val text: String,
    var finish: Boolean = false,
    val createdDate: Date = Calendar.getInstance().time
)
