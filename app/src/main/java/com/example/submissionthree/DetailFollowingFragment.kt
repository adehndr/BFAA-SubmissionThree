package com.example.submissionthree

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submissionthree.dataclass.UserItems

class DetailFollowingFragment : Fragment() {
    companion object {
        private const val ARG_USERNAME = "username"
        fun newInstance(username: String): DetailFollowingFragment {
            val fragment = DetailFollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var adapter: UserAdapter
    private lateinit var rvFollowing: RecyclerView
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBarFollowing)
        progressBar.visibility = View.VISIBLE
        val userName = arguments?.getString(ARG_USERNAME)
        rvFollowing = view.findViewById(R.id.rv_following_users)
        rvFollowing.setHasFixedSize(true)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        rvFollowing.layoutManager = LinearLayoutManager(activity)
        rvFollowing.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItems) {
                val intent = Intent(activity, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.EXTRA_PERSON, data)
                startActivity(intent)
            }
        })


        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        if (userName != null) {
            progressBar.visibility = View.GONE
            mainViewModel.setUsersFollowing(userName)
        }
        activity?.let {
            mainViewModel.getUsersFollowing().observe(it, { userItems ->
                if (userItems != null) {
                    adapter.setData(userItems)
                }
            })
        }


    }

}