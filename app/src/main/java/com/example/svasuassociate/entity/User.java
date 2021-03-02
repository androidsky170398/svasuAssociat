package com.example.svasuassociate.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class User implements Serializable {
    private String id, username, email,
            commission, name_prefix,
            first_name, last_name, mobile,
            father_name, state, city,
            address, pincode, dateofbirth,
            aadhar_no, pan_no, gender, profile_image,
            referel_code, refered_by, created_by,
            password, api_token, otp,
            otp_sent_at, fcm_token,
            remember_token, device_id, status,
            created_at, updated_at, usertype, agent_count, project_count,
            block_count, customer_count, flat_count,
            today_income,
            monthly_income,
            payment_receipt,
            plot_count,
            row_houses_count,
            ifsc_code,
            account_number,
            bank_name;

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getName_prefix() {
        return name_prefix;
    }

    public void setName_prefix(String name_prefix) {
        this.name_prefix = name_prefix;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getAadhar_no() {
        return aadhar_no;
    }

    public void setAadhar_no(String aadhar_no) {
        this.aadhar_no = aadhar_no;
    }

    public String getPan_no() {
        return pan_no;
    }

    public void setPan_no(String pan_no) {
        this.pan_no = pan_no;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getReferel_code() {
        return referel_code;
    }

    public void setReferel_code(String referel_code) {
        this.referel_code = referel_code;
    }

    public String getRefered_by() {
        return refered_by;
    }

    public void setRefered_by(String refered_by) {
        this.refered_by = refered_by;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOtp_sent_at() {
        return otp_sent_at;
    }

    public void setOtp_sent_at(String otp_sent_at) {
        this.otp_sent_at = otp_sent_at;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getRemember_token() {
        return remember_token;
    }

    public void setRemember_token(String remember_token) {
        this.remember_token = remember_token;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public static User createobjectFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<User>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String createjsonFromObject(User object) {
        Gson gson = new Gson();
        Type type = new TypeToken<User>() {
        }.getType();
        return gson.toJson(object, type);
    }

    public static List<User> createListFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<User>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String createJsonFromList(List<User> object) {
        Gson gson = new Gson();
        Type type = new TypeToken<User>() {
        }.getType();
        return gson.toJson(object, type);
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getAgent_count() {
        return agent_count;
    }

    public void setAgent_count(String agent_count) {
        this.agent_count = agent_count;
    }

    public String getProject_count() {
        return project_count;
    }

    public void setProject_count(String project_count) {
        this.project_count = project_count;
    }

    public String getBlock_count() {
        return block_count;
    }

    public void setBlock_count(String block_count) {
        this.block_count = block_count;
    }

    public String getCustomer_count() {
        return customer_count;
    }

    public void setCustomer_count(String customer_count) {
        this.customer_count = customer_count;
    }

    public String getFlat_count() {
        return flat_count;
    }

    public void setFlat_count(String flat_count) {
        this.flat_count = flat_count;
    }

    public String getToday_income() {
        return today_income;
    }

    public void setToday_income(String today_income) {
        this.today_income = today_income;
    }

    public String getMonthly_income() {
        return monthly_income;
    }

    public void setMonthly_income(String monthly_income) {
        this.monthly_income = monthly_income;
    }

    public String getPayment_receipt() {
        return payment_receipt;
    }

    public void setPayment_receipt(String payment_receipt) {
        this.payment_receipt = payment_receipt;
    }

    public String getPlot_count() {
        return plot_count;
    }

    public void setPlot_count(String plot_count) {
        this.plot_count = plot_count;
    }

    public String getRow_houses_count() {
        return row_houses_count;
    }

    public void setRow_houses_count(String row_houses_count) {
        this.row_houses_count = row_houses_count;
    }
}
