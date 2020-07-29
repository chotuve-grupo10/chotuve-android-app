package com.example.chotuve_android_client.ui.myUserProfile

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.chotuve_android_client.MainActivity
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.tools.adapters.FriendsAdapter
import com.example.chotuve_android_client.tools.adapters.PageAdapter
import com.example.chotuve_android_client.ui.login.CHOTUVE_SHARED_PREFS
import com.example.chotuve_android_client.ui.login.LoginActivity
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class MyUserProfileFragment : Fragment() {

    private lateinit var myUserProfileViewModel: MyUserProfileViewModel
    val TAG = "MyUserProfileFrag"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myUserProfileViewModel =
            ViewModelProviders.of(this).get(MyUserProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my_user_profile, container, false)
        val textView: TextView = root.findViewById(R.id.textView)
        myUserProfileViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        setHasOptionsMenu(true)

        val viewPager = root.findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = this!!.childFragmentManager?.let { PageAdapter(it, myUserProfileViewModel) }

        val tabLayout = root.findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)

        myUserProfileViewModel.getUserProfilePicture()
        myUserProfileViewModel.URL.observe(viewLifecycleOwner, Observer {
            if (it != "") {
                val profilePictureImageView = root.findViewById<CircleImageView>(R.id.profile_picture)
                Picasso
                    .get()
                    .load(it) // https://matthewjameskirk.co.uk/Images/video.jpg
                    .into(profilePictureImageView)
            }
        })
        myUserProfileViewModel.getFriendsFromServer()
        myUserProfileViewModel.getVideos()

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_user_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.my_personal_data) {

            Log.d(TAG, "Esta es tu data personal")
            val editProfileIntent : Intent = Intent(this.context, EditProfileActivity::class.java)
            startActivityForResult(editProfileIntent, 0)
        }
        else if(id == R.id.log_out) {
            Log.d(TAG, "Cerrando sesión")
            val editor = this.activity!!
                .getSharedPreferences(CHOTUVE_SHARED_PREFS, Context.MODE_PRIVATE)
                .edit()
            editor.clear()
            val confirm = editor.commit()
            if (confirm) {
                val loginLauncher = this!!.activity!!.
                    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    val intent = Intent(this.context, MainActivity::class.java)
                    startActivity(intent)
                    this.activity!!.finish()
                }.launch(Intent(this.context, LoginActivity::class.java))
            }
            else {
                Log.e(TAG, "Algo salió mal cerrando sesión")
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }
//    val editProfileLauncher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()) {
//        Log.d("LoginActivity", "Resultado de recuperar la contraseña: " + it.resultCode)
//    }

}