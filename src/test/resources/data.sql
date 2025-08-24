INSERT INTO plant (name, type, planting_date, health_status, is_annual, description, height, version, created_at, updated_at)
VALUES ('Tulip', 'FLOWER', '2024-03-01', 'HEALTHY', true, 'Test', 25, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO task (task_type, notes, task_date, status, plant_id, version, created_at, updated_at)
VALUES ('WATERING', 'Water me', '2025-03-01', 'OVERDUE', 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);