//package com.rayxxv.letswatch.ui.profil
//
//import android.Manifest
//import android.app.Activity
//import android.app.AlertDialog
//import android.content.ActivityNotFoundException
//import android.content.Context
//import android.content.ContextWrapper
//import android.content.Intent
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import android.provider.Settings
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.core.net.toUri
//import androidx.fragment.app.DialogFragment
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.fragment.findNavController
//import androidx.navigation.fragment.navArgs
//import com.bumptech.glide.Glide
//import com.example.movie.R
//import com.example.movie.databinding.FragmentProfileBinding
//import com.example.movie.datastore.DataStoreManager
//import com.example.movie.room.User
//import com.example.movie.room.UserRepository
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.runBlocking
//import com.karumi.dexter.Dexter
//import com.karumi.dexter.MultiplePermissionsReport
//import com.karumi.dexter.PermissionToken
//import com.karumi.dexter.listener.PermissionRequest
//import com.karumi.dexter.listener.multi.MultiplePermissionsListener
//import com.rayxxv.letswatch.databinding.FragmentProfileBinding
//import java.io.File
//import java.io.FileOutputStream
//import java.io.IOException
//import java.io.OutputStream
//import java.util.*
//
//class ProfileFragment : DialogFragment() {
//    private var _binding: FragmentProfileBinding?= null
//    private val binding get() = _binding!!
//    private lateinit var repository: UserRepository
//    private lateinit var pref: DataStoreManager
//    private val args: ProfileFragmentArgs by navArgs()
//    private var imageUri: Uri? = null
//
//    companion object {
//        private const val GALLERY = 1
//        private const val CAMERA = 2
//        private const val IMAGE_DIRECTORY = "RegisterUserImage"
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentProfileBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        pref = DataStoreManager(requireContext())
//
//        binding.btnImage.setImageURI(args.user?.uri.toString().toUri())
//        binding.etUsername.setText(args.user?.username)
//        binding.etEmail.setText(args.user?.email)
//        binding.etPassword.setText(args.user?.password)
//
//        binding.btnUpdate.setOnClickListener {
//            val user = User(
//                args.user?.id,
//                binding.etUsername.text.toString(),
//                binding.etEmail.text.toString(),
//                binding.etPassword.text.toString(),
//                imageUri.toString()
//            )
//            lifecycleScope.launch(Dispatchers.IO) {
//                val result = repository.updateUser(user)
//                runBlocking(Dispatchers.Main) {
//                    if (result != 0){
//                        Toast.makeText(requireContext(), "Profile berhasil diupdate", Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(requireContext(), "Profile gagal diupdate", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//            findNavController().navigate(R.id.action_profileFragment_to_menuFragment2)
//        }
//
//        binding.btnLogout.setOnClickListener {
//            AlertDialog.Builder(requireContext())
//                .setTitle("Logout")
//                .setMessage("Apakah anda yakin ingin logout?")
//                .setPositiveButton("Ya") { dialog, _ ->
//                    dialog.dismiss()
//                    lifecycleScope.launch(Dispatchers.IO) {
//                        pref.deleteUserFromPref()
//                    }
//                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
//                }
//                .setNegativeButton("Batal"){ dialog, _ ->
//                    dialog.dismiss()
//                }
//                .show()
//        }
//        binding.btnImage.setOnClickListener {
//            val pictureDialog = androidx.appcompat.app.AlertDialog.Builder(view.context)
//            pictureDialog.setTitle("Select Action")
//            val pictureDialogItems = arrayOf(
//                "Select Photo From Galery",
//                "Capture photo from camera"
//            )
//            pictureDialog.setItems(pictureDialogItems) { _, which ->
//                when (which) {
//                    0 -> choosePhotoFromGalery()
//                    1 -> takePhotoFromCamera()
//                }
//
//            }
//
//            pictureDialog.show()
//        }
//
//    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == GALLERY) {
//                if (data != null) {
//                    val contentUri = data.data
//                    try {
//                        val selectedImageBitmap =
//                            MediaStore.Images.Media.getBitmap(
//                                requireActivity().contentResolver,
//                                contentUri
//                            )
//
//                        imageUri = saveImageToInternalStorage(selectedImageBitmap)
//                        Glide.with(binding.root)
//                            .asBitmap()
//                            .load(selectedImageBitmap)
//                            .circleCrop()
//                            .into(binding.btnImage)
//                        Log.e("Save Image: ", "path :: $imageUri")
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                        Toast.makeText(
//                            requireContext(),
//                            "Failed to Load the image from",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            } else if (requestCode == CAMERA) {
//                val thumbnail: Bitmap = data!!.extras!!.get("data") as Bitmap
//                imageUri = saveImageToInternalStorage(thumbnail)
//
//                Log.e("Save Image: ", "path :: $imageUri")
//                Glide.with(binding.root).asBitmap()
//                    .load(thumbnail)
//                    .circleCrop()
//                    .into(binding.btnImage)
//
//            }
//        }
//
//    }
//
//    private fun takePhotoFromCamera() {
//        Dexter.withActivity(requireActivity()).withPermissions(
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA
//        ).withListener(object : MultiplePermissionsListener {
//            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
//                if (report.areAllPermissionsGranted()) {
//                    val galleryIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    val fm: Fragment = this@ProfileFragment
//                    fm.startActivityForResult(galleryIntent, CAMERA)
//                }
//            }
//
//            override fun onPermissionRationaleShouldBeShown(
//                permissions: MutableList<PermissionRequest>,
//                token: PermissionToken
//            ) {
//                showRationalDialogForPermssions()
//            }
//        }).onSameThread().check()
//    }
//
//    private fun choosePhotoFromGalery() {
//        Dexter.withActivity(requireActivity()).withPermissions(
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        ).withListener(object : MultiplePermissionsListener {
//            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
//                if (report.areAllPermissionsGranted()) {
//                    val galleryIntent = Intent(
//                        Intent.ACTION_PICK,
//                        MediaStore.Images.Media.INTERNAL_CONTENT_URI
//                    )
//                    val fm: Fragment = this@ProfileFragment
//                    fm.startActivityForResult(galleryIntent, GALLERY)
//                }
//            }
//
//            override fun onPermissionRationaleShouldBeShown(
//                permissions: MutableList<PermissionRequest>,
//                token: PermissionToken
//            ) {
//                showRationalDialogForPermssions()
//            }
//        }).onSameThread().check()
//    }
//
//    private fun saveImageToInternalStorage(bitmap: Bitmap): Uri {
//        val wrapper = ContextWrapper(requireContext())
//        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
//        file = File(file, "${UUID.randomUUID()}.jpg")
//
//        try {
//            val stream: OutputStream = FileOutputStream(file)
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//            stream.flush()
//            stream.close()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//        return Uri.parse(file.absolutePath)
//    }
//
//    private fun showRationalDialogForPermssions() {
//        androidx.appcompat.app.AlertDialog.Builder(requireContext()).setMessage(
//            "" +
//                    "It Looks like you have turned off permission required" +
//                    "for this feature. It can be enabled under the" +
//                    "Applications Settings"
//        )
//            .setPositiveButton("GO TO SETTINGS") { _, _ ->
//                try {
//                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                    val uri = Uri.fromParts("package", activity?.packageName, null)
//                    intent.data = uri
//                    startActivity(intent)
//                } catch (e: ActivityNotFoundException) {
//                    e.printStackTrace()
//                }
//            }.setNegativeButton("CANCEL") { dialog, which ->
//                dialog.dismiss()
//            }.show()
//    }
//
////    private val startForProfileImageResult =
////        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
////            val resultCode = result.resultCode
////            val data = result.data
////            when (resultCode) {
////                Activity.RESULT_OK -> {
////                    //Image Uri will not be null for RESULT_OK
////                    imageUri = data?.data
////                    loadImage(imageUri)
////
////                }
////                ImagePicker.RESULT_ERROR -> {
////                    Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
////                }
////                else -> {
////
////                }
////            }
////        }
////
////    private fun openImagePicker() {
////        ImagePicker.with(this)
////            .crop()
////            .saveDir(
////                File(
////                    requireContext().externalCacheDir,
////                    "ImagePicker"
////                )
////            ) //Crop image(Optional), Check Customization for more option
////            .compress(1024)            //Final image size will be less than 1 MB(Optional)
////            .maxResultSize(
////                1080,
////                1080
////            )    //Final image resolution will be less than 1080 x 1080(Optional)
////            .createIntent { intent ->
////                startForProfileImageResult.launch(intent)
////            }
////    }
////
////    private fun loadImage(uri: Uri?) {
////        uri?.let {
////            binding.btnImage.setImageURI(it)
////        }
////    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
//}
