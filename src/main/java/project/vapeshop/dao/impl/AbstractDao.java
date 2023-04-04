package project.vapeshop.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import project.vapeshop.dao.Dao;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.entity.EntityId;
import project.vapeshop.predicate.CustomPredicate;
//import javax.persistence.*;
//import javax.persistence.criteria.*;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class AbstractDao<T extends EntityId<?>,C> implements Dao<T,C> {
    private static final int BATCH_SIZE=10;
    private final  Class<T> tClass;



    public AbstractDao() {
        this.tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public T insertObject(T t) {
        entityManager.persist(t);
        return t;
    }

    @Override
    public List<T> insertObjects(List<T> tList) {
        for (int i = 0; i < tList.size(); i++) {
            if ( i>0 && i % BATCH_SIZE == 0){
                entityManager.flush();
                entityManager.clear();
            }
            entityManager.persist(tList.get(i));
        }
        return tList;
    }


    public  List<T> selectObjects(Pageable pageable){
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tClass);
        Root<T> root=criteriaQuery.from(tClass);
        sortByOrder(pageable,root,criteriaQuery);
        TypedQuery<T> query=entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPageNumber()*pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }

    public T selectObject(C id){
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tClass);
        Root<T> root=criteriaQuery.from(tClass);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"),id));
        TypedQuery<T> typedQuery=entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    @Override
    public T update(T t) {
        return entityManager.merge(t);
    }

    @Override
    public boolean delete(C id) {
        T t=selectObject(id);
        entityManager.remove(t);
        return true;
    }

    @Override
    public Page<T> selectObjectsByFilter(List<CustomPredicate<?>> customPredicates, Pageable pageable) {
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery= criteriaBuilder.createQuery(tClass);
        Root<T> root= criteriaQuery.from(tClass);
        List<Predicate> predicates=createPredicate(customPredicates,root);
        Predicate finalPredicate= criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        criteriaQuery.where(criteriaBuilder.and(finalPredicate));
        sortByOrder(pageable,root,criteriaQuery);
        TypedQuery<T> query=entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getPageNumber()*pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        long count=countObject(finalPredicate);
        return new PageImpl<>(query.getResultList(),pageable,count);
    }

    protected List<Predicate> createPredicate(List<CustomPredicate<?>> customPredicates, Root<T> root){
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        List<Predicate> predicates=new ArrayList<>();
        for (CustomPredicate<?> customPredicate : customPredicates) {
            Class<?> tClass=customPredicate.getTClass();
            switch (customPredicate.getCompType()){
                case EQUAL:
                    predicates.add(criteriaBuilder.equal(root.get(customPredicate.getNameField().getName()),customPredicate.getValue()));
                    break;
                case LIKE:
                    predicates.add(criteriaBuilder.like(root.get(customPredicate.getNameField().getName()),"%"+customPredicate.getValue()+"%"));
                    break;
                case LESS:
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(customPredicate.getNameField().getName()),customPredicate.getValue().toString()));
                    break;
                case MORE:
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(customPredicate.getNameField().getName()),customPredicate.getValue().toString()));
                    break;
                case BETWEEN:
                    if (Double.class.equals(tClass)) {
                        predicates.add(criteriaBuilder.between(root.get(customPredicate.getNameField().getName()), (Double) customPredicate.getValue(), (Double) customPredicate.getMaxValue()));
                    }
                    if (Date.class.equals(tClass)) {
                        predicates.add(criteriaBuilder.between(root.get(customPredicate.getNameField().getName()),(Date) customPredicate.getValue(), (Date) customPredicate.getMaxValue()));
                    }
                    if(Integer.class.equals(tClass)){
                        predicates.add(criteriaBuilder.between(root.get(customPredicate.getNameField().getName()),(Integer) customPredicate.getValue(), (Integer) customPredicate.getMaxValue()));
                    }
                    if (BigDecimal.class.equals(tClass)) {
                        predicates.add(criteriaBuilder.between(root.get(customPredicate.getNameField().getName()), (BigDecimal) customPredicate.getValue(), (BigDecimal) customPredicate.getMaxValue()));
                    }
                    break;
                case IN:
                    if(String.class.equals(tClass)){
                        predicates.add(criteriaBuilder.in(root.get(customPredicate.getNameField().getName())).value(customPredicate.getValues()));
                    }
                    break;
            }
        }
        return predicates;
    }

    protected Long countObject(Predicate predicate){
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery= criteriaBuilder.createQuery(Long.class);
        Root<T> root=criteriaQuery.from(tClass);
        criteriaQuery.select(criteriaBuilder.count(root)).where(predicate);
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    protected void sortByOrder(Pageable pageable,Root<T> root,CriteriaQuery<T> criteriaQuery){
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        Sort sort=pageable.getSort();
        Optional<Sort.Order> order=sort.get().findFirst();
        if(order.isPresent()){
            if(order.get().isAscending()){
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(order.get().getProperty())));
            }else {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(order.get().getProperty())));
            }
        }
    }
}
