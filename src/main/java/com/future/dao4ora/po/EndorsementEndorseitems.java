package com.future.dao4ora.po;

import java.util.Date;

public class EndorsementEndorseitems {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENDORSEMENT_ENDORSEITEMS.PROPOSALSID
     *
     * @mbggenerated
     */
    private String proposalsid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENDORSEMENT_ENDORSEITEMS.BATCHID
     *
     * @mbggenerated
     */
    private String batchid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENDORSEMENT_ENDORSEITEMS.ITEMID
     *
     * @mbggenerated
     */
    private String itemid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENDORSEMENT_ENDORSEITEMS.ITEMTYPE
     *
     * @mbggenerated
     */
    private String itemtype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENDORSEMENT_ENDORSEITEMS.CREATESTAMP
     *
     * @mbggenerated
     */
    private Date createstamp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ENDORSEMENT_ENDORSEITEMS.LASTUPDATESTAMP
     *
     * @mbggenerated
     */
    private Date lastupdatestamp;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ENDORSEMENT_ENDORSEITEMS
     *
     * @mbggenerated
     */
    public EndorsementEndorseitems(String proposalsid, String batchid, String itemid, String itemtype, Date createstamp, Date lastupdatestamp) {
        this.proposalsid = proposalsid;
        this.batchid = batchid;
        this.itemid = itemid;
        this.itemtype = itemtype;
        this.createstamp = createstamp;
        this.lastupdatestamp = lastupdatestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ENDORSEMENT_ENDORSEITEMS
     *
     * @mbggenerated
     */
    public EndorsementEndorseitems() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENDORSEMENT_ENDORSEITEMS.PROPOSALSID
     *
     * @return the value of ENDORSEMENT_ENDORSEITEMS.PROPOSALSID
     *
     * @mbggenerated
     */
    public String getProposalsid() {
        return proposalsid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENDORSEMENT_ENDORSEITEMS.PROPOSALSID
     *
     * @param proposalsid the value for ENDORSEMENT_ENDORSEITEMS.PROPOSALSID
     *
     * @mbggenerated
     */
    public void setProposalsid(String proposalsid) {
        this.proposalsid = proposalsid == null ? null : proposalsid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENDORSEMENT_ENDORSEITEMS.BATCHID
     *
     * @return the value of ENDORSEMENT_ENDORSEITEMS.BATCHID
     *
     * @mbggenerated
     */
    public String getBatchid() {
        return batchid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENDORSEMENT_ENDORSEITEMS.BATCHID
     *
     * @param batchid the value for ENDORSEMENT_ENDORSEITEMS.BATCHID
     *
     * @mbggenerated
     */
    public void setBatchid(String batchid) {
        this.batchid = batchid == null ? null : batchid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENDORSEMENT_ENDORSEITEMS.ITEMID
     *
     * @return the value of ENDORSEMENT_ENDORSEITEMS.ITEMID
     *
     * @mbggenerated
     */
    public String getItemid() {
        return itemid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENDORSEMENT_ENDORSEITEMS.ITEMID
     *
     * @param itemid the value for ENDORSEMENT_ENDORSEITEMS.ITEMID
     *
     * @mbggenerated
     */
    public void setItemid(String itemid) {
        this.itemid = itemid == null ? null : itemid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENDORSEMENT_ENDORSEITEMS.ITEMTYPE
     *
     * @return the value of ENDORSEMENT_ENDORSEITEMS.ITEMTYPE
     *
     * @mbggenerated
     */
    public String getItemtype() {
        return itemtype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENDORSEMENT_ENDORSEITEMS.ITEMTYPE
     *
     * @param itemtype the value for ENDORSEMENT_ENDORSEITEMS.ITEMTYPE
     *
     * @mbggenerated
     */
    public void setItemtype(String itemtype) {
        this.itemtype = itemtype == null ? null : itemtype.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENDORSEMENT_ENDORSEITEMS.CREATESTAMP
     *
     * @return the value of ENDORSEMENT_ENDORSEITEMS.CREATESTAMP
     *
     * @mbggenerated
     */
    public Date getCreatestamp() {
        return createstamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENDORSEMENT_ENDORSEITEMS.CREATESTAMP
     *
     * @param createstamp the value for ENDORSEMENT_ENDORSEITEMS.CREATESTAMP
     *
     * @mbggenerated
     */
    public void setCreatestamp(Date createstamp) {
        this.createstamp = createstamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ENDORSEMENT_ENDORSEITEMS.LASTUPDATESTAMP
     *
     * @return the value of ENDORSEMENT_ENDORSEITEMS.LASTUPDATESTAMP
     *
     * @mbggenerated
     */
    public Date getLastupdatestamp() {
        return lastupdatestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ENDORSEMENT_ENDORSEITEMS.LASTUPDATESTAMP
     *
     * @param lastupdatestamp the value for ENDORSEMENT_ENDORSEITEMS.LASTUPDATESTAMP
     *
     * @mbggenerated
     */
    public void setLastupdatestamp(Date lastupdatestamp) {
        this.lastupdatestamp = lastupdatestamp;
    }
}