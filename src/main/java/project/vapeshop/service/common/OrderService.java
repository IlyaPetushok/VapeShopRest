package project.vapeshop.service.common;

import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import project.vapeshop.dao.IOrderDao;
import project.vapeshop.dto.common.OrderDTOForBasket;
import project.vapeshop.dto.common.OrderDTOFullInfo;
import project.vapeshop.dto.common.OrderDTOSaleFullInfo;
import project.vapeshop.dto.common.OrderDTOSaleResponse;
import project.vapeshop.dto.filter.CustomSortDirection;
import project.vapeshop.dto.filter.FilterRequest;
import project.vapeshop.dto.filter.OrderDTOFilter;
import project.vapeshop.dto.user.UserDTOForCommon;
import project.vapeshop.entity.common.Order;
import project.vapeshop.entity.common.Order_;
import project.vapeshop.entity.common.StatusOrder;
import project.vapeshop.entity.user.User;
import project.vapeshop.exception.AccessDeniedException;
import project.vapeshop.exception.NotFoundException;
import project.vapeshop.predicate.ComparisonType;
import project.vapeshop.predicate.CustomPredicate;
import project.vapeshop.security.CustomUserDetails;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Transactional(readOnly = true)
public class OrderService {
    IOrderDao dao;
    ModelMapper modelMapper;

    @Autowired
    public OrderService(IOrderDao dao, ModelMapper modelMapper) {
        this.dao = dao;
        this.modelMapper = modelMapper;
    }

    public OrderDTOFullInfo showObject(int id, CustomUserDetails customUserDetails) {
        Order order;
        try {
            order = dao.selectObject(id);
            hasAccess(order,customUserDetails);
        } catch (NoResultException e) {
            throw new NotFoundException("operation is fail because order dont found");
        }
        return modelMapper.map(order, OrderDTOFullInfo.class);
    }

