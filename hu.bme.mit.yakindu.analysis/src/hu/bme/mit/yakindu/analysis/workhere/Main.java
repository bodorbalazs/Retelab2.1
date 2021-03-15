package hu.bme.mit.yakindu.analysis.workhere;

import java.util.ArrayList;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		
		
		
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		ArrayList<VariableDefinition> variables = new ArrayList<VariableDefinition>() ;
		ArrayList<EventDefinition> events = new ArrayList<EventDefinition>() ;
		int szam=0;
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof VariableDefinition) {
				VariableDefinition variable = (VariableDefinition) content;
				variables.add(variable);
			}
			if(content instanceof EventDefinition) {
				EventDefinition event = (EventDefinition) content;
				events.add(event);
			}
			if(content instanceof State) {
				State state = (State) content;
				System.out.println(state.getName());
				
				if(state.getName()=="") {
					state.setName("Ujnev"+szam);
					System.out.println("javasolt nev : Ujnev"+szam);
					szam++;
				}
				//print transactions
				for(Transition tr : state.getOutgoingTransitions()) {
					State outGoing =(State) tr.getTarget();
					System.out.println(state.getName()+"->"+outGoing.getName());
					
					
				}
				//csapda állapotok
				if(state.getOutgoingTransitions().size()==0) {
					System.out.println("veszelyes allapot:  " + state.getName());
				}
				
				
			}
		}
		
		
System.out.println	(	 "package hu.bme.mit.yakindu.analysis.workhere;");

System.out.println("import java.io.IOException;");

System.out.println("import hu.bme.mit.yakindu.analysis.TimerService;");
System.out.println ("import hu.bme.mit.yakindu.analysis.RuntimeService;");
System.out.println("import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;");
System.out.println("import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;");
System.out.println("import java.util.Scanner;");



System.out.println("public class RunStatechart {");
	
System.out.println(	"public static void main(String[] args) throws IOException {");
System.out.println(		"ExampleStatemachine s = new ExampleStatemachine();");
System.out.println(		"s.setTimer(new TimerService());");
System.out.println	(	"RuntimeService.getInstance().registerStatemachine(s, 200);");
System.out.println	(	"s.init();");
System.out.println	(	"s.enter();");
System.out.println	(	"s.runCycle();");
System.out.println	(	"boolean end=false;");
System.out.println	(	"while(end==false) {");
System.out.println	(		"Scanner in = new Scanner(System.in);");
System.out.println	(		"String string = in.nextLine();");
for (EventDefinition event : events) {
	String name= event.getName(); 
	name = name.substring(0,1).toUpperCase() + name.substring(1);
System.out.println	(		"if(string.equals(\""+event.getName()+"\")) {");
System.out.println	(			"s.raise"+name+"();");
System.out.println	(			"s.runCycle();");
System.out.println	(			"print(s);");
System.out.println	(		"}");

}
System.out.println	(		"if(string.equals(\"exit\")) {");
System.out.println	(			"System.exit(0);");
System.out.println	(		"end=true;");
System.out.println	(			"in.close();");
System.out.println	(		"}");
			
System.out.println(		"}");
System.out.println(	"}");
System.out.println(	"public static void print(IExampleStatemachine s) {");
//System.out.println(		"System.out.println(\"W = \" + s.getSCInterface().getWhiteTime());");
//System.out.println(		"System.out.println(\"B = \" + s.getSCInterface().getBlackTime());");
for(VariableDefinition variable : variables) {
	String Name = variable.getName();
	String Id = variable.getId();
	Name = Name.substring(0,1).toUpperCase() + Name.substring(1);
	System.out.println("System.out.println(\" " + Name.substring(0,1).toUpperCase() + " =\" + s.getSCInterface().get"+ Name +"()     );");
	
	
}




System.out.println(	);
System.out.println ("}");
System.out.println ("}");
		 
		  
		 
		
		
		
	/*	System.out.println("public static void print(IExampleStatemachine s) {");
		
			
			System.out.println
		}
		System.out.println("}");*/
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
