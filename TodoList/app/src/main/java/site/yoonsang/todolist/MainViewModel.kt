package site.yoonsang.todolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {

    private val db = Firebase.firestore
    val todoLiveData = MutableLiveData<List<DocumentSnapshot>>()

    init {
        fetchData()
    }

    private fun fetchData() {
        val user = Firebase.auth.currentUser
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR).toString()
        val month = (cal.get(Calendar.MONTH) + 1).toString()
        val day = cal.get(Calendar.DATE).toString()
        val date = "$year-$month-$day"
        if (user != null) {
            db.collection(user.uid).document(user.uid).collection(date)
                .orderBy("createdDate")
                .addSnapshotListener { value, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }

                    if (value != null) {
                        todoLiveData.value = value.documents
                    }
                }
        }
    }

    fun toggleTodo(todo: DocumentSnapshot) {
        Firebase.auth.currentUser?.let { user ->
            val finish = todo.getBoolean("finish") ?: false
            val dateFormat = SimpleDateFormat("yyyy-M-dd", Locale.KOREA).format(todo.getDate("createdDate")!!)
            db.collection(user.uid).document(user.uid).collection(dateFormat).document(todo.id).update("finish", !finish)
        }
    }

    fun addTodo(todo: Todo) {
        Firebase.auth.currentUser?.let { user ->
            val dateFormat = SimpleDateFormat("yyyy-M-dd", Locale.KOREA).format(todo.createdDate)
            db.collection(user.uid).document(user.uid).collection(dateFormat).add(todo)
        }
    }

    fun deleteTodo(todo: DocumentSnapshot) {
        Firebase.auth.currentUser?.let { user ->
            val dateFormat = SimpleDateFormat("yyyy-M-dd", Locale.KOREA).format(todo.getDate("createdDate")!!)
            db.collection(user.uid).document(user.uid).collection(dateFormat).document(todo.id).delete()
        }
    }
}