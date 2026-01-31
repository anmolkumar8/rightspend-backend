CREATE TABLE expenses (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  amount DOUBLE PRECISION NOT NULL,
  category VARCHAR(100),
  date DATE NOT NULL,
  user_id BIGINT NOT NULL,

  CONSTRAINT fk_expense_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE
);
