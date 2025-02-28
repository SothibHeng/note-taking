### 1. HOW TO RUN BACKEND (API)

Run the following command:

1. Build project
```
$ cd backend
$ mvn clean package
```
2. Run the postgresql container
```
$ docker compose up -d --build psql_db
```
3. Migrate database
```
$ chmod +x setup_db.sh
$ ./setup_db.sh
```
4. Run the note-service container
```
$ docker compose up -d --build note-service
```

### 2. HOW TO UI

Run the following command:

```
$ cd frontend
$ npm run dev
```
