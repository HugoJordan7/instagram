package com.example.instagram.feature.search

import android.net.Uri
import com.example.instagram.common.base.BasePresenter
import com.example.instagram.common.base.BaseView

interface Search {

    interface View: BaseView<Presenter> {}

    interface Presenter: BasePresenter {}

}