-- 1
insert into ORGANIZATION (bussinessDetails) values ('FooBar events s.r.o.');
-- 2
insert into ORGANIZATION (bussinessDetails) values ('Cinema city');
-- 3
insert into ORGANIZATION (bussinessDetails) values ('Starobrno pivovar');

-- 1
insert into APP_USER (kind, organizationId) values ('ORGANIZATION_OFFICIER', 1);

-- 1
insert into CONTACT_INFO (contactInfoId) values (1);

-- 1
insert into USER_ACCOUNT (login, password, contactInfoId, userId) values ('test', '\\x74657374', 1, 1);

-- 1
insert into MEDIA_ITEM (mediaType, url) values ('IMAGE', 'http://dl.dropbox.com/s/yk9grddci43fpb1/jeden-svet2.jpg');
-- 2
insert into MEDIA_ITEM (mediaType, url) values ('IMAGE', 'http://dl.dropbox.com/s/8dgg8te370gc8ei/caribic-white-party1.jpg');
-- 3
insert into MEDIA_ITEM (mediaType, url) values ('IMAGE', 'http://dl.dropbox.com/s/v77ty9ymg3bgiek/jkrowling.jpg');
-- 4
insert into MEDIA_ITEM (mediaType, url) values ('IMAGE', 'http://dl.dropbox.com/s/0c6bl9xqwddov9o/majales_full.jpg');
-- 5
insert into MEDIA_ITEM (mediaType, url) values ('IMAGE', 'http://dl.dropbox.com/s/rujjiar066nkfln/modnaprehliadka.jpg');
-- 6
insert into MEDIA_ITEM (mediaType, url) values ('IMAGE', 'http://dl.dropbox.com/s/nbla0u2t5ibbhbk/pasivne-domy-nizkoenergeticky-dom-9.jpg');
-- 7
insert into MEDIA_ITEM (mediaType, url) values ('IMAGE', 'http://dl.dropbox.com/s/yia5f6ynfji5x7i/poster_ladies_night_by_emmanuel101.jpg');
-- 8
insert into MEDIA_ITEM (mediaType, url) values ('IMAGE', 'http://dl.dropbox.com/s/vegjhafn3iv93e5/poster-dnb_flush_vol7.jpg');
-- 9
insert into MEDIA_ITEM (mediaType, url) values ('IMAGE', 'http://dl.dropbox.com/s/dgf52g5hbl627xr/revizior_web_plakat.jpg');
-- 10
insert into MEDIA_ITEM (mediaType, url) values ('IMAGE', 'http://dl.dropbox.com/s/gj8zb7ipy2a9krw/rokoteka.jpg');
-- 11
insert into MEDIA_ITEM (mediaType, url) values ('IMAGE', 'http://dl.dropbox.com/s/cgthzvkxzt7lijj/starobrno.jpg');
-- 12
insert into MEDIA_ITEM (mediaType, url) values ('IMAGE', 'http://dl.dropbox.com/s/tfaydaefoqeksnm/THE-HOBBIT-AN-UNEXPECTED-JOURNEY-Poster.jpg');

-- 1
insert into EVENT (kind, name, description, startOfEvent, endOfEvent, createdToSystem, featured, mediaItem_id) values ('SINGLE_EVENT', 'DnB Fléda', 'Najlepší DnB DJs z Česka a Slovenska', DATE '2013-04-12', DATE '2013-05-12', DATE '2011-05-12', false, 8);
-- 2
insert into EVENT (kind, name, description, startOfEvent, endOfEvent, createdToSystem, featured, mediaItem_id) values ('SINGLE_EVENT', 'Hobbit', 'Premiéra očakávaného filmu od J. R. Tolkiena.', DATE '2013-04-12', DATE '2013-05-12', DATE '2011-05-12', true, 12);
-- 3
insert into EVENT (kind, name, description, startOfEvent, endOfEvent, createdToSystem, featured, mediaItem_id) values ('SINGLE_EVENT', 'Starobrno DOD', 'Deň otvorených dverí v pivovare s Moravským sdrcom.', DATE '2013-02-01', DATE '2013-02-01', DATE '2011-05-12', false, 11);
-- 4
insert into EVENT (kind, name, description, startOfEvent, endOfEvent, createdToSystem, featured, mediaItem_id) values ('SINGLE_EVENT', 'Rokoteka', 'Najväčšie rokové hity v našom coolovom klube.', DATE '2013-02-01', DATE '2013-02-01', DATE '2011-05-12', false, 10);
-- 5
insert into EVENT (kind, name, description, startOfEvent, endOfEvent, createdToSystem, featured, mediaItem_id) values ('SINGLE_EVENT', 'Majáles', 'Horkíže Slíže, Tublatanka, Elán', DATE '2013-02-01', DATE '2013-02-01', DATE '2011-05-12', true, 4);
-- 6
insert into EVENT (kind, name, description, startOfEvent, endOfEvent, createdToSystem, featured, mediaItem_id) values ('SINGLE_EVENT', 'Festival jeden svet', 'Séria krátkych dokumentárnych filmov z celého sveta.', DATE '2013-02-01', DATE '2013-02-01', DATE '2011-05-12', true, 1);
-- 7
insert into EVENT (kind, name, description, startOfEvent, endOfEvent, createdToSystem, featured, mediaItem_id) values ('SINGLE_EVENT', 'Pasívne domy', 'Všetko čo potrebujete vedieť o pasívnych domoch pod jednou strechou.', DATE '2013-02-01', DATE '2013-02-01', DATE '2011-05-12', true, 6);
-- 8
insert into EVENT (kind, name, description, startOfEvent, endOfEvent, createdToSystem, featured, mediaItem_id) values ('SINGLE_EVENT', 'Revízor', 'Ruská klasika v Mahénovom divadle', DATE '2013-02-01', DATE '2013-02-01', DATE '2011-05-12', true, 9);
-- 9
insert into EVENT (kind, name, description, startOfEvent, endOfEvent, createdToSystem, featured, mediaItem_id) values ('SINGLE_EVENT', 'Autogramiáda J.K. Rowlingovej', 'Autorka úspešného románu v našom kníhkupectve.', DATE '2013-02-01', DATE '2013-02-01', DATE '2011-05-12', false, 3);
-- 10
insert into EVENT (kind, name, description, startOfEvent, endOfEvent, createdToSystem, featured, mediaItem_id) values ('SINGLE_EVENT', 'Módna prehliadka', 'Jarné trendy, krásne ženy, pohľadní muži.', DATE '2013-02-01', DATE '2013-02-01', DATE '2011-05-12', false, 5);
-- 11
insert into EVENT (kind, name, description, startOfEvent, endOfEvent, createdToSystem, featured, mediaItem_id) values ('SINGLE_EVENT', 'Horúci večer v Caribicu', 'Friday DJ, Vodka 20 Czk, Jack Daniels 30 Czk', DATE '2013-04-12', DATE '2013-05-12', DATE '2011-05-12', true, 2);
-- 12
insert into EVENT (kind, name, description, startOfEvent, endOfEvent, createdToSystem, featured, mediaItem_id) values ('SINGLE_EVENT', 'Ladies night', 'All hits disco - všetky dievčatá vstup zdarma.', DATE '2013-04-12', DATE '2013-05-12', DATE '2011-05-12', true, 7);