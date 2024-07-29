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
    protected final String canonicalName = getClass().getCanonicalName();

    @Override
    public void onAttach(@NonNull Context context) {
        LogUtil.d("Daisy", canonicalName + " 回调 onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.d("Daisy", canonicalName + " 回调 onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d("Daisy", canonicalName + " 回调 onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogUtil.d("Daisy", canonicalName + " 回调 onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogUtil.d("Daisy", canonicalName + " 回调 onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        LogUtil.d("Daisy", canonicalName + " 回调 onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        LogUtil.d("Daisy", canonicalName + " 回调 onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogUtil.d("Daisy", canonicalName + " 回调 onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        LogUtil.d("Daisy", canonicalName + " 回调 onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        LogUtil.d("Daisy", canonicalName + " 回调 onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LogUtil.d("Daisy", canonicalName + " 回调 onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        LogUtil.d("Daisy", canonicalName + " 回调 onDetach");
        super.onDetach();
    }
}
