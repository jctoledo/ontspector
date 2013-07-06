package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;


@Entity
public class Axiom extends Model {

	public String subClassId;

	public String superClassId;
	
	public String subClassDescription;
	
	public String superClassDescription;
	
	public String superClassLabel;
	
	public String subClassLabel;
	
	public int numAgreement;
	
	public int numDisagreement;
	
	public int numDontKnow;

	@OneToMany
	public	List<Annotation> annotations;
}
