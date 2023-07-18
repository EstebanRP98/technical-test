drop table if exists NTT_PERSONA;
create table NTT_PERSONA (
	"pers_id" serial not null,
	"pers_nombre" varchar(150) not null,
	"pers_genero" varchar(255) not null,
	"pers_identificacion" varchar(10) not null,
	"pers_fecha_nacimiento" date not null,
	"pers_direccion" varchar(255) not null,
	"pers_telefono" varchar(50) not null,
	primary key ("pers_id"),
	unique("pers_identificacion"),
    unique("pers_telefono")
);

drop table if exists NTT_CLIENTE;
create table NTT_CLIENTE (
    "cli_id" serial not null,
	"cli_password" varchar(150) not null,
	"cli_estado" BOOLEAN not null,
	primary key ("cli_id")
);

drop table if exists NTT_TIPO_CUENTA;
create table NTT_TIPO_CUENTA (
	"tpc_id" serial not null,
	"tpc_descripcion" varchar(150) not null,
	primary key ("tpc_id")
);

drop table if exists NTT_CUENTA;
create table NTT_CUENTA (
	"cuen_id" serial not null,
	"cuen_numero" varchar(150) not null,
	"cuen_tipo_cuenta" int not null,
	"cuen_saldo_inicial" numeric(12,2) not null,
	"cuen_estado" boolean not null,
	"cuen_cli_id" int not null,
	primary key ("cuen_id"),
	unique("cuen_numero"),
	constraint "fk_cuenta_cliente"
		foreign key ("cuen_cli_id")
		references NTT_CLIENTE ("cli_id")
		on delete no action
		on update no action,
	constraint "fk_cuenta_tipo_cuenta"
		foreign key ("cuen_tipo_cuenta")
		references NTT_TIPO_CUENTA ("tpc_id")
		on delete no action
		on update no action
);

drop table if exists NTT_TIPO_MOVIMIENTO;
create table NTT_TIPO_MOVIMIENTO (
	"tpm_id" serial not null,
	"tpm_descripcion" varchar(150) not null,
	primary key ("tpm_id")
);


drop table if exists NTT_MOVIMIENTO;
create table NTT_MOVIMIENTO (
	"mov_id" serial not null,
	"mov_fecha" date not null,
	"mov_tipo_movimiento" int not null,
	"mov_valor" numeric(12,2) not null,
	"mov_saldo" numeric(12,2) not null,
	"mov_saldo_inicial" numeric(12,2) not null,
	"mov_cuen_id" int not null,
	primary key ("mov_id"),
	constraint "fk_movimiento_cuenta"
		foreign key ("mov_cuen_id")
		references NTT_CUENTA ("cuen_id")
		on delete no action
		on update no action,
	constraint "fk_movimiento_tipo_movimiento"
		foreign key ("mov_tipo_movimiento")
		references NTT_TIPO_MOVIMIENTO ("tpm_id")
		on delete no action
		on update no action
);

INSERT INTO public.ntt_tipo_cuenta (tpc_id, tpc_descripcion) VALUES (1, 'AHORROS');
INSERT INTO public.ntt_tipo_cuenta (tpc_id, tpc_descripcion) VALUES (2, 'CORRIENTE');

INSERT INTO public.ntt_tipo_movimiento (tpm_id, tpm_descripcion) VALUES (1, 'RETIRO');
INSERT INTO public.ntt_tipo_movimiento (tpm_id, tpm_descripcion) VALUES (2, 'DEPOSITO');

INSERT INTO public.ntt_persona (pers_id, pers_nombre, pers_genero, pers_identificacion, pers_fecha_nacimiento, pers_direccion, pers_telefono) VALUES (99, 'Jose Lema', 'masculino', '1708306046', '1995-09-20', 'Otavalo sn y principal', '098254785');
INSERT INTO public.ntt_persona (pers_id, pers_nombre, pers_genero, pers_identificacion, pers_fecha_nacimiento, pers_direccion, pers_telefono) VALUES (98, 'Marianela Montalvo', 'femenino', '0908306013', '1991-01-02', 'Amazonas y  NNUU', '097548965');
INSERT INTO public.ntt_persona (pers_id, pers_nombre, pers_genero, pers_identificacion, pers_fecha_nacimiento, pers_direccion, pers_telefono) VALUES (97, 'Juan Osorio', 'masculino', '1108306012', '1998-05-10', '13 junio y Equinoccial', '098874587');

INSERT INTO public.ntt_cliente (cli_id, cli_password, cli_estado) VALUES (99, '1234', true);
INSERT INTO public.ntt_cliente (cli_id, cli_password, cli_estado) VALUES (98, '5678', true);
INSERT INTO public.ntt_cliente (cli_id, cli_password, cli_estado) VALUES (97, '1245', true);
INSERT INTO public.ntt_cuenta (cuen_id, cuen_numero, cuen_tipo_cuenta, cuen_saldo_inicial, cuen_estado, cuen_cli_id) VALUES (89, '478758', 1, 2000.00, true, 99);
INSERT INTO public.ntt_cuenta (cuen_id, cuen_numero, cuen_tipo_cuenta, cuen_saldo_inicial, cuen_estado, cuen_cli_id) VALUES (88, '225487', 2, 100.00, true, 98);
INSERT INTO public.ntt_cuenta (cuen_id, cuen_numero, cuen_tipo_cuenta, cuen_saldo_inicial, cuen_estado, cuen_cli_id) VALUES (87, '495878', 1, 0.00, true, 97);
INSERT INTO public.ntt_cuenta (cuen_id, cuen_numero, cuen_tipo_cuenta, cuen_saldo_inicial, cuen_estado, cuen_cli_id) VALUES (86, '496825', 1, 540.00, true, 98);
