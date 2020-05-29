package it.polito.tdp.artsmia.model;

public class TestModel {

	public static void main(String[] args) {
		Model model= new Model();
		model.creaGrafo("photographer");
		System.out.println(model.numVertex());
		System.out.println(model.numEdges());

		model.calcolaPercorso(model.getMappaArtisti().get(11));
		System.out.println(model.getBest());
	}

}
