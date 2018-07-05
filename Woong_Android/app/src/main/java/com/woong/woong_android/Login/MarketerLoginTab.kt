package com.woong.woong_android.Login
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.woong.woong_android.Join.Marketer.TermsActivity
import com.woong.woong_android.R
import kotlinx.android.synthetic.main.fragment_marketer_login.view.*

class MarketerLoginTab : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_marketer_login, container, false)
        /*
        판매자의 홈 액티비티로 전환되게 Intent 수정해야함
        v.btn_login_marketer_login.setOnClickListener {
            val intent = Intent(getActivity(), MainActivity::class.java)    // getActivity: 현재 액티비티를 가져옴
            startActivity(intent)   // 전환될 액티비티로 넘어갈때
        }*/
        v.tv_signup_marketer_login.setOnClickListener {
            val intent = Intent(getActivity(), TermsActivity::class.java)
            startActivity(intent)   // 전환될 액티비티로 넘어갈때
        }
        return v
    }
}