package com.example.utilsuser.httpurlconnect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public static ArticleBean parse(String jsonString) {
        try {
            ArticleBean articleBean = new ArticleBean();

            JSONObject articleJSONObject = new JSONObject(jsonString);
            articleBean.setErrorCode(articleJSONObject.getInt("errorCode"));
            articleBean.setErrorMsg(articleJSONObject.getString("errorMsg"));
            JSONObject dataJSONObject = articleJSONObject.getJSONObject("data");

            ArticleBean.DataBean dataBean = new ArticleBean.DataBean();
            dataBean.setCurPage(dataJSONObject.getInt("curPage"));
            dataBean.setOffset(dataJSONObject.getInt("offset"));
            dataBean.setOver(dataJSONObject.getBoolean("over"));
            dataBean.setPageCount(dataJSONObject.getInt("pageCount"));
            dataBean.setSize(dataJSONObject.getInt("size"));
            dataBean.setTotal(dataJSONObject.getInt("total"));

            JSONArray jsonArray = dataJSONObject.getJSONArray("datas");
            List<ArticleBean.DataBean.DatasBean> datasBeanList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject datasJSONObject = jsonArray.getJSONObject(i);

                ArticleBean.DataBean.DatasBean datasBean = new ArticleBean.DataBean.DatasBean();
                datasBean.setAdminAdd(datasJSONObject.getBoolean("adminAdd"));
                datasBean.setApkLink(datasJSONObject.getString("apkLink"));
                datasBean.setAudit(datasJSONObject.getInt("audit"));
                datasBean.setAuthor(datasJSONObject.getString("author"));
                datasBean.setCanEdit(datasJSONObject.getBoolean("canEdit"));
                datasBean.setChapterId(datasJSONObject.getInt("chapterId"));
                datasBean.setChapterName(datasJSONObject.getString("chapterName"));
                datasBean.setCollect(datasJSONObject.getBoolean("collect"));
                datasBean.setCourseId(datasJSONObject.getInt("courseId"));
                datasBean.setDesc(datasJSONObject.getString("desc"));
                datasBean.setDescMd(datasJSONObject.getString("descMd"));
                datasBean.setEnvelopePic(datasJSONObject.getString("envelopePic"));
                datasBean.setFresh(datasJSONObject.getBoolean("fresh"));
                datasBean.setHost(datasJSONObject.getString("host"));
                datasBean.setId(datasJSONObject.getInt("id"));
                datasBean.setIsAdminAdd(datasJSONObject.getBoolean("isAdminAdd"));
                datasBean.setLink(datasJSONObject.getString("link"));
                datasBean.setNiceDate(datasJSONObject.getString("niceDate"));
                datasBean.setNiceShareDate(datasJSONObject.getString("niceShareDate"));
                datasBean.setOrigin(datasJSONObject.getString("origin"));
                datasBean.setPrefix(datasJSONObject.getString("prefix"));
                datasBean.setProjectLink(datasJSONObject.getString("projectLink"));
                datasBean.setPublishTime(datasJSONObject.getLong("publishTime"));  //这里的数字过长，可以使用long类型
                datasBean.setRealSuperChapterId(datasJSONObject.getInt("realSuperChapterId"));
                datasBean.setSelfVisible(datasJSONObject.getInt("selfVisible"));
                datasBean.setShareDate(datasJSONObject.getLong("shareDate"));
                datasBean.setShareUser(datasJSONObject.getString("shareUser"));
                datasBean.setSuperChapterId(datasJSONObject.getInt("superChapterId"));
                datasBean.setSuperChapterName(datasJSONObject.getString("superChapterName"));

                List<ArticleBean.DataBean.DatasBean.TagsBean> tags = new ArrayList<>();
                JSONArray tagsJSONArray = datasJSONObject.getJSONArray("tags");
                for (int j = 0; j < tagsJSONArray.length(); j++) {
                    JSONObject tagJSONObject = tagsJSONArray.getJSONObject(j);
                    ArticleBean.DataBean.DatasBean.TagsBean tagsBean = new ArticleBean.DataBean.DatasBean.TagsBean();
                    tagsBean.setName(tagJSONObject.getString("name"));
                    tagsBean.setUrl(tagJSONObject.getString("url"));

                    tags.add(tagsBean);
                }
                datasBean.setTags(tags);

                datasBean.setTitle(datasJSONObject.getString("title"));
                datasBean.setType(datasJSONObject.getInt("type"));
                datasBean.setUserId(datasJSONObject.getInt("userId"));
                datasBean.setVisible(datasJSONObject.getInt("visible"));
                datasBean.setZan(datasJSONObject.getInt("zan"));

                datasBeanList.add(datasBean);
            }
            dataBean.setDatas(datasBeanList);
            articleBean.setData(dataBean);

            return articleBean;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }
}
