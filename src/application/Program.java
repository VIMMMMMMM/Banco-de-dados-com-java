package application;


import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;


public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao= DaoFactory.createSellerDao();
        System.out.println("teste findById");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n teste findByDepartment");
        Department department =new Department(2,null);
        List<Seller>list=sellerDao.findByDepartment(department);

        for (Seller seller1 : list) {
            System.out.println(seller1);
        }

        System.out.println("\n teste findAll");
       list=sellerDao.findAll();
        for (Seller seller1 : list) {
            System.out.println(seller1);
        }
        System.out.println("\n teste insert");
        Seller seller1 = new Seller(null,"Greg","greg@gmail.com",new Date(),4000.00,department);
        sellerDao.insert(seller1);
        System.out.println("id novo inserido= "+seller1.getId());

        System.out.println("\n teste update");
        seller = sellerDao.findById(1);
        seller.setName("Bob Brown");
        sellerDao.update(seller);
        System.out.println("atualizacao feita");

        System.out.println("\n teste deleteById");
        sellerDao.deleteById(9);
        System.out.println("Deletado com sucesso");
    }

}
