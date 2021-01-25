package site.yoonsang.todolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainViewModel : ViewModel() {

    private val db = Firebase.firestore
    val todoLiveData = MutableLiveData<List<DocumentSnapshot>>()

    init {
        fetchData()
    }

    private fun fetchData() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            db.collection(user.uid)
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
            db.collection(user.uid).document(todo.id).update("finish", !finish)
        }
    }

    fun addTodo(todo: Todo) {
        Firebase.auth.currentUser?.let { user ->
            db.collection(user.uid).add(todo)
        }
    }

    fun deleteTodo(todo: DocumentSnapshot) {
        Firebase.auth.currentUser?.let { user ->
            db.collection(user.uid).document(todo.id).delete()
        }
    }
}