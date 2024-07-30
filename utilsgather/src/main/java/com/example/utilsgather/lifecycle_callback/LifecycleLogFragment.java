package com.example.utilsgather.lifecycle_callback;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.utilsgather.logcat.LogUtil;

public class LifecycleLogFragment extends Fragment {
//    protected final String canonicalName = getClass().getCanonicalName();

    //如果打印的日志需要tag前缀，使用有参的构造函数
    private String tag;
    public LifecycleLogFragment(String tag) {
        this.tag = tag;
    }

    //如果打印的日志不需要tag前缀，使用无参的构造函数
    public LifecycleLogFragment() {}


    @Override
    public void onAttach(@NonNull Context context) {
        onAttachCalled();
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        onCreateCalled();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onCreateViewCalled();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        onViewCreatedCalled();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        onActivityCreatedCalled();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        onStartCalled();
        super.onStart();
    }

    @Override
    public void onResume() {
        onResumeCalled();
        super.onResume();
    }

    @Override
    public void onPause() {
        onPauseCalled();
        super.onPause();
    }

    @Override
    public void onStop() {
        onStopCalled();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        onDestroyViewCalled();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        onDestroyCalled();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        onDetachCalled();
        super.onDetach();
    }

    //如果子类继承 LifecycleLogFragment 时，有些方法不super的话，那么日志就打印不了了。所以就要手动调用下面这些方法了：

    private String getPrefix() {
        String canonicalName = getClass().getCanonicalName();
        if (tag == null) {
            return canonicalName;
        } else {
            return canonicalName + "-" + tag;
        }
    }

    protected void onAttachCalled() {
        LogUtil.d("Daisy", getPrefix() + " 回调 onAttach");
    }
    protected void onCreateCalled() {
        LogUtil.d("Daisy", getPrefix() + " 回调 onCreate");
    }
    protected void onCreateViewCalled() {
        LogUtil.d("Daisy", getPrefix() + " 回调 onCreateView");
    }
    protected void onViewCreatedCalled() {
        LogUtil.d("Daisy", getPrefix() + " 回调 onViewCreated");
    }
    protected void onActivityCreatedCalled() {
        LogUtil.d("Daisy", getPrefix() + " 回调 onActivityCreated");
    }
    protected void onStartCalled() {
        LogUtil.d("Daisy", getPrefix() + " 回调 onStart");
    }
    protected void onResumeCalled() {
        LogUtil.d("Daisy", getPrefix() + " 回调 onResume");
    }
    protected void onPauseCalled() {
        LogUtil.d("Daisy", getPrefix() + " 回调 onPause");
    }
    protected void onStopCalled() {
        LogUtil.d("Daisy", getPrefix() + " 回调 onStop");
    }
    protected void onDestroyViewCalled() {
        LogUtil.d("Daisy", getPrefix() + " 回调 onDestroyView");
    }
    protected void onDestroyCalled() {
        LogUtil.d("Daisy", getPrefix() + " 回调 onDestroy");
    }
    protected void onDetachCalled() {
        LogUtil.d("Daisy", getPrefix() + " 回调 onDetach");
    }
}
