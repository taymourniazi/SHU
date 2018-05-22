# CodesPractice

## Content

# Cassandra
### Configure Cassandra Cluster  
  
    
./stopalldbs.sh  
./cassandra/cassandraconfig.sh  
sudo service cassandra start  
nodetool status  
thisNode=$(hostname -I)  
echo $thisNode  
cqlsh $thisNode  
  
### Cassandra on a Single Node  
  
    
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
  
  
### Cassandra Cluster and Replication  
  
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
  
     
          
                   
                
# Neo4j
  
## Content
  
  
### Movies Graph database  
  
  
hostname -I  
./stopalldbs.sh  
./neo4j/setneo4jdatabase.sh  
sudo service neo4j start  
http://<Cloud Data VM IP Address>:7474/browser/  
    
MATCH (actor)-[:ACTED_IN]->(movie)   
RETURN actor, movie  
  
MATCH (actor:Person)-[:ACTED_IN]->(movie:Movie)  
RETURN actor.name AS Actor, collect(movie.title) AS Movies  
  
MATCH (actor)-[:ACTED_IN]->(movie)   
WHERE movie.title IN ['The Matrix']  
RETURN actor, movie  
  
MATCH (actor)-[:ACTED_IN]->(movie)   
WHERE movie.title IN ['The Matrix']   
OR movie.title IN ['The Matrix Reloaded']  
RETURN actor, movie  
  
// Add movie  
CREATE (BillnTed:Movie {title:'Bill and Teds Excellent Adventure', released:1989})  
  
// Add new persons  
CREATE (AlexW:Person {name:'Alex Winter', born:1965})  
CREATE (GeorgeC:Person {name:'George Carlin', born:1937})  
CREATE (Stephk:Person {name:'Stephen Herek', born:1958})  
  
// Create ACTED_IN & DIERECTED relationships  
CREATE   
  (AlexW)-[:ACTED_IN {roles:['Bill Preston']}]->(BillnTed),  
  (GeorgeC)-[:ACTED_IN {roles:['Rufus']}]->(BillnTed),  
  (Stephk)-[:DIRECTED]->(BillnTed)  
// Retrieve Person node for Keanu Reeves who already exists  
MATCH (p:Person {name:'Keanu Reeves'})   
  
// Retrieve the Movie node - just added  
MATCH (m:Movie {title:'Bill and Teds Excellent Adventure'})  
  
// Create an ACTED_IN relationship between Keanu Reeves & movie  
CREATE (p)-[:ACTED_IN {roles:['Ted Logan']}]->(m)  
sudo service neo4j stop  
    
    
       
### Northwind Example  
  
    
A)	MATCH (actor)-[:ACTED_IN]->(movie)   
WHERE actor.name IN ['Keanu Reeves']  
RETURN movie.title  
  
MATCH (actor)-[:ACTED_IN]->(movie)   
WHERE movie.title IN ['Johnny Mnemonic']   
RETURN actor, movie  
  
MATCH (cust:Customer)-[:PURCHASED]->(ord:Order)   
RETURN DISTINCT cust.companyName as Customers, collect(ord.orderID) AS Orders  
  
MATCH (cust:Customer)-[:PURCHASED]->(:Order)-[o:ORDERS]->(p:Product)  
RETURN  cust.companyName as Customers, collect(distinct p.productName) AS Products  
  
MATCH (cust:Customer)-[:PURCHASED]->(:Order)-[o:ORDERS]->(p:Product)  
WHERE cust.contactName = 'Roland Mendel'  
RETURN DISTINCT cust.contactName as CustomerName, SUM(o.quantity) AS TotalProductsPurchased  
    
