package project.vapeshop.dao;

import project.vapeshop.entity.product.Liquide;

import java.util.List;

public interface ILiquideDao extends Dao<Liquide,Integer>{
    List<Liquide> findByTypeNicotine(String typeNicotine);
}
