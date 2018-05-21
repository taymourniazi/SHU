# CodesPractice

## Content

# Cassandra

./stopalldbs.sh
./cassandra/cassandraconfig.sh
sudo service cassandra start
nodetool status
thisNode=$(hostname -I)
echo $thisNode
cqlsh $thisNode


desc keyspaces;
CREATE KEYSPACE flights_ks WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
use flights_ks;
cqlsh:flights_ks>
CREATE TABLE flightdetails
(
   airline varchar PRIMARY KEY,
   kms int,
   flights int,
   hrs float,
   pass int
);
desc schema;
INSERT INTO flightdetails (Airline, Kms, Flights, Hrs, Pass) VALUES ('BA City Flyer', 300, 545, 686, 30031);
SELECT * FROM flightdetails;
INSERT INTO flightdetails (Airline, Kms, Flights, Hrs, Pass) VALUES ('Aurigny Air Services', 192, 1388, 887, 26585);
SELECT * FROM flightdetails WHERE airline = 'Aurigny Air Services';
SELECT * FROM flightdetails WHERE kms = 192;
CREATE INDEX IF NOT EXISTS idx_kms ON flightdetails (kms);
INSERT INTO flightdetails (Airline, Kms, Flights, Hrs, Pass) VALUES ('BMI Group', 192, 2435, 2922.7, 142804);
COPY flightdetails (airline, kms, flights, hrs, pass) FROM '/home/student/cassandra/flightDetails.csv';
UPDATE flightdetails 
    SET Kms = 1000, 
    Flights = 2000, 
    Hrs = 3000, 
    Pass = 4000
WHERE Airline = 'BA City Flyer';
