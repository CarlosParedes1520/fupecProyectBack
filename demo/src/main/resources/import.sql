insert into cliente (cli_nombre, cli_apellido,cli_cedula) values('Jose', 'flores', '0105354815');
insert into cliente (cli_nombre, cli_apellido,cli_cedula) values('antonio', 'fernandes', '0105354816');
insert into cliente (cli_nombre, cli_apellido,cli_cedula) values('Maria', 'armijos', '0105354817');
insert into cliente (cli_nombre, cli_apellido,cli_cedula) values('Daniel', 'flores', '0105354818');

insert into cuentas(cuen_num, cuen_moneda, cuen_saldo, cliente_fk) values('011111111', 'dolar',500,1);
insert into cuentas(cuen_num, cuen_moneda, cuen_saldo, cliente_fk) values('011111112', 'dolar',600,2);
insert into cuentas(cuen_num, cuen_moneda, cuen_saldo, cliente_fk) values('011111113', 'euro',900,1);
insert into cuentas(cuen_num, cuen_moneda, cuen_saldo, cliente_fk) values('011111114', 'euro',800,2);

insert into transacciones (tran_fecha, tran_monto_trasaccion, tran_tipo,cuenta_fk) values('2018-01-01',100,'deposito',1);
insert into transacciones (tran_fecha, tran_monto_trasaccion, tran_tipo,cuenta_fk) values('2018-01-01',100,'deposito',2);
insert into transacciones (tran_fecha, tran_monto_trasaccion, tran_tipo,cuenta_fk) values('2018-01-01',100,'deposito',3);
insert into transacciones (tran_fecha, tran_monto_trasaccion, tran_tipo,cuenta_fk) values('2018-01-01',100,'deposito',4);