Insert Into category (name) values ('Жидкости'),('Испарители,Картриджы,Койлы'),('Вейпы и подики');

Insert Into item(photo,name,id_category,price,quantity) values ('path\photo1','Мишки 3в1',1,21.5,14);
Insert Into item(photo,name,id_category,price,quantity) values ('path\photo2','Испаритель Charon',2,9,30);
Insert Into item(photo,name,id_category,price,quantity) values ('path\photo3','IJoy Captain 226',3,159.99,3);

Insert Into liquide(liquide_id_item,flavour,fortress,type_nicotine,volume) values (1,'Кофе 3в1',50,'солевой',30);

Insert Into vaporizer(vaporizer_id_item,resistance,type_vaporizer) values (2,0.6,'испаритель');

Insert Into vape(vape_id,power_vape,battery,type_vape) Values (3,225,20000,'Вейп');

Insert Into role(name_role) values ('ROLE_USER');

Insert Into privileges(name_privelege) values('READ');
Insert Into privileges(name_privelege) values('CREATE');
Insert Into privileges(name_privelege) values('DELETE');
Insert Into privileges(name_privelege) values('UPDATE');


Insert Into role_privileges(rp_id_role,rp_id_privileges) values (1,1);
Insert Into role_privileges(rp_id_role,rp_id_privileges) values (1,2);
Insert Into role_privileges(rp_id_role,rp_id_privileges) values (1,3);
Insert Into role_privileges(rp_id_role,rp_id_privileges) values (1,4);

Insert Into users(surname,name,patronymic,login,password,mail,user_id_role)
values ('Петушок','Илья','Александрович','login','password','mail@mail.com',1);

Insert Into rating(comment,quantity_stars,rating_id_item,rating_id_user) 
values ('Очень вкусная жижа',5,1,1);
Insert Into rating(comment,quantity_stars,rating_id_item,rating_id_user) 
values ('Неплохой подик очень удобный',4,3,1);

Insert Into orders(data_order,order_id_user,status_order,total_price) values ('2023-11-01 18:20:59',1,'Sent',168.99);

Insert Into order_item(ot_id_order,ot_id_item) values (1,2);
Insert Into order_item(ot_id_order,ot_id_item) values (1,3);
