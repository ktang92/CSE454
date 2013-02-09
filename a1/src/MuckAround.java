import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotationPipeline;
import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.pipeline.PTBTokenizerAnnotator;
import edu.stanford.nlp.pipeline.WordsToSentencesAnnotator;
import edu.stanford.nlp.time.TimeAnnotations;
import edu.stanford.nlp.time.TimeAnnotator;
import edu.stanford.nlp.util.CoreMap;

public class MuckAround {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String text = "I was born Jan 1st 2000, and I got into the NBA last year, but got cut yesterday";
		String date = "2012-01-31";

		// Dummy object for TimeAnnotator constructor
		Properties noProperties = new Properties();

		// time Annotator for pipeline
		Annotator timeAnnotator = new TimeAnnotator("sutime", noProperties);

		// the pipeline
		AnnotationPipeline pipeline = new AnnotationPipeline();

		// Add annotators
		pipeline.addAnnotator(new PTBTokenizerAnnotator(false));
		pipeline.addAnnotator(new WordsToSentencesAnnotator(false));
		//pipeline.addAnnotator(new POSTaggerAnnotator(false));
		
		// add timeAnnotator to pipeline
		pipeline.addAnnotator(timeAnnotator);

		// Create annotation of our text
		Annotation annotation = new Annotation(text);

		// Set doc date
		annotation.set(CoreAnnotations.DocDateAnnotation.class, date);

		pipeline.annotate(annotation);

	    List<CoreMap> timexAnnsAll = annotation.get(TimeAnnotations.TimexAnnotations.class);
	    
		System.out.println("result: " + timexAnnsAll);
	}

}

















