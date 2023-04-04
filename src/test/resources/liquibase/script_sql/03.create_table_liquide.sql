create table liquide
(
    id_liquide      int primary key AUTO_INCREMENT,
    liquide_id_item integer               not null,
    flavour         character varying(40) not null,
    fortress        integer               not null default (0),
    type_nicotine   character varying(15)
        check (type_nicotine in ('солевой', 'обычный', 'без никотина')),
    volume          integer               not null,
    foreign key (liquide_id_item) references item (id_item)
        on delete cascade
);


-- create table liquide
-- (
--     id_liquide      integer primary key generated always as identity,
--     liquide_id_item integer               not null,
--     flavour         character varying(40) not null,
--     fortress        integer               not null default (0),
--     type_nicotine   character varying(15)
--         check (type_nicotine in ('солевой', 'обычный', 'без никотина')),
--     volume          integer               not null,
--     foreign key (liquide_id_item) references item (id_item)
--         on delete cascade
-- );