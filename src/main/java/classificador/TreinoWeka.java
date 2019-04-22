package classificador;

import weka.classifiers.*;
import weka.classifiers.trees.*;
import weka.core.*;
import weka.experiment.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;

public class TreinoWeka {
	public final static String FILENAME = "exemplo.save";

	public void train(){

		URI dbUri = null;
		try {
			dbUri = new URI(System.getenv("DATABASE_URL"));
		} catch (URISyntaxException e) {
			System.out.println("Weka não conseguiu acessar a variável de ambiente");
			e.printStackTrace();
		}
		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() +
				':' + dbUri.getPort() + dbUri.getPath();

		System.out.println("Training...");

		// load training data from database
		InstanceQuery query = null;
		try {
			query = new InstanceQuery();
		} catch (Exception e) {
			System.out.println("Weka não conseguiu criar query");
			e.printStackTrace();
		}
		query.setDatabaseURL(dbUrl);
		query.setUsername(username);
		query.setPassword(password);
		query.setQuery("select * from treinamento");
		Instances data = null;
		try {
			data = query.retrieveInstances();
		} catch (Exception e) {
			System.out.println("Weka não conseguiu executar query");
			e.printStackTrace();
		}
		data.setClassIndex(3);

		// train RandomForest
		RandomForest cl = new RandomForest();
		// further options...
		try {
			cl.buildClassifier(data);
		} catch (Exception e) {
			System.out.println("Weka não conseguiu construir classificador");
			e.printStackTrace();
		}

		// save model + header
		Vector v = new Vector();
		v.add(cl);
		v.add(new Instances(data, 0));
		try {
			SerializationHelper.write(FILENAME, v);
		} catch (Exception e) {
			System.out.println("Weka não conseguiu salvar arquivo");
			e.printStackTrace();
		}

		System.out.println("Training finished!");
	}

	/*	  
	  public void predict() throws Exception {
	    System.out.println("Predicting...");

	    // load data from database that needs predicting
	    InstanceQuery query = new InstanceQuery();
	    query.setDatabaseURL(URL);
	    query.setUsername(USER);
	    query.setPassword(PASSWORD);
	    query.setQuery("select * from Results0");  // retrieves the same table only for simplicty reasons.
	    Instances data = query.retrieveInstances();
	    data.setClassIndex(14);

	    // read model and header
	    Vector v = (Vector) SerializationHelper.read(FILENAME);
	    Classifier cl = (Classifier) v.get(0);
	    Instances header = (Instances) v.get(1);

	    // output predictions
	    System.out.println("actual -> predicted");
	    for (int i = 0; i < data.numInstances(); i++) {
	      Instance curr = data.instance(i);
	      // create an instance for the classifier that fits the training data
	      // Instances object returned here might differ slightly from the one
	      // used during training the classifier, e.g., different order of
	      // nominal values, different number of attributes.
	      Instance inst = new Instance(header.numAttributes());
	      inst.setDataset(header);
	      for (int n = 0; n < header.numAttributes(); n++) {
	        Attribute att = data.attribute(header.attribute(n).name());
	        // original attribute is also present in the current dataset
	        if (att != null) {
	          if (att.isNominal()) {
	            // is this label also in the original data?
	            // Note:
	            // "numValues() > 0" is only used to avoid problems with nominal 
	            // attributes that have 0 labels, which can easily happen with
	            // data loaded from a database
	            if ((header.attribute(n).numValues() > 0) && (att.numValues() > 0)) {
	              String label = curr.stringValue(att);
	              int index = header.attribute(n).indexOfValue(label);
	              if (index != -1)
	                inst.setValue(n, index);
	            }
	          }
	          else if (att.isNumeric()) {
	            inst.setValue(n, curr.value(att));
	          }
	          else {
	            throw new IllegalStateException("Unhandled attribute type!");
	          }
	        }
	      }

	      // predict class
	      double pred = cl.classifyInstance(inst);
	      System.out.println(inst.classValue() + " -> " + pred);
	    }

	    System.out.println("Predicting finished!");
	  }
	 */

	public static void main(String[] args) throws Exception {
		TreinoWeka m = new TreinoWeka();
		m.train();
		//m.predict();
	}

}