    public List<OrderDTOForBasket> showObjects(FilterRequest filterRequest) {
        List<Order> orders = dao.selectObjects(filterRequest.getPageable());
        if (orders.isEmpty()) {
            throw new NotFoundException("order list is empty");
        }
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTOForBasket.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDTOForBasket addObject(OrderDTOFullInfo orderDTOFullInfo) {
        return modelMapper.map(dao.insertObject(modelMapper.map(orderDTOFullInfo, Order.class)), OrderDTOForBasket.class);
    }


    @Transactional
    public boolean deleteObject(int id,CustomUserDetails customUserDetails) {
        Order order;
        try {
            order=dao.selectObject(id);
            hasAccess(order,customUserDetails);
            return dao.delete(id);
        } catch (NoResultException e) {
            throw new NotFoundException("operation is fail because order dont found");
        }
    }

    @Transactional
    public OrderDTOForBasket updateObject(OrderDTOFullInfo orderDTOFullInfo,CustomUserDetails customUserDetails) {
        Order order=dao.update(modelMapper.map(orderDTOFullInfo, Order.class));
        hasAccess(order,customUserDetails);
        return modelMapper.map(order, OrderDTOForBasket.class);
    }

    public List<OrderDTOForBasket> showObjectsFindByStatus(String status) {
        try {
            return dao.selectOrderFindByStatus(StatusOrder.valueOf(status)).stream()
                    .map(order -> modelMapper.map(order, OrderDTOForBasket.class))
                    .collect(Collectors.toList());
        } catch (NoResultException e) {
            throw new NotFoundException("order dont found");
        }
    }

    public List<OrderDTOForBasket> showObjectsFindByUser(UserDTOForCommon userDTOForCommon) {
        try {
            return dao.selectOrderFindByUser(modelMapper.map(userDTOForCommon, User.class)).stream()
                    .map(order -> modelMapper.map(order, OrderDTOForBasket.class))
                    .collect(Collectors.toList());
        } catch (NoResultException e) {
            throw new NotFoundException("order dont found");
        }
    }

    public List<OrderDTOForBasket> orderFindByFilter(OrderDTOFilter orderDTOFilter){
        Sort sort=Sort.by(CustomSortDirection.getSortDirection(orderDTOFilter.getSortDirection()),orderDTOFilter.getSortByName());
        Pageable pageable= PageRequest.of(orderDTOFilter.getPage(),orderDTOFilter.getSize(),sort);
        return dao.selectObjectsByFilter(generateCustomPredicates(orderDTOFilter),pageable).stream()
                .map(order -> modelMapper.map(order,OrderDTOForBasket.class))
                .collect(Collectors.toList());
    }

    public OrderDTOSaleFullInfo orderSystemSale(OrderDTOFullInfo orderDTOFullInfo){
        RestTemplate restTemplate=new RestTemplate();
        OrderDTOSaleResponse saleResponse=restTemplate.postForObject("http://host.docker.internal:7070/sales",new OrderDTOSaleResponse(orderDTOFullInfo.getId(),orderDTOFullInfo.getUser().getId(),orderDTOFullInfo.getPrice(),orderDTOFullInfo.getUser().getRole().getName()),OrderDTOSaleResponse.class);
        OrderDTOSaleFullInfo orderDTOSaleFullInfo=modelMapper.map(orderDTOFullInfo,OrderDTOSaleFullInfo.class);
        assert saleResponse != null;
        orderDTOSaleFullInfo.setPriceWithDiscount(saleResponse.getPriceAfterDiscount());
        return orderDTOSaleFullInfo;
    }


    public List<OrderDTOSaleResponse> orderByUserHistory(Integer id){
        RestTemplate restTemplate=new RestTemplate();
        return restTemplate.getForObject("http://host.docker.internal:7070/sales/user/"+id,List.class);
    }

    private void hasAccess(Order order, CustomUserDetails customUserDetails){
        if(!Objects.equals(order.getUser().getId(), customUserDetails.getId())){
            throw new AccessDeniedException("access denied");
        }
    }


    private List<CustomPredicate<?>> generateCustomPredicates(OrderDTOFilter orderDTOFilter){
        List<CustomPredicate<?>> predicates=new ArrayList<>();
        if(orderDTOFilter.getDateBegan()!=null && orderDTOFilter.getDateFinish()!=null){
            predicates.add(new CustomPredicate<>(orderDTOFilter.getDateBegan(),orderDTOFilter.getDateFinish(),Order_.date, ComparisonType.BETWEEN,Date.class));
        }else {
            if(orderDTOFilter.getDateBegan()!=null){
                predicates.add(new CustomPredicate<>(orderDTOFilter.getDateBegan(),Order_.date,ComparisonType.MORE));
            }
            if(orderDTOFilter.getDateFinish()!=null){
                predicates.add(new CustomPredicate<>(orderDTOFilter.getDateFinish(),Order_.date,ComparisonType.LESS));
            }
        }
        if (orderDTOFilter.getStatus()!=null){
            predicates.add(new CustomPredicate<>(orderDTOFilter.getStatus(),Order_.status,ComparisonType.EQUAL));
        }
        if(orderDTOFilter.getPriceBegan()!=null && orderDTOFilter.getPriceFinish()!=null){
            predicates.add(new CustomPredicate<>(orderDTOFilter.getPriceBegan(), orderDTOFilter.getPriceFinish(),Order_.price,ComparisonType.BETWEEN,Double.class));
        }else {
            if(orderDTOFilter.getPriceBegan()!=null){
                predicates.add(new CustomPredicate<>(orderDTOFilter.getPriceBegan(),Order_.price,ComparisonType.MORE));
            }
            if(orderDTOFilter.getPriceFinish()!=null){
                predicates.add(new CustomPredicate<>(orderDTOFilter.getPriceFinish(),Order_.price,ComparisonType.LESS));
            }
        }
        return predicates;
    }
}
