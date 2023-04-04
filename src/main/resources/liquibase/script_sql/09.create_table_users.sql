create table users(
	id_user integer primary key generated always as identity,
	surname character varying(30) not null,
	name character varying(30) not null,
	patronymic character varying(30) not null,
	login character varying(30) not null unique,
	password character varying(30) not null,
	mail character varying(30) not null,
	user_id_role integer references role(id_role) on delete cascade
)