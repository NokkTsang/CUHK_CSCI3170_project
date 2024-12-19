SELECT * 
FROM salesperson
ORDER BY salesperson.sExperience ASC;

SELECT * 
FROM salesperson
ORDER BY salesperson.sExperience DESC;

/* List all salespersons in ascending or descending order of years of experience */

SELECT s.sID AS ID, 
       s.sName AS `Name`, 
       s.sExperience AS `Years of Experience`, 
       t.`Number of Transaction`
FROM salesperson s
JOIN (
    SELECT sID, COUNT(tID) AS `Number of Transaction`
    FROM transaction
    WHERE sID IN (
        SELECT sID 
        FROM salesperson 
        WHERE sExperience >= 1 AND sExperience <= 3
    )
    GROUP BY sID
) t ON s.sID = t.sID
ORDER BY t.`Number of Transaction` DESC;

/* Count the number of transaction records of each salesperson within a given range on years of experience */

SELECT m.mID AS `Manufacturer ID`, m.mName AS `Manufacturer Name`, s.`Total Sales Value`
FROM manufacturer m
JOIN (
    SELECT mID, SUM(pPrice) AS `Total Sales Value`
    FROM part
    GROUP BY mID
)s ON m.mID = s.mID
ORDER BY s.`Total Sales Value` DESC;

/*  Sort and list the manufacturers in descending order of total sales value */

SELECT p.pID AS `Part ID`, p.pName AS `Part Name`, COUNT(t.tID) AS `Number of Transactions`
FROM part p
LEFT JOIN transaction t ON p.pID = t.pID
GROUP BY p.pID, p.pName
ORDER BY `Number of Transactions` DESC
LIMIT x;

/* Show the N most popular parts */
