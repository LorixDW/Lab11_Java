package org.example;

import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Session session = HibernetUtil.getSessionFactory().openSession();
        Scanner scanner;
        boolean exit_key = true;
        while (exit_key){
            scanner = new Scanner(System.in);
            if(scanner.hasNext()){
                List<String> sc = Arrays.stream(scanner.nextLine().split(" ")).filter(s -> s.length() > 0).collect(Collectors.toList());
                switch (sc.get(0)){
                    case "/q":
                    case "/quit":
                        exit_key = false;
                        System.out.println("Exited");
                        break;
                    case "/showProductsByPerson":
                        if(sc.size() > 1){
                            try {
                                List<Customer_Product> productList = session.createQuery("select c.customer_product from Customer c where c.name = :name")
                                        .setParameter("name", sc.get(1)).getResultList();
                                System.out.println(sc.get(1) + " has the following products:");
                                for (Customer_Product s: productList){
                                    System.out.println(s.getProduct().getName());
                                }
                            }
                            catch (Exception e){
                                System.out.println("Read Error: person not found");
                            }
                        }
                        else {
                            System.out.println("Read Error: incorrect /showProductsByPerson using; check help");
                        }
                        break;
                    case "/findPersonsByProductTitle":
                        if(sc.size() > 1){
                            try {
                                List<Customer_Product> productList = session.createQuery("select p.customer_product from Product p where p.name = :name")
                                        .setParameter("name", sc.get(1)).getResultList();
                                System.out.println(sc.get(1) + " bought by following customers:");
                                for (Customer_Product c: productList){
                                    System.out.println(c.getCustomer().getName());
                                }
                            }
                            catch (Exception e){
                                System.out.println("Read Error: product not found");
                            }
                        }
                        else {
                            System.out.println("Read Error: incorrect /findPersonsByProductTitle using; check help");
                        }
                        break;
                    case "/removePerson":
                        if (sc.size() > 1){

                                session.beginTransaction();
                                List<Integer> customer_products = session.createQuery("select cp.buy_id from Customer c join Customer_Product cp on (c = cp.customer) where c.name = :name")
                                        .setParameter("name", sc.get(1)).getResultList();
                                for(int cp: customer_products){
                                    session.createQuery("delete from Customer_Product where buy_id = :id").setParameter("id", cp).executeUpdate();
                                }
                                session.createQuery("delete from Customer c where c.name = :name").setParameter("name", sc.get(1)).executeUpdate();
                                session.getTransaction().commit();try {
                            }
                            catch (Exception e){
                                System.out.println("Read Error: Customer not found");
                            }
                        }
                        else {
                            System.out.println("Read Error: incorrect /removePerson");
                        }
                        break;
                    case "/removeProduct":
                        if (sc.size() > 1){
                            try{session.beginTransaction();
                                List<Integer> customer_products = session.createQuery("" +
                                        "select cp.buy_id from Product p join Customer_Product cp on (p = cp.product) where p.name = :name").setParameter("name", sc.get(1)).getResultList();
                                for(int cp: customer_products){
                                    session.createQuery("delete from Customer_Product where buy_id = :id").setParameter("id", cp).executeUpdate();
                                }
                                session.createQuery("delete from Product p where p.name = :name").setParameter("name", sc.get(1)).executeUpdate();
                                session.getTransaction().commit();
                            }
                            catch (Exception e){
                                System.out.println("Read Error: Product not found");
                            }
                        }
                        else {
                            System.out.println("Read Error: incorrect /removeProduct");
                        }
                        break;
                    case "/buy":
                        if(sc.size() > 2){
                            try {
                                session.beginTransaction();
                                List<Customer> c = session.createQuery("from Customer where name = :name")
                                        .setParameter("name", sc.get(1)).getResultList();
                                List<Product> p = session.createQuery("from Product where name = :name")
                                        .setParameter("name", sc.get(2)).getResultList();
                                Customer_Product c_p = new Customer_Product();
                                c_p.setCustomer(c.get(0));
                                c_p.setProduct(p.get(0));
                                c_p.setValue(p.get(0).getPrice());
                                session.save(c_p);
                                session.getTransaction().commit();
                            }
                            catch (Exception e){
                                System.out.println("Read Error: Product or Customer was not found");
                            }

                        }
                        else {
                            System.out.println("Read Error: incorrect /buy");
                        }
                        break;
                    case "/h":
                    case "/help":
                        System.out.println("/q or /quit - exit from program\n" +
                                "/showProductsByPerson <person_name> - show products bought by this person\n" +
                                "/findPersonsByProductTitle <product_title> - show persons bought this product\n" +
                                "/removePerson <person_name> - delete person\n" +
                                "/removeProduct <product_title> - delete product\n" +
                                "/buy <person_name> <product_title>" +
                                "/h or /help - show commands"
                                );
                        break;
                    default:
                        System.out.println("Read Error: Incorrect input ");
                        for (String s: sc) {
                            System.out.println("<" + s + ">");
                        }
                        break;
                }
            }
            else{
                System.out.println("Read Error: Empty input " );
            }
        }
        session.close();
        HibernetUtil.Shutdown();
    }
}