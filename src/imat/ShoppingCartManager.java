package imat;

import se.chalmers.cse.dat216.project.Product;

/* Implement to use the VarukorgItem class independently */
public interface ShoppingCartManager {
    void handleAddProduct(Product product);
    void handleRemoveProduct(Product product);
}
