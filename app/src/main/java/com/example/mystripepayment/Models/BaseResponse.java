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
        "response",
        "plans"
})
public class BaseResponse<T> {

    @JsonProperty("response")
    private String response;
    @JsonProperty("plans")
    private List<Plan> plans = null;

    @JsonProperty("subscription")
    private Subscription subscription;

    @JsonProperty("customer")
    private Customer customer;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("response")
    public String getResponse() {
        return response;
    }

    @JsonProperty("response")
    public void setResponse(String response) {
        this.response = response;
    }

    @JsonProperty("plans")
    public List<Plan> getPlans() {
        return plans;
    }

    @JsonProperty("plans")
    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    @JsonProperty("customer")
    public Customer getCustomer() {
        return customer;
    }

    @JsonProperty("customer")
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @JsonProperty("subscription")
    public Subscription getSubscription() {
        return subscription;
    }

    @JsonProperty("subscription")
    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
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
