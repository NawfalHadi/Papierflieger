package com.papierflieger.presentation.ui.home.profiles

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.papierflieger.R
import com.papierflieger.data.local.model.SettingModel
import com.papierflieger.databinding.FragmentProfileUserBinding
import com.papierflieger.presentation.bussiness.SessionViewModel
import com.papierflieger.presentation.ui.adapter.settings.SettingAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ProfileUserFragment : Fragment() {

    private lateinit var binding : FragmentProfileUserBinding
    private val sessionViewModel: SessionViewModel by viewModels()

    private val accountAndSecurity : ArrayList<SettingModel> = arrayListOf(
        SettingModel(R.drawable.ic_person, "Account Information", "", R.id.action_profileUserFragment_to_accountInformationActivity),
        SettingModel(R.drawable.ic_verified,"Password and Security", "", R.id.action_profileUserFragment_to_passwordSecurityActivity)
    )

//    private val preference : ArrayList<SettingModel> = arrayListOf(
//        SettingModel("Country", "Indonesia", R.id.action_profileUserFragment2_to_accountInformationFragment),
//        SettingModel("Currency", "Rupiah", R.id.action_profileUserFragment2_to_accountInformationFragment),
//        SettingModel("Language", "Indonesia", R.id.action_profileUserFragment2_to_accountInformationFragment)
//    )

    private val imageFile = mutableListOf<File>()
    private val application : ArrayList<SettingModel> = arrayListOf(
        SettingModel(R.drawable.ic_logout, "Sign Out", "", 0)
    )

    private val accountAdapter : SettingAdapter by lazy { SettingAdapter() }
    private val applicationAdapter : SettingAdapter by lazy { SettingAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingProfile()
        initList()
        clickListener()
    }

    private fun clickListener() {
        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_profileUserFragment_to_authActivity)
        }
    }

    private fun checkingPermissions() {
        if (isGranted(
                requireActivity()
            )
        ) {
            val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
            galleryResult.launch(intent)
        }
    }

    private val galleryResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImage = result.data?.data
                if (selectedImage != null) {
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = requireActivity().contentResolver.query(selectedImage, filePathColumn, null, null, null)
                    cursor?.use {
                        if (it.moveToFirst()) {
                            val columnIndex = it.getColumnIndex(filePathColumn[0])
                            val filePath = it.getString(columnIndex)
                            imageFile.add(File(filePath))
                        }
                    }
                }
            }
        }

    private fun isGranted(activity: Activity): Boolean {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                checkingPermissions()
            }
            false
        } else {
            true
        }
    }

    private fun bindingProfile() {
        sessionViewModel.getToken().observe(viewLifecycleOwner) { token ->
            binding.apply {
                if (token != "") {
                    sessionViewModel.getAvatar().observe(viewLifecycleOwner) {
                        ivProfile.load(it) {
                            placeholder(R.color.background_gray)
                            transformations(CircleCropTransformation())
                        }
                    }
                    sessionViewModel.getName().observe(viewLifecycleOwner) {
                        tvFullName.text = it
                    }
                    sessionViewModel.getEmail().observe(viewLifecycleOwner) {
                        tvEmail.text = it
                    }
                } else {
                    tvFullName.text = ""
                    tvEmail.text = ""

                    vBg.visibility = View.VISIBLE
                    vBg.isVisible = true

                    btnSignIn.visibility = View.VISIBLE
                    btnSignIn.isVisible = true
                }
            }
        }

    }

    private fun initList() {
        with(binding) {
            with(rvAccountSecurity) {
                accountAdapter.setItem(accountAndSecurity)
                setRecyclerData(this, accountAdapter)
            }

            with(rvApplication){
                applicationAdapter.setItem(application)
                setRecyclerData(this, applicationAdapter)
            }
        }
    }

    private fun setRecyclerData(recyclerView: RecyclerView, _adapter : SettingAdapter) {
        with(recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
            adapter = _adapter
        }

        _adapter.actionMenu(object : SettingAdapter.OnMenuActionCallback{
            override fun menuClicked(id: Int?) {
                if (id == 0) {
                    sessionViewModel.logout()
                    findNavController().navigate(R.id.profileUserFragment)
                } else {
                    findNavController().navigate(id!!)
                }
            }
        })
    }

}