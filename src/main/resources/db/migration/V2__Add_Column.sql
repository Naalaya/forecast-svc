
ALTER TABLE forecast_history ADD COLUMN (Status VARCHAR(50));

ALTER TABLE forecast_history DROP COLUMN wind_gust;

