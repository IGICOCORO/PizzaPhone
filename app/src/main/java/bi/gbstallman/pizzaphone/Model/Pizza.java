package bi.gbstallman.pizzaphone.Model;


public class Pizza {
    public String prix, ingredients, image, nom;

    public Pizza(String prix, String ingredients, String image, String nom) {
        this.prix = prix;
        this.ingredients = ingredients;
        this.image = image;
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