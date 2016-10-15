CREATE TABLE "APPVISAMANAGER"."UPAJJHAYA"
(
   ID_UPAJJHAYA int not null primary key
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
   MONASTERY int,
   UPAJJHAYA_NAME varchar(255)
)
;
ALTER TABLE "APPVISAMANAGER"."UPAJJHAYA"
ADD CONSTRAINT FKEHKLV2GBI61GHK12BVYOI9B29
FOREIGN KEY (MONASTERY)
REFERENCES "APPVISAMANAGER"."MONASTERY"(ID_MONASTERY)
;
CREATE INDEX SQL160927161614870 ON "APPVISAMANAGER"."UPAJJHAYA"(UPAJJHAYA_NAME)
;
CREATE UNIQUE INDEX SQL160922151519670 ON "APPVISAMANAGER"."UPAJJHAYA"(ID_UPAJJHAYA)
;
CREATE INDEX SQL160922151520870 ON "APPVISAMANAGER"."UPAJJHAYA"(MONASTERY)
;
