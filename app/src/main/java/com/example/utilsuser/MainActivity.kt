package com.example.utilsuser

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.example.utilsgather.application_store.AppStoreUtil
import com.example.utilsgather.browser.BrowserUtil
import com.example.utilsgather.context.ApplicationGlobal
import com.example.utilsuser.cutomerview.CustomView1_4Activity
import com.example.utilsuser.cutomerview.CustomView2Activity
import com.example.utilsuser.cutomerview.CustomView3Activity
import com.example.utilsuser.cutomerview.CustomViewActivity
import com.example.utilsuser.cutomerview.flow.FlowLayoutActivity
import com.example.utilsuser.cutomerview.flow_optimize.OptimizedFlowLayoutActivity
import com.example.utilsuser.cutomerview.one_five.CustomView1_5Activity
import com.example.utilsuser.cutomerview.one_seven.CustomView1_7Activity
import com.example.utilsuser.cutomerview.one_six.CustomView1_6Activity

import com.example.utilsgather.encoding.Base64Util
import com.example.utilsgather.exit.ExitUtil
import com.example.utilsgather.handler.HandlerUI
import com.example.utilsgather.jump.JumpActivityUtils
import com.example.utilsgather.lifecycle_callback.CallbackActivity
import com.example.utilsgather.list_guide.GuideItemEntity
import com.example.utilsgather.list_guide.GuideSettings
import com.example.utilsgather.logcat.LogUtil
import com.example.utilsgather.info.PackageInfoUtil
import com.example.utilsgather.info.DeviceInfoUtil
import com.example.utilsgather.info.NetworkInfoUtil
import com.example.utilsgather.interaction.SniffingAppUtil
import com.example.utilsgather.permission.permissionX.PermissionX
import com.example.utilsuser.qihang.one.RectPointView.UseRectPointViewActivity
import com.example.utilsgather.random.StringRandomUtil
import com.example.utilsgather.source_file.assets.AssetsUtil
import com.example.utilsgather.source_file.raw.SourceUtil
import com.example.utilsgather.string.InfoFromURI
import com.example.utilsgather.thread.ThreadUtil
import com.example.utilsgather.ui.screen.ScreenFunctionUtils
import com.example.utilsuser.databinding.ActivityMainBinding
import com.example.utilsuser.httpurlconnect.HttpActivity
import com.example.utilsuser.qihang.eight.EraserViewActivity
import com.example.utilsuser.qihang.eight.InvertedImageViewActivity
import com.example.utilsuser.qihang.eight.IrregularWaveViewActivity
import com.example.utilsuser.qihang.eight.LightBookViewActivity
import com.example.utilsuser.qihang.eight.PorterDuffXfermodeViewActivity
import com.example.utilsuser.qihang.eight.TextWave_DSTINActivity
import com.example.utilsuser.qihang.eight.TwitterViewActivity
import com.example.utilsuser.qihang.five.PathMeasureActivity
import com.example.utilsuser.qihang.five.VectorShowActivity
import com.example.utilsuser.qihang.four.AnimateLayoutChangesActivity
import com.example.utilsuser.qihang.four.KeyFrameActivity
import com.example.utilsuser.qihang.four.PropertyValuesActivity
import com.example.utilsuser.qihang.nine.SaveLayerUseExampleActivity
import com.example.utilsuser.qihang.ten.TelescopeViewZoomInActivity
import com.example.utilsuser.qihang.seven.BezierActivity
import com.example.utilsuser.qihang.seven.BillowWaveActivity
import com.example.utilsuser.qihang.seven.BlurActivity
import com.example.utilsuser.qihang.seven.HeadPortraitActivity
import com.example.utilsuser.qihang.seven.ShaowActivity
import com.example.utilsuser.qihang.seven.TelescopeViewActivity
import com.example.utilsuser.qihang.ten.CreateBitmapViewActivity
import com.example.utilsuser.qihang.ten.CustomDrawableActivity
import com.example.utilsuser.qihang.ten.GradientDrawableActivity
import com.example.utilsuser.qihang.ten.RectShapeActivity
import com.example.utilsuser.qihang.ten.ShapeShaderActivity
import com.example.utilsuser.qihang.three.AnimatorSetActivity
import com.example.utilsuser.qihang.three.PathAnimatorActivity
import com.example.utilsuser.qihang.three.ValueAnimatorActivity
import com.example.utilsuser.qihang.two.ViewAnimationActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : CallbackActivity() {
    var mainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding!!.getRoot())
        GuideSettings.set(
            mainBinding!!.lvLauncher, arrayOf<GuideItemEntity>(
                GuideItemEntity("跳转测试沉浸式的Activity") {
                    startActivity(Intent(this, TestImmersionActivity::class.java))
                },
                GuideItemEntity("测试自定义View的Activity") {
                    startActivity(Intent(this, CustomViewActivity::class.java))
                },
                GuideItemEntity("测试自定义View的Activity2") {
                    startActivity(Intent(this, CustomView2Activity::class.java))
                },
                GuideItemEntity("测试自定义View的Activity3") {
                    startActivity(Intent(this, CustomView3Activity::class.java))
                },
                GuideItemEntity("测试自定义View的Activity1-4") {
                    startActivity(Intent(this, CustomView1_4Activity::class.java))
                },
                GuideItemEntity("测试自定义View的Activity1-5") {
                    startActivity(Intent(this, CustomView1_5Activity::class.java))
                },
                GuideItemEntity("测试自定义View的Activity1-6") {
                    startActivity(Intent(this, CustomView1_6Activity::class.java))
                },
                GuideItemEntity("测试自定义View的Activity1-7") {
                    startActivity(Intent(this, CustomView1_7Activity::class.java))
                },
                GuideItemEntity("测试自定义View的FlowActivity") {
                    startActivity(Intent(this, FlowLayoutActivity::class.java))
                },
                GuideItemEntity("测试自定义View的OptimizedFlowLayoutActivity") {
                    startActivity(Intent(this, OptimizedFlowLayoutActivity::class.java))
                },
                GuideItemEntity("去UseRectPointViewActivity") {
                    startActivity(Intent(this, UseRectPointViewActivity::class.java))
                },
                GuideItemEntity("去ViewAnimationActivity") {
                    startActivity(Intent(this, ViewAnimationActivity::class.java))
                },
                GuideItemEntity("去ValueAnimatorActivity") {
                    startActivity(Intent(this, ValueAnimatorActivity::class.java))
                },
                GuideItemEntity("去AnimatorSetActivity") {
                    startActivity(Intent(this, AnimatorSetActivity::class.java))
                },
                GuideItemEntity("去PathAnimatorActivity") {
                    startActivity(Intent(this, PathAnimatorActivity::class.java))
                },
                GuideItemEntity("去PropertyValuesActivity") {
                    startActivity(Intent(this, PropertyValuesActivity::class.java))
                },
                GuideItemEntity("去KeyFrameActivity") {
                    startActivity(Intent(this, KeyFrameActivity::class.java))
                },
                GuideItemEntity("去AnimateLayoutChangesActivity") {
                    startActivity(Intent(this, AnimateLayoutChangesActivity::class.java))
                },
                GuideItemEntity("去PathMeasureActivity") {
                    startActivity(Intent(this, PathMeasureActivity::class.java))
                },
                GuideItemEntity("去VectorShowActivity") {
                    startActivity(Intent(this, VectorShowActivity::class.java))
                },
                GuideItemEntity("去BezierActivity") {
                    startActivity(Intent(this, BezierActivity::class.java))
                },
                GuideItemEntity("去BillowWaveActivity") {
                    startActivity(Intent(this, BillowWaveActivity::class.java))
                },
                GuideItemEntity("去ShaowActivity") {
                    startActivity(Intent(this, ShaowActivity::class.java))
                },
                GuideItemEntity("去BlurActivity") {
                    startActivity(Intent(this, BlurActivity::class.java))
                },
                GuideItemEntity("去TelescopeViewActivity") {
                    startActivity(Intent(this, TelescopeViewActivity::class.java))
                },
                GuideItemEntity("去HeadPortraitActivity") {
                    startActivity(Intent(this, HeadPortraitActivity::class.java))
                },
                GuideItemEntity("去PorterDuffXfermodeViewActivity") {
                    startActivity(Intent(this, PorterDuffXfermodeViewActivity::class.java))
                },
                GuideItemEntity("去LightBookViewActivity") {
                    startActivity(Intent(this, LightBookViewActivity::class.java))
                },
                GuideItemEntity("去TwitterViewActivity") {
                    startActivity(Intent(this, TwitterViewActivity::class.java))
                },
                GuideItemEntity("去InvertedImageViewActivity") {
                    startActivity(Intent(this, InvertedImageViewActivity::class.java))
                },
                GuideItemEntity("去EraserViewActivity") {
                    startActivity(Intent(this, EraserViewActivity::class.java))
                },
                GuideItemEntity("去TextWave_DSTINActivity") {
                    startActivity(Intent(this, TextWave_DSTINActivity::class.java))
                },
                GuideItemEntity("去IrregularWaveViewActivity") {
                    startActivity(Intent(this, IrregularWaveViewActivity::class.java))
                },
                GuideItemEntity("去SaveLayerUseExampleActivity") {
                    startActivity(Intent(this, SaveLayerUseExampleActivity::class.java))
                },
                GuideItemEntity("去GradientDrawableActivity") {
                    startActivity(Intent(this, GradientDrawableActivity::class.java))
                },
                GuideItemEntity("去RectShapeActivity") {
                    startActivity(Intent(this, RectShapeActivity::class.java))
                },
                GuideItemEntity("去RectShapeActivity") {
                    startActivity(Intent(this, RectShapeActivity::class.java))
                },
                GuideItemEntity("去ShapeShaderActivity") {
                    val intent = Intent(this@MainActivity, ShapeShaderActivity::class.java)
                    startActivity(intent)
                },
                GuideItemEntity("去TelescopeViewZoomInActivity") {
                    val intent = Intent(this@MainActivity, TelescopeViewZoomInActivity::class.java)
                    startActivity(intent)
                },
                GuideItemEntity("去CustomDrawableActivity") {
                    val intent = Intent(this@MainActivity, CustomDrawableActivity::class.java)
                    startActivity(intent)
                },
                GuideItemEntity("去CreateBitmapViewActivity") {
                    val intent = Intent(this@MainActivity, CreateBitmapViewActivity::class.java)
                    startActivity(intent)
                },
                GuideItemEntity("测试获取应用名") {
                    SniffingAppUtil.getAppNameByUrl("xhsdiscover://search/result?keyword=%E5%B9%BF%E4%B8%9C%E6%94%BB%E7%95%A5&open_url=baidu&groupid=60f954717ae4040001eabf05&mode=openurl&source=landingpage")
                    SniffingAppUtil.getAppNameByUrl("baiduboxapp://v11/appTab/select?item=home&upgrade=0&params=%7b%22channel%22%3a%228%22%2c%22tab_info%22%3a%7b%22id%22%3a%228%22%2c%22name%22%3a%22%e7%83%ad%e6%a6%9c%22%2c%22canDelete%22%3a%220%22%7d%2c%22tab_extend_info%22%3a%7b%22invokeFrom%22%3a%22Huawei%22%7d%2c%22refresh%22%3a%221%22%7d&next=baiduboxapp%3A%2F%2Fv1%2Fbrowser%2Fopen%3Furl%3Dhttps%253A%252F%252Fm.baidu.com%252Fs%253Fword%253D%2525E7%252599%2525BE%2525E5%2525BA%2525A6%2525E7%252583%2525AD%2525E6%252590%25259C%2526tn%2526from%253D1020854c%2526sa%253Dtre_dl_gh_logo_wj%2526rfrom%253D1023197w%2526rchannel%253D1023206g%26newwindow%3D0%26simple%3D1%26fromwise%3D1&jumpTime=1700471263982&concernHash=901560455&needlog=1&logargs=%7B%22source%22%3A%221023197w%22%2C%22from%22%3A%22openbox%22%2C%22page%22%3A%22chrome%22%2C%22type%22%3A%22%22%2C%22value%22%3A%22%22%2C%22channel%22%3A%221023206g%22%2C%22extlog%22%3A%7B%22ext_content%22%3A%7B%22from%22%3A%22844b%22%2C%22browserid%22%3A%2224%22%2C%22qid%22%3A%22%22%2C%22qidsids%22%3A%22110085_265881_263619_275733_259642_256739_269049_280899_281191_280650_281810_281867_281896_281685_281993_275097_274948_282191_282170_278414_282435_279010_281818_282521_282571_282628_282053_282758_279702_280272_282793_282232_282848_272473_253022_278953_282958_282402_282996_283075_236312_283202_280722_283354_251972_283364_283596_283498_283635_283661_281704_281050_283726_283719_282887_256223_283867_283782_283896_283928_283375_283982_284005_284024_283976_278388_284118_284131_283944_276759_284196_284281_284277_284264_281182_283356_284142_282201_276929_8000055_8000104_8000126_8000137_8000149_8000164_8000165_8000172_8000177_8000186_8000190_8000203%22%2C%22timestamp%22%3A1700471263998%2C%22invokeId%22%3A%226836025442720912963_1700471263967%22%7D%7D%2C%22baiduId%22%3A%22666404C6493A6569F92AB5FEE3B95DD0%22%7D&needlog=1&logargs=%7B%22source%22%3A%221023197w%22%2C%22from%22%3A%22openbox%22%2C%22page%22%3A%22chrome%22%2C%22type%22%3A%22%22%2C%22value%22%3A%22%22%2C%22channel%22%3A%221023206g%22%2C%22extlog%22%3A%7B%22ext_content%22%3A%7B%22from%22%3A%22844b%22%2C%22browserid%22%3A%2224%22%2C%22qid%22%3A%22%22%2C%22sids%22%3A%22110085_265881_263619_275733_259642_256739_269049_280899_281191_280650_281810_281867_281896_281685_281993_275097_274948_282191_282170_278414_282435_279010_281818_282521_282571_282628_282053_282758_279702_280272_282793_282232_282848_272473_253022_278953_282958_282402_282996_283075_236312_283202_280722_283354_251972_283364_283596_283498_283635_283661_281704_281050_283726_283719_282887_256223_283867_283782_283896_283928_283375_283982_284005_284024_283976_278388_284118_284131_283944_276759_284196_284281_284277_284264_281182_283356_284142_282201_276929_8000055_8000104_8000126_8000137_8000149_8000164_8000165_8000172_8000177_8000186_8000190_8000203%22%2C%22timestamp%22%3A1700471263986%2C%22invokeId%22%3A%226836025442720912963_1700471263967%22%7D%7D%2C%22baiduId%22%3A%22666404C6493A6569F92AB5FEE3B95DD0%3AFG%3D1%22%2C%22app_now%22%3A%22chrome_1700471264006_4595370516%22%2C%22yyb_pkg%22%3A%22com.baidu.searchbox%22%2C%22idmData%22%3A%7B%22firstOpen%22%3A%22main%22%2C%22secondOpen%22%3A%22lite%22%2C%22status%22%3A%22-1%22%7D%2C%22matrix%22%3A%22main%22%7D")
                    SniffingAppUtil.getAppNameByUrl("baiduboxlite://v1/browser/open?url=https%3A%2F%2Fm.baidu.com%2Fs%3Fword%3D%25E7%2599%25BE%25E5%25BA%25A6%25E7%2583%25AD%25E6%2590%259C%26tn%26from%3D1020854c%26sa%3Dtre_dl_gh_logo_wj%26rfrom%3D1023197w%26rchannel%3D1023206g&newwindow=0&simple=1&fromwise=1&jumpTime=1700471263982&concernHash=-1672627669&needlog=1&logargs=%7B%22source%22%3A%221023197w%22%2C%22from%22%3A%22openbox%22%2C%22page%22%3A%22chrome%22%2C%22type%22%3A%22%22%2C%22value%22%3A%22%22%2C%22channel%22%3A%221023206g%22%2C%22extlog%22%3A%7B%22ext_content%22%3A%7B%22from%22%3A%22844b%22%2C%22browserid%22%3A%2224%22%2C%22qid%22%3A%22%22%2C%22qidsids%22%3A%22110085_265881_263619_275733_259642_256739_269049_280899_281191_280650_281810_281867_281896_281685_281993_275097_274948_282191_282170_278414_282435_279010_281818_282521_282571_282628_282053_282758_279702_280272_282793_282232_282848_272473_253022_278953_282958_282402_282996_283075_236312_283202_280722_283354_251972_283364_283596_283498_283635_283661_281704_281050_283726_283719_282887_256223_283867_283782_283896_283928_283375_283982_284005_284024_283976_278388_284118_284131_283944_276759_284196_284281_284277_284264_281182_283356_284142_282201_276929_8000055_8000104_8000126_8000137_8000149_8000164_8000165_8000172_8000177_8000186_8000190_8000203%22%2C%22timestamp%22%3A1700471266120%2C%22invokeId%22%3A%226836025442720912963_1700471263967%22%7D%7D%2C%22baiduId%22%3A%22666404C6493A6569F92AB5FEE3B95DD0%22%7D&needlog=1&logargs=%7B%22source%22%3A%221023197w%22%2C%22from%22%3A%22openbox%22%2C%22page%22%3A%22chrome%22%2C%22type%22%3A%22%22%2C%22value%22%3A%22%22%2C%22channel%22%3A%221023206g%22%2C%22extlog%22%3A%7B%22ext_content%22%3A%7B%22from%22%3A%22844b%22%2C%22browserid%22%3A%2224%22%2C%22qid%22%3A%22%22%2C%22sids%22%3A%22110085_265881_263619_275733_259642_256739_269049_280899_281191_280650_281810_281867_281896_281685_281993_275097_274948_282191_282170_278414_282435_279010_281818_282521_282571_282628_282053_282758_279702_280272_282793_282232_282848_272473_253022_278953_282958_282402_282996_283075_236312_283202_280722_283354_251972_283364_283596_283498_283635_283661_281704_281050_283726_283719_282887_256223_283867_283782_283896_283928_283375_283982_284005_284024_283976_278388_284118_284131_283944_276759_284196_284281_284277_284264_281182_283356_284142_282201_276929_8000055_8000104_8000126_8000137_8000149_8000164_8000165_8000172_8000177_8000186_8000190_8000203%22%2C%22timestamp%22%3A1700471263986%2C%22invokeId%22%3A%226836025442720912963_1700471263967%22%7D%7D%2C%22baiduId%22%3A%22666404C6493A6569F92AB5FEE3B95DD0%3AFG%3D1%22%2C%22app_now%22%3A%22chrome_1700471266127_0496604382%22%2C%22yyb_pkg%22%3A%22com.baidu.searchbox%22%2C%22idmData%22%3A%7B%22firstOpen%22%3A%22main%22%2C%22secondOpen%22%3A%22lite%22%2C%22status%22%3A%22-1%22%7D%2C%22matrix%22%3A%22lite%22%7D")
                    SniffingAppUtil.getAppNameByUrl("baiduboxsand://v1/abc")
                    SniffingAppUtil.getAppNameByUrl("openapp.jdmobile://virtual?params=%7B%22des%22%3A%22HomePage%22%2C%22from%22%3A%22search%22%2C%22category%22%3A%22jump%22%2C%22sourceType%22%3A%22JSHOP_SOURCE_TYPE%22%2C%22sourceValue%22%3A%22JSHOP_SOURCE_VALUE%22%2C%22M_sourceFrom%22%3A%22index%22%2C%22msf_type%22%3A%22auto%22%2C%22m_param%22%3A%7B%22m_source%22%3A%220%22%2C%22event_series%22%3A%7B%7D%2C%22jda%22%3A%22122270672.1700471577426741366998.1700471577.1700471577.1700471577.1%22%2C%22usc%22%3A%22direct%22%2C%22ucp%22%3A%22-%22%2C%22umd%22%3A%22none%22%2C%22utr%22%3A%22-%22%2C%22jdv%22%3A%22122270672%7Cdirect%7C-%7Cnone%7C-%7C1700471577429%22%2C%22ref%22%3A%22https%3A%2F%2Fm.jd.com%2F%22%2C%22psn%22%3A%221700471577426741366998%7C1%22%2C%22psq%22%3A1%2C%22unpl%22%3A%22%22%2C%22pc_source%22%3A%22%22%2C%22mba_muid%22%3A%221700471577426741366998%22%2C%22mba_sid%22%3A%2217004715774857195937526992602%22%2C%22std%22%3A%22MO-J2011-1%22%2C%22par%22%3A%22%22%2C%22event_id%22%3A%22MDownLoadFloat_AppArouse%22%2C%22mt_xid%22%3A%22%22%2C%22mt_subsite%22%3A%22%22%2C%22YINLIUhuanqi%22%3A%22https%3A%2F%2Fm.jd.com%2F%22%7D%2C%22SE%22%3A%7B%22mt_subsite%22%3A%22%22%2C%22__jdv%22%3A%22122270672%7Cdirect%7C-%7Cnone%7C-%7C1700471577429%22%2C%22unpl%22%3A%22%22%2C%22__jda%22%3A%22122270672.1700471577426741366998.1700471577.1700471577.1700471577.1%22%7D%7D")
                },
                GuideItemEntity("跳转第三方app: 百度") {
                    JumpActivityUtils.startApp(this, "baiduboxapp://v11/appTab/select?item=home&upgrade=0&params=%7b%22channel%22%3a%228%22%2c%22tab_info%22%3a%7b%22id%22%3a%228%22%2c%22name%22%3a%22%e7%83%ad%e6%a6%9c%22%2c%22canDelete%22%3a%220%22%7d%2c%22tab_extend_info%22%3a%7b%22invokeFrom%22%3a%22Huawei%22%7d%2c%22refresh%22%3a%221%22%7d&next=baiduboxapp%3A%2F%2Fv1%2Fbrowser%2Fopen%3Furl%3Dhttps%253A%252F%252Fm.baidu.com%252Fs%253Fword%253D%2525E7%252599%2525BE%2525E5%2525BA%2525A6%2525E7%252583%2525AD%2525E6%252590%25259C%2526tn%2526from%253D1020854c%2526sa%253Dtre_dl_gh_logo_wj%2526rfrom%253D1023197w%2526rchannel%253D1023206g%26newwindow%3D0%26simple%3D1%26fromwise%3D1&jumpTime=1700471263982&concernHash=901560455&needlog=1&logargs=%7B%22source%22%3A%221023197w%22%2C%22from%22%3A%22openbox%22%2C%22page%22%3A%22chrome%22%2C%22type%22%3A%22%22%2C%22value%22%3A%22%22%2C%22channel%22%3A%221023206g%22%2C%22extlog%22%3A%7B%22ext_content%22%3A%7B%22from%22%3A%22844b%22%2C%22browserid%22%3A%2224%22%2C%22qid%22%3A%22%22%2C%22qidsids%22%3A%22110085_265881_263619_275733_259642_256739_269049_280899_281191_280650_281810_281867_281896_281685_281993_275097_274948_282191_282170_278414_282435_279010_281818_282521_282571_282628_282053_282758_279702_280272_282793_282232_282848_272473_253022_278953_282958_282402_282996_283075_236312_283202_280722_283354_251972_283364_283596_283498_283635_283661_281704_281050_283726_283719_282887_256223_283867_283782_283896_283928_283375_283982_284005_284024_283976_278388_284118_284131_283944_276759_284196_284281_284277_284264_281182_283356_284142_282201_276929_8000055_8000104_8000126_8000137_8000149_8000164_8000165_8000172_8000177_8000186_8000190_8000203%22%2C%22timestamp%22%3A1700471263998%2C%22invokeId%22%3A%226836025442720912963_1700471263967%22%7D%7D%2C%22baiduId%22%3A%22666404C6493A6569F92AB5FEE3B95DD0%22%7D&needlog=1&logargs=%7B%22source%22%3A%221023197w%22%2C%22from%22%3A%22openbox%22%2C%22page%22%3A%22chrome%22%2C%22type%22%3A%22%22%2C%22value%22%3A%22%22%2C%22channel%22%3A%221023206g%22%2C%22extlog%22%3A%7B%22ext_content%22%3A%7B%22from%22%3A%22844b%22%2C%22browserid%22%3A%2224%22%2C%22qid%22%3A%22%22%2C%22sids%22%3A%22110085_265881_263619_275733_259642_256739_269049_280899_281191_280650_281810_281867_281896_281685_281993_275097_274948_282191_282170_278414_282435_279010_281818_282521_282571_282628_282053_282758_279702_280272_282793_282232_282848_272473_253022_278953_282958_282402_282996_283075_236312_283202_280722_283354_251972_283364_283596_283498_283635_283661_281704_281050_283726_283719_282887_256223_283867_283782_283896_283928_283375_283982_284005_284024_283976_278388_284118_284131_283944_276759_284196_284281_284277_284264_281182_283356_284142_282201_276929_8000055_8000104_8000126_8000137_8000149_8000164_8000165_8000172_8000177_8000186_8000190_8000203%22%2C%22timestamp%22%3A1700471263986%2C%22invokeId%22%3A%226836025442720912963_1700471263967%22%7D%7D%2C%22baiduId%22%3A%22666404C6493A6569F92AB5FEE3B95DD0%3AFG%3D1%22%2C%22app_now%22%3A%22chrome_1700471264006_4595370516%22%2C%22yyb_pkg%22%3A%22com.baidu.searchbox%22%2C%22idmData%22%3A%7B%22firstOpen%22%3A%22main%22%2C%22secondOpen%22%3A%22lite%22%2C%22status%22%3A%22-1%22%7D%2C%22matrix%22%3A%22main%22%7D",)
                },
                GuideItemEntity("跳转第三方app: 小红书（测试应用不存在的情况）") {
                    JumpActivityUtils.startApp(this, "xhsdiscover://search/result?keyword=%E5%B9%BF%E4%B8%9C%E6%94%BB%E7%95%A5&open_url=baidu&groupid=60f954717ae4040001eabf05&mode=openurl&source=landingpage")
                },
                //在TargetGather上写了响应daisy协议的Activity，因此可以跳转到自己的应用
                GuideItemEntity("跳转自己的应用") {
                    JumpActivityUtils.startApp(this, "daisy://mobile/carDetail/hsf?car_id=123456")
                },
                GuideItemEntity("测试从子线程切换为UI线程") {
                    LogUtil.d("当前的线程是：" + Thread.currentThread())
                    HandlerUI.runOnUI { LogUtil.d("当前的线程是2：" + Thread.currentThread()) }
                },
                GuideItemEntity("从Assets中读出内容") {
                    LogUtil.d(
                        """
    读出来的文本:
    ${AssetsUtil.readAssetsFile(this@MainActivity, "uiautomatorviewer.txt")}
    """.trimIndent()
                    )
                },
                GuideItemEntity("在应用商店中打开小红书") {
                    AppStoreUtil.jumpStore(
                        this@MainActivity, "com.xingin.xhs", false
                    )
                },
                GuideItemEntity("在谷歌中打开小红书") {
                    AppStoreUtil.jumpStore(
                        this@MainActivity, "com.xingin.xhs", true
                    )
                },
                GuideItemEntity("在浏览器中打开京东") {
                    BrowserUtil.jumpBrowser(
                        "https://m.jd.com/", this@MainActivity
                    )
                },
                GuideItemEntity("能不能让我选一下跳转去哪") {
                    BrowserUtil.jumpOthers(
                        this@MainActivity, "https://www.baidu.com"
                    )
                },

                GuideItemEntity("设置屏幕常亮") { ScreenFunctionUtils.setScreenOn(this@MainActivity) },
                GuideItemEntity("从raw中读出图片") {
                    /*Thread {
                        val bitmap = SourceUtil.getBitmapFromRaw(this@MainActivity, R.raw.set_meal)
                        runOnUiThread { mainBinding!!.ivContent.setImageBitmap(bitmap) }
                    }.start()*/

                    GlobalScope.launch(Dispatchers.Main) {
                        LogUtil.d("打印当前线程：${ThreadUtil.getCurrentThread()}")
                        val bitmap =  withContext(Dispatchers.IO) {
                            LogUtil.d("打印当前线程：${ThreadUtil.getCurrentThread()}")
                            SourceUtil.getBitmapFromRaw(this@MainActivity, R.raw.set_meal)
                        }
                        mainBinding!!.ivContent.setImageBitmap(bitmap)
                    }
                },
                GuideItemEntity("测试授权情况") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        PermissionX.request(this@MainActivity, arrayOf(Manifest.permission.CAMERA)) { allGranted, deniedList ->
                            if (allGranted) {
                                LogUtil.d("所有权限都已授权")
                            } else {
                                LogUtil.d("没有通过的权限：$deniedList")
                            }
                        }
                    }
                },

                GuideItemEntity("获得指定长度，指定字典的随机字符串") {
                    LogUtil.d(StringRandomUtil.getRandomStringWithSection(3, 7, 3));
                },
                GuideItemEntity("简易地获得随机字符串") {
                    LogUtil.d(StringRandomUtil.getRandomString())
                },

                GuideItemEntity("跳转到测试两下返回的Activity") {
                    val intent = Intent(this@MainActivity, TextExitHandleActivity::class.java)
                    startActivity(intent)

                    finish()
                },

                GuideItemEntity("Base64编码") {
                    val content = "Hello I am From China."
                    LogUtil.d("编码后: " + Base64Util.base64Encode(content))
                },
                GuideItemEntity("Base64解码") {
                    val content = "SGVsbG8gSSBhbSBGcm9tIENoaW5hLg==";
                    LogUtil.d("解码后: " + Base64Util.base64Decode(content))
                },


                GuideItemEntity("获得APP信息") {
                    LogUtil.d("包名: " + PackageInfoUtil.getPackageName(ApplicationGlobal.getInstance()))
                    LogUtil.d("版本号: " + PackageInfoUtil.getPackageVersionCode(ApplicationGlobal.getInstance()))
                    LogUtil.d("版本名: " + PackageInfoUtil.getPackageVersionName(ApplicationGlobal.getInstance()))
                },
                GuideItemEntity("获得系统信息") {
                    LogUtil.d("系统品牌: " + DeviceInfoUtil.getOsType())
                    LogUtil.d("Android系统版本: " + DeviceInfoUtil.getAndroidOsVersion())
                    LogUtil.d("是否为鸿蒙系统: " + DeviceInfoUtil.isHarmonyOs())
                    LogUtil.d("获得鸿蒙系统版本: " + DeviceInfoUtil.getHarmonyOsVersion())
                    LogUtil.d("获取当前手机系统语言: " + DeviceInfoUtil.getDeviceLanguage())
                    LogUtil.d("获得sim卡国家: " + DeviceInfoUtil.getSimCountry(ApplicationGlobal.getInstance()))
                    LogUtil.d("获得国家代码: " + DeviceInfoUtil.getCountryCode())
                },
                GuideItemEntity("获得网络信息") {
                    LogUtil.d("网络连接情况: " + NetworkInfoUtil.getNetType(ApplicationGlobal.getInstance()))
                },



                GuideItemEntity("获得当前的线程信息") {
                    LogUtil.d("当前的线程信息: " + Thread.currentThread().toString())
                },

                GuideItemEntity("解析URL") {
                    LogUtil.d(InfoFromURI.justGetResource("https://ucdl.25pp.com/fs08/2024/05/14/9/1_76669abdde68e00075f019cbfaa5d0bf.apk?yingid=web_space&packageid=500959094&did=5ab0d2490bd121b49d2e88b7cd3bca41"))
                    LogUtil.d(InfoFromURI.justGetResource("https://ucdl.25pp.com/fs08/2024/05/14/9/1_76669abdde68e00075f019cbfaa5d0bf.apk"))
                },

                GuideItemEntity("跳转到网络请求的Activity") {
                    val intent = Intent(this@MainActivity, HttpActivity::class.java)
                    startActivity(intent)
                },
            )

        )
    }

    override fun onBackPressed() {
        if (ExitUtil.handle(this, ExitUtil.Action.SIMULATE_HOME, true)) {
            return
        }
        super.onBackPressed()
    }
}