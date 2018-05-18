/*Fourth Question:                                                                               */
/*Which supplier has generated the least amount of Profit for the business in 2002              */



Designing Star schemas

 ![image](https://user-images.githubusercontent.com/30389323/40235976-6e0104be-5ac5-11e8-9283-5c178c278ab7.png)



/*creating dimensional time table   */

data dim_time(keep=order_id order_date Year month);
set order_fact;
Year=year(order_date);
Month=Month(order_date);
run;
Creating star schema in SAS:

 ![image](https://user-images.githubusercontent.com/30389323/40236000-8394be6a-5ac5-11e8-881a-1923af3981d5.png)


SAS Code generated in SAS:  

PROC SQL;  
   CREATE TABLE WORK.QUERY_FOR_ORDER_FACT AS   
   SELECT t2.Order_ID,   
          t2.Order_Date,   
          t2.Year,   
          t2.Month,   
          t1.Order_ID AS Order_ID1,   
          t1.Customer_ID,   
          t1.Order_Date AS Order_Date1,   
          t1.Supplier_ID,   
          t1.Product_ID,   
          t1.Quantity,   
          t3.Product_ID AS Product_ID1,   
          t3.Start_Date,   
          t3.End_Date,   
          t3.Unit_Cost_Price,   
          t3.Unit_Sales_Price,   
          t3.Profit,   
          t4.Supplier_Name,   
          t4.Street_ID,   
          t4.Supplier_Address,   
          t4.Sup_Street_Number,   
          t4.Country,   
          t4.supplier_id AS supplier_id1  
      FROM WORK.ORDER_FACT t1  
           LEFT JOIN WORK.DIM_TIME t2 ON (t1.Order_ID = t2.Order_ID)  
           LEFT JOIN WORK.PRICE_LIST_PROFIT t3 ON (t1.Product_ID = t3.Product_ID)  
           LEFT JOIN WORK.SUPPLIER t4 ON (t1.Supplier_ID = t4.supplier_id);  
QUIT;  
  
/* Creating Net Profit By Year for Supplier*/  
proc sql;   
create table final_q4 as   
    select distinct supplier_id,Year,Supplier_name, Supplier_Address, sum(Profit) as Total_Net_Profit   
label='Total Net Profit'  
        from WORK.QUERY_FOR_ORDER_FACT   
              where Year=2002  
            group by supplier_id, year;  
quit;  
  
  
/* Sorting  dataset to get Least Profited supplier on Top*/  
  
Proc sort data=final_q4 ;  
by   Total_Net_Profit Supplier_ID;  
run;  
  
  
/* Printing The Supplier with Least Profit from final dataset */  
  
proc print data=final_q4(obs=1);  
var Year Supplier_ID Supplier_name Supplier_Address Total_Net_Profit ;  
run;  
  

 
