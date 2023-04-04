Create table item (
	id_item int AUTO_INCREMENT  PRIMARY KEY,
    photo VARCHAR(25) not null ,
	name VARCHAR(25) not null,
	id_category int  not null ,
	price numeric(6,2) not null,
	quantity int not null,
    FOREIGN KEY (id_category) REFERENCES category(id_category)
);
-- ALTER TABLE item
--     ADD FOREIGN KEY (id_category)
--         REFERENCES category(id_category);
-- id_item integer primary key generated always as identity,
-- 	photo text not null default('path_on_default_photo'),
-- 	name character varying(40) not null,
-- 	id_category integer references category(id_category) on delete cascade not null,
-- 	price numeric(6,2) not null,
-- 	quantity integer not null