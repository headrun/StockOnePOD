package com.stockone.pod.model;

import java.util.List;

public class OrdrDetails {

    /**
     * truck_number : MH20EG6217
     * data : [{"alternative_mobile_no":"9896352562","uid":" ","district":"Ahmednagar","ship_quantity":1,"id_proof_number":" ","id_card":" ","mobile_no":"7083888214","sku_code":"RR20N1Y1ZSE/HL","loan_proposal_id":"5112288000086468","signed_invoice_copy":" ","id_type":" ","time":0,"pod_status":false,"serial_number":"04194PAM100602","model":"Samsung Refrigerator 3 star","id":30979,"customer_name":"Sangita Dnyndeo Jadhav"},{"alternative_mobile_no":"9763327462","uid":" ","district":"Ahmednagar","ship_quantity":1,"id_proof_number":" ","id_card":" ","mobile_no":"9112740971","sku_code":"RR20N1Y1ZSE/HL","loan_proposal_id":"5112287000087519","signed_invoice_copy":" ","id_type":" ","time":0,"pod_status":false,"serial_number":"04194PAM100481","model":"Samsung Refrigerator 3 star","id":30980,"customer_name":"Abedabi Mahebub Shaikh"},{"alternative_mobile_no":"9272294409","uid":" ","district":"Aurangabad","ship_quantity":1,"id_proof_number":" ","id_card":" ","mobile_no":"9404007077","sku_code":"RR20N1Y1ZSE/HL","loan_proposal_id":"5112225000104459","signed_invoice_copy":" ","id_type":" ","time":0,"pod_status":false,"serial_number":"04194PAM100457","model":"Samsung Refrigerator 3 star","id":30981,"customer_name":"Karimabi Shaikh Hanif"},{"alternative_mobile_no":"9527832904","uid":" ","district":"Ahmednagar","ship_quantity":1,"id_proof_number":" ","id_card":" ","mobile_no":"8888317771","sku_code":"RR20N1Y1ZSE/HL","loan_proposal_id":"5113998000002887","signed_invoice_copy":" ","id_type":" ","time":0,"pod_status":false,"serial_number":"04194PAM100601","model":"Samsung Refrigerator 3 star","id":30982,"customer_name":"Sayarabi Shaikh"},{"alternative_mobile_no":"9021479143","uid":" ","district":"Ahmednagar","ship_quantity":1,"id_proof_number":" ","id_card":" ","mobile_no":"8928916013","sku_code":"RR20N1Y1ZSE/HL","loan_proposal_id":"5112290000088701","signed_invoice_copy":" ","id_type":" ","time":0,"pod_status":false,"serial_number":"04194PAM100447","model":"Samsung Refrigerator 3 star","id":30983,"customer_name":"Manisha Dattatray Wede"},{"alternative_mobile_no":"9172721182","uid":" ","district":"Ahmednagar","ship_quantity":1,"id_proof_number":" ","id_card":" ","mobile_no":"9921340513","sku_code":"RR20N1Y1ZSE/HL","loan_proposal_id":"5112290000088419","signed_invoice_copy":" ","id_type":" ","time":0,"pod_status":false,"serial_number":"04194PAM100529","model":"Samsung Refrigerator 3 star","id":30984,"customer_name":"Sarika Annasaheb Gunjal"},{"alternative_mobile_no":"9380151690","uid":" ","district":"Aurangabad","ship_quantity":1,"id_proof_number":" ","id_card":" ","mobile_no":"9765871781","sku_code":"RR20N1Y1ZSE/HL","loan_proposal_id":"5112225000104420","signed_invoice_copy":" ","id_type":" ","time":0,"pod_status":false,"serial_number":"04194PAM100455","model":"Samsung Refrigerator 3 star","id":30985,"customer_name":"Surekha Vitthal Mahor"},{"alternative_mobile_no":"7219099767","uid":" ","district":"Ahmednagar","ship_quantity":1,"id_proof_number":" ","id_card":" ","mobile_no":"9665832426","sku_code":"RR20N1Y1ZSE/HL","loan_proposal_id":"5113301000023227","signed_invoice_copy":" ","id_type":" ","time":0,"pod_status":false,"serial_number":"04194PAM100468","model":"Samsung Refrigerator 3 star","id":30986,"customer_name":"Parvin Shabbir Shaikh"},{"alternative_mobile_no":"01139238379,10734#","uid":" ","district":"Pune","ship_quantity":1,"id_proof_number":" ","id_card":" ","mobile_no":"10734","sku_code":"RR20N1Y1ZSE/HL","loan_proposal_id":"5112244000121520","signed_invoice_copy":" ","id_type":" ","time":0,"pod_status":false,"serial_number":"04194PAM100656","model":"Samsung Refrigerator 3 star","id":30987,"customer_name":"Laxmi Pandit Waghmode"},{"alternative_mobile_no":"01139238379,10819#","uid":" ","district":"Ahmednagar","ship_quantity":1,"id_proof_number":" ","id_card":" ","mobile_no":"10819","sku_code":"RR20N1Y1ZSE/HL","loan_proposal_id":"5112291000062297","signed_invoice_copy":" ","id_type":" ","time":0,"pod_status":false,"serial_number":"04194PAM100255","model":"Samsung Refrigerator 3 star","id":30988,"customer_name":"Vaishali Sachin Sonawane"}]
     * driver_phone_number : 8308825002
     * manifest_number : 4339504674
     * driver_name : Santosh Jagdale
     */

