package ru.veider.gitclient.ui.rxbutton

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class RxButton : AppCompatButton {

    constructor(context: Context?):super(context!!)
    constructor(context: Context?, attrs:AttributeSet?):super(context!!, attrs)
    constructor(context: Context?, attrs:AttributeSet?, defStyleAttr:Int):super(context!!, attrs, defStyleAttr)

    var onClickObserver: Observable<Boolean> = BehaviorSubject.create()

    override fun performClick(): Boolean {
        (onClickObserver as BehaviorSubject).onNext(true)
        return super.performClick()
    }
}