package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	private ArtsmiaDAO dao;
	private Map<Integer, Artist> mappaArtisti;
	
	public Map<Integer, Artist> getMappaArtisti() {
		return mappaArtisti;
	}

	private Graph<Artist, DefaultWeightedEdge> grafo;
	private List<Artist> bestList;
	private int bestPeso;
	
	public Model () {
		dao= new ArtsmiaDAO();
		
	}

	public List<String> listRoles() {
		// TODO Auto-generated method stub
		return dao.listRoles();
	}
	
	public void creaGrafo(String ruolo) {
		grafo= new SimpleWeightedGraph<Artist, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		mappaArtisti= new TreeMap<Integer, Artist>();
		dao.getArtistiByRole(mappaArtisti,ruolo);
		
		//Aggiungo i vertici
		Graphs.addAllVertices(grafo, mappaArtisti.values());
		
		//Aggiungo gli archi
		for (Coppie c: dao.getCoppie(mappaArtisti, ruolo)) {
			Graphs.addEdge(grafo, c.getArtista1(), c.getArtista2(), c.getPeso());
		}
		
	}

	public int numVertex() {
		return grafo.vertexSet().size();
	}
	
	public int numEdges() {
		return grafo.edgeSet().size();
	}
	
	public String ArtistiConnessi() {
		List <Coppie> lista= new LinkedList<Coppie>();
		String s="";
		for (DefaultWeightedEdge e: grafo.edgeSet())
		{
			lista.add(new Coppie(grafo.getEdgeTarget(e), grafo.getEdgeSource(e), (int)grafo.getEdgeWeight(e)));
		}
		lista.sort(new Comparator<Coppie>() {

			@Override
			public int compare(Coppie o1, Coppie o2) {
				// TODO Auto-generated method stub
				return o2.getPeso()-o1.getPeso();
			}
		});
		
		for (Coppie c: lista)
		{
			s+=c.getArtista1().getNome()+" "+c.getArtista1().getId()+"-"+c.getArtista2().getNome()+" "+c.getArtista2().getId()+" "+c.getPeso()+"\n";
		}
		return s;
	}
	
	public void calcolaPercorso(Artist a)
	{
		bestList= new LinkedList<Artist>();
		bestPeso=0;
		
		List<Artist> parziale= new LinkedList<Artist>();
		parziale.add(a);
		
		for (DefaultWeightedEdge e: grafo.edgesOf(a))
		{
			int peso=(int) grafo.getEdgeWeight(e);
			ricorsiva(parziale, a, peso);
		}
		
		
	}
	private void ricorsiva(List<Artist> parziale, Artist a, int peso) {
		
		if (parziale.size()>bestList.size())
		{
			bestList= new ArrayList<Artist>(parziale);
			bestPeso=peso;
		}
	
		for (DefaultWeightedEdge e: grafo.edgesOf(a)) {
			int pesoArco= (int) grafo.getEdgeWeight(e);
			if (pesoArco==peso)
			{
				Artist artista= grafo.getEdgeTarget(e);
				if (!parziale.contains(artista))
				{
					parziale.add(artista);
					ricorsiva(parziale, artista, peso);
					parziale.remove(artista);
				}
			}
		}
	}
	
	public String getBest() {
		String s="Tutti questi artisti hanno esposto "+bestPeso+" volte\n";
		for (Artist a: bestList)
		{
			s+=a.getNome()+"\n";
		}
		return s;
	}
}
