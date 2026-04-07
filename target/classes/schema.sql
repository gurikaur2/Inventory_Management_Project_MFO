CREATE TABLE IF NOT EXISTS INVENTORY (
    inventoryId BIGINT AUTO_INCREMENT PRIMARY KEY,
    itemName      VARCHAR(100),
    category      VARCHAR(100),
    price         FLOAT,
    quantity      INT,
    supplierId    BIGINT,
    warehouseId   BIGINT
);

CREATE TABLE IF NOT EXISTS SUPPLIER (
    supplierId    BIGINT PRIMARY KEY AUTO_INCREMENT,
    supplierName  VARCHAR(100),
    supplierEmail VARCHAR(500),
    supplierPhone VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS WAREHOUSE (
    warehouseId       BIGINT PRIMARY KEY AUTO_INCREMENT,
    warehouseName     VARCHAR(100),
    warehouseLocation VARCHAR(200),
    capacity          INT
);

CREATE TABLE IF NOT EXISTS CART_ITEM (
    cartItemId  BIGINT PRIMARY KEY AUTO_INCREMENT,
    inventoryId BIGINT NOT NULL,
    warehouseId BIGINT NOT NULL,
    quantity    INT    NOT NULL DEFAULT 1,
    UNIQUE (inventoryId, warehouseId)
);