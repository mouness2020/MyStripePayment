package com.example.mystripepayment.Models;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "object",
        "application_fee_percent",
        "billing",
        "billing_cycle_anchor",
        "cancel_at_period_end",
        "canceled_at",
        "created",
        "current_period_end",
        "current_period_start",
        "customer",
        "days_until_due",
        "discount",
        "ended_at",
        "livemode",
        "metadata",
        "plan",
        "quantity",
        "start",
        "status",
        "tax_percent",
        "trial_end",
        "trial_start"
})
public class Subscription {

    @JsonProperty("id")
    private String id;
    @JsonProperty("object")
    private String object;
    @JsonProperty("application_fee_percent")
    private Object applicationFeePercent;
    @JsonProperty("billing")
    private String billing;
    @JsonProperty("billing_cycle_anchor")
    private Integer billingCycleAnchor;
    @JsonProperty("cancel_at_period_end")
    private Boolean cancelAtPeriodEnd;
    @JsonProperty("canceled_at")
    private Object canceledAt;
    @JsonProperty("created")
    private Integer created;
    @JsonProperty("current_period_end")
    private Integer currentPeriodEnd;
    @JsonProperty("current_period_start")
    private Integer currentPeriodStart;
    @JsonProperty("customer")
    private String customer;
    @JsonProperty("days_until_due")
    private Object daysUntilDue;
    @JsonProperty("discount")
    private Object discount;
    @JsonProperty("ended_at")
    private Object endedAt;
    @JsonProperty("livemode")
    private Boolean livemode;
    @JsonProperty("metadata")
    private List<Object> metadata = null;
    @JsonProperty("plan")
    private Plan plan;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("start")
    private Integer start;
    @JsonProperty("status")
    private String status;
    @JsonProperty("tax_percent")
    private Object taxPercent;
    @JsonProperty("trial_end")
    private Object trialEnd;
    @JsonProperty("trial_start")
    private Object trialStart;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("object")
    public String getObject() {
        return object;
    }

    @JsonProperty("object")
    public void setObject(String object) {
        this.object = object;
    }

    @JsonProperty("application_fee_percent")
    public Object getApplicationFeePercent() {
        return applicationFeePercent;
    }

    @JsonProperty("application_fee_percent")
    public void setApplicationFeePercent(Object applicationFeePercent) {
        this.applicationFeePercent = applicationFeePercent;
    }

    @JsonProperty("billing")
    public String getBilling() {
        return billing;
    }

    @JsonProperty("billing")
    public void setBilling(String billing) {
        this.billing = billing;
    }

    @JsonProperty("billing_cycle_anchor")
    public Integer getBillingCycleAnchor() {
        return billingCycleAnchor;
    }

    @JsonProperty("billing_cycle_anchor")
    public void setBillingCycleAnchor(Integer billingCycleAnchor) {
        this.billingCycleAnchor = billingCycleAnchor;
    }

    @JsonProperty("cancel_at_period_end")
    public Boolean getCancelAtPeriodEnd() {
        return cancelAtPeriodEnd;
    }

    @JsonProperty("cancel_at_period_end")
    public void setCancelAtPeriodEnd(Boolean cancelAtPeriodEnd) {
        this.cancelAtPeriodEnd = cancelAtPeriodEnd;
    }

    @JsonProperty("canceled_at")
    public Object getCanceledAt() {
        return canceledAt;
    }

    @JsonProperty("canceled_at")
    public void setCanceledAt(Object canceledAt) {
        this.canceledAt = canceledAt;
    }

    @JsonProperty("created")
    public Integer getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(Integer created) {
        this.created = created;
    }

    @JsonProperty("current_period_end")
    public Integer getCurrentPeriodEnd() {
        return currentPeriodEnd;
    }

    @JsonProperty("current_period_end")
    public void setCurrentPeriodEnd(Integer currentPeriodEnd) {
        this.currentPeriodEnd = currentPeriodEnd;
    }

    @JsonProperty("current_period_start")
    public Integer getCurrentPeriodStart() {
        return currentPeriodStart;
    }

    @JsonProperty("current_period_start")
    public void setCurrentPeriodStart(Integer currentPeriodStart) {
        this.currentPeriodStart = currentPeriodStart;
    }

    @JsonProperty("customer")
    public String getCustomer() {
        return customer;
    }

    @JsonProperty("customer")
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @JsonProperty("days_until_due")
    public Object getDaysUntilDue() {
        return daysUntilDue;
    }

    @JsonProperty("days_until_due")
    public void setDaysUntilDue(Object daysUntilDue) {
        this.daysUntilDue = daysUntilDue;
    }

    @JsonProperty("discount")
    public Object getDiscount() {
        return discount;
    }

    @JsonProperty("discount")
    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    @JsonProperty("ended_at")
    public Object getEndedAt() {
        return endedAt;
    }

    @JsonProperty("ended_at")
    public void setEndedAt(Object endedAt) {
        this.endedAt = endedAt;
    }

    @JsonProperty("livemode")
    public Boolean getLivemode() {
        return livemode;
    }

    @JsonProperty("livemode")
    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }

    @JsonProperty("metadata")
    public List<Object> getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(List<Object> metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("plan")
    public Plan getPlan() {
        return plan;
    }

    @JsonProperty("plan")
    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @JsonProperty("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("start")
    public Integer getStart() {
        return start;
    }

    @JsonProperty("start")
    public void setStart(Integer start) {
        this.start = start;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("tax_percent")
    public Object getTaxPercent() {
        return taxPercent;
    }

    @JsonProperty("tax_percent")
    public void setTaxPercent(Object taxPercent) {
        this.taxPercent = taxPercent;
    }

    @JsonProperty("trial_end")
    public Object getTrialEnd() {
        return trialEnd;
    }

    @JsonProperty("trial_end")
    public void setTrialEnd(Object trialEnd) {
        this.trialEnd = trialEnd;
    }

    @JsonProperty("trial_start")
    public Object getTrialStart() {
        return trialStart;
    }

    @JsonProperty("trial_start")
    public void setTrialStart(Object trialStart) {
        this.trialStart = trialStart;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}