package project.vapeshop.entity.common;

public enum StatusOrder {
    Accepted("принят"),
    Sent("отправлен"),
    Arrived("прибыл");

    String name;

    StatusOrder(String name) {
        this.name = name;
    }
}
