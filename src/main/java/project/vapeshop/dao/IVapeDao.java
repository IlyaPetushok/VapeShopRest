package project.vapeshop.dao;

import project.vapeshop.entity.product.Liquide;
import project.vapeshop.entity.product.Vape;

import java.util.List;

public interface IVapeDao extends Dao<Vape,Integer>{
    List<Vape> findByTypeVape(String typeVape);
}
