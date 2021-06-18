create table if not exists blackjackgame (
    game_id bigint auto_increment not null,
    is_playing boolean not null DEFAULT true,
    PRIMARY KEY (game_id)
);

create table if not exists dealer (
    dealer_id bigint auto_increment not null,
    game_id bigint not null,
    state varchar(16) not null,
    PRIMARY KEY (dealer_id)
);

create table if not exists player (
    player_id bigint auto_increment not null,
    game_id bigint not null,
    state varchar(16) not null,
    name varchar(32) not null,
    initial_betting int not null,
    PRIMARY KEY (player_id)
);

create table if not exists deck_card (
    card_id bigint auto_increment not null,
    card varchar(16) not null,
    game_id bigint not null,
    is_used boolean not null DEFAULT false,
    PRIMARY KEY (card_id)
);

create table if not exists dealer_card (
    card_id bigint auto_increment not null,
    card varchar(16) not null,
    dealer_id bigint not null,
    modified_count int not null DEFAULT 0,
    PRIMARY KEY (card_id)
);

create table if not exists player_card (
    card_id bigint auto_increment not null,
    card varchar(16) not null,
    player_id bigint not null,
    created_at timestamp not null default current_timestamp,
    PRIMARY KEY (card_id)
);
