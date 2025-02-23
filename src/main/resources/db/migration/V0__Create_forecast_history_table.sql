DROP TABLE IF EXISTS forecast_history;
CREATE TABLE IF NOT EXISTS forecast_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    -- Status code
    cod INT NOT NULL,

    -- Message if exists
    message VARCHAR(100),

    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- City Information
    city_name VARCHAR(100),

    -- Weather Information
    weather_id INT,
    weather_main VARCHAR(50),
    weather_description VARCHAR(100),

    -- Current temperature, min, max
    temperature DOUBLE,
    temperature_min DOUBLE,
    temperature_max DOUBLE,

    -- Vi: độ ẩm
    humidity INT,
    pressure INT,

    -- Wind
    wind_speed DOUBLE,
    wind_deg INT,
    wind_gust DOUBLE,

    -- Cloud
    cloud_percentage INT,
    sunrise TIMESTAMP,
    sunset TIMESTAMP
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

