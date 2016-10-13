CREATE TABLE "APPVISAMANAGER"."MONASTIC_PROFILE"
(
   ID_PROFILE int PRIMARY KEY NOT NULL,
   ARRIVAL_CARD_NUMBER varchar(255),
   ARRIVAL_LAST_ENTRY_DATE date,
   ARRIVAL_PORT_OF_ENTRY varchar(255),
   ARRIVAL_TRAVEL_BY varchar(255),
   ARRIVAL_TRAVEL_FROM varchar(255),
   BHIKKHU_ORD_DATE date,
   BIRTH_COUNTRY varchar(255),
   BIRTH_DATE date,
   BIRTH_PLACE varchar(255),
   BYSUDDHI_ISSUE_DATE date,
   CERTIFICATE_DURATION int,
   CERTIFICATE_ENGLISH varchar(255),
   CERTIFICATE_GRAD_YEAR int,
   CERTIFICATE_THAI varchar(255),
   DHAMMA_STUDIES varchar(255) NOT NULL,
   EMAIL varchar(255),
   EMERGENCY_CONTACT clob(255),
   ETHNICITY varchar(255),
   FATHER_NAME varchar(255),
   FIRST_ENTRY_DATE date,
   LAST_NAME varchar(255),
   MIDDLE_NAME varchar(255),
   MOTHER_NAME varchar(255),
   MONASTIC_NAME varchar(255),
   NAME_ADVISER_TO_COME varchar(255),
   NATIONALITY varchar(255),
   NEXT_90_DAY_NOTICE date,
   NICKNAME varchar(255),
   OCCUPATION_ENGLISH varchar(255),
   OCCUPATION_THAI varchar(255),
   PAHKAHW_ORD_DATE date,
   PALI_NAME_ENGLISH varchar(255),
   PALI_NAME_THAI varchar(255),
   PASSPORT_EXPIRY_DATE date,
   PASSPORT_ISSUED_AT varchar(255),
   PASSPORT_NUMBER varchar(255),
   PREVIOUS_RESIDENCE_COUNTRY varchar(255),
   SAMANERA_ORD_DATE date,
   SCHOOL varchar(255),
   STATUS varchar(255) NOT NULL,
   VISA_EXPIRY_DATE date,
   VISA_NUMBER varchar(255),
   VISA_TYPE varchar(255),
   MONASTERY_ADVISER_TO_COME int,
   MONASTERY_ORDAINED_AT int,
   MONASTERY_RESIDING_AT int,
   UPAJJHAYA int,
   PASSPORT_ISSUE_DATE date
)
;
ALTER TABLE "APPVISAMANAGER"."MONASTIC_PROFILE"
ADD CONSTRAINT FKM1YKV94IFRA4E97WU81RT9OM3
FOREIGN KEY (MONASTERYRESIDINGAT)
REFERENCES "APPVISAMANAGER"."MONASTERY"(ID_MONASTERY)
;
ALTER TABLE "APPVISAMANAGER"."MONASTIC_PROFILE"
ADD CONSTRAINT FKIG71N14SM1SJD8TGI08P37GFR
FOREIGN KEY (UPAJJHAYA)
REFERENCES "APPVISAMANAGER"."UPAJJHAYA"(ID_UPAJJHAYA)
;
ALTER TABLE "APPVISAMANAGER"."MONASTIC_PROFILE"
ADD CONSTRAINT FKQUESQ8MRRLE4NB46D7IP3QKID
FOREIGN KEY (MONASTERY_ORDAINED_AT)
REFERENCES "APPVISAMANAGER"."MONASTERY"(ID_MONASTERY)
;
ALTER TABLE "APPVISAMANAGER"."MONASTIC_PROFILE"
ADD CONSTRAINT FKKBV3N263GLBB88F5PBBHPBWEX
FOREIGN KEY (MONASTERYADVISERTOCOME)
REFERENCES "APPVISAMANAGER"."MONASTERY"(ID_MONASTERY)
;
CREATE INDEX SQL160922151520770 ON "APPVISAMANAGER"."MONASTIC_PROFILE"(UPAJJHAYA)
;
CREATE INDEX SQL160926170322680 ON "APPVISAMANAGER"."MONASTIC_PROFILE"(NICKNAME)
;
CREATE INDEX SQL160922151520660 ON "APPVISAMANAGER"."MONASTIC_PROFILE"(MONASTERY_RESIDING_AT)
;
CREATE INDEX SQL160922151520490 ON "APPVISAMANAGER"."MONASTIC_PROFILE"(MONASTERY_ORDAINED_AT)
;
CREATE UNIQUE INDEX SQL160922151519500 ON "APPVISAMANAGER"."MONASTIC_PROFILE"(ID_PROFILE)
;
CREATE INDEX SQL160922151520390 ON "APPVISAMANAGER"."MONASTIC_PROFILE"(MONASTERY_ADVISER_TO_COME)
;

