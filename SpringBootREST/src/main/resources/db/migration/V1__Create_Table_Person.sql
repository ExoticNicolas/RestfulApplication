
CREATE TABLE IF NOT EXISTS person (
  `id` bigint AUTO_INCREMENT,
  first_name varchar(80),
  last_name varchar(80),
  `address` varchar(100),
  gender varchar(6),
  PRIMARY KEY (id)
);