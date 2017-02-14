package es.cice.moviedbfinalproject.model;

/**
 * Created by cice on 20/1/17.
 */
public class Film {
    private String titulo;
    private String anyo;
    private String genero;
    private int imagen;

    public Film(String titulo, String anyo, int imagen, String genero) {
        this.titulo = titulo;
        this.anyo = anyo;
        this.imagen = imagen;
        this.genero = genero;

    }

    public String getDescripcion() {
        return titulo;
    }

    public void setDescripcion(String descripcion) {
        this.titulo = descripcion;
    }

    public String getFabricante() {
        return anyo;
    }

    public void setFabricante(String fabricante) {
        this.anyo = fabricante;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
    public int getMiniatura (){
        return imagen;
    }




    public String getModelo() {
        return genero;
    }

    public void setModelo(String modelo) {
        this.genero = modelo;
    }


}