    private String truck_number;
    private String driver_phone_number;
    private String manifest_number;
    private String driver_name;
    private List<OrdrDetails.DataBean> data;

    public String getTruck_number() {
        return truck_number;
    }

    public void setTruck_number(String truck_number) {
        this.truck_number = truck_number;
    }

    public String getDriver_phone_number() {
        return driver_phone_number;
    }

    public void setDriver_phone_number(String driver_phone_number) {
        this.driver_phone_number = driver_phone_number;
    }

    public String getManifest_number() {
        return manifest_number;
    }

    public void setManifest_number(String manifest_number) {
        this.manifest_number = manifest_number;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public List<OrdrDetails.DataBean> getData() {
        return data;
    }

    public void setData(List<OrdrDetails.DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * alternative_mobile_no : 9896352562
         * uid :
         * district : Ahmednagar
         * ship_quantity : 1
         * id_proof_number :
         * id_card :
         * mobile_no : 7083888214
         * sku_code : RR20N1Y1ZSE/HL
         * loan_proposal_id : 5112288000086468
         * signed_invoice_copy :
         * id_type :
         * time : 0
         * pod_status : false
         * serial_number : 04194PAM100602
         * model : Samsung Refrigerator 3 star
         * id : 30979
         * customer_name : Sangita Dnyndeo Jadhav
         */

        private String alternative_mobile_no;
        private String uid;
        private String district;
        private int ship_quantity;
        private String id_proof_number;
        private String id_card;
        private String mobile_no;
        private String sku_code;
        private String loan_proposal_id;
        private String signed_invoice_copy;
        private String id_type;
        private int time;
        private boolean pod_status;
        private String serial_number;
        private String model;
        private int id;
        private String customer_name;
        private String manifest_no;
        private boolean isUploaded = true;

        public String getAlternative_mobile_no() {
            return alternative_mobile_no;
        }

        public void setAlternative_mobile_no(String alternative_mobile_no) {
            this.alternative_mobile_no = alternative_mobile_no;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public int getShip_quantity() {
            return ship_quantity;
        }

        public void setShip_quantity(int ship_quantity) {
            this.ship_quantity = ship_quantity;
        }

        public String getId_proof_number() {
            return id_proof_number;
        }

        public void setId_proof_number(String id_proof_number) {
            this.id_proof_number = id_proof_number;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }

        public String getSku_code() {
            return sku_code;
        }

        public void setSku_code(String sku_code) {
            this.sku_code = sku_code;
        }

        public String getLoan_proposal_id() {
            return loan_proposal_id;
        }

        public void setLoan_proposal_id(String loan_proposal_id) {
            this.loan_proposal_id = loan_proposal_id;
        }

        public String getSigned_invoice_copy() {
            return signed_invoice_copy;
        }

        public void setSigned_invoice_copy(String signed_invoice_copy) {
            this.signed_invoice_copy = signed_invoice_copy;
        }

        public String getId_type() {
            return id_type;
        }

        public void setId_type(String id_type) {
            this.id_type = id_type;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public boolean isPod_status() {
            return pod_status;
        }

        public void setPod_status(boolean pod_status) {
            this.pod_status = pod_status;
        }

        public String getSerial_number() {
            return serial_number;
        }

        public void setSerial_number(String serial_number) {
            this.serial_number = serial_number;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public String getManifest_no() {
            return manifest_no;
        }

        public void setManifest_no(String manifest_no) {
            this.manifest_no = manifest_no;
        }

        public boolean isUploaded() {
            return isUploaded;
        }

        public void setUploaded(boolean uploaded) {
            isUploaded = uploaded;
        }
    }
}
