package com.pitang.test.payload.request;

import com.pitang.test.models.Car;

public class SessionUserIdRequest {

	private Long id;

	private Car car;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
}
