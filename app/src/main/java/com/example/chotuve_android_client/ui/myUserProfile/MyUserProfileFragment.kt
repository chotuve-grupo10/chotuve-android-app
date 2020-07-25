package com.example.chotuve_android_client.ui.myUserProfile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.tools.adapters.FriendsAdapter
import com.example.chotuve_android_client.ui.recovery.RecoveryActivity
import kotlinx.android.synthetic.main.fragment_user_profile.*

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
        val root = inflater.inflate(R.layout.fragment_user_profile, container, false)
        val textView: TextView = root.findViewById(R.id.textView)
        myUserProfileViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        setHasOptionsMenu(true)

        myUserProfileViewModel.getFriendsFromServer()
        myUserProfileViewModel.friends.observe(viewLifecycleOwner, Observer { friends ->
            recyclerview_user_profile_friends.also {
                it.layoutManager = LinearLayoutManager(requireContext())
//                it.setHasFixedSize(true)
                it.adapter =
                    FriendsAdapter(
                        friends
                    )
            }

        })

        return root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_user_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.my_personal_data) {
            // sucede la magia
            Log.d(TAG, "Esta es tu data personal")
            val editProfileIntent : Intent = Intent(this.context, EditProfileActivity::class.java)
            startActivityForResult(editProfileIntent, 0)
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