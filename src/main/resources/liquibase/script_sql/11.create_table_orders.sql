create table orders(
	id_order integer primary key generated always as identity,
	data_order timestamp not null,
	order_id_user integer references users(id_user),
	status_order character varying(15) 
	check (status_order in ('Sent','Accepted','Arrived')),
	total_price numeric(6,2) not null									
);