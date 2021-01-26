package site.yoonsang.todolist.fragmentClasses

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import site.yoonsang.todolist.LoginActivity
import site.yoonsang.todolist.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var mBinding: FragmentProfileBinding? = null
    private val GALLERY = 200

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        mBinding = binding

        mBinding?.profileName?.text = Firebase.auth.currentUser?.email

        mBinding?.logoutBtn?.setOnClickListener {
            if (Firebase.auth.currentUser != null) {
                AlertDialog.Builder(requireContext())
                    .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                    .setPositiveButton("로그아웃") { _, _ ->
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        Firebase.auth.signOut()
                        startActivity(intent)
                    }
                    .setNegativeButton("취소") { _, _ ->
                    }
                    .show()
            }
        }

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY) {
            if (resultCode == RESULT_OK) {
                val dataUri = data?.data
                mBinding?.profileImage?.setImageURI(dataUri)
            }
        }
    }
}