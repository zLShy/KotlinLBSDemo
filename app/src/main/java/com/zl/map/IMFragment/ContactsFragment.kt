package com.zl.map.IMFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import com.zl.map.R
import com.zl.map.db.Friend
import com.zl.map.pinyin.PinyinComparator
import com.zl.map.pinyin.SideBar
import io.rong.imkit.tools.CharacterParser
import java.util.ArrayList

/**
 * Created by zhangli on 2018/7/4.
 */
class ContactsFragment:Fragment() {


    private var mSelectableRoundedImageView: SelectableRoundedImageView? = null
    private var mNameTextView: TextView? = null
    private var mNoFriends: TextView? = null
    private var mUnreadTextView: TextView? = null
    private var mHeadView: View? = null
    private var mSearchEditText: EditText? = null
    private var mListView: ListView? = null
    private var mPinyinComparator: PinyinComparator? = null
    private var mSidBar: SideBar? = null


    /**
     * 中部展示的字母提示
     */
    private var mDialogTextView: TextView? = null

    private var mFriendList: MutableList<Friend>? = null
    private var mFilteredFriendList: List<Friend>? = null
    /**
     * 好友列表的 mFriendListAdapter
     */
    private var mFriendListAdapter: FriendListAdapter? = null
    /**
     * 汉字转换成拼音的类
     */
    private var mCharacterParser: CharacterParser? = null
    /**
     * 根据拼音来排列ListView里面的数据类
     */

    private var mId: String? = null
    private var mCacheName: String? = null

    private val CLICK_CONTACT_FRAGMENT_FRIEND = 2


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_address, container, false)
        initView(view)
        initData()
//        updateUI()
//        refreshUIListener()
        return view
    }

    private fun initView(view: View) {
        mSearchEditText = view!!.findViewById(R.id.search)
        mListView = view!!.findViewById(R.id.listview)
        mNoFriends = view!!.findViewById(R.id.show_no_friend)
        mSidBar = view!!.findViewById(R.id.sidrbar)
        mDialogTextView = view!!.findViewById(R.id.group_dialog)

        mSidBar!!.setTextView(mDialogTextView)
        val mLayoutInflater = LayoutInflater.from(activity)

        mHeadView = mLayoutInflater.inflate(R.layout.item_contact_list_header,
                null)
        mUnreadTextView = mHeadView!!.findViewById(R.id.tv_unread)

//        val newFriendsLayout = mHeadView!!.findViewById(R.id.re_newfriends) as RelativeLayout
//        val groupLayout = mHeadView!!.findViewById(R.id.re_chatroom) as RelativeLayout
//        val publicServiceLayout = mHeadView.findViewById(R.id.publicservice) as RelativeLayout
//        val selfLayout = mHeadView.findViewById(R.id.contact_me_item) as RelativeLayout
//        mSelectableRoundedImageView = mHeadView.findViewById(R.id.contact_me_img) as SelectableRoundedImageView
//        mNameTextView = mHeadView.findViewById(R.id.contact_me_name) as TextView
//        updatePersonalUI()
//        mListView.addHeaderView(mHeadView)
//        mNoFriends.setVisibility(View.VISIBLE)
//
//        selfLayout.setOnClickListener(this)
//        groupLayout.setOnClickListener(this)
//        newFriendsLayout.setOnClickListener(this)
//        publicServiceLayout.setOnClickListener(this)
        //设置右侧触摸监听
        mSidBar!!.setOnTouchingLetterChangedListener(object : SideBar.OnTouchingLetterChangedListener {

            override fun onTouchingLetterChanged(s: String) {
                //该字母首次出现的位置
                val position = mFriendListAdapter!!.getPositionForSection(s[0].toInt())
                if (position != -1) {
                    mListView!!.setSelection(position)
                }

            }
        })
    }

    private fun initData() {
        mFriendList = ArrayList()
        val adapter = FriendListAdapter(activity, mFriendList)
        mListView!!.setAdapter(adapter)
        mFilteredFriendList = ArrayList()
        //实例化汉字转拼音类
        mCharacterParser = CharacterParser.getInstance()
        mPinyinComparator = PinyinComparator.getInstance()
    }

//    private fun updateUI() {
//        SealUserInfoManager.getInstance().getFriends(object : SealUserInfoManager.ResultCallback<List<Friend>>() {
//            fun onSuccess(friendsList: List<Friend>) {
//                updateFriendsList(friendsList)
//            }
//
//            fun onError(errString: String) {
//                updateFriendsList(null)
//            }
//        })
//    }
}