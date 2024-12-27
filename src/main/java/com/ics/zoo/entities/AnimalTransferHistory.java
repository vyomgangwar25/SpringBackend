
package com.ics.zoo.entities;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

/**
 * AnimalTransferHistory Entity
 * @author Vyom Gangwar
 * */
@Entity
public class AnimalTransferHistory extends CommonEntity {

	@ManyToOne
	@JoinColumn(name = "from_zoo_id", referencedColumnName = "id")
	private Zoo fromZoo;

	@ManyToOne
	@JoinColumn(name = "to_zoo_id", referencedColumnName = "id")
	private Zoo toZoo;

	@ManyToOne
	@JoinColumn(name = "user_name", referencedColumnName = "id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "animal_id", referencedColumnName = "id")
	private Animal animal;

	private Date date;

}
