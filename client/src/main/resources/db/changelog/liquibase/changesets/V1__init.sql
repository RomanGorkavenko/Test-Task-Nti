CREATE TABLE "cursor"
(
    "id"   bigserial                      NOT NULL,
    "time" TIME(0) WITHOUT TIME ZONE NOT NULL,
    "x"    DOUBLE PRECISION               NOT NULL,
    "y"    DOUBLE PRECISION               NOT NULL
);
ALTER TABLE
    "cursor"
    ADD PRIMARY KEY ("id");