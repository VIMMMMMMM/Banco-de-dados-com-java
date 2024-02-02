package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;


import java.util.List;

public class Program2 {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        System.out.println("\nteste findAll");
        List<Department> list = departmentDao.findAll();
        for (Department department : list) {
            System.out.println(department);
        }
        System.out.println("\nteste findById");
        Department department = departmentDao.findById(2);
        System.out.println(department);

        System.out.println("\nteste insert");
        Department department1 = new Department(null,"Games");
        departmentDao.insert(department1);
        System.out.println("Departamento inserido "+department1.getId());


        System.out.println("\nteste deleteById");
        departmentDao.deleteById(5);
        System.out.println("Departamento deletado com sucesso");

        System.out.println("\nteste update");
        department = departmentDao.findById(3);
        department.setName("Food");
        departmentDao.update(department);
        System.out.println("Atualizacao feita");


    }
}
