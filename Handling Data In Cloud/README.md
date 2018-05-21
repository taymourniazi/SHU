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
  
  
  
cqlsh $thisNode  
CREATE KEYSPACE customers_ks WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 2 };  
use customers_ks;  
CREATE TABLE customers_table   
(  
   customerid int PRIMARY KEY,  
   name varchar,  
   address varchar  
);  
INSERT INTO customers_table (customerid, name, address) VALUES (1, 'Joe Bloggs', '12 Acacia Ave, Sheffield, S44 1PQ');  
INSERT INTO customers_table (customerid, name, address) VALUES (2, 'Mary Smith', '42 Furnival Ave, Sheffield, S1 9AB');  
desc schema;  
cqlsh $thisNode  
desc schema;  
quit  
sudo service cassandra stop  
cqlsh $thisNode  
desc schema;  
cat > cassandratimetest  
use users_ks;  
SELECT * FROM users_table;  
cat cassandratimetest  
cqlsh $thisNode < cassandratimetest  
time cqlsh $thisNode < cassandratimetest  
