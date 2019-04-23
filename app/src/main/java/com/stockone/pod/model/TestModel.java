package com.stockone.pod.model;

import java.util.List;

public class TestModel {


    /**
     * invoice_id : 12
     * data : [{"id":17,"name":"invoice 1","order_id":132,"amount":5400,"created_at":"2019-03-26 19:30:00","updated_at":"2019-03-28 04:48:32","company_id":3,"pod_images":{"id_card_image":"","Acknowledgement_slip_image":""},"status":0}]
     */

    private String invoice_id;
    private List<DataBean> data;

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 17
         * name : invoice 1
         * order_id : 132
         * amount : 5400
         * created_at : 2019-03-26 19:30:00
         * updated_at : 2019-03-28 04:48:32
         * company_id : 3
         * pod_images : {"id_card_image":"","Acknowledgement_slip_image":""}
         * status : 0
         */

        private int id;
        private String name;
        private int order_id;
        private int amount;
        private String created_at;
        private String updated_at;
        private int company_id;
        private PodImagesBean pod_images;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
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

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }

        public PodImagesBean getPod_images() {
            return pod_images;
        }

        public void setPod_images(PodImagesBean pod_images) {
            this.pod_images = pod_images;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public static class PodImagesBean {
            /**
             * id_card_image :
             * Acknowledgement_slip_image :
             */

            private String id_card_image;
            private String Acknowledgement_slip_image;

            public String getId_card_image() {
                return id_card_image;
            }

            public void setId_card_image(String id_card_image) {
                this.id_card_image = id_card_image;
            }

            public String getAcknowledgement_slip_image() {
                return Acknowledgement_slip_image;
            }

            public void setAcknowledgement_slip_image(String Acknowledgement_slip_image) {
                this.Acknowledgement_slip_image = Acknowledgement_slip_image;
            }
        }
    }
}
