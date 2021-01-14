package com.jpaspecwebmodel;

import lombok.Data;

@Data
public class FilterRequest {
    private Boolean active;
    private String zipFilter;

    public FilterRequest() { }

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getZipFilter() {
		return zipFilter;
	}

	public void setZipFilter(String zipFilter) {
		this.zipFilter = zipFilter;
	}
    
    
}
