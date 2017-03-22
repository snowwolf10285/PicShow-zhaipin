package com.magic.picshow.mvp.model.entity;

/**
 * Created by snowwolf on 17/2/22.
 */

import com.magic.picshow.mvp.model.api.Api;
import com.ta.utdid2.android.utils.StringUtils;

import java.util.List;

/**
 * 相册
 */
public class Photos {
//{"code":"0","data":{"total":1,"page":1,"records":1,"rows":[
// ]},"respMsg":"SUCCESS"}

    /**
     * 总页数
     */
    private int total;

    /**
     * 当前页数
     */
    private int page;

    /**
     * 总相册数
     */
    private int records;
    private List<PhotosItems> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public List<PhotosItems> getRows() {
        return rows;
    }

    public void setRows(List<PhotosItems> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "Photos{" +
                "total=" + total +
                ", page=" + page +
                ", records=" + records +
                ", rows=" + rows +
                '}';
    }


    public class PhotosItems {

    // {"res":"职场","cover_url":"http://image.winlang.com:8080/picshow-web/resources/upload/photo/20170308164430584.jpeg",
    // "classify_id":3,"name":"职场","id":15,"type":0}

        private String res;
        private String cover_url;
        private int classify_id;
        private String name;
        private int id;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRes() {
            return res;
        }

        public void setRes(String res) {
            this.res = res;
        }

        public String getCover_url() {

            return cover_url;
        }

        public void setCover_url(String cover_url) {
            this.cover_url = cover_url;
        }

        public int getClassify_id() {
            return classify_id;
        }

        public void setClassify_id(int classify_id) {
            this.classify_id = classify_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Photos{" +
                    "res='" + res + '\'' +
                    ", cover_url='" + cover_url + '\'' +
                    ", classify_id=" + classify_id +
                    ", name='" + name + '\'' +
                    ", id=" + id +
                    ", type=" + type +
                    '}';
        }
    }
}
