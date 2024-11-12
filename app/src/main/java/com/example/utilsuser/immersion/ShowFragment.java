package com.example.utilsuser.immersion;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.utilsgather.lifecycle_callback.LifecycleLogFragment;
import com.example.utilsgather.list_guide.GuideAdapter;
import com.example.utilsgather.list_guide.GuideItemEntity;
import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.R;

import java.util.ArrayList;
import java.util.List;

public class ShowFragment extends LifecycleLogFragment {

    @LayoutRes int mResource;
    int mFragmentTag;
    //    private InnerItemEntity[] mEntities;

    OnFragmentInteractionListener mListener;

    static ShowFragment newInstance(@LayoutRes int resource, int fragmentTag) {
        Bundle bundle = new Bundle();
        bundle.putInt("resource", resource);
        bundle.putInt("fragment_tag", fragmentTag);
//        bundle.putSerializable("entities", entities);
        ShowFragment showFragment = new ShowFragment();
        showFragment.setArguments(bundle);
        return showFragment;
    }

    // 弄个默认的布局id
    static ShowFragment newInstance(int fragmentTag) {
        return newInstance(R.layout.activity_email, fragmentTag);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateCalled();
        Bundle arguments = getArguments();
        if (arguments != null) {
            mResource = arguments.getInt("resource");
            mFragmentTag = arguments.getInt("fragment_tag");
//            mEntities = (InnerItemEntity[]) arguments.getSerializable("entities");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onCreateViewCalled();
        return inflater.inflate(mResource, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        onViewCreatedCalled();
        ListView listView = view.findViewById(R.id.lv_container);
        listView.setAdapter(new InnerAdapter(mListener.getEntities(mFragmentTag)));

        /*Bundle arguments = getArguments();
        ArrayList<Integer> colors = (ArrayList<Integer>)arguments.getSerializable("color");
        Integer item = arguments.getInt("item");

        view.findViewById(R.id.fl_show).setBackgroundResource(colors.get(item));
        view.<TextView>findViewById(R.id.tv_show).setText("第" + item + "个界面");*/
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        LogUtil.d(getPrefix() + " 回调 onViewStateRestored");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        LogUtil.d(getPrefix() + " 回调 onSaveInstanceState");
//        super.onSaveInstanceState(outState);
    }

    public interface OnFragmentInteractionListener {
        InnerItemEntity[] getEntities(int fragmentTag);
    }

}
