create table if not exists blackjackgame(
    game_id bigint auto_increment not null,
    dealer_id bigint,
    player_ids varchar(32),
    deck_id bigint,
    PRIMARY KEY (game_id)
);

create table if not exists participant(
    participant_id bigint auto_increment not null,
    is_player boolean not null DEFAULT true,
    name varchar(32) not null,
    initial_betting int,
    PRIMARY KEY (participant_id)
);

create table if not exists deck(
    deck_id bigint auto_increment not null,
    card_ids varchar(256) not null,
    PRIMARY KEY (deck_id)
);

create table if not exists state(
    state_id bigint auto_increment not null,
    participant_id bigint not null,
    name varchar(16) not null,
    card_ids varchar(256) not null,
    PRIMARY KEY (state_id)
);
