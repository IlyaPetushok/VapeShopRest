package project.vapeshop.dao;

import project.vapeshop.entity.product.Vaporizer;

import java.util.List;

public interface IVaporizerDao extends Dao<Vaporizer,Integer> {
    List<Vaporizer> findByTypeVaporizer(String typeVape);
}
