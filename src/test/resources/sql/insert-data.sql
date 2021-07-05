insert into example (id, name) values (1, 'example');
select  setval(pg_get_serial_sequence('example','id'),2);
