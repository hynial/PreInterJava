package com.hynial.preinter.dmodel.prototype;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class Money implements Cloneable, Serializable {

    private int faceValue;

    private Area area;

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    private List<Area> areaList;

    public int getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(int faceValue) {
        this.faceValue = faceValue;
    }

    public Money(int faceValue, Area area) {
        this.faceValue = faceValue;
        this.area = area;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    protected Money clone() throws CloneNotSupportedException {
        Money cloneMoney = null;
        // Method 1
//        cloneMoney = (Money) super.clone(); // 浅拷贝无法拷贝Area
//        cloneMoney.area = this.area.clone();  // 增加Area的拷贝 - 深拷贝
//        cloneMoney.areaList = this.areaList != null ? this.areaList.stream().map(area1 -> {
//            try {
//                return area1.clone();
//            } catch (CloneNotSupportedException e) {
//                e.printStackTrace();
//                return null;
//            }
//        }).collect(Collectors.toList()) : null;

        // Method 22
        // 调用deepClone，而不是Object的clone方法
        try {
            cloneMoney = (Money) deepClone();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return cloneMoney;
    }

    // 通过序列化深拷贝
    public Object deepClone() throws IOException, ClassNotFoundException {
        //将对象写到流里
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        //从流里读回来
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }
}
