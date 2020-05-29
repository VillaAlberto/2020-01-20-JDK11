package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnCreaGrafo;

	@FXML
	private Button btnArtistiConnessi;

	@FXML
	private Button btnCalcolaPercorso;

	@FXML
	private ComboBox<String> boxRuolo;

	@FXML
	private TextField txtArtista;

	@FXML
	private TextArea txtResult;

	@FXML
	void doArtistiConnessi(ActionEvent event) {
		txtResult.clear();
		txtResult.appendText("Calcola artisti connessi\n");
		txtResult.appendText(model.ArtistiConnessi());
	}

	@FXML
	void doCalcolaPercorso(ActionEvent event) {
		try {
			int artista = Integer.parseInt(txtArtista.getText());
			if (!model.getMappaArtisti().containsKey(artista)) {
				txtResult.setText("Questo artista non esiste, informati meglio...");
				return;
			}
			else {model.calcolaPercorso(model.getMappaArtisti().get(artista));
					txtResult.setText(model.getBest());
					};
		} catch (NumberFormatException e) {
			txtResult.setText("Non hai introdotto un numero, cafone!");
		} catch (

		Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void doCreaGrafo(ActionEvent event) {
		txtResult.clear();
		model.creaGrafo(boxRuolo.getValue());
		txtResult.appendText(
				String.format("Grafo creato con vertici %d e archi %d", model.numVertex(), model.numEdges()));

	}

	public void setModel(Model model) {
		this.model = model;
		List<String> listaRuoli = model.listRoles();
		boxRuolo.getItems().addAll(listaRuoli);
		boxRuolo.setValue(listaRuoli.get(0));

	}

	@FXML
	void initialize() {
		assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

	}
}
