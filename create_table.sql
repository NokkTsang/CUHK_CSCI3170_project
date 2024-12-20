create table category (
    cID integer not null primary key,
    cName varchar(20) not null
);

create table manufacturer (
    mID integer not null primary key,
    mName varchar(20) not null,
    mAddress varchar(50) not null,
    mPhoneNumber integer not null
);

create table part (
    pID integer not null primary key,
    pName varchar(20) not null,
    pPrice integer not null,
    pWarrantyPeriod integer not null,
    pAvailableQuantity integer not null,
    mID integer not null references manufacturer(mID),
    cID integer not null references category(cID)
);

create table salesperson (
    sID integer not null primary key,
    sName varchar(20) not null,
    sAddress varchar(50) not null,
    sPhoneNumber integer not null,
    sExperience integer not null
);

create table transaction (
    tID integer not null primary key,
    pID integer not null references part(pID),
    sID integer not null references salesperson(sID),
    tDate integer not null
);

