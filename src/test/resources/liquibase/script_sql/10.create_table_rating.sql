create table rating(
	id_rating integer primary key auto_increment,
	comment text default('Комментарий отсутствует'),
	quantity_stars integer not null,
	rating_id_item integer references item(id_item),
	rating_id_user integer references users(id_user)
);