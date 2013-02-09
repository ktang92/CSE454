import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import sf.SFConstants;
import sf.SFEntity;
import sf.retriever.ProcessedCorpus;
import sf.filler.regex.SUTimeUtils;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotationPipeline;
import edu.stanford.nlp.pipeline.Annotator;
import edu.stanford.nlp.pipeline.PTBTokenizerAnnotator;
import edu.stanford.nlp.pipeline.WordsToSentencesAnnotator;
import edu.stanford.nlp.time.TimeAnnotations;
import edu.stanford.nlp.time.TimeAnnotator;
import edu.stanford.nlp.util.CoreMap;

public class PreProcessTime {
  
	public static void main(String[] args) {
		ProcessedCorpus corpus;
		SUTimeUtils timeUtil = new SUTimeUtils();

		
		try {
			
			FileWriter outFile = new FileWriter("test.time");
			PrintWriter out = new PrintWriter(outFile);

			
			
			corpus = new ProcessedCorpus();
			Map<String, String> annotations = null;
			int c = 0;
			while (corpus.hasNext()) {
				annotations = corpus.next();
				if (c++ % 100000 == 0) {
					System.err.print("finished reading " + c + " lines\r");
				}
				
				String[] tokens = annotations.get(SFConstants.TOKENS).split("\t");
				String[] meta = annotations.get(SFConstants.META).split("\t");
				String time = annotations.get(SFConstants.TIME);
				//System.out.println(tokens[0] + "\t Time: " + time);
				String senid = meta[2];
				String dateraw = senid.substring(8, 16);
				String date = dateraw.substring(0, 4) + "-" + dateraw.substring(4, 6) + "-"
									+ dateraw.substring(4, 6);
				
				
				String result = timeUtil.standardizeFormat(tokens[1], date);
				//System.out.println(tokens[0] + result);
				out.println(tokens[0] + result);
			}
			
			out.close();
			outFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
