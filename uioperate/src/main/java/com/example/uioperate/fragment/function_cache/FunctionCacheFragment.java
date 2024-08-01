package com.example.uioperate.fragment.function_cache;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.uioperate.R;
import com.example.utilsgather.list_guide.GuideItemEntity;
import com.example.utilsgather.list_guide.GuideSettings;
import com.example.utilsgather.logcat.LogUtil;

import java.util.List;

public class FunctionCacheFragment extends BaseFuncFragment{
    /**
     * 没有参数没有返回值的函数
     */
    public static final String FUNCTION_NO_PARAM_NO_RESULT = "FUNCTION_NO_PARAM_NO_RESULT";
    /**
     * 没有参数有返回值的函数
     */
    public static final String FUNCTION_NO_PARAM_HAS_RESULT = "FUNCTION_NO_PARAM_HAS_RESULT";
    /**
     * 有参数没有返回值的函数
     */
    public static final String FUNCTION_HAS_PARAM_NO_RESULT = "FUNCTION_HAS_PARAM_NO_RESULT";
    /**
     * 有参数有返回值的函数
     */
    public static final String EVENT_HAS_PARAM_HAS_RESULT = "EVENT_HAS_PARAM_HAS_RESULT";

    private TextView tvResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onCreateViewCalled();
        return inflater.inflate(R.layout.fragment_function_cache, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvResult = view.findViewById(R.id.tv_result);

        GuideSettings.set(view.findViewById(R.id.lv_launcher), new GuideItemEntity[] {
                new GuideItemEntity("调用activity方法(啥都没)", new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mFunctions.invokeFunc(FUNCTION_NO_PARAM_NO_RESULT);
                        } catch (FunctionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }),
                new GuideItemEntity("调用activity方法(没有参数有返回值)", new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = mFunctions.invokeFuncWithResult(FUNCTION_NO_PARAM_HAS_RESULT, String.class);
                            LogUtil.d("打印获得的返回值: " + result);
                        } catch (FunctionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }),
                new GuideItemEntity("调用activity方法(有参数没有返回值)", new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mFunctions.invokeFuncWithParam(FUNCTION_HAS_PARAM_NO_RESULT, 23);
                        } catch (FunctionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }),
                new GuideItemEntity("调用activity方法(有参数有返回值)", new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<Float> result = mFunctions.invokeFunctionWithParamAndResult(EVENT_HAS_PARAM_HAS_RESULT, 2333333333L, List.class);
                            for (Float f : result) {
                                LogUtil.d("打印List的元素: " + f);
                            }

                        } catch (FunctionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }),
        });
    }
}
