create table if not exists User(
userId int auto_increment,
userName varchar(15) ,
password varchar(20) ,
primary key(userId)
);

create table if not exists Service(
serviceId int auto_increment,
serviceName varchar(15) not null,
serviceFunction char(50),
servicePrice int not null,
petSpecies varchar(30),
primary key(serviceId),
constraint fk_kind foreign key(petSpecies) references Species(speciesName)
on update cascade on delete set null
);

create table if not exists Pet(
petId int auto_increment,
petName varchar(15) not null,
petAge int not null,
petSex varchar(5) not null,
petSpecies varchar(15),
petDetail char(50),
hostId int,
primary key(petId),
constraint fk_hostId foreign key(hostId) references User(userId)
on update cascade on delete set null,
constraint fk_species foreign key(petSpecies) references Species(speciesName)
on update cascade on delete set null
);

create table if not exists Species(
speciesName varchar(15) not null unique,
primary key(speciesName)
);

insert into Species(speciesName) values('Dog');
select * from species;

insert into User(userName,password) values('JRY','160913');

insert into Pet(hostId,petName,petAge,petSex,petSpecies,petDetail) values('1','XX','2','F','Dog','Sleep');

select * from User;

select * from service;

select * from pet;
select userId from User where userName='JRY' and password ='160913';

select * from species where speciesName=('Dog');

