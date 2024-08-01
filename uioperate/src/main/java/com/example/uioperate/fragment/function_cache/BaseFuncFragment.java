package com.example.uioperate.fragment.function_cache;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.utilsgather.lifecycle_callback.LifecycleLogFragment;

public class BaseFuncFragment extends LifecycleLogFragment {



    protected BaseFuncActivity mBaseActivity;

    protected Functions mFunctions;

    public void setFunctions(Functions functions) {
        this.mFunctions = functions;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getActivity() instanceof BaseFuncActivity) {
            mBaseActivity = (BaseFuncActivity) getActivity();
            mBaseActivity.setFunctionForFragment(getId());
        }
    }
}
