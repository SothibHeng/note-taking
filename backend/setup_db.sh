#!/bin/bash

# Set the version based on user input
version="${version:-1}"

# PostgreSQL connection details
DB_USER="note-dev"
POSTGRES_CONTAINER="psql_db"

psql_script() {
  local script=$1
  echo "Running script: $script"

  docker exec -i "$POSTGRES_CONTAINER" psql -U "$DB_USER" -d postgres -c "$script;"

  if [ $? -eq 0 ]; then
    echo "This script \"$script\" run successfully."
  else
    echo "This script \"$script\" is failed to run."
  fi
}

# Function to run SQL file
migrate_db() {
  local sql_file=$1
  local db_name=$2

  echo "Running $sql_file on $db_name database..."

  # Copy the SQL file to the Docker container
  docker cp "$sql_file" "$POSTGRES_CONTAINER:/tmp/$(basename $sql_file)"

  # Execute the SQL file using psql inside the container
  docker exec "$POSTGRES_CONTAINER" psql -U "$DB_USER" -d "$db_name" -f "/tmp/$(basename $sql_file)"

  # Remove the SQL file from the container after execution
  docker exec "$POSTGRES_CONTAINER" rm "/tmp/$(basename $sql_file)"
}

# If statement to handle different versions
psql_script "CREATE DATABASE note_db"
migrate_db "./database/note.sql" "note_db"

echo "Migration completed."
