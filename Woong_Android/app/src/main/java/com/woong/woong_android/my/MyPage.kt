package com.woong.woong_android.my

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.woong.woong_android.MainActivity
import com.woong.woong_android.R
import com.woong.woong_android.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_my_page.view.*

class MyPage : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_my_page,container,false)
        v.btn_logout_mypage.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)   // 전환될 액티비티로 넘어갈때
            activity!!.finish()

        }
        return v
    }
}