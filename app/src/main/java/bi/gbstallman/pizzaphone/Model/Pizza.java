package bi.gbstallman.pizzaphone.Model;


public class Pizza {
    public String nom, ingredients, image;
    public Integer prix;

    public Pizza(String nom, String image, String ingredients, int prix) {
        this.nom = nom;
        this.ingredients = ingredients;
        this.prix = prix;
        this.image = image;

    }

    @Override
    public String toString() {
        return "Pizza{" +
                "nom='" + nom + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", image='" + image + '\'' +
                ", prix=" + prix +
                '}';
    }

    public Integer getPrix() {
        return prix;
    }

    public String getImage() {
        return image;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getNom() {
        return nom;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }
}


