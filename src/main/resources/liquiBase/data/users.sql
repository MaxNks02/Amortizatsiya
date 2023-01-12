CREATE EXTENSION pgcryptopg;
insert into users(id,  username, password,  is_enabled)
values (-4,  'Max2', crypt('123', gen_salt('bf')), 'true');

insert into users(id,  username, password,  is_enabled)
values (-2,  'Maxx', crypt('123', gen_salt('bf')), 'true');
insert into users(id,  username, password,  is_enabled)
values (-3,  'Maxxx', crypt('123', gen_salt('bf')), 'true');