package com.ics.zoo.entities;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

/**
 * @MappedSuperclass indicates that a class is a superclass and is not
 *                   associated with a specific database table, but its fields
 *                   (or properties) can be inherited by child entity classes
 *                   that are associated with tables
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)

public class Audit extends CommonEntity {
	@Column(updatable = false)
	@CreatedBy
	private String createdBy;

	/**
	 * @Temporal(TemporalType.DATE): stores only date.
	 *
	 * @Temporal(TemporalType.TIME):stores only the time.
	 * @Temporal(TemporalType.TIMESTAMP):stores both date and time.
	 * 
	 */
	@Column(updatable = false)
	@CreatedDate
	@Temporal(TemporalType.DATE)
	private Date createdAt;

	@LastModifiedBy
	private String modifiedBy;

	@LastModifiedDate
	private Date lastmodifiedDate;
}
