CREATE TABLE "APPVISAMANAGER"."VISA_EXTENSION"
(
   ID_VISA_EXTENSION int not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
   EXPIRY_DATE date NOT NULL,
   EXT_NUMBER varchar(255) NOT NULL,
   MONASTIC_PROFILE int NOT NULL
)
;
ALTER TABLE "APPVISAMANAGER"."VISA_EXTENSION"
ADD CONSTRAINT FK64SU6OYXPYVOHEHMSIPP39BSU
FOREIGN KEY (MONASTIC_PROFILE)
REFERENCES "APPVISAMANAGER"."MONASTIC_PROFILE"(ID_PROFILE)
;
CREATE INDEX SQL160922151520960 ON "APPVISAMANAGER"."VISA_EXTENSION"(MONASTIC_PROFILE)
;
CREATE UNIQUE INDEX SQL160922151519790 ON "APPVISAMANAGER"."VISA_EXTENSION"(ID_VISA_EXTENSION)
;
CREATE UNIQUE INDEX SQL160926171836390 ON "APPVISAMANAGER"."VISA_EXTENSION"(EXT_NUMBER)
;