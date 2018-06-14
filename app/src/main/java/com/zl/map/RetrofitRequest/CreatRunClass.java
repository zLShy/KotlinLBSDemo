package com.zl.map.RetrofitRequest;

/**
 * Created by zhangli on 2018/6/14.
 */

public class CreatRunClass {
    private String guid;
    private String name;
    private String num;
    private String minKm;
    private String money;
    private long startTime;
    private long endTime;
    private String remark;
    private String imgUrl;
    private int weekCount;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(int weekCount) {
        this.weekCount = weekCount;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMinKm() {
        return minKm;
    }

    public void setMinKm(String minKm) {
        this.minKm = minKm;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "CreatRunClass{" +
                "guid='" + guid + '\'' +
                ", name='" + name + '\'' +
                ", num='" + num + '\'' +
                ", minKm='" + minKm + '\'' +
                ", money='" + money + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", remark='" + remark + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", weekCount=" + weekCount +
                '}';
    }
}
