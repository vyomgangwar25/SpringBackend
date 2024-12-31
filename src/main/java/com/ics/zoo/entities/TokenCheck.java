package com.ics.zoo.entities;

import java.time.LocalTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
/**
 * TokenCheck Entity
 * 
 * @author Vyom Gangwar
 */
@EntityListeners(AuditingEntityListener.class)
@Builder
@Entity
public class TokenCheck extends CommonEntity {

	private String token;
	private Boolean isvalid;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Column(updatable = false)
	@CreatedDate
	@Temporal(TemporalType.TIME)
	private LocalTime createdAt;

//	public static TokenCheckBuilder builder(String generated_token, boolean b, User existingUser) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	public TokenCheck(String token, Boolean isvalid, User user) {
//		this.token = token;
//		this.isvalid = isvalid;
//		this.user = user;
//
//	}

}
