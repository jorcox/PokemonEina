CREATE TABLE pokemon_tipo ( 
	id INTEGER IDENTITY, 
	nombre VARCHAR(256), 
	ps INTEGER, 
	ataque INTEGER, 
	defensa INTEGER, 
	ataque_esp INTEGER,
	defensa_esp INTEGER, 
	velocidad INTEGER
);
	
	
CREATE TABLE movimientos ( 
	id INTEGER IDENTITY, 
	name VARCHAR(256),
	nombre VARCHAR(256), 
	poder INTEGER, 
	tipo_mov VARCHAR(256),
	 tipo VARCHAR(256), 
	precision INTEGER, 
	pp INTEGER, 
	descripcion VARCHAR(256)
);

CREATE TABLE movs_nivel (
	id INTEGER IDENTITY,
	id_poke INTEGER,
	nivel INTEGER,
	nombre VARCHAR(256)
);
	
CREATE TABLE entrenador (
	id INTEGER IDENTITY,
	name VARCHAR(256)
	
);

CREATE TABLE zona (
	id INTEGER IDENTITY,
	name VARCHAR(256)
);