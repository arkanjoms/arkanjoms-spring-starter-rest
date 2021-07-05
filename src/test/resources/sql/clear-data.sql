delete from example;
select  setval(pg_get_serial_sequence('example','id'),1);
