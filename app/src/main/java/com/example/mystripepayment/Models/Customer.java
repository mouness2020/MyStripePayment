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
        "account_balance",
        "created",
        "currency",
        "default_source",
        "delinquent",
        "description",
        "discount",
        "email",
        "invoice_prefix",
        "livemode",
        "metadata",
        "shipping",
        "sources",
        "subscriptions"
})

public class Customer {

    @JsonProperty("id")
    private String id;
    @JsonProperty("object")
    private String object;
    @JsonProperty("account_balance")
    private Integer accountBalance;
    @JsonProperty("created")
    private Integer created;
    @JsonProperty("currency")
    private Object currency;
    @JsonProperty("default_source")
    private Object defaultSource;
    @JsonProperty("delinquent")
    private Boolean delinquent;
    @JsonProperty("description")
    private Object description;
    @JsonProperty("discount")
    private Object discount;
    @JsonProperty("email")
    private String email;
    @JsonProperty("invoice_prefix")
    private String invoicePrefix;
    @JsonProperty("livemode")
    private Boolean livemode;
    @JsonProperty("metadata")
    private List<Object> metadata = null;
    @JsonProperty("shipping")
    private Object shipping;
    @JsonProperty("sources")
    private Sources sources;
    @JsonProperty("subscriptions")
    private Subscriptions subscriptions;
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

    @JsonProperty("account_balance")
    public Integer getAccountBalance() {
        return accountBalance;
    }

    @JsonProperty("account_balance")
    public void setAccountBalance(Integer accountBalance) {
        this.accountBalance = accountBalance;
    }

    @JsonProperty("created")
    public Integer getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(Integer created) {
        this.created = created;
    }

    @JsonProperty("currency")
    public Object getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(Object currency) {
        this.currency = currency;
    }

    @JsonProperty("default_source")
    public Object getDefaultSource() {
        return defaultSource;
    }

    @JsonProperty("default_source")
    public void setDefaultSource(Object defaultSource) {
        this.defaultSource = defaultSource;
    }

    @JsonProperty("delinquent")
    public Boolean getDelinquent() {
        return delinquent;
    }

    @JsonProperty("delinquent")
    public void setDelinquent(Boolean delinquent) {
        this.delinquent = delinquent;
    }

    @JsonProperty("description")
    public Object getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(Object description) {
        this.description = description;
    }

    @JsonProperty("discount")
    public Object getDiscount() {
        return discount;
    }

    @JsonProperty("discount")
    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("invoice_prefix")
    public String getInvoicePrefix() {
        return invoicePrefix;
    }

    @JsonProperty("invoice_prefix")
    public void setInvoicePrefix(String invoicePrefix) {
        this.invoicePrefix = invoicePrefix;
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

    @JsonProperty("shipping")
    public Object getShipping() {
        return shipping;
    }

    @JsonProperty("shipping")
    public void setShipping(Object shipping) {
        this.shipping = shipping;
    }

    @JsonProperty("sources")
    public Sources getSources() {
        return sources;
    }

    @JsonProperty("sources")
    public void setSources(Sources sources) {
        this.sources = sources;
    }

    @JsonProperty("subscriptions")
    public Subscriptions getSubscriptions() {
        return subscriptions;
    }

    @JsonProperty("subscriptions")
    public void setSubscriptions(Subscriptions subscriptions) {
        this.subscriptions = subscriptions;
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