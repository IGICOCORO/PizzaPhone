package bi.gbstallman.pizzaphone.Model;


public class Pizza {
    public String  ingredients, image, nom;
    public Double prix;
    public int quantite;

    public Pizza(Double  prix, String ingredients, String image, String nom) {
        this.prix = prix;
        this.ingredients = ingredients;
        this.image = image;
        this.quantite = 0;
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "prix='" + prix + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", image='" + image + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }
}