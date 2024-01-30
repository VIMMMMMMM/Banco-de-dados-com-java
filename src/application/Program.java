package application;


import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Program {
    public static void main(String[] args) {
        Department obj = new Department(1,"livros");
        System.out.println(obj);
        Seller seller = new Seller(21,"bob","bob@gmail.com",new Date(),3000.00,obj);
        System.out.println(seller);
        SellerDao sellerDao= DaoFactory.createSellerDao();
    }
}