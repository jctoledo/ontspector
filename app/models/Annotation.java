package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Annotation extends Model {

	public String date;

	public String comment;

	public String evaluation;

}