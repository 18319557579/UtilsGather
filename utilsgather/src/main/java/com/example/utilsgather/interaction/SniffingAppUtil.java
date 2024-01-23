package com.example.utilsgather.interaction;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.example.utilsgather.context.ApplicationGlobal;
import com.example.utilsgather.logcat.LogUtil;

public class SniffingAppUtil {

    /**
     * 获取该url是要跳转到哪一个App的（通常这类url都是该app自己定义的，例如：
     * xhsdiscover://search/result?keyword=%E5%B9%BF%E4%B8%9C%E6%94%BB%E7%95%A5&open_url=baidu&groupid=60f954717ae4040001eabf05&mode=openurl&source=landingpage）
     * openapp.jdmobile://virtual?params=%7B%22des%22%3A%22HomePage%22%2C%22from%22%3A%22search%22%2C%22category%22%3A%22jump%22%2C%22sourceType%22%3A%22JSHOP_SOURCE_TYPE%22%2C%22sourceValue%22%3A%22JSHOP_SOURCE_VALUE%22%2C%22M_sourceFrom%22%3A%22index%22%2C%22msf_type%22%3A%22auto%22%2C%22m_param%22%3A%7B%22m_source%22%3A%220%22%2C%22event_series%22%3A%7B%7D%2C%22jda%22%3A%22122270672.1700471577426741366998.1700471577.1700471577.1700471577.1%22%2C%22usc%22%3A%22direct%22%2C%22ucp%22%3A%22-%22%2C%22umd%22%3A%22none%22%2C%22utr%22%3A%22-%22%2C%22jdv%22%3A%22122270672%7Cdirect%7C-%7Cnone%7C-%7C1700471577429%22%2C%22ref%22%3A%22https%3A%2F%2Fm.jd.com%2F%22%2C%22psn%22%3A%221700471577426741366998%7C1%22%2C%22psq%22%3A1%2C%22unpl%22%3A%22%22%2C%22pc_source%22%3A%22%22%2C%22mba_muid%22%3A%221700471577426741366998%22%2C%22mba_sid%22%3A%2217004715774857195937526992602%22%2C%22std%22%3A%22MO-J2011-1%22%2C%22par%22%3A%22%22%2C%22event_id%22%3A%22MDownLoadFloat_AppArouse%22%2C%22mt_xid%22%3A%22%22%2C%22mt_subsite%22%3A%22%22%2C%22YINLIUhuanqi%22%3A%22https%3A%2F%2Fm.jd.com%2F%22%7D%2C%22SE%22%3A%7B%22mt_subsite%22%3A%22%22%2C%22__jdv%22%3A%22122270672%7Cdirect%7C-%7Cnone%7C-%7C1700471577429%22%2C%22unpl%22%3A%22%22%2C%22__jda%22%3A%22122270672.1700471577426741366998.1700471577.1700471577.1700471577.1%22%7D%7D
     * baiduboxapp://v11/appTab/select?item=home&upgrade=0&params=%7b%22channel%22%3a%228%22%2c%22tab_info%22%3a%7b%22id%22%3a%228%22%2c%22name%22%3a%22%e7%83%ad%e6%a6%9c%22%2c%22canDelete%22%3a%220%22%7d%2c%22tab_extend_info%22%3a%7b%22invokeFrom%22%3a%22Huawei%22%7d%2c%22refresh%22%3a%221%22%7d&next=baiduboxapp%3A%2F%2Fv1%2Fbrowser%2Fopen%3Furl%3Dhttps%253A%252F%252Fm.baidu.com%252Fs%253Fword%253D%2525E7%252599%2525BE%2525E5%2525BA%2525A6%2525E7%252583%2525AD%2525E6%252590%25259C%2526tn%2526from%253D1020854c%2526sa%253Dtre_dl_gh_logo_wj%2526rfrom%253D1023197w%2526rchannel%253D1023206g%26newwindow%3D0%26simple%3D1%26fromwise%3D1&jumpTime=1700471263982&concernHash=901560455&needlog=1&logargs=%7B%22source%22%3A%221023197w%22%2C%22from%22%3A%22openbox%22%2C%22page%22%3A%22chrome%22%2C%22type%22%3A%22%22%2C%22value%22%3A%22%22%2C%22channel%22%3A%221023206g%22%2C%22extlog%22%3A%7B%22ext_content%22%3A%7B%22from%22%3A%22844b%22%2C%22browserid%22%3A%2224%22%2C%22qid%22%3A%22%22%2C%22qidsids%22%3A%22110085_265881_263619_275733_259642_256739_269049_280899_281191_280650_281810_281867_281896_281685_281993_275097_274948_282191_282170_278414_282435_279010_281818_282521_282571_282628_282053_282758_279702_280272_282793_282232_282848_272473_253022_278953_282958_282402_282996_283075_236312_283202_280722_283354_251972_283364_283596_283498_283635_283661_281704_281050_283726_283719_282887_256223_283867_283782_283896_283928_283375_283982_284005_284024_283976_278388_284118_284131_283944_276759_284196_284281_284277_284264_281182_283356_284142_282201_276929_8000055_8000104_8000126_8000137_8000149_8000164_8000165_8000172_8000177_8000186_8000190_8000203%22%2C%22timestamp%22%3A1700471263998%2C%22invokeId%22%3A%226836025442720912963_1700471263967%22%7D%7D%2C%22baiduId%22%3A%22666404C6493A6569F92AB5FEE3B95DD0%22%7D&needlog=1&logargs=%7B%22source%22%3A%221023197w%22%2C%22from%22%3A%22openbox%22%2C%22page%22%3A%22chrome%22%2C%22type%22%3A%22%22%2C%22value%22%3A%22%22%2C%22channel%22%3A%221023206g%22%2C%22extlog%22%3A%7B%22ext_content%22%3A%7B%22from%22%3A%22844b%22%2C%22browserid%22%3A%2224%22%2C%22qid%22%3A%22%22%2C%22sids%22%3A%22110085_265881_263619_275733_259642_256739_269049_280899_281191_280650_281810_281867_281896_281685_281993_275097_274948_282191_282170_278414_282435_279010_281818_282521_282571_282628_282053_282758_279702_280272_282793_282232_282848_272473_253022_278953_282958_282402_282996_283075_236312_283202_280722_283354_251972_283364_283596_283498_283635_283661_281704_281050_283726_283719_282887_256223_283867_283782_283896_283928_283375_283982_284005_284024_283976_278388_284118_284131_283944_276759_284196_284281_284277_284264_281182_283356_284142_282201_276929_8000055_8000104_8000126_8000137_8000149_8000164_8000165_8000172_8000177_8000186_8000190_8000203%22%2C%22timestamp%22%3A1700471263986%2C%22invokeId%22%3A%226836025442720912963_1700471263967%22%7D%7D%2C%22baiduId%22%3A%22666404C6493A6569F92AB5FEE3B95DD0%3AFG%3D1%22%2C%22app_now%22%3A%22chrome_1700471264006_4595370516%22%2C%22yyb_pkg%22%3A%22com.baidu.searchbox%22%2C%22idmData%22%3A%7B%22firstOpen%22%3A%22main%22%2C%22secondOpen%22%3A%22lite%22%2C%22status%22%3A%22-1%22%7D%2C%22matrix%22%3A%22main%22%7D
     * baiduboxlite://v1/browser/open?url=https%3A%2F%2Fm.baidu.com%2Fs%3Fword%3D%25E7%2599%25BE%25E5%25BA%25A6%25E7%2583%25AD%25E6%2590%259C%26tn%26from%3D1020854c%26sa%3Dtre_dl_gh_logo_wj%26rfrom%3D1023197w%26rchannel%3D1023206g&newwindow=0&simple=1&fromwise=1&jumpTime=1700471263982&concernHash=-1672627669&needlog=1&logargs=%7B%22source%22%3A%221023197w%22%2C%22from%22%3A%22openbox%22%2C%22page%22%3A%22chrome%22%2C%22type%22%3A%22%22%2C%22value%22%3A%22%22%2C%22channel%22%3A%221023206g%22%2C%22extlog%22%3A%7B%22ext_content%22%3A%7B%22from%22%3A%22844b%22%2C%22browserid%22%3A%2224%22%2C%22qid%22%3A%22%22%2C%22qidsids%22%3A%22110085_265881_263619_275733_259642_256739_269049_280899_281191_280650_281810_281867_281896_281685_281993_275097_274948_282191_282170_278414_282435_279010_281818_282521_282571_282628_282053_282758_279702_280272_282793_282232_282848_272473_253022_278953_282958_282402_282996_283075_236312_283202_280722_283354_251972_283364_283596_283498_283635_283661_281704_281050_283726_283719_282887_256223_283867_283782_283896_283928_283375_283982_284005_284024_283976_278388_284118_284131_283944_276759_284196_284281_284277_284264_281182_283356_284142_282201_276929_8000055_8000104_8000126_8000137_8000149_8000164_8000165_8000172_8000177_8000186_8000190_8000203%22%2C%22timestamp%22%3A1700471266120%2C%22invokeId%22%3A%226836025442720912963_1700471263967%22%7D%7D%2C%22baiduId%22%3A%22666404C6493A6569F92AB5FEE3B95DD0%22%7D&needlog=1&logargs=%7B%22source%22%3A%221023197w%22%2C%22from%22%3A%22openbox%22%2C%22page%22%3A%22chrome%22%2C%22type%22%3A%22%22%2C%22value%22%3A%22%22%2C%22channel%22%3A%221023206g%22%2C%22extlog%22%3A%7B%22ext_content%22%3A%7B%22from%22%3A%22844b%22%2C%22browserid%22%3A%2224%22%2C%22qid%22%3A%22%22%2C%22sids%22%3A%22110085_265881_263619_275733_259642_256739_269049_280899_281191_280650_281810_281867_281896_281685_281993_275097_274948_282191_282170_278414_282435_279010_281818_282521_282571_282628_282053_282758_279702_280272_282793_282232_282848_272473_253022_278953_282958_282402_282996_283075_236312_283202_280722_283354_251972_283364_283596_283498_283635_283661_281704_281050_283726_283719_282887_256223_283867_283782_283896_283928_283375_283982_284005_284024_283976_278388_284118_284131_283944_276759_284196_284281_284277_284264_281182_283356_284142_282201_276929_8000055_8000104_8000126_8000137_8000149_8000164_8000165_8000172_8000177_8000186_8000190_8000203%22%2C%22timestamp%22%3A1700471263986%2C%22invokeId%22%3A%226836025442720912963_1700471263967%22%7D%7D%2C%22baiduId%22%3A%22666404C6493A6569F92AB5FEE3B95DD0%3AFG%3D1%22%2C%22app_now%22%3A%22chrome_1700471266127_0496604382%22%2C%22yyb_pkg%22%3A%22com.baidu.searchbox%22%2C%22idmData%22%3A%7B%22firstOpen%22%3A%22main%22%2C%22secondOpen%22%3A%22lite%22%2C%22status%22%3A%22-1%22%7D%2C%22matrix%22%3A%22lite%22%7D
     */

    public static String getAppNameByUrl(String url) {
        String appName = null;

        Uri myUri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, myUri);
        PackageManager mPackageManager = ApplicationGlobal.getInstance().getPackageManager();

        //如果不申请QUERY_ALL_PACKAGES权限，那么得到的componentName将会为null
        //如果设备没有能响应url的应用，那么这里也将为null
        ComponentName componentName = intent.resolveActivity(mPackageManager);
        try {
//            List<ResolveInfo> mResolveInfos = mPackageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//            ResolveInfo info = mPackageManager.resolveActivity(intent, PackageManager.MATCH_ALL);

            String packageName = componentName.getPackageName();
            ApplicationInfo applicationInfo = mPackageManager.getApplicationInfo(packageName, 0);
            appName = (String) mPackageManager.getApplicationLabel(applicationInfo);
            LogUtil.d("应用名：" + appName);

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("查找应用名失败: " + e);
        }
        return appName;
    }
}