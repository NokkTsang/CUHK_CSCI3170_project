-- search by part ascending

SELECT pID AS ID, pName AS Name, mName AS Manufacturer, cName AS Category, pAvailableQuantity AS Quantity, pWarrantyPeriod AS Warranty, pPrice AS Price
FROM part, manufacturer, category
WHERE pName LIKE '%[keyword]%'
AND part.mID = manufacturer.mID
AND part.cID = category.cID
ORDER BY pPrice ASC;

-- search by part descending

SELECT pID AS ID, pName AS Name, mName AS Manufacturer, cName AS Category, pAvailableQuantity AS Quantity, pWarrantyPeriod AS Warranty, pPrice AS Price
FROM part, manufacturer, category
WHERE pName LIKE '%[keyword]%'
AND part.mID = manufacturer.mID
AND part.cID = category.cID
ORDER BY pPrice DESC;

-- search by manufacturer ascending

SELECT pID AS ID, pName AS Name, mName AS Manufacturer, cName AS Category, pAvailableQuantity AS Quantity, pWarrantyPeriod AS Warranty, pPrice AS Price
FROM part, manufacturer, category
WHERE mName LIKE '%[keyword]%'
AND part.mID = manufacturer.mID
AND part.cID = category.cID
ORDER BY pPrice ASC;

-- search by manufacturer descending

SELECT pID AS ID, pName AS Name, mName AS Manufacturer, cName AS Category, pAvailableQuantity AS Quantity, pWarrantyPeriod AS Warranty, pPrice AS Price
FROM part, manufacturer, category
WHERE mName LIKE '%[keyword]%'
AND part.mID = manufacturer.mID 
AND part.cID = category.cID
ORDER BY pPrice DESC;

-- update [Part] table

UPDATE part
SET pAvailableQuantity = (quantity - 1) 
WHERE pID = [input part_id];

SELECT pName, pAvailableQuantity
FROM part
WHERE pID = [input part_id];

-- update [Transaction Records] table

INSERT INTO transaction(pID, sID, tDate)
VALUES(part_id, salesperson_id, ft.format(date));
