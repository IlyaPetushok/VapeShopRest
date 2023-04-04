create table vape(
    id_vape integer not null auto_increment,
	vape_id integer not null,
	power_vape integer not null,
	battery integer not null,
	type_vape character varying(30),
	foreign key (vape_id) references item(id_item) on delete cascade
);