create table privileges(
	id_privelege integer generated always as identity,
	name_privelege character varying(40) not null,
	Primary key(id_privelege)
);