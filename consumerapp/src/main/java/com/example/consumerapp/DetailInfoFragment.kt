package com.example.consumerapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide


class DetailInfoFragment : Fragment() {
    companion object {
        private const val ARG_USERNAME = "username"
        fun newInstance(username: String): DetailInfoFragment {
            val fragment = DetailInfoFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }

    }
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView: ImageView = view.findViewById(R.id.iv_user_detail_avatar)
        val tvFullName: TextView = view.findViewById(R.id.tv_user_detail_full_name)
        val tvUserName: TextView = view.findViewById(R.id.tv_user_detail_username)
        val tvLocation: TextView = view.findViewById(R.id.tv_user_detail_location)
        val tvCompany: TextView = view.findViewById(R.id.tv_user_detail_company)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBarDetail)
        progressBar.visibility = View.VISIBLE


        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        val userName = arguments?.getString(ARG_USERNAME)

        if (userName != null) {
            mainViewModel.setUsersProfile(userName)
        }
        activity?.let { it ->
            mainViewModel.getUsersDetail().observe(it, { userItems ->
                if (userItems != null) {
                    Glide.with(view.context)
                        .load(userItems.avatar)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(imageView)
                    tvUserName.text = userItems.userName
                    tvFullName.text = userItems.userFullName
                    tvCompany.text = userItems.company
                    tvLocation.text = userItems.location
                    progressBar.visibility = View.GONE
                }
            })
        }
    }
}
