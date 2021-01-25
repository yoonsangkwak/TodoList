package site.yoonsang.todolist.fragmentClasses

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

        mBinding?.profileImageSettingBtn?.setOnClickListener {
            loadImage()
        }

        mBinding?.profileName?.setText(Firebase.auth.currentUser?.email)

        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    private fun loadImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, GALLERY)
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