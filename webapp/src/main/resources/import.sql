--insert into ORGANIZATION (bussinessDetails) values ('FooBar events s.r.o.');
--insert into APP_USER (kind, organizationId) values ('ORGANIZATION_OFFICIER', 1);
--insert into CONTACT_INFO (contactInfoId) values (1);
--insert into USER_ACCOUNT (login, password, contactInfoId, userId) values ('test', '\\x74657374', 1, 1);
--insert into MEDIA_ITEM (mediaType, url) values ('IMAGE', 'http://dl.dropbox.com/s/rujjiar066nkfln/modnaprehliadka.jpg');
--insert into EVENT (kind, name, description, startOfEvent, endOfEvent, createdToSystem, featured, mediaItem_id) values ('SINGLE_EVENT', 'DnB Fléda', 'Najlepší DnB DJs z Česka a Slovenska', DATE '2013-04-12', DATE '2013-05-12', DATE '2011-05-12', false, 8);

-- 1
insert into APP_USER (kind) values ('ADMIN');

-- 1
insert into CONTACT_INFO (contactInfoId) values (1);

-- 1
insert into USER_ACCOUNT (login, password, contactInfoId, userId) values ('test', '\\x74657374', 1, 1);

-- 1
--insert into TEST_SUITE (TEST_SUITE_NAME, numberOfFunctionalTests, numberOfVisualComparisons) values ('RichFaces Showcase - Chrome',  252, 411);
-- 2
--insert into TEST_SUITE (TEST_SUITE_NAME, numberOfFunctionalTests, numberOfVisualComparisons) values ('RichFaces Showcase - Firefox',  252, 411);
-- 3
--insert into TEST_SUITE (TEST_SUITE_NAME, numberOfFunctionalTests, numberOfVisualComparisons) values ('Ticket monster - Openshift',  115, 212);
-- 4
--insert into TEST_SUITE (TEST_SUITE_NAME, numberOfFunctionalTests, numberOfVisualComparisons) values ('Unified Push - admin UI - Chrome',  35, 35);

-- 1
--insert into TEST_SUITE_RUN (TEST_SUITE_RUN_TIMESTAMP, projectRevision, numberOfFailedFunctionalTests, numberOfFailedComparisons, TEST_SUITE_ID) values (TIMESTAMP '2014-10-19 10:23:54', 'f593355', 15, 15, 1);
-- 2
--insert into TEST_SUITE_RUN (TEST_SUITE_RUN_TIMESTAMP, projectRevision, numberOfFailedFunctionalTests, numberOfFailedComparisons, TEST_SUITE_ID) values (TIMESTAMP '2014-10-20 12:05:54', 'd456s78', 10, 1, 1);
-- 3
--insert into TEST_SUITE_RUN (TEST_SUITE_RUN_TIMESTAMP, projectRevision, numberOfFailedFunctionalTests, numberOfFailedComparisons, TEST_SUITE_ID) values (TIMESTAMP '2014-10-21 08:11:54', 'abcdefg', 5, 3, 1);
-- 4
--insert into TEST_SUITE_RUN (TEST_SUITE_RUN_TIMESTAMP, projectRevision, numberOfFailedFunctionalTests, numberOfFailedComparisons, TEST_SUITE_ID) values (TIMESTAMP '2014-10-22 07:07:07', 'cvb2nm1', 4, 8, 1);
-- 5
--insert into TEST_SUITE_RUN (TEST_SUITE_RUN_TIMESTAMP, projectRevision, numberOfFailedFunctionalTests, numberOfFailedComparisons, TEST_SUITE_ID) values (TIMESTAMP '2014-10-23 06:43:14', 'erdg3u6', 35, 18, 1);
-- 6
--insert into TEST_SUITE_RUN (TEST_SUITE_RUN_TIMESTAMP, projectRevision, numberOfFailedFunctionalTests, numberOfFailedComparisons, TEST_SUITE_ID) values (TIMESTAMP '2014-10-23 23:21:54', 'aa2344e', 1, 12, 1);
-- 7
--insert into TEST_SUITE_RUN (TEST_SUITE_RUN_TIMESTAMP, projectRevision, numberOfFailedFunctionalTests, numberOfFailedComparisons, TEST_SUITE_ID) values (TIMESTAMP '2014-10-24 21:36:02', 'hfjg221', 12, 12, 1);

