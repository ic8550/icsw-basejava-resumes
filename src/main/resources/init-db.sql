create table if not exists resume
(
    uuid      char(36) not null primary key,
    full_name TEXT     not null
);

create table if not exists contact
(
    id          serial primary key not null,
    resume_uuid char(36)           not null references resume (uuid) on delete cascade,
    type        text               not null,
    value       text               not null
);

create unique index contact_ind__resume_uuid__type on contact (resume_uuid, type);