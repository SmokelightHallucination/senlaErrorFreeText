CREATE TABLE correction_tasks (
                                  id UUID PRIMARY KEY,
                                  original_text TEXT NOT NULL,
                                  corrected_text TEXT,
                                  language VARCHAR(10) NOT NULL,
                                  status VARCHAR(20) NOT NULL,
                                  error_message TEXT,
                                  created_at TIMESTAMP NOT NULL,
                                  updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_correction_tasks_status_created_at
    ON correction_tasks(status, created_at);