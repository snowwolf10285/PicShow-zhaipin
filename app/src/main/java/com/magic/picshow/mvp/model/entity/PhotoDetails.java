package com.magic.picshow.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by snowwolf on 17/2/22.
 */

public class PhotoDetails {
//    {"code":"0","data":{
// "product":[
// {"price":2.0,"name":"产品1","id":1},
// {"price":4.0,"name":"产品2","id":2},
// {"price":6.0,"name":"产品3","id":3}],
// "resource":[],"buyproduct":[]},"respMsg":"SUCCESS"}
    /**
     * 相册中包含的收费项的信息
     */
    private List<Product> product;

    /**
     * 已购买的本相册的收费项
     */
    private List<Product> buyproduct;

    /**
     * 资源信息
     */
    private List<Resource> resource;

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public List<Product> getBuyproduct() {
        return buyproduct;
    }

    public void setBuyproduct(List<Product> buyproduct) {
        this.buyproduct = buyproduct;
    }

    public List<Resource> getResource() {
        return resource;
    }

    public void setResource(List<Resource> resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "PhotoDetails{" +
                "product=" + product +
                ", buyproduct=" + buyproduct +
                ", resource=" + resource +
                '}';
    }

    public class Product implements Serializable {
        //        {"price":2.0,"name":"产品1","id":1},
        private double price;
        private String name;
        private int id;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "price=" + price +
                    ", name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Product other = (Product) obj;
            if (this.getId() == other.getId())
                return true;
            return false;
        }
    }

    public class Resource implements Serializable{
//          {
//              "res":"阿斯顿发斯蒂芬",
//              "pro_id":3,
//              "name":"图片3",
//              "id":3,
//              "time":"2017-02-17 15:10:27",
//              "type":1,
//              "url":"dafasgf",
//              "operator":1,
//              "p_id":1}

//        {
//            "res":"多图上传",
//                "pro_id":1,
//                "name":"图片",
//                "rank":1,
//                "id":37,
//                "time":"2017-03-07 17:53:41",
//                "type":1,
//                "url":"\/resources\/upload\/img\/1\/20170307175341997.jpg",
//                "p_id":1,
//                "sb_name":"20170307175341997.jpg"
//        }


        /**
         *
         */
        private String res;

        /**
         * pro_id是产品ID
         */
        private int pro_id;

        /**
         *
         */
        private int id;

        /**
         * type是资源类型（1，图片，2，视频，3故事）
         */
        private int type;

        /**
         * operator是操作员
         */
        private int operator;

        /**
         *
         */
        private int p_id;

        private String name;
        private String time;
        private String url;

        public String getRes() {
            return res;
        }

        public void setRes(String res) {
            this.res = res;
        }

        public int getPro_id() {
            return pro_id;
        }

        public void setPro_id(int pro_id) {
            this.pro_id = pro_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getOperator() {
            return operator;
        }

        public void setOperator(int operator) {
            this.operator = operator;
        }

        public int getP_id() {
            return p_id;
        }

        public void setP_id(int p_id) {
            this.p_id = p_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUrl() {
            return  url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "Resource{" +
                    "res='" + res + '\'' +
                    ", pro_id=" + pro_id +
                    ", id=" + id +
                    ", type=" + type +
                    ", operator=" + operator +
                    ", p_id=" + p_id +
                    ", name='" + name + '\'' +
                    ", time='" + time + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Resource other = (Resource) obj;
            if (this.getId() == other.getId())
                return true;
            return false;
        }
    }
}
