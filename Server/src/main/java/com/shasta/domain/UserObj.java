package com.shasta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserObj {

    private long id;
    private String name;
    private String email;
    private String twitter;
    private String facebook;
    private String weixin;
    private String qq;
    private String whatsapp;
    private String phone;
    private List<HouseObj> houses;
    private List<ShastaObj> shastas;

    public List<ShastaObj> getShastas() {
        return shastas;
    }

    public void setShastas(List<ShastaObj> shastas) {
        this.shastas = shastas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<HouseObj> getHouses() {
        return houses;
    }

    public void setHouses(List<HouseObj> houses) {
        this.houses = houses;
    }
}