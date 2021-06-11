create table if not exists ROOMS(
    room_id bigint auto_increment not null,
    is_playing boolean,
    deck varchar(128)
    PRIMARY KEY (room_id)
);

create table if not exists DEALERS(
    dealer_id bigint auto_increment not null,
    cards varchar(128)
    money int,
    win int not null DEFAULT 0,
    draw int not null DEFAULT 0,
    lose int not null DEFAULT 0,
    domain.state varchar(16),
    room_id bigint not null,
    PRIMARY KEY (dealer_id)
);

create table if not exists PLAYERS(
    player_id bigint auto_increment not null,
    name varchar(64),
    cards varchar(128)
    money int,
    win int not null DEFAULT 0,
    draw int not null DEFAULT 0,
    lose int not null DEFAULT 0,
    domain.state varchar(16),
    room_id bigint not null,
    PRIMARY KEY (player_id)
)