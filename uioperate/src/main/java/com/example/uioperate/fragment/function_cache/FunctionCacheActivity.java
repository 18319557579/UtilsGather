package com.example.uioperate.fragment.function_cache;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.uioperate.R;
import com.example.utilsgather.logcat.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionCacheActivity extends BaseFuncActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_cache);


    }

    @Override
    public void setFunctionForFragment(int fragmentId) {
        if (fragmentId == R.id.fragment_function_cache) {
            FragmentManager fm = getSupportFragmentManager();
            BaseFuncFragment bff = (BaseFuncFragment) fm.findFragmentById(fragmentId);
            bff.setFunctions(
                    new Functions().addFunction(new Functions.FunctionNo(FunctionCacheFragment.FUNCTION_NO_PARAM_NO_RESULT) {
                        @Override
                        public void function() {
                            LogUtil.d("成功调用无参数无返回值方法");
                        }
                    }).addFunction(new Functions.FunctionWithResult<String>(FunctionCacheFragment.FUNCTION_NO_PARAM_HAS_RESULT) {
                        @Override
                        public String function() {
                            LogUtil.d("成功调用无参有返回值方法");
                            return "Activity给回的返回值";
                        }
                    }).addFunction(new Functions.FunctionWithParam<Integer>(FunctionCacheFragment.FUNCTION_HAS_PARAM_NO_RESULT) {

                        @Override
                        public void function(Integer integer) {
                            LogUtil.d("成功调用有参无返回值方法, 参数值: " + integer);
                        }
                    }).addFunction(new Functions.FunctionWithParamAndResult<List<Float>, Long>(FunctionCacheFragment.EVENT_HAS_PARAM_HAS_RESULT) {
                        @Override
                        public List<Float> function(Long data) {
                            LogUtil.d("成功调用有参有返回值方法, 参数值: " + data);

                            List<Float> floatList = new ArrayList<>();
                            floatList.add(0.1f);
                            floatList.add(1.2f);
                            floatList.add(3.3f);

                            return floatList;
                        }
                    })
            );
        }
    }
}