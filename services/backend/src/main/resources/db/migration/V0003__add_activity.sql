DROP TABLE IF EXISTS "public"."activity";
CREATE TABLE "public"."activity" (
  "id"              char(32) COLLATE "pg_catalog"."default"     NOT NULL,
  "name"            varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "description"     varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "start_date_time" timestamp(6)                                NOT NULL,
  "end_date_time"   timestamp(6)                                NOT NULL,
  "audit_status"    int2                                        NOT NULL,
  "created_at"      timestamp(6)                                NOT NULL,
  "created_by"      char(32) COLLATE "pg_catalog"."default"     NOT NULL,
  "updated_at"      timestamp(6),
  "updated_by"      char(32) COLLATE "pg_catalog"."default",
  "deleted"         bool                                        NOT NULL default false,
  "deleted_at"      timestamptz(6),
  "deleted_by"      char(32) COLLATE "pg_catalog"."default",
  "deleted_token"   varchar(255) COLLATE "pg_catalog"."default" NOT NULL default ''
);

ALTER TABLE "public"."activity"
  ADD CONSTRAINT "activity_pkey" PRIMARY KEY ("id");