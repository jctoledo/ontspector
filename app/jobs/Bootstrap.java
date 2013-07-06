package jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import controllers.Application;

import models.Annotation;
import models.Axiom;
import models.User;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {

	public void doJob() throws OWLOntologyCreationException {
		System.out.println("Loading dummy data...");
		Fixtures.loadModels("../test/data.yml");
		System.out.println("Dummy data loaded!");

		System.out.println("Load ontology...");

		String DOCUMENT_IRI = "http://semanticscience.org/ontology/sio.owl";

		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		Application.df = manager.getOWLDataFactory();
		IRI docIRI = IRI.create(DOCUMENT_IRI);
		Application.sio = manager.loadOntologyFromOntologyDocument(docIRI);
		System.out.println("ontology loaded!");
		System.out.println("Loaded " + Application.sio.getOntologyID());
		OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
		Application.reasoner = reasonerFactory.createReasoner(Application.sio);
		System.out.println("starting reasoning...");
		Application.reasoner.precomputeInferences();
		System.out.println("end reasoning.");

	}
}
