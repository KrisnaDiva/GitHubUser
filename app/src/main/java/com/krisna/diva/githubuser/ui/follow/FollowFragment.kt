package com.krisna.diva.githubuser.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.krisna.diva.githubuser.R
import com.krisna.diva.githubuser.data.remote.response.UsersItem
import com.krisna.diva.githubuser.databinding.FragmentFollowBinding
import com.krisna.diva.githubuser.ui.UserAdapter

class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private var position: Int = 0
    private var username: String? = null

    private val followViewModel: FollowViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        username?.let { followViewModel.get(it, position) }
        followViewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
        if (position == 1) {
            followViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            followViewModel.listFollowers.observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {
                    binding.tvEmpty.text = getString(R.string.no_followers)
                } else {
                    setUserData(it)
                }
            }
        } else {
            followViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            followViewModel.listFollowing.observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {
                    binding.tvEmpty.text = getString(R.string.not_following_anyone)
                } else {
                    setUserData(it)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUserData(userData: List<UsersItem>) {
        val adapter = UserAdapter()
        adapter.submitList(userData)
        binding.rvFollow.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}
