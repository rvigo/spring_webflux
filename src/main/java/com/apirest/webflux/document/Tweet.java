package com.apirest.webflux.document;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Tweet {
	@Id
	private String id;

	@NotBlank
	private String user;
	@NotBlank
	@Size(max = 140)
	private String message;
	@NotNull
	private LocalDate timeStamp;

	public Tweet(String user, String message) {
		this.message = message;
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public LocalDate getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDate timeStamp) {
		this.timeStamp = timeStamp;
	}

}
