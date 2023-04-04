create table order_item(
	ot_id_order integer references orders(id_order),
	ot_id_item integer references item(id_item)
);