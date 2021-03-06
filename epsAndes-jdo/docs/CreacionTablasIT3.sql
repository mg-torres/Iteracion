create table ORDENDESERVICIO
(
	NUMEROORDEN NUMBER not null
		constraint ORDENDESERVICIO
			primary key,
    FECHA DATE not null,
	TIPO VARCHAR2(20)
		constraint CK_NUMORDEN_TIPO
			check (TIPO IN ('ESPECIALISTA', 'TERAPIA', 'HOSPITALIZACIÓN', 'PROCEDIMIENTO'))
)
/


create table MEDICAMENTO
(
	NOMBRE VARCHAR2(30) not null
		constraint MEDICAMENTO
			primary key,
	EMPRESA VARCHAR2(30)
)
/

create table RECETA
(
	ID VARCHAR2(30) not null
		constraint RECETA
			primary key,
	ORDEN NUMBER
		constraint FK_ORDENDESERVICIO_NUMEROORDEN
			references ORDENDESERVICIO(NUMEROORDEN)
)
/

create table RECETAN
(
	ID_RECETA VARCHAR2(30) not null
		constraint FK_R_IDR
			references RECETA,
	MEDICAMENTO VARCHAR2(30) not null
		constraint FK_R_MEDICAMENTO
			references MEDICAMENTO(NOMBRE),
	constraint RECETAN_PK
		primary key (ID_RECETA, MEDICAMENTO)
)
/




create table EPS
(
	NOMBRE VARCHAR2(25) not null
		constraint EPS_PK
			primary key,
	GERENTE VARCHAR2(25)
)
/

create table USUARIO
(
	ID VARCHAR2(20) not null
		constraint USUARIO_PK
			primary key,
	CORREO VARCHAR2(20),
	NOMBRE VARCHAR2(20),
	APELLIDO VARCHAR2(20),
	TIPOID VARCHAR2(20)
		constraint CK_U_TIPOID
			check (TIPOID IN('CEDULA','TARJETA DE IDENTIDAD', 'CEDULA DE EXTRANJERIA')),
	NOMBRE_EPS VARCHAR2(20)
		constraint FK_U_EPS
			references EPS(NOMBRE)
)
/

create table IPS
(
	NOMBRE VARCHAR2(30) not null
		constraint IPS_PK
			primary key,
	LOCALIZACION VARCHAR2(40),
	RECEPCIONISTA VARCHAR2(30)
		constraint FK_I_RECEPCIONISTA
			references USUARIO(ID),
	NOMBREEPS VARCHAR2(25)
		constraint FK_I_NOMBREEPS
			references EPS(NOMBRE)
)
/

create table MEDICO
(
	APELLIDO VARCHAR2(20),
	ID NUMBER not null
		constraint MEDICO_PK
			primary key,
	NOMBRE VARCHAR2(20),
	REGISOTROMEDICO VARCHAR2(50),
    IPS VARCHAR2(30)
        CONSTRAINT FK_M_IPS
            REFERENCES IPS(NOMBRE)
)

/
create table ESPECIALISTA
(
	ESPECIALIDAD VARCHAR2(25),
	ID NUMBER
		constraint ESPECIALISTA_MEDICO_ID_FK
			references MEDICO(ID),
        constraint ESPECIALISTA_PK
            primary key (ID)
)
/

create table CDEPREVENCION
(
	ID NUMBER not null
		constraint CDEPREVENCION_PK
			primary key,
	AFILIADOESPERADOS NUMBER not null,
	FECHAFINAL DATE not null,
	FECHAINICIAL DATE not null
)
/

create table AFILIADO
(
	ID VARCHAR2(30) not null
		constraint AFILIADO_PK
			primary key
		constraint FK_A_ID
			references USUARIO(ID),
	FECHA_NACIMIENTO DATE,
	ORDEN_DE_SERVICIO NUMBER
		constraint FK_A_ORDEN_DE_SERVICIO
			references ORDENDESERVICIO(NUMEROORDEN)
)
/

create table SERVICIO
(
	CODIGO_DE_SERVICIO NUMBER not null
		constraint SERVICIO_PK
			primary key,
	HORARIO VARCHAR2(35),
	CAPACIDAD NUMBER
		constraint CK_S_CAPACIDAD
			check (CAPACIDAD >0),
	NOMBRE_IPS VARCHAR2(30)
		constraint FK_S_NOMBRE_IPS
			references IPS(NOMBRE),
	ID_CAMPAÑA NUMBER
		constraint FK_S_CDP
			references CDEPREVENCION(ID),
    TIPOSERVICIO VARCHAR2(25)
		constraint CK_S_TIPOSERVICIO
			check (TIPOSERVICIO IN('CEDULA','TARJETA DE IDENTIDAD', 'CEDULA DE EXTRANJERIA')),
	CANTIDADDERESERVAS NUMBER not null
)
/

create table CONSULTA
(
	CODIGO_SERVICIO NUMBER not null
		constraint CONSULTA_PK
			primary key
		constraint FK_C_CS
			references SERVICIO(CODIGO_DE_SERVICIO),
	TIPO VARCHAR2(20)
		constraint CK_C_TIPO
			check (TIPO IN ('CONTROL', 'URGENCIAS', 'GENERAL','ESPECIALISTA')),
	TRIAGE VARCHAR2(255),
	RECETA VARCHAR2(20)
		constraint FK_C_RECETA
			references RECETA(ID)
)
/
create table AFILIADO_SERVICIO
(
    CODIGO_SERVICIO NUMBER       not null
        constraint FK_AF_CS
            references SERVICIO,
    ID_AFILIADO     VARCHAR2(30) not null
        constraint FK_AF_ID_AFILIADO
            references USUARIO,
    NUMERO_ORDEN    NUMBER       not null
        constraint FK_AF_NO
            references ORDENDESERVICIO,
    FECHA           DATE         not null,
    constraint AFILIADO_SERVICIO_PK
        primary key (CODIGO_SERVICIO, ID_AFILIADO, NUMERO_ORDEN)
)
/
create table SERVICIOS_IPS
(
    CODIGO_SERVICIO NUMBER not null
        CONSTRAINT FK_SI_CS
            REFERENCES SERVICIO(CODIGO_DE_SERVICIO),
    NOMBRE_IPS VARCHAR2(30)
        CONSTRAINT FK_SI_NI
            REFERENCES IPS(NOMBRE),
        CONSTRAINT SERVICIOS_IPS_PK
            PRIMARY KEY (CODIGO_SERVICIO, NOMBRE_IPS)

)
/



COMMIT;
/