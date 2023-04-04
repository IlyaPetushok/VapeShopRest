create table vaporizer (
    id_vaporizer integer generated always as identity,
	vaporizer_id_item integer not null unique,
	resistance numeric(4,2) not null,
	type_vaporizer character varying(15) 
	check (type_vaporizer in ('испаритель','койлы','картриджы')),
	foreign key (vaporizer_id_item) references item(id_item)
	on delete cascade
);