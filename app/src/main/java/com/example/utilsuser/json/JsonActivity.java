package com.example.utilsuser.json;

import android.os.Bundle;
import android.widget.ListView;

import com.example.utilsgather.json.GsonUtil;
import com.example.utilsgather.lifecycle_callback.LifecycleLogActivity;
import com.example.utilsgather.list_guide.GuideItemEntity;
import com.example.utilsgather.list_guide.GuideSettings;
import com.example.utilsuser.R;
import com.example.utilsuser.xlog.XLogConstant;
import com.example.utilsuser.xlog.XLogGlobal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class JsonActivity extends LifecycleLogActivity {
    String partsListJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_common);

        ListView listView = findViewById(R.id.lv_launcher);
        GuideSettings.set(listView, new GuideItemEntity[]{
                new GuideItemEntity("利用Gson，将List转为Json", () -> {
                    List<PartShowNumBean> partsList = Arrays.asList(
                            new PartShowNumBean("one", 5),
                            new PartShowNumBean("two", 6),
                            new PartShowNumBean("three", 10)
                    );
                    partsListJson = GsonUtil.toJson(partsList);
                    XLogGlobal.logger(XLogConstant.Normal).d("Json: " + partsListJson);
                }),
                new GuideItemEntity("利用Gson，将List转为Json（直接使用new TypeToken()）", () -> {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<PartShowNumBean>>(){}.getType();
                    List<PartShowNumBean> list = gson.fromJson(partsListJson, type);
                    list.forEach(new Consumer<PartShowNumBean>() {
                        @Override
                        public void accept(PartShowNumBean partShowNumBean) {
                            XLogGlobal.logger(XLogConstant.Normal).d(partShowNumBean);
                        }
                    });
                }),

                /* 为什么在可以直接用new TypeToken()获得Type的情况下，还要用我们封装的工具中的TypeToken.getParameterized()方法呢？
                因为TypeToken.getParameterized()方法就是为了类型参数在编译时不可用的情况，如果我们要写一个方法来封装功能的话，我们是没有办法
                传List<PartShowNumBean>这样的参数给new TypeToken()的泛型类型的，只能在编译器去确定。
                所以在设计我们的工具类是，我们使用了TypeToken.getParameterized()，也可以说它在泛型类型上，是完全包含new TypeToken()的功能的
                 */
                new GuideItemEntity("利用Gson，将List转为Json（使用封装的工具）", () -> {
                    List<PartShowNumBean> list = GsonUtil.fromJsonToList(partsListJson, PartShowNumBean.class);
                    list.forEach(new Consumer<PartShowNumBean>() {
                        @Override
                        public void accept(PartShowNumBean partShowNumBean) {
                            XLogGlobal.logger(XLogConstant.Normal).d(partShowNumBean);
                        }
                    });
                }),
        });
    }
}
