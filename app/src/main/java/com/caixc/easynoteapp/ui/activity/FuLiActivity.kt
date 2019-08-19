package com.caixc.easynoteapp.ui.activity

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.caixc.easynoteapp.adapter.PictureAdapter
import com.caixc.easynoteapp.base.BaseActivity
import com.caixc.easynoteapp.global.Urls
import com.caixc.easynoteapp.net.MyDefaultObserver
import com.caixc.easynoteapp.net.RetrofitClient
import com.caixc.easynoteapp.net.service.PicService
import com.caixc.easynoteapp.widget.StaggeredDividerItemDecoration
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_templature.*
import android.R


class FuLiActivity : BaseActivity() {
    override fun setLayout(): Int = com.caixc.easynoteapp.R.layout.activity_fuli
    override fun getData() {
        smart_refresh_layout.finishRefresh()
        indexs.clear()
        list.clear()
        index = 0
        requestDir()
    }

    override fun initView() {
        initRecyclerView()
        initRefreshLayout()
    }

    private fun initRefreshLayout() {
        smart_refresh_layout.setEnableRefresh(true)
        smart_refresh_layout.setEnableLoadMore(true)
        smart_refresh_layout.setRefreshHeader(ClassicsHeader(this))
        smart_refresh_layout.setRefreshFooter(ClassicsFooter(this))
        smart_refresh_layout.setOnRefreshListener {
            smart_refresh_layout.finishRefresh()
            list.clear()
            getData()
        }
        smart_refresh_layout.setOnLoadMoreListener {
            index++
            if (index == indexs.size) {
                smart_refresh_layout.finishLoadMoreWithNoMoreData()
            } else {
                smart_refresh_layout.finishLoadMore()
                requestPics(indexs[index])
            }
        }
    }

    var list = mutableListOf<String>()
    var indexs = mutableListOf<String>()
    var index: Int = 0
    lateinit var adapter: PictureAdapter
    private fun initRecyclerView() {
        var spanCount = 2
        var layoutmanager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
        layoutmanager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recycler_view.layoutManager = layoutmanager
        recycler_view.itemAnimator = null
        recycler_view.addItemDecoration(StaggeredDividerItemDecoration(this, 10))
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val first = IntArray(spanCount)
                layoutmanager.findFirstCompletelyVisibleItemPositions(first)
                if (newState === RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                    layoutmanager.invalidateSpanAssignments()
                }
            }
        })
        adapter = PictureAdapter(com.caixc.easynoteapp.R.layout.item_picture, list)
        recycler_view.adapter = adapter
        adapter.setOnItemClickListener { _, _, position ->
            list[position]
//            var intent = Intent(activity, TemplatureActivity::class.java)
//            intent.putExtra(TemplatureActivity.BEAN, list[position])
//            startActivity(intent)
        }

        adapter.setEmptyView(com.caixc.easynoteapp.R.layout.item_empty, recycler_view)
    }


    private fun requestDir() {
        showDialog()
        RetrofitClient()
            .getInstance(Urls.HOST)
            .create(PicService::class.java)
            .getPicDir()
            .doOnSubscribe { addDisposable(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<MutableList<String>>(this) {
                override fun onNext(t: MutableList<String>) {
                    hideDialog()
                    indexs.clear()
                    indexs.addAll(t.filter {
                        !it.contains("header")
                    })
                    requestPics(indexs[index])
                }
            })
    }

    private fun requestPics(dir: String) {
        showDialog()
        RetrofitClient()
            .getInstance(Urls.HOST)
            .create(PicService::class.java)
            .getPics(dir)
            .doOnSubscribe { addDisposable(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MyDefaultObserver<List<String>>(this) {
                override fun onNext(t: List<String>) {
                    hideDialog()
                    list.addAll(t)
                    adapter.notifyItemRangeChanged(0, list.size)
                }
            })
    }
}