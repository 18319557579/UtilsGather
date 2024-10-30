package com.example.utilsuser.immersion;

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


import com.example.utilsgather.list_guide.GuideAdapter;
import com.example.utilsgather.list_guide.GuideItemEntity;
import com.example.utilsuser.R;

import java.util.ArrayList;
import java.util.List;

public class ShowFragment extends Fragment {

    @LayoutRes int mResource;
    private InnerItemEntity[] mEntities;

    static ShowFragment newInstance(@LayoutRes int resource, InnerItemEntity[] entities) {
        Bundle bundle = new Bundle();
        bundle.putInt("resource", resource);
        bundle.putSerializable("entities", entities);
        ShowFragment showFragment = new ShowFragment();
        showFragment.setArguments(bundle);
        return showFragment;
    }

    // 弄个默认的布局id
    static ShowFragment newInstance(InnerItemEntity[] entities) {
        return newInstance(R.layout.activity_email, entities);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mResource = arguments.getInt("resource");
            mEntities = (InnerItemEntity[]) arguments.getSerializable("entities");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(mResource, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ListView listView = view.findViewById(R.id.lv_container);
        listView.setAdapter(new InnerAdapter(mEntities));

        /*Bundle arguments = getArguments();
        ArrayList<Integer> colors = (ArrayList<Integer>)arguments.getSerializable("color");
        Integer item = arguments.getInt("item");

        view.findViewById(R.id.fl_show).setBackgroundResource(colors.get(item));
        view.<TextView>findViewById(R.id.tv_show).setText("第" + item + "个界面");*/
    }
}
