package com.example.articles.ui.base.viewmodel

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.lifecycle.*
import com.example.articles.extension.observeCleaner
import com.example.articles.navigator.NavigatorHelper
import com.example.domain.livedata.SafeMutableLiveData
import com.example.domain.model.ErrorObject
import com.example.domain.model.Resource
import com.example.domain.model.State
import com.example.domain.model.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

abstract class BaseViewModel : ViewModel() {

    @NonNull
    val stateLiveData = SafeMutableLiveData<State>()

    private var isFirstTimeUiCreate = true

    protected lateinit var lifecycleOwner: LifecycleOwner

    protected var navigatorHelper: NavigatorHelper? = null

    /**
     * called after fragment / activity is created with input bundle arguments
     * @param bundle argument data
     */
    @CallSuper
    fun onCreate(
        lifecycleOwner: LifecycleOwner,
        bundle: Bundle?,
        navigatorHelper: NavigatorHelper) {
        this.lifecycleOwner = lifecycleOwner
        this.navigatorHelper = navigatorHelper
        if (isFirstTimeUiCreate) {
            onFirstTimeUiCreate(lifecycleOwner, bundle)
            isFirstTimeUiCreate = false
        }
    }

    /**
     * Called when UI create for first time only, since activity / fragment might be rotated,
     * we don't need to re-init data, because view model will survive, data aren't destroyed
     * @param bundle
     */
    abstract fun onFirstTimeUiCreate(lifecycleOwner: LifecycleOwner, bundle: Bundle?)

    /**
     * It is importance to un-reference activity / fragment instance after they are destroyed
     * For situation of configuration changes, activity / fragment will be destroyed and recreated,
     * but view model will survive, so if we don't un-reference them, memory leaks will occur
     */
    open fun onDestroyView() {

    }

    internal fun <T> launchOnViewModelScope(block: suspend () -> LiveData<T>): LiveData<T> {
        return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(block())
        }
    }

    @MainThread
    protected fun publishState(state: State) {
        stateLiveData.setValue(state)
    }

    /**
     * Loading, error, success status will be updated automatically via [.stateLiveData] which should be observed
     * by fragments / activities to update UI appropriately
     * @param showProgress true if should show progress when executing, false if not
     */
    @MainThread
    protected fun <T> LiveData<Resource<T>>?.withState(
        showProgress: Boolean? = null,
        viewLifecycleOwner: LifecycleOwner,
        callBack: ((data: T?) -> Unit)?
    ) {
        this.observeCleaner(viewLifecycleOwner) {
            if (showProgress == true && it?.state != null) publishState(it.state)
            if (it?.state == State.success()) callBack?.invoke(it.data)
        }
    }

    @MainThread
    protected fun <T> LiveData<Resource<T>>?.withState(
        viewLifecycleOwner: LifecycleOwner,
        callBack: ((data: T?) -> Unit)?
    ) {
        withState(true, viewLifecycleOwner, callBack)
    }

    /**
     * Add and execute an resource Flow created by
     * see [jp.ne.goo.doctor.data.source.BaseDataSource]
     * Loading, error, success status will be updated automatically via [.stateLiveData] which should be observed
     * by fragments / activities to update UI appropriately
     * @param showProgress true if should show progress when executing, false if not
     * @param onDataSuccess consume response data
     * @param <T> type of response
    </T> */

    suspend fun <T> Flow<Resource<T>>.execute(
        showProgress: Boolean,
        onDataSuccess: (T?) -> Unit,
        onDataError: ((ErrorObject?) -> Unit)? = null
    ) =
        catch { cause ->
            onDataError?.invoke(ErrorObject.getError(cause))
        }.collect {
            if (it.status == Status.SUCCESS) {
                val data = it.data
                onDataSuccess.invoke(data)
            }
            if (it.status == Status.LOADING && !showProgress) {
                // do nothing if progress showing is not allowed
            } else {
                publishState(it.state)
                if (it.status == Status.ERROR) onDataError?.invoke(it.error)
            }
        }

    suspend fun <T> Flow<Resource<T>>.execute(showProgress: Boolean, onDataSuccess: (T?) -> Unit) =
        execute(showProgress, onDataSuccess, null)

    suspend fun <T> Flow<Resource<T>>.execute(showProgress: Boolean) =
        execute(showProgress, onDataSuccess = {}, null)
}