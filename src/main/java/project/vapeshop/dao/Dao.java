package project.vapeshop.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.vapeshop.predicate.CustomPredicate;

import java.util.List;

@Repository
public interface Dao<T, С> {
    T insertObject(T t);

    List<T> insertObjects(List<T> t);

    List<T> selectObjects(Pageable pageable);

    T selectObject(С id);

    T update(T t);

    boolean delete(С id);

    Page<T> selectObjectsByFilter(List<CustomPredicate<?>> customPredicates, Pageable pageable);
}
