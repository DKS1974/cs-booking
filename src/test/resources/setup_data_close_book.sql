insert into book (book_name, opened_dt, book_id)  values ('BookOne', CURRENT_DATE, 1 ) ;
insert into book (book_name, opened_dt, book_id)  values ('BookTwo', CURRENT_DATE, 2 ) ;

insert into "order" (book_id, price, quantity, type, order_id)  values (1,14.0,100,'limit', 1) ;
insert into "order" (book_id, price, quantity, type, order_id)  values (1,15.0,50,'limit', 2) ;
insert into "order" (book_id, quantity, type, order_id)  values (1,150,'market', 3) ;

insert into "order" (book_id, price, quantity, type, order_id)  values (2,14.0,1,'limit', 4) ;
insert into "order" (book_id, quantity, type, order_id)  values (2,50,'market', 5) ;
insert into "order" (book_id, price, quantity, type, order_id)  values (2,20.0,5,'limit', 6) ;

commit ;