CREATE SEQUENCE IF NOT EXISTS job_seq;

CREATE TABLE IF NOT EXISTS jobs
(
  id        INTEGER PRIMARY KEY DEFAULT nextval('job_seq'),
  job_id    INTEGER      NOT NULL,
  device    VARCHAR(255) NOT NULL,
  user_name VARCHAR(255) NOT NULL,
  type      VARCHAR(10)  NOT NULL,
  amount    INTEGER      NOT NULL,
  time      TIMESTAMP    NOT NULL,
  CONSTRAINT job_id_device_idx UNIQUE (job_id, device)
);