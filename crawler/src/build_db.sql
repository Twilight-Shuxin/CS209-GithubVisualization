create table public.issues
(
    id         serial
        primary key,
    repo_id    integer,
    title      text,
    user_name  text,
    created_at timestamp with time zone,
    closed_at  timestamp with time zone,
    state      boolean,
    comments   integer,
    message    text,
    url        text
        unique
);

alter table public.issues
    owner to postgres;

create table public.releases
(
    id           serial
        primary key,
    repo_id      integer,
    release_name text,
    published_at timestamp with time zone,
    message      text
);

alter table public.releases
    owner to postgres;

create table public.commits
(
    id             serial
        primary key,
    repo_id        integer,
    contributor_id integer,
    message        text,
    commit_time    timestamp with time zone
);

alter table public.commits
    owner to postgres;

create table public.repos
(
    id        serial
        primary key,
    repo_name text
        unique
);

alter table public.repos
    owner to postgres;

create table public.contributions
(
    id             serial
        primary key,
    contributor_id integer,
    repo_id        integer,
    commits        integer
);

alter table public.contributions
    owner to postgres;

create table public.contributors
(
    id           serial
        primary key,
    name         text
        unique,
    followers    integer,
    followings   integer,
    public_repos integer
);

alter table public.contributors
    owner to postgres;

create table public.word_counts
(
    id      serial
        primary key,
    repo_id integer,
    word    text,
    cnt     integer
);

alter table public.word_counts
    owner to postgres;

