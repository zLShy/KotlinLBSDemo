package com.zl.map

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import com.zl.map.IMFragment.CenterFragment
import com.zl.map.IMFragment.HomeFragment
import com.zl.map.IMFragment.NoticeFragment
import com.zl.map.present.IMPresenterCompl
import com.zl.map.view.IIMView
import io.rong.imkit.RongIM
import io.rong.imkit.RongContext
import io.rong.imkit.fragment.ConversationListFragment
import io.rong.imlib.model.Conversation


class IMActivity : BaseActivity(),IIMView,ViewPager.OnPageChangeListener{
    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

    }

    var mIMPresenterCompl: IMPresenterCompl? = null
    var mViewPager: ViewPager? = null
    var mFragments: List<Fragment> = listOf()
    var mulist = mFragments.toMutableList();
    var mLists:ArrayList<Fragment> = ArrayList()
    private var mConversationListFragment: ConversationListFragment? = null

    override fun showConnectInfo(t: Any) {
        Log.e("TGA",t.toString())
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_im
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mIMPresenterCompl = IMPresenterCompl(this@IMActivity)
        mIMPresenterCompl!!.doConnect()


        mViewPager = findViewById(R.id.im_viewpager)

        mLists.add(initConversationList())
        mLists.add(HomeFragment())
        mLists.add(NoticeFragment())
        mLists.add(CenterFragment())
        val fragmentPagerAdapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {

                return mLists.get(position)
            }

            override fun getCount(): Int {
                return mLists.size
            }

        }

        mViewPager!!.adapter = fragmentPagerAdapter

    }


    private fun initConversationList(): Fragment {
        if (mConversationListFragment == null) {
            val listFragment = ConversationListFragment()
//            listFragment.setAdapter(ConversationListAdapterEx(RongContext.getInstance()))
            val uri: Uri
//            if (isDebug) {
                uri = Uri.parse("rong://" + applicationInfo.packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
//                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
//                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统
                        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                        .build()
//                mConversationsTypes = arrayOf<Conversation.ConversationType>(Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP, Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE, Conversation.ConversationType.SYSTEM, Conversation.ConversationType.DISCUSSION)

//            } else {
//                uri = Uri.parse("rong://" + applicationInfo.packageName).buildUpon()
//                        .appendPath("conversationlist")
//                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
//                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
//                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
//                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
//                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
//                        .build()
//                mConversationsTypes = arrayOf<Conversation.ConversationType>(Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP, Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE, Conversation.ConversationType.SYSTEM)
//            }
            listFragment.uri = uri
            mConversationListFragment = listFragment
            return listFragment
        } else {
            return mConversationListFragment!!
        }
    }


}
