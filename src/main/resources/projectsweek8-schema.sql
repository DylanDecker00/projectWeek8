-- Drop the tables in the correct order (only if they exist)
DROP TABLE IF EXISTS step;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS project_category;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS project;

-- Create the tables in the desired order
CREATE TABLE project (
  project_id INT AUTO_INCREMENT PRIMARY KEY,
  project_name VARCHAR(255) NOT NULL,
  estimated_hours INT,
  actual_hours INT,
  difficulty INT,
  notes TEXT
);

CREATE TABLE category (
  category_id INT AUTO_INCREMENT PRIMARY KEY,
  category_name VARCHAR(255) NOT NULL
);

CREATE TABLE project_category (
  project_id INT,
  category_id INT,
  PRIMARY KEY (project_id, category_id),
  FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE,
  FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE CASCADE
);

CREATE TABLE material (
  material_id INT AUTO_INCREMENT PRIMARY KEY,
  project_id INT,
  material_name VARCHAR(255) NOT NULL,
  num_required INT,
  cost DECIMAL(10, 2),
  FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE
);

CREATE TABLE step (
  step_id INT AUTO_INCREMENT PRIMARY KEY,
  project_id INT,
  step_text TEXT,
  step_order INT,
  FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE
);
