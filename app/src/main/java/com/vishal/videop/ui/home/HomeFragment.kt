package com.vishal.videop.ui.home

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vishal.videop.R
import com.vishal.videop.commons.base.BaseFragment
import com.vishal.videop.commons.base.observe
import com.vishal.videop.commons.utils.ResultWrapper
import com.vishal.videop.databinding.FragmentHomeBinding
import com.vishal.videop.ui.home.adapter.VideoListAdapter
import com.vishal.videop.ui.home.domain.model.VideoModel
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest


@Suppress("DEPRECATION")
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home),
    EasyPermissions.PermissionCallbacks {
    private val viewModel: HomeViewModel by viewModels()
    private val itemClickHandler: (path: String) -> Unit =
        {
            val bundle = Bundle()
            bundle.putString("path", it)
            findNavController().navigate(R.id.action_nav_home_to_videoPlayerFragment, bundle)
        }
    private val adapter by lazy { VideoListAdapter(itemClickHandler) }


    override fun onInitDataBinding() {
        viewBinding.recyclerView.adapter = adapter
        observe(viewModel.getVideoListLiveData, ::observeVideoList)
        checkPermission()
    }

    private fun checkPermission() {
        if (EasyPermissions.hasPermissions(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            viewModel.getVideoList()
        } else {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(this, 1001, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .setRationale("Permissions are Required to get videos!")
                    .setPositiveButtonText("OK")
                    .setNegativeButtonText("No")
                    .build()
            )
        }
    }

    private fun observeVideoList(result: ResultWrapper<MutableList<VideoModel>>) {
        when (result) {
            is ResultWrapper.Loading -> {
                viewBinding.progress = true
            }
            is ResultWrapper.Success -> {
                viewBinding.progress = false
                adapter.submitList(result.data)

            }
            is ResultWrapper.Failed -> {
                viewBinding.progress = false
                Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        viewModel.getVideoList()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }


}