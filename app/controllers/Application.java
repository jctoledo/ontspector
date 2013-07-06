package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


import models.*;

public class Application extends Controller {

	public static OWLOntology sio;
	public static OWLDataFactory df;
	public static OWLReasoner reasoner;

	public static void index() {
		render();
	}
	
	public static void about() {
		render();
	}

	public static void signin() {
		//TODO handle the signin
		render();
	}

	public static void dashboard() {
		//TODO show board
		render();
	}

	public static void evaluate() {

		Axiom axiom = new Axiom();

		OWLClass subClass = getRandomClass();
		OWLClass superClass = getRandomSuperClass(subClass);

		axiom.subClassDescription = getDescription(subClass);
		axiom.superClassDescription = getDescription(superClass);
		axiom.subClassLabel = getLabel(subClass);
		axiom.superClassLabel = getLabel(superClass);

		axiom.subClassId = subClass.getIRI().toString();
		axiom.superClassId = superClass.getIRI().toString();
		render(axiom);
	}

	private static OWLClass getRandomSuperClass(OWLClass subClass) {
		Set<OWLClass> superClasses = reasoner.getSuperClasses(subClass, false).getFlattened();
		int randomSuperClassIndex = (int)(1 + (Math.random() * (superClasses.size() - 1)));
		List<OWLClass> owlSuperClasses = new ArrayList<OWLClass>();
		owlSuperClasses.addAll(superClasses);
		OWLClass superClass = owlSuperClasses.get(randomSuperClassIndex);
		System.out.println("random super class: " + superClass);
		return superClass;
	}

	private static OWLClass getRandomClass() {
		Set<OWLClass> allClasses = Application.sio.getClassesInSignature();
		int numberOfClasses = allClasses.size();
		System.out.println(numberOfClasses);
		int randomClassIndex = (int)(1 + (Math.random() * (numberOfClasses - 1)));
		List<OWLClass> owlClasses = new ArrayList<OWLClass>();
		owlClasses.addAll(allClasses);
		OWLClass startingClass = owlClasses.get(randomClassIndex);
		System.out.println("starting class: " + startingClass);
		return startingClass;
	}

	public static void showUser(String email) {
		User user = User.find("byEmail", email).first();
		render(user);
	}

	public static void metrics() {
		List<Axiom> axioms = Axiom.findAll();
		render(axioms);
	}

	public static String getLabel(OWLClass superClass) {
		String labelValue = null;

		OWLAnnotationProperty label = df.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI());
		for (OWLAnnotation annotation : superClass.getAnnotations(sio, label)) {
			OWLLiteral val = (OWLLiteral) annotation.getValue();
			labelValue = val.getLiteral();
		}
		return labelValue;
	}

	public static String getDescription(OWLClass superClass) {
		String descriptionValue = null;
		OWLAnnotationProperty description = df.getOWLAnnotationProperty(IRI.create("http://purl.org/dc/terms/description"));
		for (OWLAnnotation annotation : superClass.getAnnotations(sio, description)) {
			OWLLiteral val = (OWLLiteral) annotation.getValue();
			descriptionValue = val.getLiteral();
		}
		return descriptionValue;
	}

}