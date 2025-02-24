package com.example.mobiletest.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * “非粘性”状态的MutableLiveData，支持用于事件传递，可避免数据倒灌等情况发生
 * 主要特点是observe方法被调用时，不会回调onChanged事件
 */
class NonStickyMutableLiveData<T> : MutableLiveData<T> {
    constructor(): super()

    constructor(value: T): super(value)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        hook(observer)
        super.observe(owner, observer)
    }

    /**
     * 通过反射保持Observer的mLastVersion值与LiveData的值相同, 实现LiveData的“非粘性”状态
     * @param observer observer实例
     */
    private fun hook(observer: Observer<*>) {
        val classLiveData = LiveData::class.java
        val fieldObservers: Field = classLiveData.getDeclaredField("mObservers")
        fieldObservers.isAccessible = true
        val objectObservers: Any = fieldObservers.get(this) ?: return
        val classObservers: Class<*> = objectObservers.javaClass
        val methodGet: Method = classObservers.getDeclaredMethod("get", Any::class.java)
        methodGet.isAccessible = true
        val objectWrapperEntry: Any = methodGet.invoke(objectObservers, observer) ?: return
        var objectWrapper: Any? = null
        if (objectWrapperEntry is Map.Entry<*, *>) {
            objectWrapper = objectWrapperEntry.value
        }
        if (objectWrapper == null) {
            throw NullPointerException("Wrapper can not be bull!")
        }
        val classObserverWrapper: Class<*> = objectWrapper.javaClass.superclass ?: return
        val fieldLastVersion: Field = classObserverWrapper.getDeclaredField("mLastVersion")
        fieldLastVersion.isAccessible = true
        //get livedata's version
        val fieldVersion: Field = classLiveData.getDeclaredField("mVersion")
        fieldVersion.isAccessible = true
        val objectVersion: Any = fieldVersion.get(this) ?: return
        //set wrapper's version
        fieldLastVersion.set(objectWrapper, objectVersion)
    }
}