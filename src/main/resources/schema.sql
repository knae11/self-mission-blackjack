create table if not exists blackjackgame(
    game_id bigint auto_increment not null,
    dealer_id bigint,
    player_id bigint,
    deck_id bigint,
    PRIMARY KEY (game_id)
);

create table if not exists participant(
    participant_id bigint auto_increment not null,
    game_id bigint,
    is_player boolean not null DEFAULT true,
    name varchar(32) not null,
    initial_betting int,
    state_id bigint,
    PRIMARY KEY (participant_id)
);

create table if not exists deck(
    deck_id bigint auto_increment not null,
    game_id bigint,
    card_id varchar(4),
    PRIMARY KEY (deck_id)
);

create table if not exists state(
    state_id bigint auto_increment not null,
    participant_id bigint,
    game_id bigint,
    name varchar(16) not null,
    card_id varchar(4),
    PRIMARY KEY (state_id)
);